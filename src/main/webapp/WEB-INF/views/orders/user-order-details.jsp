<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User orders</title>
</head>
<body>
<h1>User orders</h1>
<c:forEach var="order" items="${orders}">
    <p>id = ${order.id}</p>
    <table border="1">
        <tr>
            <th>id</th>
            <th>name</th>
            <th>price</th>
        </tr>
        <c:forEach var="product" items="${order.products}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
            </tr>
        </c:forEach>
    </table>
</c:forEach> <br>
<a href="${pageContext.request.contextPath}/products/all">All products</a> <br>
<a href="${pageContext.request.contextPath}/">Main page</a> <br>
</body>
</html>
