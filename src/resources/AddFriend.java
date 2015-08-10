package resources;

import java.io.Serializable;

import calendar.User;

public class AddFriend implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private boolean isSuccessfulAdd;
	private User adder;
	private User requestedUser;
	
	public AddFriend(String username, User adder) {
		this.username = username;
		this.adder = adder;
		this.isSuccessfulAdd = false;
	}

	public boolean isSuccessfulAdd() {
		return isSuccessfulAdd;
	}

	public void setSuccessfulAdd(boolean isSuccessfulAdd) {
		this.isSuccessfulAdd = isSuccessfulAdd;
	}

	public String getUsername() {
		return username;
	}
	
	public void setAdder(User u) {
		this.adder = u;
	}
	
	public User getAdder() {
		return adder;
	}

	public User getRequestedUser() {
		return requestedUser;
	}

	public void setRequestedUser(User requestedUser) {
		this.requestedUser = requestedUser;
	}
	
	
}