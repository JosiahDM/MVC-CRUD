package data;

import java.util.List;

public class Movie {
	private int id;
	private String name;
	private String mpaaRating;
	private String userNotes;
	private int userRating;
	private List<String> genre;
	private String description;
	private String image;
	private boolean watched;
	public static int numCreated;
	
	public Movie() {
		this.id = ++numCreated;
	}
	
	public Movie(String name, String mpaaRating, boolean watched, int userRating, List<String> genre, String description, String image, String userNotes) {
		this.id = ++numCreated;
		this.name = name;
		this.mpaaRating = mpaaRating;
		this.watched = watched;
		this.userRating = userRating;
		this.genre = genre;
		this.description = description;
		setImage(image);
		this.userNotes = userNotes;
	}
	
	
	// Getters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getMpaaRating() {
		return mpaaRating;
	}
	
	public boolean getWatched() {
		return watched;
	}

	public String getUserNotes() {
		return userNotes;
	}

	public int getUserRating() {
		return userRating;
	}

	public List<String> getGenre() {
		return genre;
	}

	public String getDescription() {
		return description;
	}

	public String getImage() {
		return image;
	}
	
	
	// Setters
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMpaaRating(String mpaaRating) {
		this.mpaaRating = mpaaRating;
	}
	
	public void setWatched(boolean watched) {
		this.watched = watched;
	}
	
	public void setUserNotes(String userNotes) {
		this.userNotes = userNotes;
	}

	public void setUserRating(int userRating) {
		this.userRating = userRating;
	}

	public void setGenre(List<String> genre) {
		this.genre = genre;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImage(String image) {
		this.image = "img/moviePosters/" + image;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", name=" + name + ", mpaaRating=" + mpaaRating + ", userNotes=" + userNotes
				+ ", userRating=" + userRating + ", genre=" + genre + ", description=" + description + ", image="
				+ image + ", watched=" + watched + "]";
	}
	
}
