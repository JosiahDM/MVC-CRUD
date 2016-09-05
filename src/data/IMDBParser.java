package data;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IMDBParser {
	private String movieName;
	private Document doc;
	
	
	public IMDBParser() {
		
	}
	
	public String googleMovieUrl(String movieName) {
		String url = "";
		try {
			doc = Jsoup.connect("https://www.google.com/search?q=site:imdb.com+"+movieName+"&btnI")
					.userAgent("Mozilla")
					.cookie("auth", "token")
					.timeout(3000)
					.get();
			
			url = doc.baseUri();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	public String getImageLocation(String movieUrl) {
		String loc = "unknown.jpeg";
		try {
			doc = Jsoup.connect(movieUrl).get();
			Elements media = doc.select(".poster");
			for (Element element : media) {
				loc = element.children().select("img").attr("src");
			}
			loc = loc.split("._V1_")[0] + "._V1_.jpg";
		} catch (Exception e) {
			System.out.println(e);
		}
		if(loc.length() < 50 || !loc.contains("ia.media-imdb.com/images")) {
			loc="unknown.jpeg";
		}
		return loc;
	}
	
//	public static void main(String[] args) {
//		IMDBParser parser = new IMDBParser();
//		String poster = parser.getImageLocation(parser.googleMovieUrl("asdf"));
//		System.out.println(poster);
//	}
}
