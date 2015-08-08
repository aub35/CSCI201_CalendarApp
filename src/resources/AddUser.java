package resources;

import java.io.Serializable;

import calendar.User;

public class AddUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username, password, name;
	private boolean successfulAdd, isGuest;
	private User user;
	
	public AddUser(String username, String password, String name, User user, boolean isGuest) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.isGuest = isGuest;
		successfulAdd = false;
		this.user = user;
	}

	public boolean isGuest() {
		return isGuest;
	}

	public User getUser() {
		System.out.println("AddUser user: " + user);
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isSuccessfulAdd() {
		return successfulAdd;
	}

	public void setSuccessfulAdd(boolean successfulAdd) {
		this.successfulAdd = successfulAdd;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}
	
	
}
