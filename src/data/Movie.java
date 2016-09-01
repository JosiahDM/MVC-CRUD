package data;

public class Movie {
	private int id;
	private String name;
	private String mpaaRating;
	private String userNotes;
	private String userRating;
	private String genre;
	private String description;
	private String image;
	private Boolean watched;
	public static int numCreated;
	
	public Movie() {
	}
	
	public Movie(String name, String mpaaRating, Boolean watched, String userRating, String genre, String description, String image, String userNotes) {
		this.id = ++numCreated;
		this.name = name;
		this.mpaaRating = mpaaRating;
		this.watched = watched;
		this.userRating = userRating;
		this.genre = genre;
		this.description = description;
		this.image = image;
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
	
	public Boolean getWatched() {
		return watched;
	}

	public String getUserNotes() {
		return userNotes;
	}

	public String getUserRating() {
		return userRating;
	}

	public String getGenre() {
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
	
	public void setWatched(Boolean watched) {
		this.watched = watched;
	}
	
	public void setUserNotes(String userNotes) {
		this.userNotes = userNotes;
	}

	public void setUserRating(String userRating) {
		this.userRating = userRating;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", name=" + name + "]";
	}
	
	
}
