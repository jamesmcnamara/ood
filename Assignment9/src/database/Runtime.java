package database;

import java.text.ParseException;

import edu.neu.ccs.Stringable;

public class Runtime implements Stringable {

	private int startTime;
	private int endTime;
	private int runTime;

	public Runtime(int sTime, int eTime) {
		startTime= sTime;
		endTime = eTime;
		if (endTime > startTime && endTime < 1440) {
			runTime = endTime - startTime;
		}
		else {
			throw new RuntimeException("Invalid runtime provided to " + 
					"constructor\n\t" + (sTime / 60) + ":" + 
					((sTime % 60) < 10 ? "0" +(sTime % 60) : (sTime % 60)) 
					+ " to " + (eTime / 60)+ ":" + 
					((eTime % 60) < 10 ? "0" +(eTime % 60) : (eTime % 60)));
		}
	}

	public String toStringData() {
		return startTime+","+endTime;
	}

	public String toString() {
		String startMin = 
				(String) ((startTime % 60) < 10 ? 
						"0" +(startTime % 60) :
							"" + (startTime % 60));
		String endMin = 
				(String) ((endTime % 60) < 10 ? 
						"0" +(endTime % 60) :
							"" + (endTime % 60));
		return startTime/60 + ":" +startMin + 
				" to " + endTime/60 + ":"+endMin;	
	}

	public void fromStringData(String s) throws ParseException {

	}

	public boolean equals(Object o) {
		if (o instanceof Runtime) {
			return this.equals((Runtime) o);
		} 
		else {
			return false;
		}
	}

	public boolean equals(Runtime o) {
		return this.startTime == o.startTime &&
				this.endTime == o.endTime;
	}
	public boolean isOverlapping(Runtime r) {
		Runtime first = ((this.startTime) < (r.startTime) ? this : r);
		Runtime second = (first.equals(this) ? r : this);
		return first.endTime >= second.startTime;
	}

	public int getRuntime() {
		return this.runTime;
	}

	public int getStartTime() {
		return this.startTime;
	}

	public int getEndTime() {
		return this.endTime;
	}
}