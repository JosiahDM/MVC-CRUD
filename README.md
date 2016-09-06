# MVC-CRUD
Week 7 Skill Distillery Project

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
  
## Wish List

Some features I didn't/couldn't get around to in the time frame that I would like to add in the future:
  - display options? Toggle what should be shown in the list view…etc.
  - maybe get a trailer to auto populate in selected view
  - need to do an equality check on new movie creation. Prompt to overwrite or cancel.
  - sorting by genre before displaying
	    - also by rating, name, watched.
  - rating display as stars rather than text
  - would like to have the ability to re-query for auto population of fields in case 
	something went wrong during search, user can try again without having to add
	a movie again
	
## Stumbling points:
  - Figuring out how to pull the images from IMDB was a challenge.
  - Formatting the UI. Still have some issues with it that need tightened up. CSS pains.
  - I made the page almost like a "manual REST" which was tedious and difficult with only Java and JSP, so I could have gone
   a different route on that to save time. Javascript will hopefully clean that up in the future.
