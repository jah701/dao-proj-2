<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>This is registration page!</h1>
<h3 style="color:red">${message}</h3>
<form method="post" action="${pageContext.request.contextPath}/users/registration" >
    Enter your name: <input type="text" name="name"> <br/><br/>
    Enter your login: <input type="text" name="login"> <br/><br/>
    Enter your password: <input type="password" name="pass"> <br/><br/>
    Repeat your password: <input type="password" name="pass-repeat"> <br/><br/>
    <button type="submit">Register</button>
</form>
</body>
</html>
