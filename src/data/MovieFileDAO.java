package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

public class MovieFileDAO implements MovieDAO {
	private static final String FILE_NAME="/WEB-INF/movies.csv";
	private static final String POSTER_DIR="/img/moviePosters/";
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
				int userRating = Integer.parseInt(tokens[3]);
				List<String> genre = new ArrayList<>(Arrays.asList(tokens[4].split(",")));
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
	
	public void downloadMovieImage(Movie movie) throws IOException, MalformedURLException {
		IMDBParser parser = new IMDBParser();
		String movieUrl = parser.googleMovieUrl(movie.getName());
		URL movieImageUrl = new URL(parser.getImageLocation(movieUrl));
		File destination = new File(POSTER_DIR+nameToFileName(movie.getName()));
;		FileUtils.copyURLToFile(movieImageUrl, destination, 10000, 15000);
	}
	
	public String nameToFileName(String movieName) {
		StringBuilder fileName = new StringBuilder();
		String[] splitName = movieName.split(" ");
		for (String word : splitName) {
			fileName.append(word);
		}
		return fileName.toString()+".jpg";
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
	
	@Override
	public Movie deleteMovie(int id) {
		return movies.remove(id);
	}
}
