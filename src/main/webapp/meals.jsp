<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<p><a href = "meals?action=insert">Create</a></p>

<table cellspacing="0" border="1" cellpadding="5" width="800">
    <thead>
        <tr>
            <th align="center">Date</th>
            <th align="center">Description</th>
            <th align="center">Calories</th>
            <th></th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${meals}" var="meal">
            <tr style = "color:${meal.excess ? 'red' : 'green'}">
                <td>
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate pattern="yyyy.MM.dd HH:mm" value="${ parsedDateTime }"/>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td align="center">
                <a href="meals?action=edit&id=${meal.id}">Update</a>
                </td>
                <td align="center">
                <a href="meals?action=delete&id=${meal.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
<table>

</body>
</html>