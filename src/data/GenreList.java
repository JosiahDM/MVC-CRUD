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
	
}
