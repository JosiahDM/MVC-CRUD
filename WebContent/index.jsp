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
        <c:when test="${newMovie == true}">
            <form id="movieForm" action="createMovie.do" method="post">
                <ul style="list-style-type:none">
                    <li>
                        <input type="text" name="name" placeholder="Movie Name">
                        <input type="checkbox" name="watched" value="true">Already seen?
                        <input type="hidden" name="watched" value="null"> <!-- In case nothing selected-->
                    <li>
                    <li>
                        <select name="mpaaRating" form="movieForm" required>
                            <option value="null" selected>I'm not sure</option>
                            <option value="G">G</option>
                            <option value="PG">PG</option>
                            <option value="PG-13">PG</option>
                            <option value="R">R</option>
                            <option value="X">X</option>
                            <option value="NC-17">NC-17</option>
                        </select>
                    </li>
                    <li>
                        <select name="userRating" form="movieForm" required>Enter your rating 1-5
                            <option value="1">1 - Nope</option>
                            <option value="2">2 - Didn't like</option>
                            <option value="3">3 - It's ok</option>
                            <option value="4">4 - Good</option>
                            <option value="5">5 - Amazing</option>
                        </select>
                    </li>
                    <li>
                        <input type="checkbox" name="genre" value="Horror">Horror
                        <input type="checkbox" name="genre" value="Action">Action
                        <input type="checkbox" name="genre" value="Sci-Fi">Sci-Fi
                        <input type="checkbox" name="genre" value="Drama">Drama
                        <input type="checkbox" name="genre" value="Family">Family
                        <input type="checkbox" name="genre" value="Comedy">Comedy
                        <input type="checkbox" name="genre" value="Thriller">Thriller
                        <input type="checkbox" name="genre" value="Suspense">Suspense
                        <input type="checkbox" name="genre" value="Adventure">Adventure
                        <input type="checkbox" name="genre" value="Romance">Romance
                        <input type="checkbox" name="genre" value="Documentary">Documentary
                        <input type="checkbox" name="genre" value="Musical">Musical
                        <input type="hidden" name="genre" value="null"> <!-- In case nothing selected-->
                    </li>
                    <li>
                        <input type="text" name="description" placeholder="Movie Description Here...">
                    </li>
                    <li>
                        <input type="text" name="notes" placeholder="Any notes you'd like to enter here...">
                    </li>
                    <li>
                        <input type="submit" name="submit" value="Submit">
                    </li>
                </ul>
            </form>
        </c:when>
    </c:choose>
    <h2>${result}</h2>
    <a href="newMovie.do">Add a Movie</a>


</body>
</html>
