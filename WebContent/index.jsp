<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width" />
<meta charset="UTF-8">
	<title>Movies</title>
    <link rel="stylesheet" href="css/basscss.min.css">
    <link rel="stylesheet" href="css/layout.css" >
</head>
	<body>

	<div id="container">
    <div id="navbar">
        <ul>
            <li><a href="listMovies.do">List All Movies</a></li>
            <li><a href="newMovie.do">Add a Movie</a></li>
        <ul>
    </div>
        <c:choose>
            <%-- when "movies" is passed in, which is a list, list all movies in a table--%>
            <c:when test="${! empty movies}">
                <%@ include file="listAll.jsp" %>
            </c:when>
            <c:when test="${newMovie == true}">
                <%-- Form to create movie. Gives values to CommandObject --%>
                <%@ include file="newMovieForm.jsp" %>
            </c:when>
            <c:when test="${! empty selectedMovie}">
                <%@ include file="selected.jsp" %>
            </c:when>

        </c:choose>
        <h2>${result}</h2>
</div>


</body>
<script type="text/javascript">
    document.body.style.backgroundColor = "rgb(223, 223, 223)";
</script>
</html>
