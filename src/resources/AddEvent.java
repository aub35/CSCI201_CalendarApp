package resources;

import java.io.Serializable;

import calendar.MyEvent;
import calendar.User;

public class AddEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private MyEvent e;
	private User u;
	private boolean successfulAdd;
	public AddEvent (User u, MyEvent e) {
		this.e = e;
		this.u = u;
		successfulAdd = false;
	}
	public MyEvent getE() {
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
