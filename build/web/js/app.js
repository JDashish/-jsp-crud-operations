/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function loginValidate() {
    var email = $(".loginEmail").val();
    var pass = $(".loginPass").val();
    var error = 0;
    if (email.length === 0) {
        $(".loginEmailError").show();
        $(".loginEmailError").html("Enter some email field!");
        error = 1;
    }else{
        $(".loginEmailError").hide();
    }
    if (IsEmail(email) === false) {
        $(".loginEmailError").show();
        $(".loginEmailError").html("Enter correct email-id!");
        error = 1;
    }else{
        $(".loginEmailError").hide();
    }
    if (pass.length === 0) {
        $(".loginPassError").show();
        $(".loginPassError").html("Enter Password!");
        error = 1;
    }else{
        $(".loginPassError").hide();
    }

    if (error === 0) {
        $(".loginEmailError").hide();
        $(".loginPassError").hide();
        return true;
    }

    return false;
}

function signupValidate() {
    var firstName = $(".firstName").val();
    var lastName = $(".lastName").val();
    var emailID = $(".emailID").val();
    var signupPassword = $(".signupPassword").val();

    if (firstName.length === 0) {
        toast("Enter First Name!");
        return false;
    }
    if (lastName.length === 0) {
        toast("Enter Last Name!");
        return false;
    }
    if (emailID.length === 0) {
        toast("Enter some email field!");
        return false;
    }
    if (IsEmail(emailID) === false) {
        toast("Enter correct email-id!");
        return false;
    }
    if (signupPassword.length === 0) {
        toast("Enter Password!");
        return false;
    }
    return true;

}
