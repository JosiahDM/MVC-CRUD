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
	private static final String user = "application";
	private static final String pass = "application";
	private static final String DEFAULT_IMG = "unknown.png";
//	 private static final String FULL_POSTER_PATH="/var/lib/tomcat8/webapps/Movies/img/moviePosters/";
	private static final String FULL_POSTER_PATH = "/Users/jodev/SD/Java/workspace/Movies/WebContent/img/moviePosters/";
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
				+ "RIGHT JOIN movie_genre mg ON mg.movie_id = movie.id " + "RIGHT JOIN genre g ON g.id = mg.genre_id "
				+ "WHERE movie.id = ? AND user_id = ?";
		Movie movie = null;
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sqltxt);) {
			stmt.setInt(1, id);
			stmt.setInt(2, 1); // TODO real userid
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				GenreList<String> genre = new GenreList<>();
				movie = new Movie(rs.getString(1), rs.getString(2), rs.getBoolean(3), rs.getInt(4), rs.getString(6),
						rs.getString(7), rs.getString(8));
				genre.add(rs.getString(5));
				while (rs.next()) {
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

	// Maybe should break the genre stuff into separate queries?????????????????
	@Override
	public Collection<Movie> getAllValues() {
		Map<Integer, Movie> movies = new HashMap<>();
		String sqltxt = "SELECT movie.id, movie.name, mpaa_rating, ud.watched, ud.rating, g.name, description, image, ud.notes "
				+ "FROM user_data ud   RIGHT JOIN movie ON  ud.movie_id = movie.id "
				+ "RIGHT JOIN movie_genre mg ON mg.movie_id = movie.id " + "JOIN genre g ON g.id = mg.genre_id "
				+ "WHERE ud.user_id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sqltxt);) {
			// TODO this is where an actual user id will go to relate each movie
			// to a user.
			stmt.setInt(1, 1);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				if (movies.containsKey(id)) {
					movies.get(id).getGenre().add(rs.getString(6));
				} else {
					Movie movie = new Movie(rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5),
							rs.getString(7), rs.getString(8), rs.getString(9));
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

	// Adds a movie to the database if it isn't already in.
	// If movie is in database, will check if user already has it as well.
	// If user doesn't have it, insert the movie id into user_data so the user
	// has it.
	// no new movie entries created unless it is an entirely new movie to the
	// DB.
	@Override
	public void addMovie(Movie movie) {
		setUnique(movie);
		String movies = "SELECT name, id, year FROM movie";
		String userMovies = "SELECT m.name, m.id, m.year " + "FROM movie m JOIN user_data ud ON ud.movie_id = m.id "
				+ "WHERE user_id = ?;";
		String insertMovie = "INSERT INTO movie (name, mpaa_rating, description, image, year) "
				+ "VALUES (?, ?, ?, ?, ?)";
		String insertGenres = "INSERT INTO movie_genre (movie_id, genre_id) " + "VALUES (?, ?)";
		String insertUserData = "INSERT INTO user_data (movie_id, rating, user_id, notes, watched) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement userList = conn.prepareStatement(userMovies);
				PreparedStatement list = conn.prepareStatement(movies);
				PreparedStatement stmtM = conn.prepareStatement(insertMovie, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement stmtG = conn.prepareStatement(insertGenres);
				PreparedStatement stmtU = conn.prepareStatement(insertUserData);) {
			userList.setInt(1, 1); // real userid will go here
			ResultSet rs = userList.executeQuery();
			// Add movie data only if movie isn't already in database
			boolean inDb = movieInDb(movie, list);
			conn.setAutoCommit(false);
			if (!inDb) {
				fullParse(movie);
				movieDataInsert(movie, stmtM);
				genreInsert(movie, stmtG);
				inDb = true;
			}
			if (inDb && !userHasMovie(movie, rs)) {
				userDataInsert(movie, stmtU);
			}
			conn.commit();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	// This updates the user_data for the movie
	@Override
	public void updateMovie(Movie movie) {
		String updateSql = "UPDATE user_data " + "SET rating = ?, notes = ?, watched = ? "
				+ "WHERE user_id = ? AND movie_id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement update = conn.prepareStatement(updateSql);) {
			conn.setAutoCommit(false);
			update.setInt(1, movie.getUserRating());
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
	public Movie deleteMovie(int movieId) {
		String del = "DELETE FROM user_data WHERE movie_id = ? AND user_id = ?";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement delete = conn.prepareStatement(del);) {
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

	// Attempt to update the auto-fetched movie data.
	// In case something failed in the first attempt, user can retry.
	@Override
	public void retryParse(Movie movie) {
		parser = new IMDBParser(movie.getName(), movie);
		fullParse(movie);
		String updateSql = "UPDATE movie " 
		                 + "SET name = ?, mpaa_rating = ?, description = ?, image = ?, year = ? "
		                 + "WHERE id = ?";
		String updateGenre = "INSERT INTO movie_genre (movie_id, genre_id) "
						   + "VALUES (?, ?);";
		String deleteEmptyGenre = "DELETE from movie_genre WHERE movie_id = ? and genre_id = 27";
		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement update = conn.prepareStatement(updateSql);
				PreparedStatement genres = conn.prepareStatement(updateGenre);
				PreparedStatement delete = conn.prepareStatement(deleteEmptyGenre)) 
		{
			conn.setAutoCommit(false);
			try { // needed a separate try/catch here to ensure if this part is successful, it gets committed
				update.setString(1, movie.getName());
				update.setString(2, movie.getMpaaRating());
				update.setString(3, movie.getDescription());
				update.setString(4, movie.getRawImage());
				update.setString(5, movie.getYear());
				update.setInt(6, movie.getId());
				update.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				System.err.println("Movie update 1");
				e.printStackTrace();
			}
			genreInsert(movie, genres);
			delete.setInt(1, movie.getId());
			delete.executeUpdate();
			conn.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	private void setUnique(Movie movie) {
		parser = new IMDBParser(movie.getName());
		movie.setName(parser.getMovieName());
		movie.setYear(parser.getParsedYear());
	}

	// Movie name and date combination should be unique
	private boolean movieInDb(Movie movie, PreparedStatement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			if (movie.getName().equals(rs.getString(1)) && movie.getYear().equals(rs.getString(3))) {
				movie.setId(rs.getInt(2));
				return true;
			}
		}
		return false;
	}

	// Check if user already has this movie
	private boolean userHasMovie(Movie movie, ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while (rs.next()) {
			if (movie.getId() == rs.getInt(2)) {
				return true;
			}
		}
		return false;
	}

	private void movieDataInsert(Movie movie, PreparedStatement stmt) throws SQLException {
		stmt.setString(1, movie.getName());
		stmt.setString(2, movie.getMpaaRating());
		stmt.setString(3, movie.getDescription());
		stmt.setString(4, movie.getRawImage());
		stmt.setString(5, movie.getYear());
		stmt.executeUpdate();
		ResultSet keys = stmt.getGeneratedKeys();
		if (keys.next()) {
			movie.setId(keys.getInt(1));
		}
	}

	private void genreInsert(Movie movie, PreparedStatement stmt) throws SQLException {
		GenreList<String> genres = (GenreList<String>) movie.getGenre();
		for (int i = 0; i < genres.size(); i++) {
			stmt.setInt(1, movie.getId());
			stmt.setInt(2, genres.getGenreId(i));
			stmt.executeUpdate();
		}
	}

	private void userDataInsert(Movie movie, PreparedStatement stmt) throws SQLException {
		stmt.setInt(1, movie.getId());
		stmt.setInt(2, movie.getUserRating());
		// TODO actual userid would go in next line
		stmt.setInt(3, 1);
		stmt.setString(4, movie.getUserNotes());
		stmt.setBoolean(5, movie.getWatched());
		stmt.executeUpdate();
	}

	private void fullParse(Movie movie) {
		// If parser doesn't currently have the movie data for some reason, run
		// a new fetch/parse
		if (!parser.getMovieName().equals(movie.getName())) {
			parser = new IMDBParser(movie.getName());
			parser.parseAll();
		} else {
			parser.parseAll();
		}
		if (parser.getImageLocation().equals(DEFAULT_IMG)) {
			movie.setImage(DEFAULT_IMG);
		} else {
			String fileName = downloadMovieImage(parser.getImageLocation(), movie.getName() + movie.getYear());
			movie.setImage(fileName);
		}
		movie.setDescription(parser.getParsedDescription());
		movie.setGenre(parser.getParsedGenre());
		movie.setMpaaRating(parser.getParsedRating());
		movie.setYear(parser.getParsedYear());
	}

	// try to do the download, if exceptions occur returns the default image,
	// else returns the new file name
	public String downloadMovieImage(String movieLocation, String name) {
		String outFile = DEFAULT_IMG;
		try {
			URL url = new URL(movieLocation);
			String fileName = nameToFileName(name);
			File destination = new File(FULL_POSTER_PATH + fileName);
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
		String out = fileName.toString().replaceAll("\\W", "");
		return out + ".jpg";
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

}
