package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import data.Movie;
import data.MovieDAO;

@Controller
public class MoviesController {
	@Autowired
	private MovieDAO movieDao;
	
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
	
	@RequestMapping(path="createMovie.do",
			method=RequestMethod.POST)
	public ModelAndView createMovie( @RequestParam("name") String name,
									 @RequestParam("watched") String watchedInput,
									 @RequestParam("mpaaRating") String mpaaRating,
									 @RequestParam("userRating") Double userRating,
									 @RequestParam("genre") String genreInput,
									 @RequestParam("description") String description,
									 @RequestParam("notes") String userNotes) {
		ModelAndView mv = new ModelAndView("index.jsp");

		Boolean watched = Boolean.parseBoolean(watchedInput.split(",")[0]);
		List<String> genre = new ArrayList<>(Arrays.asList(genreInput.split(",")));
		genre.remove("null");
		Movie movie = new Movie(name, mpaaRating, watched, userRating, genre, description, "unknown.png", userNotes);
		movieDao.addMovie(movie);
		mv.addObject("result", "Successfully Added " + movie.getName() + "!");
		return mv;
	}
	
}

















