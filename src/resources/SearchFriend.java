package resources;

import java.io.Serializable;
import java.util.Vector;

import calendar.User;

public class SearchFriend implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private User user;
	private Vector<String> usernameList;
	private boolean succesfulSearch = false;
	
	public SearchFriend(String username, User user) {
		this.username = username;
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public User getUser() {
		return user;
	}

	public Vector<String> getUsernameList() {
		return usernameList;
	}

	public void setUsernameList(Vector<String> usernameList) {
		this.usernameList = usernameList;
	}

	public boolean isSuccesfulSearch() {
		return succesfulSearch;
	}

	public void setSuccesfulSearch(boolean succesfulSearch) {
		this.succesfulSearch = succesfulSearch;
	}
	
	
}
