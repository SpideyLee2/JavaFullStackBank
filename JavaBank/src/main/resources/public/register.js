window.onload = function() {
    document.getElementById("registerButton").addEventListener("click", postNewAccount);
    document.getElementById("firstName").addEventListener("input", checkInput);
    document.getElementById("lastName").addEventListener("input", checkInput);
    document.getElementById("username").addEventListener("input", checkInput);
    document.getElementById("password").addEventListener("input", checkInput);
}

const nameRegex = /[^A-Za-z-]/;
const unameOrPwordRegex = /[^A-Za-z0-9 ]/;
const url = "register";

function postNewAccount(event) {
    let firstNameElement = document.getElementById("firstName");
    let lastNameElement = document.getElementById("lastName");
    let usernameElement = document.getElementById("username");
    let passwordElement = document.getElementById("password");

    if(firstNameElement.value.length == 0 || lastNameElement.value.length == 0 || usernameElement.value.length == 0 || passwordElement.value.length == 0) {
        createAlert("Fields must contain at least 1 character!", AlertEnum.DANGER);
        return;
    }

	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){

			if(xhr.status == 200){
                //Don't need to do anything here
			}
            else if (xhr.status == 422){
                console.log("appending warning alert");
                createAlert("Username already exists.", AlertEnum.WARNING);
            }
		}
	}

	xhr.open("POST", url);

    let user = {
        username: usernameElement.value,
        password: passwordElement.value,
        firstName: firstNameElement.value,
        lastName: lastNameElement.value,
        employee: false
    };

	xhr.send(JSON.stringify(user));
}

function checkInput(event) {
    let input = event.srcElement;
    let inputClass = event.srcElement.className;

    if (String(inputClass).includes("nameRegex")) {
        input.value = revertInput(input, nameRegex);
    }
    else if (String(inputClass).includes("unameOrPwordRegex")) {
        input.value = revertInput(input, unameOrPwordRegex);
    }
}