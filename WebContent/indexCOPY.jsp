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
    <a href="newMovie.do">Add a Movie</a>
    <c:choose>
        <%-- when "movies" is passed in, which is a list, list all movies in a table--%>
        <c:when test="${! empty movies}">
            <table border="1">
                <c:forEach var="movie" items="${movies}">
                    <tr>
                        <td><img width="80px" src="img/moviePosters/${movie.image}"/></td>
                        <td><a href="selectMovie.do?id=${movie.id}">${movie.name}</a></td>
                        <td>Description: ${movie.description}</td>
                        <td><a href="deleteMovie.do?id=${movie.id}">Delete<a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:when test="${newMovie == true}">
            <%-- Form to create movie. Gives values to CommandObject --%>
            <form id="movieForm" action="createMovie.do" method="post">
                <ul style="list-style-type:none">
                    <li>
                        <input type="text" name="name" placeholder="Movie Name">
                        <input type="checkbox" name="watched" value="true">Already seen?
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
                    </li>
                    <li>
                        <input type="text" name="description" placeholder="Movie Description Here...">
                    </li>
                    <li>
                        <input type="text" name="userNotes" placeholder="Any notes you'd like to enter here...">
                    </li>
                    <li>
                        <input type="hidden" name="image" value="unknown.png">
                        <input type="submit" name="submit" value="Submit">
                    </li>
                </ul>
            </form> <%------ END OF NEW MOVIE FORM ------------%>

        </c:when>
    </c:choose>
    <h2>${result}</h2>



</body>
</html>
