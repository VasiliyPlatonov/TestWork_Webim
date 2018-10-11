'use strict';

// popover
function removeShownClass() {
    var personOptions = this.parentNode.parentNode.getElementsByClassName("person-list__options")[0];
    personOptions.classList.remove("shown");
}

function addShownClass() {
    var personOptions = this.parentNode.parentNode.getElementsByClassName("person-list__options")[0];
    personOptions.classList.add("shown")
}

var personListIconOptions = document.getElementsByClassName("person-list__icon-option");
for (var i = 0; i < personListIconOptions.length; i++) {
    personListIconOptions[i].addEventListener("mouseover", addShownClass);
    personListIconOptions[i].addEventListener("mouseout", removeShownClass);
}

var personListOptions = document.getElementsByClassName("person-list__options");
for (var i = 0; i < personListOptions.length; i++) {
    personListOptions[i].addEventListener("mouseover", addShownClass);
    personListOptions[i].addEventListener("mouseout", removeShownClass);
}



