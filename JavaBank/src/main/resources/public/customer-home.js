window.onload = function() {
	grabAccounts();
};

const url = "accounts";

function grabAccounts() {
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			// console.log(xhr.status);

			if(xhr.status == 200){
				// console.log(xhr.responseText);

				let accountList = JSON.parse(xhr.responseText);

				// console.log(accountList);

				accountList.forEach(account => {
						addRow(account);
				});
			}
		}
	}

	xhr.open("GET", url);
	xhr.send();
}

function putBalance(event) {
	// console.log(event);
	let buttonClasses = event.srcElement.classList;
	let className = classRegexMatcher(buttonClasses);
	if(className === null) {
		console.log("Something went wrong with the regex classMatcher function. className is null!");
	}
	else {
		let transactionElements = document.getElementsByClassName(className);
		// console.log(transactionElements);
		let accBalance = transactionElements[1].innerText;
		let amount = transactionElements[3].value;

		// User clicked button without entering an amount
		if(amount == ""){
			return;
		}
		else {
			console.log(amount);

			let newBalance = 0;
			if(event.srcElement.innerText == "Deposit") {
				newBalance = parseFloat(accBalance) + parseFloat(amount);
			}
			else {
				newBalance = parseFloat(accBalance) - parseFloat(amount);
				if (newBalance < 0) {
					createAlert("You cannot withdraw more than your balance.", AlertEnum.DANGER);
					return;
				}
			}
	
			let xhr = new XMLHttpRequest();
	
			xhr.onreadystatechange = function(){
				if(xhr.readyState == 4){
					console.log(xhr.status);
	
					if(xhr.status == 200){
						removeAllAlerts();

						clearAccounts();
						grabAccounts();
					}
				}
			}
	
			xhr.open("PUT", url);
	
			let bankAcc = {
				accountId: className,
				usernameRef: null,
				name: transactionElements[0].innerText,
				balance: newBalance,
				approved: transactionElements[2].innerText
			}
	
			xhr.send(JSON.stringify(bankAcc));
		}
	}
}

function addRow(account) {
	let tableBody = document.getElementById("accountTableBody");
	
	let tableRow = document.createElement("tr");

	let accNameCell = document.createElement("th");
	let accBalanceCell = document.createElement("td");
	let accApprovedCell = document.createElement("td");

	let depositWithdrawCell = document.createElement("td");
	let label = document.createElement("label");
	let divInputGroup = document.createElement("div");
	let divInputGroupPrepend = document.createElement("div");
	let spanText = document.createElement("span");
	let amountInput = document.createElement("input");
	let depositButton = document.createElement("button");
	let withdrawButton = document.createElement("button");
	
	accNameCell.innerHTML = account.name;
	accBalanceCell.innerHTML = account.balance;
	accApprovedCell.innerHTML = account.approved;

	accNameCell.setAttribute("class", `${account.accountId}`);
	accBalanceCell.setAttribute("class", `${account.accountId}`);
	accApprovedCell.setAttribute("class", `${account.accountId}`);

	label.setAttribute("class", "moneyInputLabel")

	divInputGroup.setAttribute("class", "input-group mb-3");
	divInputGroupPrepend.setAttribute("class", "input-group-prepend");
	
	spanText.setAttribute("class", "input-group-text");
	spanText.setAttribute("id", "inputGroup-sizing-sm");
	spanText.append("$");

	if(accApprovedCell.innerText == "false") {
		amountInput.disabled = true;
		depositButton.disabled = true;
		withdrawButton.disabled = true;
	}

	amountInput.setAttribute("type", "number");
	amountInput.setAttribute("class", `form-control ${account.accountId}`);
	amountInput.setAttribute("placeholder", "Amount");
	amountInput.setAttribute("min", "10");
	amountInput.setAttribute("max", accBalanceCell.value);
	amountInput.setAttribute("aria-label", "Amount (to the nearest penny)");
	amountInput.addEventListener("input", regulateAmount)

	depositButton.setAttribute("type", "button");
	depositButton.setAttribute("class", `btn btn-primary ${account.accountId}`);
	depositButton.append("Deposit");
	depositButton.addEventListener("click", putBalance);

	withdrawButton.setAttribute("type", "button");
	withdrawButton.setAttribute("class", `btn btn-primary ml-1 ${account.accountId}`);
	withdrawButton.append("Withdraw");
	withdrawButton.addEventListener("click", putBalance);

	divInputGroupPrepend.appendChild(spanText);
	divInputGroup.appendChild(divInputGroupPrepend);
	divInputGroup.appendChild(amountInput);

	label.appendChild(divInputGroup);

	depositWithdrawCell.appendChild(label);
	depositWithdrawCell.appendChild(depositButton);
	depositWithdrawCell.appendChild(withdrawButton);

	tableRow.appendChild(accNameCell);
	tableRow.appendChild(accBalanceCell);
	tableRow.appendChild(accApprovedCell);
	tableRow.appendChild(depositWithdrawCell);
	
	tableBody.appendChild(tableRow);
}