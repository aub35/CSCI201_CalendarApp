package resources;

import java.io.Serializable;

import calendar.Event;
import calendar.User;

public class AddEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private Event e;
	private User u;
	private boolean successfulAdd;
	public AddEvent (User u, Event e) {
		this.e = e;
		successfulAdd = false;
	}
	public Event getE() {
		return e;
	}
	public User getU() {
		return u;
	}
	public boolean isSuccessfulAdd() {
		return successfulAdd;
	}
	public void setSuccessfulAdd(boolean successfulAdd) {
		this.successfulAdd = successfulAdd;
	}
	
}
