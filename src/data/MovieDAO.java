package data;

import java.util.Collection;
import java.util.function.Predicate;


public interface MovieDAO {
	public void addMovie(Movie movie);
	public Movie getOne(Predicate<Movie> predicate);
	public Movie getOne(int id);
	public Movie change(int direction, int currentId);
	public void applyFilter(Predicate<Movie> predicate);
	public Collection<Movie> getAllValues();
	public Movie deleteMovie(int id);
	
}
