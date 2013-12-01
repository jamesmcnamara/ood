package GUIs;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.Action;

import IO.DBReader;
import database.Database;
import database.Movie;
import edu.neu.ccs.gui.ActionsPanel;
import edu.neu.ccs.gui.Display;
import edu.neu.ccs.gui.DisplayCollection;
import edu.neu.ccs.gui.DropdownView;
import edu.neu.ccs.gui.JPTFrame;
import edu.neu.ccs.gui.SimpleAction;
import edu.neu.ccs.gui.TextAreaView;
import edu.neu.ccs.gui.TextFieldView;
import edu.neu.ccs.util.JPTConstants;

public class CashierInterface extends DisplayCollection 
implements JPTConstants{
	private Database db;
	final String s = "";
	private String[] movies;
	private String[] times;


	private DropdownView movieList;
	private DropdownView timesList;
	private TextFieldView tickets = new TextFieldView();

	private TextAreaView feedback = new TextAreaView();

	private Action updateTimes = new SimpleAction("update") {
		public void perform() {getTimes();}
	};
	
	private Action sellTickets = new SimpleAction("Purchase") {
		public void perform() {sellTickets();}
	};
	
	private Action[] actionsList = {sellTickets};
	
	private ActionsPanel ap = new ActionsPanel(actionsList);

	public CashierInterface(Database database) {
		
		this.db = database;
		movies = db.getMovies();
		times = db.getShowTimes(movies[0]);


		movieList = new DropdownView(movies);
		timesList = new DropdownView(times);
				
		movieList.addActionListener(updateTimes);
		feedback.setEditable(false);
		feedback.setLineWrap(true);
		feedback.setWrapStyleWord(true);
		
		Display movies = new Display(movieList,"Select Movie:", null);
		Display times = new Display(timesList,"Select Showing:", null);
		Display tix = new Display(tickets, "How many of each ticket " + 
				"type would you like?(" + getTicketAnnotation() + ")", null);
		Display feedbk = new Display(feedback, "Info", null);
		
		add(movies);
		add(times);
		add(tix);
		add(new Display(new TextAreaView()));
		add(feedbk);
		add(ap);
	}

	public void getTimes() {
		times = db.getShowTimes(movieList.getViewState());
		timesList.removeAllItems();
		for (String s : times) {
			timesList.addItem(s);
		}
	}


	public String getTicketAnnotation() {
		ArrayList<String> codes = db.getDiscountCodes();
		String tickets = "";
		for (String s : codes) {
			tickets = tickets + s + ",";
		}
		return tickets.substring(0,tickets.length()-1);
	}

	public void sellTickets() {
		StringTokenizer st = new StringTokenizer(tickets.getViewState(), ",");
		ArrayList<Map.Entry<String, Integer>> disCodes = db.getDiscountsAndPrices();
		int discountCodeCount = disCodes.size();
		if (st.countTokens() == discountCodeCount) {
			ArrayList<Map.Entry<String, Integer>> order = 
					new ArrayList<Map.Entry<String, Integer>> ();
			int orderSize = 0;
			int price = 0;
			for(Map.Entry<String, Integer> codeAndPrice : disCodes) {
				int ticCount = Integer.parseInt(st.nextToken());
				orderSize = orderSize + ticCount;
				order.add(new AbstractMap.SimpleEntry<String, Integer>
					(codeAndPrice.getKey(), ticCount));
				price = price + (codeAndPrice.getValue() * ticCount);
			}
			try {
				Movie m = db.getMovie(movieList.getViewState(), 
						timesList.getViewState());
				int attendance = m.getAttendance();
				int capacity = m.getTheater().getCapacity();
				if (capacity >= attendance + orderSize) {
					
					feedback.setViewState("Please tender $" + price +
							" to the cashier and proceed to theater " + 
							m.getTheater().getID());
					for (Map.Entry<String, Integer> suborder : order) {
						m.addOrder(suborder.getKey(), suborder.getValue());
					}
				}
				else {
					feedback.setViewState("I'm sorry, but the showing of "
							+ m.toString() +
							" is sold out. Please select another film.");
				}
			}
			catch(Exception e) {
				System.out.println(e);
			}
			
		}
		else {
			String err = "Malformed ticket order: Input must be comma " + 
					"separated, without spaces," +
					" with the value in each postion reflecting " + 
					"the number of each type of ticket you would like." +
					"e.g. If the discount codes are Adult,Child,Senior " +
					"2,1,0 would represent an order of two adults and" +
					" one child";
			System.out.println(err);
			feedback.setViewState(err);
		}
	}
}
