package resources;

import java.io.Serializable;

import calendar.User;

public class AddFriend implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private boolean isSuccessfulAdd;
	private User friend;
	
	public AddFriend(String username) {
		this.username = username;
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
	
	public void setUser(User u) {
		this.friend = u;
	}
	
	public User getUser() {
		return friend;
	}
}
