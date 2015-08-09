package resources;

import java.io.Serializable;
import java.util.Vector;

import calendar.MyDate;
import calendar.MyEvent;
import calendar.User;

public class GetEvents implements Serializable {

	private static final long serialVersionUID = 1L;
	private MyDate start;
	private MyDate end;
	private boolean isSuccessfulGet;
	private Vector<MyEvent> events;
	private User user;
	
	public User getUser() {
		return user;
	}

	public Vector<MyEvent> getEvents() {
		return events;
	}

	public void setEvents(Vector<MyEvent> events) {
		this.events = events;
	}

	public GetEvents(MyDate start, MyDate end, User user) {
		this.start = start;
		this.end = end;
		this.user = user;
	}

	public MyDate getStart() {
		return start;
	}

	public MyDate getEnd() {
		return end;
	}

	public boolean isSuccessfulGet() {
		return isSuccessfulGet;
	}

	public void setSuccessfulGet(boolean isSuccessfulGet) {
		this.isSuccessfulGet = isSuccessfulGet;
	}
	
}
