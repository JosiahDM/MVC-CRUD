<div id="listAll">
    <table class="main-table">
        <c:forEach var="movie" items="${movies}">
            <tr class="rows">
                <td id="movieImg">
                    <a name="${movie.id}"></a>
                    <img src="${movie.image}"/>
                </td>
                <td class="centerResponsive">
                    <a class="clickable" href="selectMovie.do?id=${movie.id}#${movie.id}">${movie.name}</a>
                    <p>${movie.mpaaRating} | ${movie.genre}</p>
                </td>
                <td class="centerResponsive">
                    <div id="watchedButton">
                        <a class="clickable" href="submitEditInList.do?attribute=watched&id=${movie.id}#${movie.id}">
                            Watched
                                <span class="checked" ><input type="checkbox" name="watched" value="${movie.watched}" disabled
                                    <c:if test="${movie.watched}">style="background-color:red" checked</c:if>>
                                </span>

                        </a>
                    </div>
                </td>
                <td class="centerResponsive">
                    Your Rating: ${movie.userRating}
                    <a href="submitEditInList.do?attribute=userRating&newValue=1&id=${movie.id}#${movie.id}">1 </a>
                    <a href="submitEditInList.do?attribute=userRating&newValue=2&id=${movie.id}#${movie.id}">2 </a>
                    <a href="submitEditInList.do?attribute=userRating&newValue=3&id=${movie.id}#${movie.id}">3 </a>
                    <a href="submitEditInList.do?attribute=userRating&newValue=4&id=${movie.id}#${movie.id}">4 </a>
                    <a href="submitEditInList.do?attribute=userRating&newValue=5&id=${movie.id}#${movie.id}">5 </a>

                </td>
                <td id="bottom-td">
                    Description: ${movie.description}
                    <c:choose> <%-- User notes edit block --%>
                        <c:when test="${(edit == true) and (item == 'userNotes') and (movie.id == movieId) }">
                            <p>
                                <form action="submitEditInList.do" method="post">
                                    <input type="hidden" name="attribute" value="userNotes">
                                    <input type="hidden" name="id" value="${movie.id}">
                                    <textarea style="border:1px solid rgb(5, 137, 232); background-color:white" type="textarea" name="newValue" >${editNotes}</textarea>
                                    <input id="editSubmit" type="submit" name="submitEdit" value="Save">
                                </form>
                            </p>
                        </c:when>
                        <c:otherwise>
                            <p>Notes:</p>
                            <textarea disabled="true">${movie.userNotes}</textarea>
                            <p><a class="edit" href="edit.do?attribute=userNotes&listedMovie=${movie.id}#${movie.id}">Edit</a></p>


                            <div style="display:none" id="delete">
                                <p><a class="clickable" href="deleteMovie.do?id=${movie.id}#${movie.id+1}">Delete<a></p>
                            </div>
                        </c:otherwise>
                    </c:choose> <%-- End of user notes edit block --%>
                </td>
                <td id="right-td-full">
                    <a class="clickable" href="deleteMovie.do?id=${movie.id}#${movie.id+1}">Delete<a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
