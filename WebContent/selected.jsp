<img width="400px" src="${selectedMovie.image}" />
<h2>${selectedMovie.name}</h2>
<h3>Rated: ${selectedMovie.mpaaRating}</h3>
<p>Genre: ${selectedMovie.genre}</p>
<p>Description: ${selectedMovie.description}</p>

<p>Your Rating: ${selectedMovie.userRating}
    <a href="submitEdit.do?attribute=userRating&newValue=1">1 </a>
    <a href="submitEdit.do?attribute=userRating&newValue=2">2 </a>
    <a href="submitEdit.do?attribute=userRating&newValue=3">3 </a>
    <a href="submitEdit.do?attribute=userRating&newValue=4">4 </a>
    <a href="submitEdit.do?attribute=userRating&newValue=5">5 </a>
</p>

<c:choose>
    <c:when test="${(edit == true) and (item == 'userNotes')}">
        <form action="submitEdit.do" method="post">
            <input type="hidden" name="attribute" value="userNotes">
            <textarea type="textarea" rows="4" cols="50" name="newValue" >${editNotes}</textarea>
            <input type="submit" name="submitEdit" value="Save Changes">
        </form>
    </c:when>
    <c:otherwise>
        <p>User Notes: ${selectedMovie.userNotes} <a href="edit.do?attribute=userNotes">Edit Notes</a></p>
    </c:otherwise>
</c:choose>
