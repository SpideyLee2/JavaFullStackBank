window.onload = function() {
	grabAccounts();
};

function grabAccounts() {
	const url = "accounts";

	let xhr = new XMLHttpRequest();

	xhr.open("GET", url);

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

	xhr.onload = function() {
		console.log(xhr.responseText);
	}

	xhr.send();
}

function addRow(account) {
	let table = document.getElementById("accountTableBody");
	
	let tableRow = document.createElement("tr");

	let accNameCell = document.createElement("th");
	let accBalanceCell = document.createElement("td");
	let accApprovedCell = document.createElement("td");

	let depoistWithdrawCell = document.createElement("td");
	let label = document.createElement("label");
	let amountInput = document.createElement("input");
	let depositButton = document.createElement("button");
	let withdrawButton = document.createElement("button");
	
	accNameCol.innerHTML = account.name;
	accBalanceCol.innerHTML = account.balance;
	accApprovedCol.innerHTML = account.approved;

	label.setAttribute("class", "moneyInputLabel");

	amountInput.setAttribute("type", "number");
	amountInput.setAttribute("placeholder", "Enter amount (min 10, max 100,000)");
	amountInput.setAttribute("min", "10");
	amountInput.setAttribute("max", "100000");
	amountInput.setAttribute("pattern", "^\\d+(?:\\.\\d{1,2})?$");

	depositButton.setAttribute("type", "button");
	depositButton.setAttribute("class", "btn btn-primary");

	withdrawButton.setAttribute("type", "button");
	withdrawButton.setAttribute("class", "btn btn-primary");

	label.appendChild("$");
	label.appendChild(amountInput);
	label.appendChild(depositButton);
	label.appendChild(withdrawButton);
	depoistWithdrawCell.appendChild(label);

	tableRow.appendChild(accNameCell);
	tableRow.appendChild(accBalanceCell);
	tableRow.appendChild(accApprovedCell);
	tableRow.appendChild(depositWithdrawCell);
	
	table.appendChild(tableRow);
}

function deposit() {

	clearAccounts();
	grabAccounts();
}

function withdraw() {

	clearAccounts();
	grabAccounts();
}

function clearAccounts() {

}