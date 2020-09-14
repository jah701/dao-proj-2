<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<h1>Sign up/in</h1>
<body>
<h3 style="color:red">${errMsg}</h3>
<form method="post" action="${pageContext.request.contextPath}/login">
    Login: <input type="text" name="login"> <br/><br/>
    Password: <input type="password" name="pass"> <br/><br/>
    <button type="submit">Login</button>
</form> <br>
<a href="${pageContext.request.contextPath}/users/registration">Register</a>
</body>
</html>
