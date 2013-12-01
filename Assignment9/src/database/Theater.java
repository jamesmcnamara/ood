package database;

import java.util.HashMap;

public class Theater {
	private String ID;
	private int capacity;
	private HashMap<Integer, Movie> nowPlaying;

	public Theater (String ID, int capacity) {
		this.ID = ID;
		this.capacity = capacity;
		this.nowPlaying = new HashMap<Integer, Movie>();
	}

	public int hashCode() {
		return ID.hashCode();
	}
	
	public String toString() {
		String s = "Theater " + ID + ":";
		for (Movie m : nowPlaying.values()) {
			s = s + "\n\t" + m.toString();
		}
		return s;
	}

	public int getCapacity() {
		return this.capacity;
	}
	
	public String getID() {
		return this.ID;
	}
	
	public HashMap<Integer, Movie> getNowPlaying() {
		return nowPlaying;
	}

	public void addMovie(Movie m) {
		if(this.isAvailable(m.showtime)) {
			nowPlaying.put(m.hashCode, m);
		}
		else {
			throw new RuntimeException("Theater unavailable");
		}
	}
	
	public void deleteMovie(Movie m) {
		nowPlaying.remove(m.hashCode());
	}
	
	public boolean isAvailable(Runtime runtime) {
		for (Movie playing : nowPlaying.values()) {
			if(runtime.isOverlapping(playing.showtime)) {
				return false;
			}
		}
		return true;
	}


}