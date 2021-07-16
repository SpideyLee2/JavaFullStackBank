window.onload = function() {
	grabAccounts();
};

const url = "approvals";
const classNameRegex = /^[0-9]+$/;

function grabAccounts() {
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){

			if(xhr.status == 200){
                // console.log(xhr.responseText);
				let accountList = JSON.parse(xhr.responseText);

				accountList.forEach(account => {
						addRow(account);
				});

                removeAllAlerts();
			}
            else if (xhr.status == 407) {
                console.log("hello there!");
                createAlert("There are no more accounts left to approve. Your job is done!", AlertEnum.SUCCESS);
            }
		}
	}

	xhr.open("GET", url);
	xhr.send();
}

function addRow(account) {
	let tableBody = document.getElementById("accountTableBody");
	
	let tableRow = document.createElement("tr");

	let customerNameCell = document.createElement("td");
	let accNameCell = document.createElement("td");
	let accBalanceCell = document.createElement("td");

	let approveRejectCell = document.createElement("td");
	let approveButton = document.createElement("button");
	let rejectButton = document.createElement("button");
	
    customerNameCell.innerHTML = account.usernameRef;
	accNameCell.innerHTML = account.name;
	accBalanceCell.innerHTML = account.balance;

    customerNameCell.setAttribute("class", `${account.accountId}`);
	accNameCell.setAttribute("class", `${account.accountId}`);
	accBalanceCell.setAttribute("class", `${account.accountId}`);

	approveButton.setAttribute("type", "button");
	approveButton.setAttribute("class", `btn btn-success ${account.accountId}`);
	approveButton.append("Approve");
	approveButton.addEventListener("click", handleApproval);

	rejectButton.setAttribute("type", "button");
	rejectButton.setAttribute("class", `btn btn-danger ml-1 ${account.accountId}`);
	rejectButton.append("Reject");
	rejectButton.addEventListener("click", handleApproval);

	approveRejectCell.appendChild(approveButton);
	approveRejectCell.appendChild(rejectButton);

    tableRow.appendChild(customerNameCell);
	tableRow.appendChild(accNameCell);
	tableRow.appendChild(accBalanceCell);
	tableRow.appendChild(approveRejectCell);
	
	tableBody.appendChild(tableRow);
}

function handleApproval(event) {
	let buttonClasses = event.srcElement.classList;
	let className = regexArrayMatcher(buttonClasses, classNameRegex);

	if(className != null) {
		let transactionElements = document.getElementsByClassName(className);

        let xhr = new XMLHttpRequest();

        xhr.onreadystatechange = function(){
            if(xhr.readyState == 4){
    
                if(xhr.status == 200){
                    clearAccounts();
                    grabAccounts();
                }
            }
        }
    
        if(event.srcElement.innerText == "Approve") {
            xhr.open("PUT", url);
        }
        else {
            xhr.open("DELETE", url);
        }
    
        let bankAcc = {
            accountId: className,
            usernameRef: transactionElements[0].innerText,
            name: transactionElements[1].innerText,
            balance: transactionElements[2].innerText,
            approved: true
        }
    
        xhr.send(JSON.stringify(bankAcc));
	}
}