<div class="clearfix" id="selected">
    <div class="sm-col sm-col-6 center">
        <div id="selectedImage">
            <img src="${selectedMovie.image}" />
        </div>
    </div>
  <div class="sm-col sm-col-6" id="details">
      <h2 class="h2">${selectedMovie.name}</h2>
      <p>${selectedMovie.mpaaRating} | ${selectedMovie.genre}</p>
      <p>Description: ${selectedMovie.description}</p>
      <div id="watchedButtonSelected">
          <a class="clickable" href="submitEdit.do?attribute=watched&id=${selectedMovie.id}">
              Watched
                  <span class="checked" ><input type="checkbox" name="watched" value="${selectedMovie.watched}" disabled
                      <c:if test="${selectedMovie.watched}">checked</c:if>>
                  </span>

          </a>
      </div>

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
