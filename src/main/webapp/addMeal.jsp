<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        div.field {
            padding-bottom: 5px;
        }

        div.field label {
            display: block;
            float: left;
            width: 150px;
            height: 20px;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add/Edit Meal</h2>
<p/>
<fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
<form method="POST" action='meals' name="frmAddMeal">
    <input type="hidden" name="id" value="${meal.id}"/>
    <div class="field">
        <label>Meal Date Time:</label>
        <input
                type="datetime-local" name="dateTime"
                value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" />"/> <br/>
    </div>
    <div class="field">
        <label>Meal Description:</label>
        <input
                type="text" name="description"
                value="<c:out value="${meal.description}" />"/> <br/>
    </div>
    <div class="field">
        <label>Meal calories:</label>
        <input
                type="text" name="calories"
                value="<c:out value="${meal.calories}" />"/> <br/>
    </div>
    <p/>
    <input type="submit" value="Submit"/>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>
