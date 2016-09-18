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
      				   selectedMovie.year == ''  ||
                       empty selectedMovie.mpaaRating   }">
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

      <div class="starWrapper">
          <div class="stars">

              <form action="submitEdit.do" method="get">
                <input type="hidden" name="attribute" value="userRating"/>
                <input class="star star-5" id="star-5" type="radio" name="newValue"
                    value="5" onclick="this.form.submit()"
                    <c:if test="${ selectedMovie.userRating == 5}">checked</c:if>
                />
                <label class="star star-5" for="star-5"></label>
                <input class="star star-4" id="star-4" type="radio" name="newValue"
                    value="4" onclick="this.form.submit()"
                    <c:if test="${ selectedMovie.userRating == 4}">checked</c:if>
                />
                <label class="star star-4" for="star-4"></label>
                <input class="star star-3" id="star-3" type="radio" name="newValue"
                    value="3" onclick="this.form.submit()"
                    <c:if test="${ selectedMovie.userRating == 3}">checked</c:if>
                />
                <label class="star star-3" for="star-3"></label>
                <input class="star star-2" id="star-2" type="radio" name="newValue"
                    value="2" onclick="this.form.submit()"
                    <c:if test="${ selectedMovie.userRating == 2}">checked</c:if>
                />
                <label class="star star-2" for="star-2"></label>
                <input class="star star-1" id="star-1" type="radio" name="newValue"
                    value="1" onclick="this.form.submit()"
                    <c:if test="${ selectedMovie.userRating == 1}">checked</c:if>
                />
                <label class="star star-1" for="star-1"></label>
              </form>
          </div>

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
