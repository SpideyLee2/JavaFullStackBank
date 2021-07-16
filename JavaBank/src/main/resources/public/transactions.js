window.onload = function() {
    grabLogs();
}

const url = "transactions"

function grabLogs() {
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){

			if(xhr.status == 200){
				let transactionsList = JSON.parse(xhr.responseText);

				transactionsList.forEach(transaction => {
                    addLine(transaction);
				});

                removeAllAlerts();
			}
            else if (xhr.status == 407) {
                createAlert("The transaction log is empty! Very suspicious...", AlertEnum.WARNING);
            }
		}
	}

	xhr.open("GET", url);
	xhr.send();
}

function addLine(transaction) {
	let container = document.getElementById("log");
	
	let logElement = document.createElement("h4");

    logElement.append(transaction);
    container.appendChild(logElement);
}