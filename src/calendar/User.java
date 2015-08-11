package calendar;

import java.io.Serializable;
import java.util.Vector;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username, password, name;
	private boolean isGuest;
	private Vector<User> friends;
	private Vector<User> friendRequests;
	private MyDate currDate;
	private int guestIndex;
	
	public int getGuestIndex() {
		return guestIndex;
	}

	public void setGuestIndex(int guestIndex) {
		this.guestIndex = guestIndex;
	}

	public User(String username, String password, String name, boolean isGuest) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.isGuest = isGuest;
		friends = new Vector<User>();
		friendRequests = new Vector<User>();
	}

	public static boolean isEqual(User u1, User u2) {
		if (u1.getUsername().equals(u2.getUsername())) {
			return true;
		}
		return false;
	}
	
	public static boolean isGuestEqual(User u1, User u2) {
		if (u1.getGuestIndex() == u2.getGuestIndex()) {
			return true;
		}
		return false;
	}
	
	public void addFriend(User u) {
		friends.add(u);
	}
	
	public void removeFriendRequest(User u) {
		for (int i = 0; i < friendRequests.size(); i++) {
			if (User.isEqual(u, friendRequests.elementAt(i))) {
				friendRequests.remove(i);
			}
		}
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

	public boolean isGuest() {
		return isGuest;
	}

	public Vector<User> getFriends() {
		return friends;
	}

	public MyDate getCurrDate() {
		return currDate;
	}

	public void setCurrDate(MyDate currDate) {
		this.currDate = currDate;
	}
	
	public Vector<User> getFriendRequests() {
		return friendRequests;
	}
	
	public void addFriendRequest(User u) {
		friendRequests.add(u);
	}

	public void setFriends(Vector<User> friends) {
		this.friends = friends;
	}
}
