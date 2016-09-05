package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IMDBParser {
	private String movieName;
	private Document doc;
	private String imdbUrl;
	private String imageLocation;
	private String parsedDescription;
	private GenreList<String> parsedGenre;
	private String parsedRating;
	private boolean hasDoc;
	private static final String DEFAULT_LOC = "unknown.png";
	private static final String GENRE_OPTIONS = "action, adventure, animation, biography,"  +
												"comedy, crime, documentary, drama, family," +
												"fantasy, film-noir, game-show, history, horror, " +
												"music, musical, mystery, news, reality-tv," +
												"romance, sci-fi, sport, talk-show, thriller," +
												"war, western";
	
	public IMDBParser() {
		
	}
	
	public IMDBParser(String movieName) {
		this.movieName = movieName;
		googleMovieUrl();
		fetchDoc();
		parseAll();
	}
	
	public void parseAll() {
		parseImageLocation();
		parseGenre();
		parseRating();
		parseDescription();
	}
	
	public void fetchDoc() {
		try {
			doc = Jsoup.connect(imdbUrl).get();
			hasDoc = true;
		} catch (IOException e) {
			hasDoc = false;
		}
	}
	
	public String googleMovieUrl() {
		imdbUrl = "";
		try {
			doc = Jsoup.connect("https://www.google.com/search?q=site:imdb.com+"+movieName+"&btnI")
					.userAgent("Mozilla")
					.cookie("auth", "token")
					.timeout(3000)
					.get();
			
			imdbUrl = doc.baseUri();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imdbUrl;
	}

	
	public String parseImageLocation() {
		String loc = DEFAULT_LOC;
		if (!this.hasDoc) { // no doc, google search failed or connection to imdb failed
			this.imageLocation = loc;
			return loc;
		}

		Elements media = doc.select(".poster");
		for (Element element : media) {
			loc = element.children().select("img").attr("src");
		}
		loc = loc.split("._V1_")[0] + "._V1_.jpg";

		// could use a bit more hardy validation here
		if(loc.length() < 50 || !loc.contains("ia.media-imdb.com/images")) {
			loc=DEFAULT_LOC;
		}
		imageLocation = loc;
		return loc;
	}
	
	public String parseDescription() {
		if (!hasDoc) {
			return "Not found.";
		}
		String description = "";
		Elements media = doc.select(".summary_text");
		description = media.text();
		this.parsedDescription = description;
		return description;
	}
	
	public String parseRating() {
		if (!hasDoc) {
			return "";
		}
		String rating = "";
		rating = doc.select(".subtext")
				.select("meta[itemprop=contentRating]")
				.attr("content");
		switch (rating.toUpperCase()) {
		case "G": case "PG": case "PG-13":
		case "R": case "NC-17": case "X":
			break;
		default:
			rating = "";
			break;
		}
		parsedRating = rating;
		return rating;
	}
	
	public List<String> parseGenre() {
		GenreList<String> genre = new GenreList<>();
		if (!hasDoc) {
			return genre;
		}
		String hit = doc.select(".subtext").select(".itemprop").html();
		for (String string : hit.split("\n")) {
			if (string.length() > 0 && GENRE_OPTIONS.contains(string.toLowerCase())) {
				genre.add(string);
			}
		}
		parsedGenre = genre;
		return genre;
	}

	public String getMovieName() {
		return movieName;
	}

	public Document getDoc() {
		return doc;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public String getParsedDescription() {
		return parsedDescription;
	}

	public GenreList<String> getParsedGenre() {
		return parsedGenre;
	}

	public String getParsedRating() {
		return parsedRating;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	
//	public static void main(String[] args) {
//		IMDBParser parser = new IMDBParser("asdfafwrt3twer");
//		System.out.println(parser.parseImageLocation());
//	}
}
