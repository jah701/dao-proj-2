<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order details</title>
</head>
<body>
<h1>Your order details</h1>
<table border="1">
    <tr>
        <th>Order ID</th>
        <th>User ID</th>
        <th>Products</th>
    </tr>
        <tr>
            <td>${order.id}</td>
            <td>${order.userId}</td>
            <td>${order.products}</td>
        </tr>
</table>
<a href="${pageContext.request.contextPath}/">Main page</a> <br>
</body>
</html>
