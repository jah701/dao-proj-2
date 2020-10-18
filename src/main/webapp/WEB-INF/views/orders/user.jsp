<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="../header.jsp" %>
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
        </tr>
    </table>
</c:forEach> <br>
<a href="${pageContext.request.contextPath}/">Main page</a> <br>
</body>
</html>
