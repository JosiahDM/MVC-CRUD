# MVC-CRUD
~~Week 7 Skill Distillery Project~~

UPDATED Week 8 - Added MySQL Database backend

Created by: Josiah Moye

## Project 

This project was our second full web application and our first time using Spring MVC. The goal was to create a CRUD
(Create, Read, Update, Delete) app with basic functionality. This specific project is a movie list/journal which allows 
users to keep track of their movies by adding new movies, marking them if they've watched the movie or not, adding notes 
to movies, displaying all movies and deleting movies. 

An extra feature I added was auto-fetching/population of new movie images, description, genre, and MPAA rating. When a
user adds a new movie, the app automatically attempts to find the movie on IMDB.com, then downloads its poster image and the above
information.

## Technologies used:
  - Java
  - Spring MVC
  - JSP
  - Jsoup Java HTML Parser
  - Apache Commons IO
  - HTML/CSS
  
### Week 8:
  - MySQL
  - JDBC
  
## Wish List

Some features I didn't/couldn't get around to in the time frame that I would like to add in the future:
  - display options? Toggle what should be shown in the list view…etc.
  - maybe get a trailer to auto populate in selected view
  - ~~need to do an equality check on new movie creation. Prompt to overwrite or cancel.~~ (Done - week 8)
  - sorting by genre before displaying
	    - also by rating, name, watched.
  - ~~rating display as stars rather than text~~ (Halfway there...)
  - ~~would like to have the ability to re-query for auto population of fields in case 
	something went wrong during search, user can try again without having to add
	a movie again~~ (Done - week 8)
	
## Stumbling points:
  - Figuring out how to pull the images from IMDB was a challenge.
  - Formatting the UI. Still have some issues with it that need tightened up. CSS pains.
  - I made the page almost like a "manual REST" which was tedious and difficult with only Java and JSP, so I could have gone
   a different route on that to save time. Javascript will hopefully clean that up in the future.


## Week 8 Updates:
  - Added MySQL backend with minimal changes to the overall controller/Java logic.
  	- Validates input to disallow duplicate entries.
	- Set up the structure for addition of future users. The results pulled/inserted into the database are connected to specific user id's. 
  - Added a few of my previous wishlist items such as:
  	- Ability to attempt a re-fetch of external movie data if something goes wrong during the initial creation of the movie. So if the user adds a movie and for some reason it fails to pull the movie image, description, MPAA rating, or Genre, the user can attempt to retry that fetch.
  	- Equality check on insertion. As mentioned above will not allow duplicate entries.
  	- Halfway there on the user rating stars. Working in the selected movie display, but not the list version. 

## Week 8 Stumbling Points (AKA Learning Lessons :) )
  - Understanding foreign key constraints and how to deal with them. 
  - Correctly using JDBC API. Minor mistakes but once I figured it out, things went smoothly.
  - HTML/CSS/JS problems in displaying the star rating system on the list view. The form would only update the first item even though each form had unique ids, correct values relating to the movie id, everything looked good. Not sure why it wasn't sending the correct id in the request. Wrestled with it for far too long before deciding to revert back to the anchor tag method. May want to revisit and fix this.
