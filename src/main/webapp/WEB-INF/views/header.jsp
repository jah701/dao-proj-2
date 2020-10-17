<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <title>Title</title>
    <title>Hello world</title>
    <style>
        body {
            font-family: "Bookman Old Style";
            padding: 10px 20px 10px 20px;
            font-size: 20px;
            background-color: ghostwhite;
        }

        .header {
            padding: 25px 50px 75px 100px;
            text-align: center;
            background: gray;
            background-size: auto;
            font-size: 25px;
        }
    </style>
</head>
<body>
<div class="header">
    <ul class="navbar-nav" style="color: Background">
        <li class="nav-item active" style="position: absolute; left: 30px; top: 15px">
            <a class="nav-link" href="${pageContext.request.contextPath}/" style="color: white">Home page</a>
        </li>
        <li class="nav-item active" style="position: absolute; right: 180px; top: 15px">
            <a class="nav-link" href="${pageContext.request.contextPath}/users/registration" style="color: white">Sign up</a>
        </li>
        <li class="nav-item active" style="position: absolute; right: 320px; top: 15px">
            <a class="nav-link" href="${pageContext.request.contextPath}/shopping-cart/products" style="color: white">Basket</a>
        </li>
        <li class="nav-item active" style="position: absolute; right: 30px; top: 15px">
            <a class="nav-link" href="${pageContext.request.contextPath}/logout" style="color: darkred">Logout</a>
        </li>
    </ul>
</div>
</body>
</html>
