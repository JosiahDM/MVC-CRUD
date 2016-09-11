package data;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;

public class MovieDbDAO implements MovieDAO {
	private static final String URL = "jdbc:mysql://localhost:3306/moviesdb";
	private static final String user = "root";
	private static final String pass = "root";
	private static final String DEFAULT_IMG="unknown.png";
//	private static final String FULL_POSTER_PATH="/var/lib/tomcat8/webapps/Movies/img/moviePosters/";
	private static final String FULL_POSTER_PATH="/Users/jodev/SD/Java/workspace/Movies/WebContent/";
	private IMDBParser parser;
	
	public MovieDbDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Movie getMovieById(int id) {
		String sqltxt = "SELECT movie.name, mpaa_rating, ud.watched, ud.rating, g.name, description, image, ud.notes "
			    + "FROM user_data ud   RIGHT JOIN movie ON  ud.movie_id = movie.id "
			                        + "RIGHT JOIN movie_genre mg ON mg.movie_id = movie.id "
			                        + "RIGHT JOIN genre g ON g.id = mg.genre_id "
			    + "WHERE movie.id = ? ";
		Movie movie = null; 
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
					 PreparedStatement stmt = conn.prepareStatement(sqltxt);)
		{
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				GenreList<String> genre = new GenreList<>();
				movie = new Movie(rs.getString(1),rs.getString(2), rs.getBoolean(3),rs.getInt(4),
						rs.getString(6),rs.getString(7),rs.getString(8));
				genre.add(rs.getString(5));
				while(rs.next() ) {
					genre.add(rs.getString(5));
				}
				movie.setGenre(genre);
				movie.setId(id);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return movie;
	}

	// TODO maybe have a check here if the movie is already in database, don't add.
	@Override
	public void addMovie(Movie movie) {
		runParse(movie);
		String insertMovie = "INSERT INTO movie (name, mpaa_rating, description, image) "
						   + "VALUES (?, ?, ?, ?)";
		String insertGenres = "INSERT INTO movie_genre (movie_id, genre_id) "
							+ "VALUES (?, ?)";
		String insertUserData = "INSERT INTO user_data (movie_id, rating, user_id, notes, watched) "
							  + "VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmtM = conn.prepareStatement(insertMovie, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement stmtG = conn.prepareStatement(insertGenres);
				PreparedStatement stmtU = conn.prepareStatement(insertUserData);)
		{
			conn.setAutoCommit(false);
			stmtM.setString(1, movie.getName());
			stmtM.setString(2, movie.getMpaaRating());
			stmtM.setString(3, movie.getDescription());
			stmtM.setString(4, movie.getRawImage());
			int mov = stmtM.executeUpdate();
			ResultSet keys = stmtM.getGeneratedKeys();
			if (keys.next()) {
				movie.setId(keys.getInt(1));
			}
			int genreUpdate = 0;
			GenreList<String> genres = (GenreList<String>) movie.getGenre();
			for (int i = 0; i < genres.size(); i++) {
				stmtG.setInt(1, movie.getId());
				stmtG.setInt(2, genres.getGenreId(i));
				genreUpdate = stmtG.executeUpdate();
			}
			stmtU.setInt(1, movie.getId());
			stmtU.setInt(2, movie.getUserRating());
// TODO actual userid would go in next line
			stmtU.setInt(3, 1); 
			stmtU.setString(4, movie.getUserNotes());
			stmtU.setBoolean(5, movie.getWatched());
			int ud = stmtU.executeUpdate();
			conn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	private void runParse(Movie movie) {
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
	}

	// Maybe should break the genre stuff into separate queries?????????????????
	@Override
	public Collection<Movie> getAllValues() {
		Map<Integer, Movie> movies = new HashMap<>();
		String sqltxt = "SELECT movie.id, movie.name, mpaa_rating, ud.watched, ud.rating, g.name, description, image, ud.notes "
			    + "FROM user_data ud   RIGHT JOIN movie ON  ud.movie_id = movie.id "
			                        + "RIGHT JOIN movie_genre mg ON mg.movie_id = movie.id "
			                        + "JOIN genre g ON g.id = mg.genre_id "
			    + "WHERE ud.user_id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
					 PreparedStatement stmt = conn.prepareStatement(sqltxt);)
		{
// TODO this is where an actual user id will go to relate each movie to a user.
			stmt.setInt(1, 1);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				if (movies.containsKey(id)) {
					movies.get(id).getGenre().add(rs.getString(6));
				} else {
					Movie movie = new Movie(rs.getString(2),rs.getString(3), rs.getBoolean(4),
							rs.getInt(5), rs.getString(7),rs.getString(8),rs.getString(9));
					movie.setId(id);
					movie.setGenre(new GenreList<String>());
					movie.getGenre().add(rs.getString(6));
					movies.put(id, movie);
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return movies.values();
	}
	
	@Override
	public Movie deleteMovie(int movieId) {
		String del = "DELETE FROM user_data WHERE movie_id = ? AND user_id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement delete = conn.prepareStatement(del);)
		{
		conn.setAutoCommit(false);
		delete.setInt(1, movieId);
		delete.setInt(2, 1); // TODO real userid would go here
		delete.executeUpdate();
		conn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void updateMovie(Movie movie) {
		String updateSql = "UPDATE user_data "
					  + "SET rating = ?, notes = ?, watched = ? "
				  	  + "WHERE user_id = ? AND movie_id = ?"; 
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement update = conn.prepareStatement(updateSql);)
			{
			conn.setAutoCommit(false);
			update.setInt(1,  movie.getUserRating());
			update.setString(2, movie.getUserNotes());
			update.setBoolean(3, movie.getWatched());
			update.setInt(4, 1); // TODO real userid would go here
			update.setInt(5, movie.getId());
			update.executeUpdate();
			conn.commit();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
	}
	
	
	
	@Override
	public Movie getOne(Predicate<Movie> predicate) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Movie change(int direction, int currentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applyFilter(Predicate<Movie> predicate) {
		// TODO Auto-generated method stub
		
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


}
