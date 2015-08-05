package calendar;

public class Event {

	private Date start, end;
	private String name, location;
	private boolean isImportant;
	
	public Event(Date start, Date end, String name, String location,
			boolean isImportant) {
		this.start = start;
		this.end = end;
		this.name = name;
		this.location = location;
		this.isImportant = isImportant;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
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
