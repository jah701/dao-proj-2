<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<%@include file="../header.jsp" %>
<h3>Please enter you login and password</h3>
<h4 style="color:red">${errMsg}</h4>
<form method="post" action="${pageContext.request.contextPath}/login">
    Login: <input type="text" name="login"> <br/><br/>
    Password: <input type="password" name="password"> <br/><br/>
    <button type="submit">Login</button>
</form> <br> <br>
<a href="${pageContext.request.contextPath}/users/registration">Register</a> <br>
</body>
</html>
