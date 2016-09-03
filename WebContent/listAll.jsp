<table class="table">
    <c:forEach var="movie" items="${movies}">
        <tr>
            <td>
                <a name="${movie.id}"></a>
                <img width="160px" src="${movie.image}"/>
            </td>
            <td>
                <a href="selectMovie.do?id=${movie.id}#${movie.id}">${movie.name}</a>
                <p>${movie.mpaaRating} | ${movie.genre}</p>
            </td>
            <td>
                <a href="submitEditInList.do?attribute=watched&id=${movie.id}#${movie.id}">
                    Watched?
                    <input type="checkbox" name="watched" value="${movie.watched}" disabled <c:if test="${movie.watched}">checked</c:if>>
                </a>
            </td>
            <td>
                Your Rating: ${movie.userRating}
                <a href="submitEditInList.do?attribute=userRating&newValue=1&id=${movie.id}#${movie.id}">1 </a>
                <a href="submitEditInList.do?attribute=userRating&newValue=2&id=${movie.id}#${movie.id}">2 </a>
                <a href="submitEditInList.do?attribute=userRating&newValue=3&id=${movie.id}#${movie.id}">3 </a>
                <a href="submitEditInList.do?attribute=userRating&newValue=4&id=${movie.id}#${movie.id}">4 </a>
                <a href="submitEditInList.do?attribute=userRating&newValue=5&id=${movie.id}#${movie.id}">5 </a>

            </td>
            <td>
                Description: ${movie.description}
                <c:choose> <%-- User notes edit block --%>
                    <c:when test="${(edit == true) and (item == 'userNotes') and (movie.id == movieId) }">
                        <p>
                            <form action="submitEditInList.do" method="post">
                                <input type="hidden" name="attribute" value="userNotes">
                                <input type="hidden" name="id" value="${movie.id}">
                                <textarea type="textarea" rows="2" cols="100" name="newValue" >${editNotes}</textarea>
                                <input type="submit" name="submitEdit" value="Save Changes">
                            </form>
                        </p>
                    </c:when>
                    <c:otherwise>
                        <p><a href="edit.do?attribute=userNotes&listedMovie=${movie.id}#${movie.id}">Notes</a>: ${movie.userNotes} </p>
                    </c:otherwise>
                </c:choose> <%-- End of user notes edit block --%>
            </td>
            <td>
                <a href="deleteMovie.do?id=${movie.id}#${movie.id+1}">Delete<a>
            </td>



        </tr>
    </c:forEach>
</table>
