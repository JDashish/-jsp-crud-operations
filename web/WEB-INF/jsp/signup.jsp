<%-- 
    Document   : login
    Created on : 6 May, 2022, 5:12:52 PM
    Author     : Dell1
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="inc/header.jsp" %>

<main class="form-signin">
    <form method="post" onsubmit="return signupValidate()">
        <img class="mb-4" src="img/login_iocn.png" alt="" width="72" height="57">
        <h1 class="h3 mb-3 fw-normal">Create new account</h1>

        <div class="form-floating1">
            <label for="name">First name : </label>
            <input type="text" class="form-control firstName" name="fname" id="name" placeholder="First name">
          
        </div>
        <div class="form-floating1">
            <label for="lname">Last name</label>
            <input type="text" class="form-control lastName" name="lname" id="lname" placeholder="Last name">
            
        </div>
        <div class="form-floating1">
            <label for="floatingInput">Email address</label>
            <input type="email" class="form-control emailID" id="floatingInput" name="email" placeholder="name@example.com">
            
        </div>
        <div class="form-floating1">
            <label for="floatingPassword">Password</label>
            <input type="password" class="form-control signupPassword" id="floatingPassword" name="password" placeholder="Password">
            
        </div>
        <button class="w-100 btn btn-lg btn-primary" type="submit">Sign Up</button>
        
        <h6 class="mt-3">Have already account ? </h6>
    
        <a class="w-100 btn btn-lg btn-success mt-2" href="login">Sign In</a>
        
        <p class="mt-5 mb-3 text-muted">&copy; 2017–<%= new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()) %></p>
    </form>
</main>



<%@include file="inc/footer.jsp" %>