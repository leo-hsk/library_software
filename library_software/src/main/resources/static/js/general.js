// Delete alerts

function deleteUserConfirm() {
	console.log("test");
	return confirm('Do you really want to delete your Account?');
}


// Copy password to hidden field
function copyPassword() {
var box1 = document.getElementById('password');
var box2 = document.getElementById('pw');
box2.value = box1.value;
};