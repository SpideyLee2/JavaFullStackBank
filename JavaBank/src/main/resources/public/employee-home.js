window.onload = function() {
    document.getElementById(searchAccountsButton).addEventListener("click", grabAccounts);
}

function grabAccounts() {
	const url = "customerAccounts";

	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			console.log(xhr.status);
			
			if(xhr.status == 200){
				console.log("Retrieved customer's accounts:");
				console.log(xhr.ResponseText);
				
				let accountList = JSON.parse(xhr.ResponseText);
				
				console.log(accountList);
			
				accountList.forEach(element => {
						addRow(element);
				});
			}
		}
	}

	xhr.open("GET", url);

	xhr.onload = function() {
		console.log(xhr.responseText);
	}
	
	xhr.send();
}

function addRow(account) {
	let table = document.getElementById("accountSearchTableBody");
	
	let tableRow = document.createElement("tr");

	let accNameCell = document.createElement("th");
	let accBalanceCell = document.createElement("td");
	let accApprovedCell = document.createElement("td");
	
	accNameCol.innerHTML = account.name;
	accBalanceCol.innerHTML = account.balance;
	accApprovedCol.innerHTML = account.approved;

	tableRow.appendChild(accNameCell);
	tableRow.appendChild(accBalanceCell);
	tableRow.appendChild(accApprovedCell);
	
	table.appendChild(tableRow);
}