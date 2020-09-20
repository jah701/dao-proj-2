<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders are displayed here</title>
</head>
<body>
<c:forEach var="order" items="${orders}">
    <table border="1">
        <tr>
            <th>Order ID</th>
            <th>User ID</th>
        </tr>
            <tr>
                <td>${order.id}</td>
                <td>${order.userId}</td>
                <td><a href="${pageContext.request.contextPath}/order/details?id=${order.id}">Details</a></td>
                <td><a href="${pageContext.request.contextPath}/orders/delete?id=${order.id}">Delete</a></td>
            </tr>
    </table>
</c:forEach> <br>
<a href="${pageContext.request.contextPath}/">Main page</a> <br>
</body>
</html>
