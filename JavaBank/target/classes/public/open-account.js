window.onload = function() {
    document.getElementById("button").addEventListener("click", postAccount);
    document.getElementById("balance").addEventListener("input", regulateAmount);
    document.getElementById("name").addEventListener("input", regulateAccountName);
    grabAccounts();
}

const badCharactersRegex = /[^0-9.]/;
const accountNameRegex = /[^A-Za-z0-9 ]/;
const url = "accounts";

function grabAccounts() {
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){

			if(xhr.status == 200){
				let accountList = JSON.parse(xhr.responseText);

				accountList.forEach(account => {
						addHiddenNameInput(account);
				});
			}
		}
	}

	xhr.open("GET", url);
	xhr.send();
}

function postAccount(event) {
    let accNames = (document.getElementById("hiddenInputs")).childNodes;
    let desiredName = document.getElementById("name");
    let startingBalance = document.getElementById("balance");
    for(let i = 0; i < accNames.length; i++) {
        if(accNames[i].value == desiredName.value) {
            createAlert("You cannot have two accounts with the same name.", AlertEnum.DANGER);
            return;
        }
    }

    let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){

			if(xhr.status == 200){
                removeAllAlerts();
                grabAccounts();
                resetInputs();
                createAlert("Your new account has been opened!", AlertEnum.SUCCESS);
			}
		}
	}

    xhr.open("POST", url);

    let acc = {
        accountId: 0,
        usernameRef: null,
        name: desiredName.value,
        balance: startingBalance.value,
        approved: false,
    }

	xhr.send(JSON.stringify(acc));
}

function addHiddenNameInput(account) {
    let openAccountDiv = document.getElementById("hiddenInputs");
    let hiddenNameInput = document.createElement("input");
    
    hiddenNameInput.setAttribute("type", "hidden");
    hiddenNameInput.setAttribute("name", "name");
    hiddenNameInput.setAttribute("value", account.name);

    openAccountDiv.appendChild(hiddenNameInput);
}

function regulateAmount(event) {
	let input = event.srcElement;
	input.value = revertInput(input, badCharactersRegex);
}

function regulateAccountName(event) {
    let input = event.srcElement;
	input.value = revertInput(input, accountNameRegex);
}

function resetInputs() {
    document.getElementById("name").value = "";
    document.getElementById("balance").value = "";
    removeAllChildNodes(document.getElementById("hiddenInputs"));
}