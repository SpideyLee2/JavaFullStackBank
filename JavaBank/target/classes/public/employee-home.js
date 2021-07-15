window.onload = function() {
	grabAccounts();
}

const url = "employeeViewAccounts";

function grabAccounts() {
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			// console.log(xhr.status);

			if(xhr.status == 200){
				removeAllAlerts();
				
				let accountList = JSON.parse(xhr.responseText);

				accountList.forEach(account => {
						addRow(account);
				});
			}
			else if (xhr.status == 407) {
				createAlert("A customer with that username does not exist.", AlertEnum.DANGER);
			}
		}
	}

	xhr.open("GET", url);
	xhr.send();
}

function addRow(account) {
	let table = document.getElementById("accountTableBody");
	
	let tableRow = document.createElement("tr");

	let accNameCell = document.createElement("th");
	let accBalanceCell = document.createElement("td");
	let accApprovedCell = document.createElement("td");
	
	accNameCell.innerHTML = account.name;
	accBalanceCell.innerHTML = account.balance;
	accApprovedCell.innerHTML = account.approved;

	tableRow.appendChild(accNameCell);
	tableRow.appendChild(accBalanceCell);
	tableRow.appendChild(accApprovedCell);
	
	table.appendChild(tableRow);
}