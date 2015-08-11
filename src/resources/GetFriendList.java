package resources;

import java.io.Serializable;
import java.util.Vector;

import calendar.User;

public class GetFriendList implements Serializable {


	private static final long serialVersionUID = 6886549679322324837L;
	private User u;
	private Vector<User> friends;
	private Vector<User> friendRequests;
	public Vector<User> getFriendRequests() {
		return friendRequests;
	}

	public void setFriendRequests(Vector<User> friendRequests) {
		this.friendRequests = friendRequests;
	}
	private boolean isSuccessfulGet;
	
	
	public GetFriendList(User u) {
		this.u = u;
		isSuccessfulGet = false;
	}

	public User getU() {
		return u;
	}
	public Vector<User> getFriends() {
		return friends;
	}
	public void setFriends(Vector<User> friends) {
		this.friends = friends;
	}
	public boolean isSuccessfulGet() {
		return isSuccessfulGet;
	}
	public void setSuccessfulGet(boolean isSuccessfulGet) {
		this.isSuccessfulGet = isSuccessfulGet;
	}
	
	
}
