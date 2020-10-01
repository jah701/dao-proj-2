<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <title>MATE HOMEWORK</title>
</head>
<body>
    <h2>Welcome to our Internet Shop! :)</h2>
    <h4>User menu</h4>
    <a href="${pageContext.request.contextPath}/shopping-cart/products">Shopping cart</a> <br>
    <a href="${pageContext.request.contextPath}/products/all">All products</a> <br>
    <a href="${pageContext.request.contextPath}/user/orders/all">My orders</a> <br>
    <a href="${pageContext.request.contextPath}/inject-data">Inject data!</a> <br> <br>
    <a style="color:darkred" href="${pageContext.request.contextPath}/logout">Logout</a> <br>
    <h4>Admin menu</h4>
    <a href="${pageContext.request.contextPath}/users/all">All users</a> <br>
    <a href="${pageContext.request.contextPath}/products/add">Add product</a> <br>
    <a href="${pageContext.request.contextPath}/orders/all">All orders</a> <br>
    <a href="${pageContext.request.contextPath}/products/manage">Delete product</a> <br>
</body>
</html>
