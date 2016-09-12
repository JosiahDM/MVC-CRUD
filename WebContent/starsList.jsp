<div class="stars">

    <form action="submitEditInList.do" method="get" id="rating_${movie.id}">
      <input type="hidden" name="id" value="${movie.id}">
      <input type="hidden" name="attribute" value="userRating"/>
      <input class="star star-5" id="star-5" type="radio" name="newValue"
          value="5" onclick="submitForm('rating_${movie.id}')"
          <c:if test="${ selectedMovie.userRating == 5}">checked</c:if>
      />
      <label class="star star-5" for="star-5"></label>
      <input class="star star-4" id="star-4" type="radio" name="newValue"
          value="4" onclick="submitForm('rating_${movie.id}')"
          <c:if test="${ selectedMovie.userRating == 4}">checked</c:if>
      />
      <label class="star star-4" for="star-4"></label>
      <input class="star star-3" id="star-3" type="radio" name="newValue"
          value="3" onclick="submitForm('rating_${movie.id}')"
          <c:if test="${ selectedMovie.userRating == 3}">checked</c:if>
      />
      <label class="star star-3" for="star-3"></label>
      <input class="star star-2" id="star-2" type="radio" name="newValue"
          value="2" onclick="submitForm('rating_${movie.id}')"
          <c:if test="${ selectedMovie.userRating == 2}">checked</c:if>
      />
      <label class="star star-2" for="star-2"></label>
      <input class="star star-1" id="star-1" type="radio" name="newValue"
          value="1" onclick="submitForm('rating_${movie.id}')"
          <c:if test="${ selectedMovie.userRating == 1}">checked</c:if>
      />
      <label class="star star-1" for="star-1"></label>
    </form>
</div>


<!--  Another way I tried...

<div class="stars">

    <form action="submitEditInList.do" method="get" id="rating_${movie.id}">
      <input type="hidden" name="ratingId" value="${movie.id}">
          <input type="hidden" name="id" value="${movie.id}">
      <input type="hidden" name="attribute" value="userRating"/>
      <input class="star star-5" id="star-5" type="radio" name="newValue"
          value="5" onclick="document.getElementById('rating_${movie.id}').submit();"
          <c:if test="${ selectedMovie.userRating == 5}">checked</c:if>
      />
      <label class="star star-5" for="star-5"></label>
      <input class="star star-4" id="star-4" type="radio" name="newValue"
          value="4" onclick="document.getElementById('rating_${movie.id}').submit();"
          <c:if test="${ selectedMovie.userRating == 4}">checked</c:if>
      />
      <label class="star star-4" for="star-4"></label>
      <input class="star star-3" id="star-3" type="radio" name="newValue"
          value="3" onclick="document.getElementById('rating_${movie.id}').submit();"
          <c:if test="${ selectedMovie.userRating == 3}">checked</c:if>
      />
      <label class="star star-3" for="star-3"></label>
      <input class="star star-2" id="star-2" type="radio" name="newValue"
          value="2" onclick="document.getElementById('rating_${movie.id}').submit();"
          <c:if test="${ selectedMovie.userRating == 2}">checked</c:if>
      />
      <label class="star star-2" for="star-2"></label>
      <input class="star star-1" id="star-1" type="radio" name="newValue"
          value="1" onclick="document.getElementById('rating_${movie.id}').submit();"
          <c:if test="${ selectedMovie.userRating == 1}">checked</c:if>
      />
      <label class="star star-1" for="star-1"></label>
    </form>
</div>
-->
