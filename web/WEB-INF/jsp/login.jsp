<%-- 
    Document   : login
    Created on : 6 May, 2022, 5:12:52 PM
    Author     : Dell1
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="inc/header.jsp" %>

<main class="form-signin ">
    <form method="post" onsubmit="return loginValidate()">
        <div class="text-center">
            <img class="mb-4" src="img/login_iocn.png" alt="" width="72" height="57">
            <h1 class="h3 mb-3 fw-normal">Please sign in</h1>
        </div>
        

        <div class="form-floating1">
            <label for="floatingInput">Email address</label>
            <input type="email" class="form-control loginEmail" id="floatingInput" name="email" placeholder="name@example.com">
            <span class="text-danger loginEmailError" style="display: none"></span>
        </div>
        <div class="form-floating1">
            <label for="floatingPassword">Password</label>
            <input type="password" class="form-control loginPass" id="floatingPassword" name="password" placeholder="Password">
            <span class="text-danger loginPassError" style="display: none">ahdgsgadhgh</span>
        </div>
        
        
        <button class="w-100 btn btn-lg btn-primary mt-2" type="submit">Sign in</button>
        
        <h6 class="mt-3 text-center">Create account ? </h6>
    
        <a class="w-100 btn btn-lg btn-success mt-2" href="signup">Sign Up</a>
        <p class="mt-5 mb-3 text-muted text-center">&copy; 2017 - <%= new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()) %></p>
    </form>
</main>



<%@include file="inc/footer.jsp" %>