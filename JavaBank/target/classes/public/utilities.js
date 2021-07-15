let AlertEnum = {
    SUCCESS: {text: "success", flashColor: "green"},
    DANGER: {text: "danger", flashColor: "red"},
    WARNING: {text: "warning", flashColor: "yellow"},
    INFO: {text: "info", flashColor: "cyan"}
};

function regulateAmount(event) {
	// console.log("Amount value changed!")
	let amountInput = event.srcElement;
	let amount = amountInput.value;
	amountInput.value = amount;
	// amountInput.value = amount.replace("+", "");
	// amountInput.value = amount.replace("-", "");
}

function clearAccounts() {
	let tableBody = document.getElementById("accountTableBody");
	while(tableBody.firstChild) {
		tableBody.removeChild(tableBody.firstChild);
	}
}

function classRegexMatcher(buttonClasses) {
	let classNamePattern = /^[0-9]+$/;

	for(let i = 0; i < buttonClasses.length; i++) {
		if(buttonClasses[i].match(classNamePattern)){
			return buttonClasses[i];
		}
	}
	console.log("classMatcher couldn't find class that matched pattern. returning null!");
	return null;
}

function createAlert(text, alertEnumValue) {
    let alert = document.getElementById(`${alertEnumValue.text}Alert`);
    if(alert == null) {
        let bodyMainContent = document.getElementsByClassName("bodyMainContent")[0];
		let alertDiv = document.createElement("div");
		let errorText = document.createElement("b");
		let dismissButton = document.createElement("button");
		let dismissSpan = document.createElement("span");

        alertDiv.setAttribute("id", `${alertEnumValue.text}Alert`);
		alertDiv.setAttribute("class", `alert alert-${alertEnumValue.text} alert-dismissible fade show`);
		alertDiv.setAttribute("role", "alert");
		errorText.append(`${alertEnumValue.text}: `.toUpperCase());
		alertDiv.append(errorText);
		alertDiv.append(text);

        dismissButton.setAttribute("type", "button");
		dismissButton.setAttribute("class", "close");
		dismissButton.setAttribute("data-dismiss", "alert");
		dismissButton.setAttribute("aria-label", "Close");

        dismissSpan.setAttribute("aria-hidden", "true");
		dismissSpan.append("X");

        dismissButton.appendChild(dismissSpan);
		alertDiv.appendChild(dismissButton);
		bodyMainContent.prepend(alertDiv);
		alert = alertDiv;
    }
    alertFlash(dangerAlert, alertEnumValue.flashColor)
}

function alertFlash(alert, flashColor) {
    alert.animate(
		[{color: flashColor, offset: 0}],
		{duration: 2000, iterations: 1}
	);
}

function removeAllAlerts() {
    let alerts = document.getElementsByClassName("alert");
    for(let i = 0; i < alerts.length; i++) {
        alerts[i].parentNode.removeChild(alerts[i]);
    }
}