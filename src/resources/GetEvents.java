package resources;

import java.io.Serializable;
import java.util.Vector;

import calendar.Date;
import calendar.Event;
import calendar.User;

public class GetEvents implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date start;
	private Date end;
	private boolean isSuccessfulGet;
	private Vector<Event> events;
	private User user;
	
	public User getUser() {
		return user;
	}

	public Vector<Event> getEvents() {
		return events;
	}

	public void setEvents(Vector<Event> events) {
		this.events = events;
	}

	public GetEvents(Date start, Date end, User user) {
		this.start = start;
		this.end = end;
		this.user = user;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public boolean isSuccessfulGet() {
		return isSuccessfulGet;
	}

	public void setSuccessfulGet(boolean isSuccessfulGet) {
		this.isSuccessfulGet = isSuccessfulGet;
	}
	
	
}
