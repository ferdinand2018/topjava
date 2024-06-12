<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<jsp:forward page="meals.jsp" />

<table>
<tr>
<td align="center">Date</td>
<td align="center">Description</td>
<td align="center">Calories</td>
</tr>
<c:forEach items="${meals}" var="meal">
<tr>
<td align="left">${meal.dateTime}</td>
<td align="left">${meal.description}</td>
<td align="left">${meal.calories}</td>
</tr>
</c:forEach>
<table>

</body>
</html>