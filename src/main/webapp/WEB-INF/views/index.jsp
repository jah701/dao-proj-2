<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <title>MATE HOMEWORK</title>
</head>
<body>
    <h2 style="color:darkgreen">Welcome to our Internet Shop! :)</h2>
    <h3>User menu</h3>
    <a href="${pageContext.request.contextPath}/users/registration">Register</a> <br>
    <a href="${pageContext.request.contextPath}/login">Login</a> <br>
    <a href="${pageContext.request.contextPath}/shopping-cart/products">Shopping cart</a> <br>
    <a href="${pageContext.request.contextPath}/products/all">All products</a> <br>
    <a href="${pageContext.request.contextPath}/user/orders/all">All orders</a> <br>
    <a href="${pageContext.request.contextPath}/users/all">All users</a> <br> <br>
    <h3>Admin menu</h3>
    <a href="${pageContext.request.contextPath}/products/add">Add product</a> <br>
    <a href="${pageContext.request.contextPath}/admin/orders/all">All orders</a> <br>
    <a href="${pageContext.request.contextPath}/injectData">Inject data!</a> <br>
    <a href="${pageContext.request.contextPath}/admin/products/access">Delete product</a> <br>
</body>
</html>
