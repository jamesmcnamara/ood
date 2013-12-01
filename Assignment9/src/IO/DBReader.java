package IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import database.Database;
import database.Runtime;

public final class DBReader {
	public static Database readDBFromFile(String filename) {
		Database db = new Database();
		ArrayList<Map.Entry<String, Integer>> movies = 
				new ArrayList<Map.Entry<String, Integer>>();
		
		ArrayList<Integer> theaterIDs = new ArrayList<Integer>();

		try {

			FileReader fin = new FileReader(filename);
			BufferedReader dbFile = new BufferedReader(fin);

			String line = dbFile.readLine();
			StringTokenizer st = new StringTokenizer(line);
			if (st.nextToken().equals("Movies")) {
				while (!(line = dbFile.readLine()).equals("Theaters")) {
					st = new StringTokenizer(line, ":");
					String title = st.nextToken();
					int runtime = Integer.parseInt(st.nextToken());
					movies.add(new AbstractMap.SimpleEntry<String, Integer> (title, runtime));
				}
			}
			while (!(line = dbFile.readLine()).equals("Shows")) {
				st = new StringTokenizer(line, ":");
				String ID = st.nextToken();
				int capacity = Integer.parseInt(st.nextToken());
				db.addTheater(ID, capacity);
				theaterIDs.add(ID.hashCode());
			}
			while (!(line=dbFile.readLine()).equals("Prices")) {
				st = new StringTokenizer(line, ",");
				int moviePos = Integer.parseInt(st.nextToken()) - 1;
				int theaterPos = Integer.parseInt(st.nextToken()) - 1;
				int startTime = Integer.parseInt(st.nextToken());
				int theaterID = theaterIDs.get(theaterPos);
				String movieTitle = movies.get(moviePos).getKey();
				int runtime = movies.get(moviePos).getValue();
				Runtime r = new Runtime(startTime, startTime+runtime);
				db.addMovie(movieTitle, r, theaterID);
			}
			while (!(line = dbFile.readLine()).equals("End")) {
				st = new StringTokenizer(line, ":");
				String discountCode = st.nextToken();
				int price = Integer.parseInt(st.nextToken());
				db.addDiscountCode(discountCode, price);
			}
		}
		catch(IOException e) {
			System.out.println("Exception was caught"  +
					" while reading file in:" + e);
		}
		return db;
	}
} 