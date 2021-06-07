<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tayyab
  Date: 24-Apr-2021
  Time: 11:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>JersyApp</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">JersyApp</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Features</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Pricing</a>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
            </li>
        </ul>
    </div>
</nav>
<div class="container-fluid">

    <div class="container-lg">
    <div class="row">
        <c:if test="${response!=null&&dataaction!=null&&dataaction=='save'}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <strong>Data saved!</strong> ${response}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

        <div class="col-xl-5 col-lg-6 col-md-8 col-sm-10 mx-auto text-center form p-4">
            <h1 class="display-4 py-2 text-truncate">Save Person Data</h1>
            <div class="px-2">


        <form name="dataForm" id="dataForm">
            <div class="form-group">
                <label for="personsname">Person Name</label>
                <input type="text" name="personsname" class="form-control" id="personsname" />
            </div>
            <input type="hidden" name="action" value="save"/>
            <button type="submit" class="btn btn-primary" onClick='submitForm()'>Submit</button>
        </form>
    </div>
    </div>
    </div>
    </div>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4= sha384-vtXRMe3mGCbOeY7l30aIg8H9p3GdeSe4IFlP6G8JMa7o7lXvnz3GFKzPxzJdPfGK sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
<script type="text/javascript">
    $(function(){
        $('#dataForm').on('submit', function(event){
            event.preventDefault();
         //   alert("Form Submission stopped.");
        });
    });
    function submitForm(){
     //   console.log( "Calling ajax"+x );
        var values = $('#dataForm').serialize();
        console.log( values );
        var request= $.ajax({
            url: "PersonDataServlet",
            type: "post",
            data: values,
            // data:  {
            //     personsname: "Test Person",
            //     action: "save"
            // },

            //dataType: 'json',
            success: function(data, textStatus, xhr) {
                console.log(xhr.status);

                console.log("msg:"+xhr.getResponseHeader('msg'));
                var obj = jQuery.parseJSON( xhr.getResponseHeader('msg') );
                console.log(obj);
                var json = JSON.parse(obj);
                console.log(json);
                alert( json.msg );
                $('#dataForm').trigger("reset");

                // $('.toast').toast('show');

                //  location.reload();
                /// console.log("xhr:"+xhr);
            },
            complete: function(xhr, textStatus) {
                console.log(xhr.status);
                //  console.log("xhr:"+xhr);
            },
            error:function(){
                console.log("Error");//does not print in the console
                alert("error");
            }
        });
    }

</script>
</body>
</html>

