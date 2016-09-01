package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	
	
}
