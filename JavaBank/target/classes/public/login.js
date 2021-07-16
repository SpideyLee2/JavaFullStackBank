const unameOrPwordRegex = /[^A-Za-z0-9 ]/

window.onload = function() {
    document.getElementById("username").addEventListener("input", checkInput);
    document.getElementById("password").addEventListener("input", checkInput);
}

function checkInput(event) {
    let input = event.srcElement;

    input.value = revertInput(input, unameOrPwordRegex);
}