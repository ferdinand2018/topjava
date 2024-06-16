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
<h2>${param.action == 'insert' ? 'Add' : 'Edit'} Meal</h2>

<form action='meals' method='POST' name = "addMeal">
    <input type="hidden" name="id" value="${meal.id}">
    <table>
    <tr>
        <td>Date Time: </td>
        <td><input type="datetime-local" name = "dateTime" value="${meal.dateTime}" /></td>
    </tr>
    <tr>
        <td>Description: </td>
        <td><input type="text" name = "description" value="${meal.description}" /></td>
    </tr>
    <tr>
        <td>Calories: </td>
        <td><input type="number" name = "calories" value="${meal.calories}" /></td>
    </tr>
    <table>
    <input type="submit" value="Submit" />
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>