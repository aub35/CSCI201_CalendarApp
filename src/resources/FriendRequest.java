package resources;

import java.io.Serializable;

import calendar.User;

public class FriendRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private User adder;
	private User requestedUser;
	private boolean successfulSend;
	
	public FriendRequest(User adder, User requestedUser) {
		this.adder = adder;
		this.requestedUser = requestedUser;
		successfulSend = false;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User getAdder() {
		return adder;
	}

	public User getRequestedUser() {
		return requestedUser;
	}

	public boolean isSuccessfulSend() {
		return successfulSend;
	}

	public void setSuccessfulSend(boolean successfulSend) {
		this.successfulSend = successfulSend;
	}

	
	
}
