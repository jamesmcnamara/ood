package database;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Database {
	private HashMap<Integer, Theater> theaters = 
			new HashMap<Integer, Theater>();

	private ArrayList<Map.Entry<String, Integer>> discountCodes = 
			new ArrayList<Map.Entry<String, Integer>>(); 

	public void addMovie(String title, Runtime runtime, int TheaterID) {

		Theater Theater = theaters.get(TheaterID);
		Movie m = new Movie(title, Theater, runtime);
		Theater.getNowPlaying().put(m.hashCode, m);

	}


	public void addTheater(String ID, int capacity) {
		if (!theaters.containsKey(ID)) {
			theaters.put(ID.hashCode(), new Theater(ID, capacity));
		}	
	}

	public void addDiscountCode(String code, int price) {
		Map.Entry<String, Integer> ent = 
				new AbstractMap.SimpleEntry<String, Integer>(code, price);
		discountCodes.add(ent);
	}

	public void deleteMovie(Movie m) {
		theaters.get(m.getTheater().hashCode()).deleteMovie(m);
	}

	public String[] getMovies() {
		String[] movieList = {};
		ArrayList<String> movies = new ArrayList<String>();
		for (Theater Theater : theaters.values()) {
			for (Movie m : Theater.getNowPlaying().values()) {
				if (!(movies.contains(m.title))) {
					movies.add(m.title);
				}
			}
		}
		Collections.sort(movies);
		return movies.toArray(movieList);
	}

	public String[] getShowTimes(String movieTitle) {
		String[] showTimes = {};
		ArrayList<String> showings = new ArrayList<String>();
		for (Theater Theater : theaters.values()) {
			for(Movie m : Theater.getNowPlaying().values()) {
				if (m.title.equals(movieTitle)) {
					showings.add(m.showtime.toString());
				}
			}
		}
		Collections.sort(showings);
		return showings.toArray(showTimes);
	}

	public ArrayList<String> getDiscountCodes() {
		ArrayList<String> codes = new ArrayList<String>();
		for (Map.Entry<String, Integer> pair : discountCodes) {
			codes.add(pair.getKey());
		}
		return codes;
	}
	
	public String[] getTheaters() {
		ArrayList<String> teatros = new ArrayList<String>();
		String[] TheaterList = {};
		for (Theater Theater : theaters.values()) {
			teatros.add(Theater.getID());
		}
		Collections.sort(teatros);
		return teatros.toArray(TheaterList);
	}

	public ArrayList<Map.Entry<String, Integer>> 
	getDiscountsAndPrices() {
		return this.discountCodes;
	}

	public Theater getTheater(int ID) {
		return theaters.get(ID);
	}

	public Movie getMovie(String title, String runtime) throws NoSuchElementException{
		for (Theater theater : theaters.values()) {
			for (Movie movie : theater.getNowPlaying().values()) {
				if (movie.title.equals(title) && 
						movie.showtime.toString().equals(runtime)) {
					return movie;
				}
			}
		}
		throw new NoSuchElementException();
	}


	public String toString() {
		String s ="";

		for (Theater t : theaters.values()) {
			s = s + "\n\n" + t.toString();
		}

		return s;
	}
	
	public String toFileData() {
		String s = "Movies\n";
		ArrayList<String> movies = new ArrayList<String>();
		s = s + moviesToFileData(movies);
		s = s + "Theaters\n";
		ArrayList<String> theaterList = new ArrayList<String>();
		s = s + theatersToFileData(theaterList);
		s = s + "Shows\n" + showingsToFileData(movies, theaterList);
		s = s + "Prices\n" + discountCodesToFileData();
		return s + "End";
	}
	
	public String moviesToFileData(ArrayList<String> movies) {
		String s = "";
		for (Theater theater : theaters.values()) {
			for (Movie movie : theater.getNowPlaying().values()) {
				if (!(movies.contains(movie))) {
					movies.add(movie.title);
					s = s + movie.title + ":" + movie.showtime.getRuntime() + "\n";
				}
			}
		}
		return s;
	}
	
	public String theatersToFileData(ArrayList<String> theaterList) {
		String s = "";
		for (Theater theater : theaters.values()) {
			theaterList.add(theater.getID());
			s = s + theater.getID() + ":" + theater.getCapacity() + "\n";
		}
		return s;
	}
	
	public String showingsToFileData(ArrayList<String> movies, 
			ArrayList<String> theaterList) {
		String s = "";
		for (Theater theater : theaters.values()) {
			for (Movie movie : theater.getNowPlaying().values()) {
				int moviePos = movies.indexOf(movie.title) + 1;
				int theaterPos = theaterList.indexOf(movie.theater.getID()) + 1;
				s = s + moviePos + "," + theaterPos + 
						"," + movie.showtime.getStartTime() + "\n";
			}
		}
		return s;
	}
	
	public String discountCodesToFileData() {
		String s = "";
		for (Map.Entry<String, Integer> discounts : discountCodes) {
			s = s + discounts.getKey() + ":" + discounts.getValue() + "\n";
		}
		return s;
	}
}