package GUIs;

import javax.swing.Action;

import IO.DBReader;
import database.Database;
import database.Runtime;
import edu.neu.ccs.gui.ActionsPanel;
import edu.neu.ccs.gui.Display;
import edu.neu.ccs.gui.DisplayCollection;
import edu.neu.ccs.gui.DisplayWrapper;
import edu.neu.ccs.gui.DropdownView;
import edu.neu.ccs.gui.JPTFrame;
import edu.neu.ccs.gui.SimpleAction;
import edu.neu.ccs.gui.TextAreaView;
import edu.neu.ccs.gui.TextFieldView;
import edu.neu.ccs.util.JPTConstants;

public class AddMovieInterface extends DisplayCollection implements JPTConstants {
	
	private Database db;
	private String[] theaters;
	private DropdownView theater;
	
	
	private TextFieldView title = new TextFieldView("", 150);
	private TextFieldView runtime = new TextFieldView("",
			"Runtime must be an integer less than 1440",
			"Incorrect Input", 50);
	
	private TextFieldView startHour = new TextFieldView("",
			"Start hour must be an integer from 0 to 24", 
			"Invalid time", 20);
	
	private TextFieldView startMin = new TextFieldView("",
			"Start minute must be an integer from 1 to 60", 
			"Invalid time", 20);
	
	private TextAreaView feedback = new TextAreaView();
	
	private Action add = new SimpleAction("Add Movie") {
		public void perform() {add();}
	};
	
	private Action clear = new SimpleAction("Clear All") {
		public void perform() {clear();}
	};
	
	private Action[] actionList = {add, clear};
	
	private ActionsPanel actionPanel = new ActionsPanel(actionList);
	
	private DisplayCollection movieInfo = new DisplayCollection(LEFT);
	private DisplayCollection theaterInfo = new DisplayCollection(LEFT);
	private DisplayCollection timeInfo = new DisplayCollection(LEFT);
	
	
	public AddMovieInterface(Database database) {
		this.db = database;
		theaters = db.getTheaters();
		theater = new DropdownView(theaters);
		
		feedback.setEditable(false);
		feedback.setLineWrap(true);
		feedback.setWrapStyleWord(true);
		
		movieInfo.add(new DisplayWrapper(new Display(title, "Title", null)));
		movieInfo.add(new DisplayWrapper(new Display(runtime, "Runtime(mins)", null)));
		theaterInfo.add(new DisplayWrapper(new Display(theater, "Theater ID", null)));
		timeInfo.add(new DisplayWrapper(new Display(startHour, "", null)));
		timeInfo.add(new DisplayWrapper(new Display(startMin, ":", null)));
		
		
		
		Display timeDisplay = new Display(timeInfo, "Start Time", null);
		Display theaterDisplay = new Display(theaterInfo, "Theater", null);
		Display movieDisplay = new Display(movieInfo, "Movie Information", null);
		Display feedbackDisplay = new Display(feedback, "Status", null);
		Display actionsDisplay = new Display(actionPanel);
		
		
		add(movieDisplay);
		add(theaterDisplay);
		add(timeDisplay);
		add(feedbackDisplay);
		add(actionsDisplay);
		
	}
	
	
	public void addMovieInstance() {
		int theaterID, sHour, sMin, start, end, rTime;
		try {
			String mTitle = title.demandString();
			theaterID = theater.getViewState().hashCode(); 
			sHour = startHour.demandInt();
			sMin = startMin.demandInt();
			rTime = runtime.demandInt();
			start = sHour*60+sMin;
			end = start + rTime;
			Runtime r = new Runtime(start, end);
			db.addMovie(mTitle, r, theaterID);
			feedback.setViewState("Movie successfully added");
			System.out.println(db);
		}
		catch (Exception e) {
			feedback.setViewState(e.getMessage());
			System.out.println("Caught Exception " + e);
		}
	}
	
	public void add() {
		addMovieInstance();
		clearTime();
	}
	
	
	
	public void clearTime() {
		startHour.setViewState("");
		startMin.setViewState("");
	}
	
	public void clear(){
		runtime.setViewState("");
		title.setViewState("");
		theater.setViewState("");
		startHour.setViewState("");
		startMin.setViewState("");
		feedback.setViewState("");
	}
}