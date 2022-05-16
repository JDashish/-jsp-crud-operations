<%-- 
    Document   : login
    Created on : 6 May, 2022, 5:12:52 PM
    Author     : Dell1
--%>
<%@page import="java.io.File"%>
<%@page import="Model.Files"%>
<%@page import="Dao.FilesDao"%>
<%@page import="Model.UserDetails"%>
<%@page import="Helper.Hash"%>
<%@page import="Model.User"%>
<%@page import="Dao.UserDaoImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    final String UPLOAD_PATH_FILES = "C:/Users/Dell1/Documents/NetBeansProjects/Curd/web/user/files/";
%>

<%@include file="inc/header_1.jsp" %>
<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
<link rel="stylesheet" href="css/home.css"/>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>

<style>
    .user-img{
        height: 100px;
        width: 100px;
        margin-top: 15px;
    }
</style>
<body class="bg-light">
    <%
        String uid = request.getParameter("id");
        UserDaoImpl udi = new UserDaoImpl();
        User user = udi.getUser(Integer.valueOf(uid));
        UserDetails userDetails = udi.getUserDetails(Integer.valueOf(uid));

        FilesDao filesDao = new FilesDao();
    %>

    <%@include file="inc/nav.jsp" %>

    <div class="container">
        <div class="row">
            <div class="col-sm-3 ">

                <img src="${pageContext.request.contextPath}/user/<%= userDetails.getProfile_image()%>" class="user-img"/>
                <form class="upload-box" enctype="multipart/form-data" method="post">
                    <input type="file" id="file" name="file1"  style="display: none;" />
                    <input type="hidden" value="UploadUserDp" name="type"/>
                    <input type="hidden" value="<%= user.getId()%>" name="UserID" class="UserIDs"/>
                    <span id="upload-error" class="error" />
                </form>
                <button class="btn btn-secondary mt-3 mr-5 change-img " type="button">Change image</button>
            </div>
            <div class="col-sm-3 text-end">
                <form class="upload-files-form" enctype="multipart/form-data" method="post">
                    <input type="file" id="uploadFiles" name="files"  style="display: none;" multiple/>
                </form>
                <button class="btn btn-secondary mt-3 mr-5 upload-files" type="button">Upload files</button>
            </div>
        </div>
        <div class="row mt-5">
            <div class="col-sm-6 ">

                <table>
                    <tr>
                        <th>Name: </th>
                        <td><%= user.getFirst_name()%> <%= user.getLast_name()%></td>
                    </tr>
                    <tr>
                        <th>Email: </th>
                        <td><%= user.getEmail()%> </td>
                    </tr>
                    <tr>
                        <th>Age: </th>
                        <td><%= user.getAge()%> </td>
                    </tr>
                    <tr>
                        <th>Created At: </th>
                        <td><%= user.getCreated_at()%> </td>
                    </tr>
                </table>
            </div>
            <div class="col-sm-12 ">

                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Files</th>
                            <th scope="col">type</th>
                            <th scope="col">Created at</th>
                            <th scope="col">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int cnt = 0;
                            for (Files files : filesDao.getUploadFiles(String.valueOf(user.getId()))) {
                                String extension = "";

                                int i = files.getFile_link().lastIndexOf('.');
                                if (i > 0) {
                                    extension = files.getFile_link().substring(i + 1);
                                }
                        %>
                        <tr>
                            <th scope="row"><%= ++cnt%></th>
                            <td><%= files.getFile_link()%></td>
                            <td><%= extension%></td>
                            <td><%= files.getCreated_at()%></td>
                            <td>
                                <a class="deleteUser text-purple cursor-pointer" id="<%= files.getId()%>">
                                    <i class="fas fa-trash-alt"></i></a>&nbsp;&nbsp;&nbsp;


                                <a class="downloadFiles text-purple cursor-pointer" href="download?filename=<%= files.getFile_link()%>&path=<%= UPLOAD_PATH_FILES%>">
                                    <i class="fas fa-download"></i></a>&nbsp;&nbsp;&nbsp;

                            </td>
                        </tr>

                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>


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
            $("#uploadFiles").change(function () {

                var url = "home";
                var files = $("#uploadFiles").get(0).files;
                var fileData = new FormData();

                for (var i = 0; i < files.length; i++) {
                    fileData.append("uploadFiles", files[i]);
                }
                fileData.append("type", "UploadUserFIles");
                fileData.append("UserID", $(".UserIDs").val());
                $.ajax({
                    type: "POST",
                    encType: "multipart/form-data",
                    url: url,
                    processData: false,
                    contentType: false,
                    data: fileData,
                    success: function (msg) {
                        alert(msg);
                    },
                    error: function (msg) {
                        alert("Couldn't upload file");
                    }
                });
            });
            $(".upload-files").click(function () {
                $("#uploadFiles").click();
            });
            function encodeImageFileAsURL(element) {
                var file = element.files[0];
                var reader = new FileReader();
                reader.onloadend = function() {
                  $(".user-img").attr("src",reader.result);
                }
                reader.readAsDataURL(file);
            }
            $("#file").change(function () {
//                $(".upload-box").submit();

                var url = "home";
                var form = $(".upload-box")[0];


                var data = new FormData(form);
               
               encodeImageFileAsURL(this);
               
                /* var data = {};
                 data['key1'] = 'value1';
                 data['key2'] = 'value2'; */
                $.ajax({
                    type: "POST",
                    encType: "multipart/form-data",
                    url: url,
                    processData: false,
                    contentType: false,
                    data: data,
                    success: function (msg) {
                        alert(msg);
                    },
                    error: function (msg) {
                        alert("Couldn't upload file");
                    }
                });
            })
            $(".change-img").click(function () {
                $("#file").click();
            })
            function refreshPage() {
                location.reload(true);
            }
            $(".deleteUser").click(function () {
                var id = $(this).attr("id");
//                alert($(this).attr("id"));

                $.confirm({
                    title: 'Are you sure?',
                    content: 'This file delete permanently',
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
                                    data: {type: "file_delete", id: id},
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