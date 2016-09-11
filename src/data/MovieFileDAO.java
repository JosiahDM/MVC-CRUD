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
	private static final String DEFAULT_IMG="unknown.png";
	private static final String FULL_POSTER_PATH="/var/lib/tomcat8/webapps/Movies/img/moviePosters/";
	private Map<Integer, Movie> movies = new HashMap<>();
	private IMDBParser parser;
	
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
				ArrayList<String> genre = new GenreList<>(Arrays.asList(tokens[4].split(",")));
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
		parser = new IMDBParser(movie.getName());
		if (parser.getImageLocation().equals(DEFAULT_IMG)) {
			movie.setImage(DEFAULT_IMG);
		} else {
			String fileName = downloadMovieImage(parser.getImageLocation(), movie.getName());
			movie.setImage(fileName);
		}
		movie.setDescription(parser.getParsedDescription());
		movie.setGenre(parser.getParsedGenre());
		movie.setMpaaRating(parser.getParsedRating());
		
//			movie.setImage(nameToFileName(movie.getName()));

		movies.put(movie.getId(), movie);
	}
	
	// try to do the download, if exceptions occur returns the default image, 
	// else returns the new file name
	public String downloadMovieImage(String movieLocation, String name) {
		String outFile = DEFAULT_IMG;
		try {
			URL url = new URL(movieLocation);
			String fileName = nameToFileName(name);
			File destination = new File(FULL_POSTER_PATH+fileName);
			FileUtils.copyURLToFile(url, destination, 10000, 15000);
			outFile = fileName;
		} catch (MalformedURLException mfe) {
			mfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return outFile;
	}
	
	public String nameToFileName(String movieName) {
		StringBuilder fileName = new StringBuilder();
		String[] splitName = movieName.toLowerCase().split(" ");
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
	public Movie getMovieById(int id) {
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
	@Override
	public void updateMovie(Movie movie) {
		// only relevant to the DB version
	}
}
