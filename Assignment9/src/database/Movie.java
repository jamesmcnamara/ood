package database;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class Movie {
	String title;
	int hashCode;
	int ID;
	Theater theater;
	Runtime showtime;
	ArrayList<Map.Entry<String, Integer>> tickets;
	
	public Movie (String title,Theater theater, Runtime showtime) {
		if (theater.isAvailable(showtime)) {
			this.title = title;
			this.theater = theater;
			this.showtime = showtime;
			this.hashCode = hashCodeGenerator();
			this.tickets = new ArrayList<Map.Entry<String, Integer>>();
		}
		else {
			throw new RuntimeException("Theater " + theater.getID()
					+ " is not available from " + showtime.toString());
		}
	}
	
	private int hashCodeGenerator() {		
		int showtimeDigits = (int) Math.log10(showtime.getStartTime()) + 1;
		int theaterDigits = (int) Math.log10(theater.hashCode()) + 1;

		return (this.title.hashCode() * (int)Math.pow(10, theaterDigits) + theater.hashCode()) 
				* (int)Math.pow(10, showtimeDigits) + showtime.getStartTime();
	}
	
	public int hashCode() {
		return this.hashCode;
	}
	
	public String toString() {
		return title + " from " + showtime.toString();
	}
	
	public int getAttendance() {
		int attendance = 0;
		for (Map.Entry<String, Integer> order: tickets) {
			attendance = attendance + order.getValue();
		}
		return attendance;
	}
	
	public Theater getTheater() {
		return this.theater;
	}
	
	public void addOrder(String discountCode, int size) {
		if (getAttendance() < theater.getCapacity()) {
			tickets.add(
					new AbstractMap.SimpleEntry<String, Integer>(discountCode,
							size));
		}
		else {
			throw new RuntimeException("This film is at capacity");
		}
	}
}