package calendar;

import java.util.Vector;

public class Calendar {

	private Vector<Event> events;
	
	public Calendar() {

	}
	
	public void addEvent(Event e, int index) {
		events.add(index, e);
	}
	
	public int getLength() {
		return events.size();
	}
}
