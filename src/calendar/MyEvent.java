package calendar;

import java.io.Serializable;

public class MyEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private MyDate start, end;
	private String name, location;
	private boolean isImportant;
	
	public MyEvent(MyDate start, MyDate end, String name, String location,
			boolean isImportant) {
		this.start = start;
		this.end = end;
		this.name = name;
		this.location = location;
		this.isImportant = isImportant;
	}

	public MyDate getStart() {
		return start;
	}

	public MyDate getEnd() {
		return end;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public boolean isImportant() {
		return isImportant;
	}
	
}
