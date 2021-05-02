<%--
  Created by IntelliJ IDEA.
  User: Tayyab
  Date: 26-Apr-2021
  Time: 2:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>JersyApp</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">JersyApp</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
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
    <h1>List of People</h1>
    <c:import url="PersonDataServlet"/>

    <c:set var="myProducts" value="${data}"/>
    <c:if test="${response!=null&&dataaction!=null&&dataaction=='delete'}">
    <div class="alert alert-warning alert-dismissible fade show" role="alert">
        <strong>Data deleted!</strong> ${response}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    </c:if>
    <p>"Res:"${response}</p>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Action</th>

        </tr>
        </thead>
        <tbody>


        <c:forEach var="product" items="${myProducts}" varStatus="i">

            <tr>
                <th scope="row">${product.id}</th>
                <td>${product.name}</td>
                <td>

                    <input type="hidden" value="${product.id}" name="deleteid">
                        <input type="hidden" name="action" value="delete"/>
                    <button id="action-button" class="btn btn-primary" onClick='submitForm(${product.id})'>Delete</button>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div id="result">Test</div>


    <!-- <a href="rest/api/getdata">Api Data</a>
     <a href="Persondata.jsp">Person Data</a>-->

</div>
<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Understood</button>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>

<script type="text/javascript">
    function submitForm(x){
        console.log( "Calling ajax"+x );
        var values = $(this).serialize();
        console.log( values );
       var request= $.ajax({
            url: "PersonDataServlet",
            type: "post",
            data:  {
                deleteid: x,
                action: "delete"
             },
           //dataType: 'json',
           success: function(data, textStatus, xhr) {
               console.log(xhr.status);
           },
           complete: function(xhr, textStatus) {
               console.log(xhr.status);
           },
            error:function(){
                console.log("Error");//does not print in the console
                alert("error");
            }
        });
        console.log( "Request : " + request );
        request.done(function(msg) {
            console.log( msg );
        });

        request.fail(function(jqXHR, textStatus) {
            console.log( "Request failed: " + textStatus );
        });
    }
    // $(document).ready(function() {
    //     alert("test");
    //     $('#action-button').click(function (){
    //         $.ajax({
    //             type: "post",
    //             url: "/path",
    //             data: "email=" + $('#email').val() + "&subject="+$('#subject').val() + "&msg=" + $('#msg').val(),
    //             success: function(msg){
    //                 //
    //             }
    //         });
    //     });
    // });
</script>
<script>
    document.getElementById("action-button").onclick = function() {myFunction()};

    /* myFunction toggles between adding and removing the show class, which is used to hide and show the dropdown content */
    function myFunction() {
        alert("error");
    }
    $('#action-button').click(function() {
        alert("error");
        // $.ajax({
        //     url: "PersonDataServlet",
        //     type: "post",
        //     data: values,
        //     success:function(response) {
        //         document.getElementById("result").innerHTML =response;
        //     },
        //     error:function(){
        //         alert("error");
        //     }
        // });
    });
</script>




</body>
</html>

