<%-- 
    Document   : login
    Created on : 6 May, 2022, 5:12:52 PM
    Author     : Dell1
--%>
<%@page import="Helper.Hash"%>
<%@page import="Model.User"%>
<%@page import="Dao.UserDaoImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="inc/header_1.jsp" %>
<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
<link rel="stylesheet" href="css/home.css"/>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>

<body class="bg-light">

    <%@include file="inc/nav.jsp" %>

    <div class="nav-scroller bg-body shadow-sm">
        <nav class="nav nav-underline" aria-label="Secondary navigation">
            <a class="nav-link active" aria-current="page" href="#">Dashboard</a>
            <a class="nav-link" href="#">
                Friends
                <span class="badge bg-light text-dark rounded-pill align-text-bottom">27</span>
            </a>
            <a class="nav-link" href="#">Explore</a>
            <a class="nav-link" href="#">Suggestions</a>
            <a class="nav-link" href="#">Link</a>
            <a class="nav-link" href="#">Link</a>
            <a class="nav-link" href="#">Link</a>
            <a class="nav-link" href="#">Link</a>
            <a class="nav-link" href="#">Link</a>
        </nav>
    </div>

    <main class="container">
        <div class="d-flex align-items-center p-3 my-3 text-white bg-purple rounded shadow-sm">
            <h3 class='far fa-user-circle pr-3' style='font-size:38px;color:white'></h3>
            <div class="lh-1 ">
                <h1 class="h6 mb-0 text-white lh-1">Bootstrap</h1>
                <small>Since 2011</small>
            </div>
        </div>

        <div class="my-3 p-3 bg-body rounded shadow-sm">
            <h6 class="border-bottom pb-2 mb-0">User List</h6>
            <div class="container mt-3">

                <%
                    UserDaoImpl userDaoImpl = new UserDaoImpl();

                %>
                <table class="table">
                    <thead>
                        <tr>
                            <th>SN. </th>
                            <th>Firstname</th>
                            <th>Lastname</th>
                            <th>Email</th>
                            <th>Age</th>
                            <th>Created At </th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%                            int count = 0;
                            for (User user : userDaoImpl.getUserList()) {

                        %>
                        <tr>

                            <td><%= ++count%></td>
                            <td><%= user.getFirst_name()%></td>
                            <td><%= user.getLast_name()%></td>
                            <td><%= user.getEmail()%></td>
                            <td><%= user.getAge()%></td>
                            <td><%= user.getCreated_at()%></td>
                            <td>
                                <a class="deleteUser text-purple cursor-pointer" id="<%= user.getId()%>">
                                    <i class="fas fa-trash-alt"></i></a>&nbsp;&nbsp;&nbsp;
                                <a class="updateUserModal text-purple cursor-pointer" id="<%= user.getId()%>"
                                   fname="<%= user.getFirst_name()%>" lname="<%= user.getLast_name()%>"
                                   email="<%= user.getEmail()%>" age="<%= user.getAge()%>">
                                    <i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;&nbsp;
                                <a class="text-purple cursor-pointer" href="user?id=<%= user.getId()%>" id="<%= user.getId()%>">
                                    <i class="fas fa-eye"></i></a>
                            </td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
            <small class="d-block text-end mt-3">
                <a href="#">All updates</a>
            </small>
        </div>


        <!-- Modal -->
        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">Modal title</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" class="userID">
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
                            <label for="floatingPassword">Age</label>
                            <input type="number" class="form-control ageField" id="floatingPassword" name="password" placeholder="Age">

                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary updateMe">Update User</button>
                    </div>
                </div>
            </div>
        </div>
    </main>


    <script src="https://getbootstrap.com/docs/5.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

    <script>
        (function () {
            'use strict'

            document.querySelector('#navbarSideCollapse').addEventListener('click', function () {
                document.querySelector('.offcanvas-collapse').classList.toggle('open')
            })
        })()
    </script>


    <script>
        $(document).ready(function () {
            function refreshPage() {
                location.reload(true);
            }
            $(".deleteUser").click(function () {
                var id = $(this).attr("id");
//                alert($(this).attr("id"));

                $.confirm({
                    title: 'Are you sure?',
                    content: 'This user delete permanently',
                    type: 'green',
                    buttons: {
                        ok: {
                            text: "yes!",
                            btnClass: 'btn-primary',
                            keys: ['enter'],
                            action: function () {
                                $.ajax({
                                    url: "home",
                                    async: false,
                                    data: {type: "delete_user", id: id},
                                    type: "post",
                                    success: function (data) {
                                        toast(data, "success");

                                        setInterval('location.reload(true)', 1000);
                                    }
                                });
                            }
                        },
                        cancel: function () {
                            console.log('the user clicked cancel');
                        }
                    }
                });
            });

            $(".updateUserModal").click(function () {
                var fname = $(this).attr("fname");
                var lname = $(this).attr("lname");
                var email = $(this).attr("email");
                var age = $(this).attr("age");
                var id = $(this).attr("id");

                $(".firstName").val(fname);
                $(".lastName").val(lname);
                $(".emailID").val(email);
                $(".ageField").val(age);
                $(".userID").val(id);

                $("#staticBackdropLabel").text(fname + " " + lname);
                $('#staticBackdrop').modal('show');

            })
            $(".updateMe").click(function () {
                var fname = $(".firstName").val();
                var lname = $(".lastName").val();
                var email = $(".emailID").val();
                var age = $(".ageField").val();
                var id = $(".userID").val();

                $.confirm({
                    title: 'Are you sure?',
                    content: 'Want to update this user.',
                    type: 'green',
                    buttons: {
                        ok: {
                            text: "yes!",
                            btnClass: 'btn-primary',
                            keys: ['enter'],
                            action: function () {
                                $.ajax({
                                    url: "home",
                                    async: false,
                                    data: {type: "update_user", id: id, fname: fname, lname: lname, email: email, age: age},
                                    type: "post",
                                    success: function (data) {
                                        toast(data, "success");

                                        setInterval('location.reload(true)', 1000);
                                    }
                                });
                            }
                        },
                        cancel: function () {
                            console.log('the user clicked cancel');
                        }
                    }
                });
            })
        });

    </script>

    <%@include file="inc/footer.jsp" %>