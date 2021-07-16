window.onload = function() {
    document.getElementById("amount").addEventListener("input", regulateAmount);
    document.getElementById("transferButton").addEventListener("click", putTransfer);
    grabAccounts();
}

const url = "accounts";
const badCharactersRegex = /[^0-9.]/;

let transferFromButton = null;
let transferToButton = null;

function grabAccounts() {
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){

			if(xhr.status == 200){
				let accountList = JSON.parse(xhr.responseText);

				accountList.forEach(account => {
						addSelection(account);
				});
			}
		}
	}

	xhr.open("GET", url);
	xhr.send();
}

function putTransfer(event) {
    let amountInput = document.getElementById("amount");
    let fromChildElements = transferFromButton.childNodes;
    let toChildElements = transferToButton.childNodes;

    if(transferFromButton == null || transferToButton == null) {
        createAlert("Please select both the sending and receiving accounts before trying to transfer money.", AlertEnum.WARNING);
        return;
    }
    if(transferFromButton.innerText == transferToButton.innerText) {
        createAlert("You cannot transfer money to the same account.", AlertEnum.DANGER);
        return;
    }
    if(amountInput.value == "" || amountInput.value < 0.01) {
        createAlert("Please enter an amount to transfer.", AlertEnum.WARNING);
        return;
    }
    if(parseFloat(fromChildElements[3].value) < parseFloat(amountInput.value)) {
        console.log(fromChildElements[3].value);
        console.log(amountInput.value);
        createAlert("You cannot transfer more than your balance.", AlertEnum.DANGER);
        return;
    }

    let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){

			if(xhr.status == 200){
                removeAllAlerts();
                resetDropdownMenus();
                grabAccounts();
				createAlert("Transfer was successful!", AlertEnum.SUCCESS);
			}
		}
	}

	xhr.open("PUT", "transfer");

    let bankAccounts = [
        {
            accountId: fromChildElements[0].value,
            usernameRef: fromChildElements[1].value,
            name: fromChildElements[2].value,
            balance: parseFloat(fromChildElements[3].value) - parseFloat(amountInput.value),
            approved: fromChildElements[4].value
        },
        {
            accountId: toChildElements[0].value,
            usernameRef: toChildElements[1].value,
            name: toChildElements[2].value,
            balance: parseFloat(toChildElements[3].value) + parseFloat(amountInput.value),
            approved: toChildElements[4].value
        }
    ]

	xhr.send(JSON.stringify(bankAccounts));
}

function addSelection(account) {
    let button1 = createButton(account, 1);
    let button2 = createButton(account, 2);
    document.getElementById("dropdownAccounts1").appendChild(button1);
    document.getElementById("dropdownAccounts2").appendChild(button2);
}

function createButton(account, menuNum) {
    let button = document.createElement("button");
    let accId = document.createElement("input");
    let accUsernameRef = document.createElement("input");
    let accName = document.createElement("input");
    let accBalance = document.createElement("input");
    let accApproved = document.createElement("input");

    accId.setAttribute("type", "hidden");
    accId.setAttribute("name", "accId");
    accId.setAttribute("value", account.accountId);

    accUsernameRef.setAttribute("type", "hidden");
    accUsernameRef.setAttribute("name", "accUsernameRef");
    accUsernameRef.setAttribute("value", account.usernameRef);

    accName.setAttribute("type", "hidden");
    accName.setAttribute("name", "accName");
    accName.setAttribute("value", account.name);

    accBalance.setAttribute("type", "hidden");
    accBalance.setAttribute("name", "accBalance");
    accBalance.setAttribute("value", parseFloat(account.balance));

    accApproved.setAttribute("type", "hidden");
    accApproved.setAttribute("name", "accApproved");
    accApproved.setAttribute("value", account.approved);

    button.appendChild(accId);
    button.appendChild(accUsernameRef);
    button.appendChild(accName);
    button.appendChild(accBalance);
    button.appendChild(accApproved);

    button.setAttribute("type", "button");
    button.setAttribute("class", `dropdown-item menu${menuNum} ${account.id}`);
    button.addEventListener("click", changeDropdownTitle);
    button.append(account.name);

    if(account.approved == false) {
        button.disabled = true;
    }

    return button;
}

function changeDropdownTitle(event) {
    let button = event.srcElement;
    let accName = button.innerText;

    if(button.className.includes("menu1")) {
        document.getElementById("dropdownMenu1").innerText = accName;
        document.getElementById("menu1Balance").innerText = `bal: $${button.childNodes[3].value}`;
        transferFromButton = button;
    }
    else {
        document.getElementById("dropdownMenu2").innerText = accName;
        document.getElementById("menu2Balance").innerText = `bal: $${button.childNodes[3].value}`;
        transferToButton = button;
    }
}

function regulateAmount(event) {
	let input = event.srcElement;
	
	input.value = revertInput(input, badCharactersRegex);
}

function resetDropdownMenus() {
    let dropdownMenu1 = document.getElementById("dropdownMenu1");
    let dropdownMenu2 = document.getElementById("dropdownMenu2");

    removeAllChildNodes(document.getElementById("dropdownAccounts1"));
    removeAllChildNodes(document.getElementById("dropdownAccounts2"));
    removeAllChildNodes(dropdownMenu1);
    removeAllChildNodes(dropdownMenu2);
    removeAllChildNodes(document.getElementById("menu1Balance"));
    removeAllChildNodes(document.getElementById("menu2Balance"));

    dropdownMenu1.innerText = "Select an account";
    dropdownMenu2.innerText = "Select an account";
    document.getElementById("amount").value = "";
}