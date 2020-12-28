// Delete alerts

function deleteUserConfirm() {
	return confirm('Do you really want to delete your Account?');
}

function deleteBookConfirm() {
	return confirm('Do you really want to delete the book?');
}

function deleteUserConfirmAdmin() {
	return confirm('Do you really want to delete this user?');
}


// Copy password to hidden field
function copyPassword() {
var box1 = document.getElementById('password');
var box2 = document.getElementById('pw');
box2.value = box1.value;
};