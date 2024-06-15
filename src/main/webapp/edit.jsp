<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Edit</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meal</h2>

<form action='meals' method='POST' name = "addMeal">
    Date Time: <input type="datetime-local" name = "dateTime"
    value="
    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" />" />
    <br />
    Description: <input type="text" name = "description"
    value="<c:out value="${meal.description}" />" />
    <br />
    Calories: <input type="number" name = "calories"
    value="<c:out value="${meal.calories}" />" />
    <br />
    <input type="submit" value="Submit" />
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>

</body>
</html>