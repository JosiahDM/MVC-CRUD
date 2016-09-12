package data;

import java.util.ArrayList;
import java.util.Collection;

public class GenreList<E> extends ArrayList<E> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GenreList() {
		super();
	}
	
	public GenreList(Collection<? extends E> c) {
		super(c);
	}
	
	
	public String toString() {
		StringBuilder out = new StringBuilder();
		for (E string : this) {
			out.append(string+", ");
		}
		if(out.length() >= 2) {
			out.delete(out.length()-2, out.length());
		}
		return out.toString();
	}
	
	public int getGenreId(int index) {
		String genre = (String) this.get(index);
		switch (genre.toLowerCase()) {
			case "action": return 1; case "adventure": return 2; case "animation": return 3;
			case "biography": return 4; case "comedy": return 5; case "crime": return 6;
			case "documentary": return 7; case "drama": return 8; case "family": return 9;
			case "fantasy": return 10; case "film-noir": return 11; case "game-show": return 12;
			case "history": return 13; case "horror": return 14; case "music": return 15;
			case "musical": return 16; case "mystery": return 17; case "news": return 18;
			case "reality-tv": return 19; case "romance": return 20; case "sci-fi": return 21;
			case "sport": return 22; case "talk-show": return 23; case "thriller": return 24;
			case "war": return 25; case "western": return 26;
			default: return 27;
		}
	}

	
}
