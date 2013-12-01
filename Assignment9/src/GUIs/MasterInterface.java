package GUIs;

import javax.swing.Action;
import javax.swing.JFileChooser;

import IO.DBReader;
import database.Database;
import edu.neu.ccs.gui.ActionsPanel;
import edu.neu.ccs.gui.Display;
import edu.neu.ccs.gui.DisplayCollection;
import edu.neu.ccs.gui.JPTFrame;
import edu.neu.ccs.gui.SimpleAction;
import edu.neu.ccs.gui.TextAreaView;
import edu.neu.ccs.util.FileUtilities;
import edu.neu.ccs.util.JPTConstants;

public class MasterInterface extends DisplayCollection
	implements JPTConstants {
	
	Database db = new Database();
	
	private TextAreaView feedback = new TextAreaView();
	
	private Action addMovie = new SimpleAction("Add Movie") {
		public void perform() {addMovie();}
	};
	
	private Action cashier = new SimpleAction("Cashier") {
		public void perform() {cashier();}
	};
	
	private Action readDB = new SimpleAction("Read DB From File") {
		public void perform() {readDBFromFile();}
	};
	
	private Action saveDB = new SimpleAction("Save DB") {
		public void perform() {saveDBToFile();}
	};
	
	private Action[] actionsList = {addMovie, cashier, readDB, saveDB};
	
	private ActionsPanel actions = new ActionsPanel(actionsList);
	
	
	public MasterInterface() {
		feedback.setLineWrap(true);
		feedback.setWrapStyleWord(true);
		
		Display actionsDisplay = new Display(actions, "Select Activity", 
				null, ABOVE, ABOVE);
		
		Display feedbackDisplay = new Display(feedback, "Output:", null);
		add(actionsDisplay);
		add(feedbackDisplay);
	}
	
	public void addMovie() {
		JPTFrame.createQuickJPTFrame("Add Movie", new AddMovieInterface(db));
	}
	
	public void cashier() {
		JPTFrame.createQuickJPTFrame("Cashier", new CashierInterface(db));
	}
	
	public void readDBFromFile() {
		try{
			JFileChooser chooser = new JFileChooser(".");
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				db  = DBReader.readDBFromFile(chooser.getSelectedFile().getName());
				feedback.setViewState("Database successfully loaded");
			}
			
		}
		catch(Exception e) {
			feedback.setViewState(e.getMessage());
			System.out.println(e.getMessage());
		}
	}
	
	public void saveDBToFile() {
		try {
			JFileChooser chooser = new JFileChooser(".");
			if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				FileUtilities.writeFile(chooser.getSelectedFile(), 
						db.toFileData(), true);
				feedback.setViewState("Database successfully saved");
			}
		}
		catch(Exception e) {
			feedback.setViewState(e.getMessage());
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		JPTFrame jFrame = new JPTFrame();
		jFrame.createQuickJPTFrame("Manager", new MasterInterface());
	}
}