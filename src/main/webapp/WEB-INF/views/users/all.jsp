<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
<%@include file="../header.jsp" %>
<h1>All users here</h1>
<table class="table" border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.name}"/></td>
            <td><a href="${pageContext.request.contextPath}/users/delete?id=${user.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/">Main page</a>
</body>
</html>
