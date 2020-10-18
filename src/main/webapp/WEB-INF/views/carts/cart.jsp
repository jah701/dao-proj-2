<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart</title>
</head>
<body>
<%@include file="../header.jsp" %>
<h1>Here you can see your picked products</h1> <br> <br>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${productsInCart}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/shopping-cart/products/delete?id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table> <br>
<form action="${pageContext.request.contextPath}/shopping-cart/complete-order" method="post">
    <input type="submit" value="Make order">
</form> <br>
<a href="${pageContext.request.contextPath}/products/all">Go back</a> <br>
<a href="${pageContext.request.contextPath}/">Main page</a> <br>
</body>
</html>
