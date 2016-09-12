<div class="clearfix" id="selected">
    <div class="sm-col sm-col-6 center">
        <div id="selectedImage">
            <img src="${selectedMovie.image}" />
        </div>
    </div>
  <div class="sm-col sm-col-6" id="details">
      <h2 class="h2">${selectedMovie.name}</h2>
      <p>${selectedMovie.mpaaRating} | ${selectedMovie.genre}</p>
      <p>Description: ${selectedMovie.description}
      	<c:if test="${ selectedMovie.image == 'img/moviePosters/unknown.png' ||
      				   selectedMovie.description == 'Movie not found' ||
      				   selectedMovie.genre == '' ||
      				   selectedMovie.year == ''     }">
      		<a href="repopulate.do?id=${selectedMovie.id}">Re-Attempt auto-populate of missing fields.</a>
      	</c:if>
      </p>
      <div id="watchedButtonSelected">
          <a class="clickable" href="submitEdit.do?attribute=watched&id=${selectedMovie.id}">
              Watched
                  <span class="checked" ><input type="checkbox" name="watched" value="${selectedMovie.watched}" disabled
                      <c:if test="${selectedMovie.watched}">checked</c:if>>
                  </span>

          </a>
      </div>

      <div class="starWrapper"> <%@ include file="stars.jsp" %>
        <span id="ratingSpan">${selectedMovie.userRating}/5</span>
      </div>

      <c:choose>
          <c:when test="${(edit == true) and (item == 'userNotes')}">
              <form action="submitEdit.do" method="post">
                  <input type="hidden" name="attribute" value="userNotes">
                  <textarea style="background-color:white" type="textarea" rows="5" cols="50" name="newValue" >${editNotes}</textarea>
                  <input id="editSubmit" type="submit" name="submitEdit" value="Save Changes">
              </form>
          </c:when>
          <c:otherwise>
              <p>Notes:</p>
              <textarea rows="5" disabled>${selectedMovie.userNotes}</textarea>
              <a class="edit" href="edit.do?attribute=userNotes">Edit</a>
          </c:otherwise>
      </c:choose>

  </div>
</div>
