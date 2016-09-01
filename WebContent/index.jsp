<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Movies</title>
</head>
<body>
	<form method="POST" action="results.do">
		<input type="text" name="userInput">
		<input type="submit" value="Submit">
	</form>

    <a href="listMovies.do">List All Movies!!1!</a>
    <c:choose>
        <c:when test="${! empty movies}">
            <table border="1">
                <c:forEach var="movie" items="${movies}">
                    <tr>
                        <td><img width="80px" src="img/moviePosters/${movie.image}"/></td>
                        <td>${movie.name}</td>
                        <td>Description: ${movie.description}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
    </c:choose>

</body>
</html>
