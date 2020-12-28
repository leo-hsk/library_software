// Delete alerts

function deleteUserConfirm() {
	return confirm('Do you really want to delete your Account?');
}

function deleteUserConfirmAdmin() {
	return confirm('Do you really want to delete this user?');
}

function deleteBookConfirm(id) {
	var num = id.replace(/\D/g, "");
	var id_lend = "lend" + num;
	var element = document.getElementById(id_lend);
	if (element.innerHTML === "No") {
		return confirm('Do you really want to delete the book?');
	} else {
		alert("Can not delete book, because book is not returned yet.");
		return false;
	}

	
}

// Copy password to hidden field
function copyPassword() {
var box1 = document.getElementById('password');
var box2 = document.getElementById('pw');
box2.value = box1.value;
};

function validateBookAddForm() {
var isbn = document.forms["bookAddForm"]["isbn13"].value;
  if (isbn.length !== 13) {
   alert("Given ISBN 13 is not valid or empty.");
   return false;
 }

var dateString = document.forms["bookAddForm"]["publicationDate"].value;
var regEx = /^\d{4}-\d{2}-\d{2}$/;
  if(!dateString.match(regEx)) {
   alert("Date needs to be in format 'YYYY-MM-DD'.");
   return false;
 }
}