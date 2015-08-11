package resources;

import java.io.Serializable;

import calendar.User;

public class FriendRequestResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//added is the user that just accepted or denied
	private User added, adder;
	private boolean didAccept;
	
	public User getAdded() {
		return added;
	}

	public User getAdder() {
		return adder;
	}

	public boolean isDidAccept() {
		return didAccept;
	}

	public FriendRequestResponse(User added, User adder) {
		this.added =added;
		this.adder = adder;
	}

	public void setDidAccept(boolean didAccept) {
		this.didAccept = didAccept;
	}
}
