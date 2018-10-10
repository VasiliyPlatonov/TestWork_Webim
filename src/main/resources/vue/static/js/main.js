import Vue from 'vue'
import VueResource from 'vue-resource'
import App from './pages/App.vue'

Vue.use(VueResource);

var app = new Vue({
    el: '#app',
    render: a => a(App)
});

import '../css/styles.css';



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

