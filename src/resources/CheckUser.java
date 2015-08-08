package resources;

import java.io.Serializable;

import calendar.User;

public class CheckUser implements Serializable {
	

	private static final long serialVersionUID = -4176061050776046663L;
	String username;
	String password;
	private User user;
	boolean doesExist;
	
	public CheckUser(String username, String password) {
		this.username = username;
		this.password = password;
		doesExist = false;
	}

	public boolean doesExist() {
		return doesExist;
	}

	public void setDoesExist(boolean doesExist) {
		this.doesExist = doesExist;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}
