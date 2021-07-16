let AlertEnum = {
    SUCCESS: {text: "success", flashColor: "greenyellow"},
    DANGER: {text: "danger", flashColor: "red"},
    WARNING: {text: "warning", flashColor: "goldenrod"},
    INFO: {text: "info", flashColor: "cyan"}
};

function clearAccounts() {
	let tableBody = document.getElementById("accountTableBody");
	removeAllChildNodes(tableBody);
}

function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

function regexMatcher(text, regex) {
	return String(text).match(regex);
}

function regexArrayMatcher(textArray, regex) {
	for(let i = 0; i < textArray.length; i++) {
		if (String(textArray[i]).match(regex)) {
			return textArray[i];
		}
	}
	return null;
}

function revertInput(input, regex) {
	let inputValueStore = input.value;
    let index = inputValueStore.search(regex);
	
    while(index != -1) {
		console.log("input value bad");
        console.log(`Removing ${input.value.charAt(index)} from ${input.value}`);
		let substringStart = inputValueStore.substring(0, index);
		let substringEnd = inputValueStore.substring(index + 1);
		if (substringEnd == undefined) {
			inputValueStore = substringStart;
		}
		else {
			inputValueStore = substringStart + substringEnd;
		}
        index = inputValueStore.search(regex);
    }

	return inputValueStore;
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
	else if (!alert.innerText.includes(text)) {
		removeAllChildNodes(alert);

		let errorTextAgain = document.createElement("b");
		errorTextAgain.append(`${alertEnumValue.text}: `.toUpperCase());

		alert.append(errorTextAgain);
		alert.append(text);
	}
    alertFlash(alert, alertEnumValue.flashColor)
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