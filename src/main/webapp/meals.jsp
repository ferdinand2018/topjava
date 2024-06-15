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
            <th align="center">Date</td>
            <th align="center">Description</td>
            <th align="center">Calories</td>
            <th></td>
            <th></td>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${mealsList}" var="meal">
            <tr style = " color: <c:out value = "${meal.excess == true ? 'red' : 'green'}"/>">
                <td>
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td align="center">
                <a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a>
                </td>
                <td align="center">
                <a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
<table>

</body>
</html>