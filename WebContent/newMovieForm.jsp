<div id="newMovie">
    <div class="centering">
        <div class="spacing">

        </div>
        <h1 class="h1" id="newMovieHeader">Add a movie to your list</h1>
        <div id="formStyling">
            <form id="movieForm" action="createMovie.do" method="post">
                <ul style="list-style-type:none">
                    <li>
                        <input class="textInput" type="text" name="name" placeholder="Movie Name">

                    </li>
                    <li>
                        <input type="checkbox" name="watched" value="true">Already seen?
                    </li>
                    <li>
                        <select class="options" name="mpaaRating" form="movieForm" required>
                            <option value="null" selected>I'm not sure</option>
                            <option value="G">G</option>
                            <option value="PG">PG</option>
                            <option value="PG-13">PG-13</option>
                            <option value="R">R</option>
                            <option value="X">X</option>
                            <option value="NC-17">NC-17</option>
                        </select>
                    </li>
                    <li>
                        <select class="options" name="userRating" form="movieForm" required>Enter your rating 1-5
                            <option value="0">0 - Haven't seen it yet</option>
                            <option value="1">1 - Nope</option>
                            <option value="2">2 - Didn't like</option>
                            <option value="3">3 - It's ok</option>
                            <option value="4">4 - Good</option>
                            <option value="5">5 - Amazing</option>
                        </select>
                    </li>
                    <li>
                        <ul>
                            <li>
                            <input type="checkbox" name="genre" value="Horror">Horror
                            <input type="checkbox" name="genre" value="Action">Action
                            <input type="checkbox" name="genre" value="Sci-Fi">Sci-Fi
                            <input type="checkbox" name="genre" value="Drama">Drama
                            </li>
                            <li>
                            <input type="checkbox" name="genre" value="Family">Family
                            <input type="checkbox" name="genre" value="Comedy">Comedy
                            <input type="checkbox" name="genre" value="Thriller">Thriller
                            <input type="checkbox" name="genre" value="Suspense">Suspense
                            </li>
                            <li>
                            <input type="checkbox" name="genre" value="Adventure">Adventure
                            <input type="checkbox" name="genre" value="Romance">Romance
                            <input type="checkbox" name="genre" value="Documentary">Documentary
                            <input type="checkbox" name="genre" value="Musical">Musical
                            </li>
                        <ul>
                    </li>
                    <li>
                        <input class="textInput" type="text" name="description" placeholder="Movie Description Here...">
                    </li>
                    <li>
                        <input class="textInput" type="text" name="userNotes" placeholder="Any notes you'd like to enter here...">
                    </li>
                    <li id="submitWrap">
                        <input type="hidden" name="image" value="unknown.png">
                        <input class="submitButton" type="submit" name="submit" value="Submit">
                    </li>
                </ul>
            </form>
        </div>
    </div>
</div>
