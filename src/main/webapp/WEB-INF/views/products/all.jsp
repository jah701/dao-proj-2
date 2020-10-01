<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<h1>All products in shop will be displayed here</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Product</th>
        <th>Price</th>
        <th>Action</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td><c:out value="${product.id}"/></td>
            <td><c:out value="${product.name}"/></td>
            <td><c:out value="${product.price}"/></td>
            <td><a href="${pageContext.request.contextPath}/shopping-cart/products/add?id=${product.id}">Buy</a></td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/products/add">Create product</a> <br> <br>
<a href="${pageContext.request.contextPath}/">Main page</a> <br> <br>
</body>
</html>
