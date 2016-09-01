package data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

public class MovieFileDAO implements MovieDAO {
	private static final String FILE_NAME="/WEB-INF/movies.csv";
	private Map<Integer, Movie> movies = new HashMap<>();
	
	@Autowired
	private WebApplicationContext wac;
	
	/*
	 * The @PostConstruct method is called by Spring after 
	 * object creation and dependency injection
	 */
	@PostConstruct
	public void init() {
		// Retrieve input stream from the servlet context
		try (
				InputStream is = wac.getServletContext().getResourceAsStream(FILE_NAME);
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
			) {
			String line = br.readLine();
			while( (line = br.readLine()) != null) {
				String[] tokens = line.split("\\|");
				String name = tokens[0];
				String mpaaRating = tokens[1];
				Boolean watched = Boolean.parseBoolean(tokens[2]);
				String userRating = tokens[3];
				String genre = tokens[4];
				String description = tokens[5];
				String image = tokens[6];
				String userNotes = tokens[7];
				Movie movie = new Movie(name, mpaaRating, watched, userRating, genre, description, image, userNotes);
				movies.put(movie.getId(), movie);
			}
			
		} catch(Exception e) {
			System.err.println(e);
		}
	}
	@Override
	public void addMovie(Movie movie) {
		movies.put(movie.getId(), movie);
	}
	
	@Override
	public Movie getOne(Predicate<Movie> predicate) {
		return null;
	}
	
	@Override
	public Movie getOne(int id) {
		return movies.get(id);
	}
	
	@Override
	public Movie change(int direction, int currentId) {
		return null;
	}
	
	@Override
	public void applyFilter(Predicate<Movie> predicate){
		
	}
	
	@Override
	public Collection<Movie> getAllValues() {
		return movies.values();
	}
}
