<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create you product</title>
</head>
<body>
<h3 style="color:red">${errMessage}</h3>

<form method="post" action="${pageContext.request.contextPath}/products/add">
    Product name: <input type="text" name="product-name"> <br>
    Product price: <input type="text" name="product-price"> <br> <br>
    <button type="submit" value="product">Create</button>
</form> <br> <br>
<a href="${pageContext.request.contextPath}/">Go back</a> <br> <br>
</body>
</html>
