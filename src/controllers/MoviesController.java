package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import data.Movie;
import data.MovieDAO;

@Controller
@SessionAttributes("movieId")
public class MoviesController {
	@Autowired
	private MovieDAO movieDao;
	
	// movieId is a session object to view/remember the movie when needed.
	@ModelAttribute("movieId")
	public Integer initMovieId() {
		return 1;
	}
	
	@RequestMapping("results.do")
	public ModelAndView show(@RequestParam("userInput") String in) {
		ModelAndView mv = new ModelAndView("index.jsp");
		mv.addObject("output", in);
		return mv;
	}
	
	@RequestMapping("listMovies.do")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("index.jsp");
		Collection<Movie> allMovies = movieDao.getAllValues();
		mv.addObject("movies", allMovies);
		return mv;
	}
	
	@RequestMapping("newMovie.do")
	public ModelAndView addMovie() {
		ModelAndView mv = new ModelAndView("index.jsp");
		mv.addObject("newMovie", true);
		return mv;
	}
	
	
/**** TODO: Maybe check if new movie equals a movie already in map, if so either 
*** dont write it, or maybe overwrite it...? Ask user?
*/
	// Command object automatically loads values into Movie object from the form in jsp
	@RequestMapping(path="createMovie.do",
			method=RequestMethod.POST)
	public ModelAndView createMovie(Movie movie) {
		movieDao.addMovie(movie);
		ModelAndView mv = new ModelAndView("index.jsp");
		return mv;
	}
	
	@RequestMapping("deleteMovie.do")
	public ModelAndView deleteMovie(@RequestParam("id") Integer id) {
		ModelAndView mv = new ModelAndView("index.jsp");
		Movie movie = movieDao.deleteMovie(id);
//		String result = ( movie != null) ? "Successfully Deleted " + movie.getName() : "Movie not found...nothing deleted."; 
		mv.addObject("movies", movieDao.getAllValues());
		return mv;
	}
	
	@RequestMapping("selectMovie.do")
	public ModelAndView selectMovie(@RequestParam("id") Integer id,
			@ModelAttribute("movieId") Integer movieId) {
		ModelAndView mv = new ModelAndView("index.jsp");
		mv.addObject("movieId", id);
		mv.addObject("selectedMovie", movieDao.getOne(id));
		
		return mv;
	}
	
	@RequestMapping("edit.do")
	public ModelAndView editMovie(@ModelAttribute("movieId") Integer id,
			@RequestParam("attribute") String attribute,
			@RequestParam(value="listedMovie", required=false) Integer listedMovie) {
//		System.out.println("Movie id: " + id);
//		System.out.println("Field to edit: " + edit);
		ModelAndView mv = new ModelAndView("index.jsp");
		Movie movie = null;
		if(listedMovie == null) {
			movie = movieDao.getOne(id);
			mv.addObject("selectedMovie", movie);
		} else {
			movie = movieDao.getOne(listedMovie);
			mv.addObject("movieId", listedMovie);
			mv.addObject("movies", movieDao.getAllValues());
		}
		mv.addObject("edit", selectAttribute(attribute, mv, movie));
		return mv;
	}
	
	@RequestMapping("submitEdit.do")
	public ModelAndView submitEdit(@RequestParam("attribute") String attribute,
			@RequestParam("newValue") String newValue,
			@ModelAttribute("movieId") Integer id) {
		
		ModelAndView mv = new ModelAndView("index.jsp");
		Movie movie = movieDao.getOne(id);
		sendResponse(attribute, newValue, mv, movie);
		mv.addObject("selectedMovie", movie);
		return mv;
	}
	
	@RequestMapping("submitEditInList.do")
	public ModelAndView submitEditInList(@RequestParam("attribute") String attribute,
			@RequestParam(value="newValue", required=false) String newValue,
			@RequestParam("id") Integer id) {
		Movie movie = movieDao.getOne(id);
		ModelAndView mv = new ModelAndView("index.jsp");
		mv.addObject("movieId", id);
		sendResponse(attribute, newValue, mv, movie);
		mv.addObject("movies", movieDao.getAllValues());
		return mv;
	}
	
	// Boolean value listView is true if on the main "List All" page and wants to edit
	// False if in "Selected" view.
	private void sendResponse( String attribute, String newValue, ModelAndView mv, 
							   Movie movie) {
//		String destination = (listView) ? "movies" : "selectedMovie";
//		Object toSend = (listView) ? movieDao.getAllValues() : movie;
		switch (attribute) {
		case "userNotes":
			movie.setUserNotes(newValue);
			break;
		case "userRating":
			movie.setUserRating(Integer.parseInt(newValue));
			movie.setWatched(true);
			break;
		case "watched":
			movie.setUserRating(0);
			movie.setWatched(!movie.getWatched());
		default:
			break;
		}
	}
	
	
	
	private boolean selectAttribute(String attribute, ModelAndView mv, Movie movie) {
		switch (attribute) {
		case "userNotes":
			mv.addObject("item", "userNotes");
			mv.addObject("editNotes", movie.getUserNotes());
			return true;
//		case "":
//			break;
		default:
			mv.addObject("item", "none");
			return false;
		}
	}
	
}

















