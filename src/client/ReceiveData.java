package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import resources.AddEvent;
import resources.AddFriend;
import resources.AddUser;
import resources.CheckUser;
import resources.FriendRequest;
import resources.FriendRequestResponse;
import resources.GetEvents;
import resources.GetFriendList;
import resources.SearchFriend;

public class ReceiveData extends Thread {

	private ObjectInputStream inputStream;
	private MyClient client;
	private boolean isQuit = false;
	
	CheckUser checkuser;
	AddUser adduser;
	AddEvent addevent;
	GetEvents getevents;
	SearchFriend searchfriend;
	AddFriend addfriend;
	FriendRequest friendrequest;
	GetFriendList getfriendlist;
	FriendRequestResponse friendrequestresponse;
	
	ReceiveData(ObjectInputStream inputStream, MyClient client) {
		this.inputStream = inputStream;
		this.client = client;
	}
	
	public boolean isQuit() {
		return isQuit;
	}

	public void setQuit(boolean isQuit) {
		this.isQuit = isQuit;
	}
	
	public void run() {
		Object obj;
		while (true) {
			try {
				obj = inputStream.readObject();
				System.out.println("Client received: " + obj);
				
				ifCheckUser(obj);
				ifAddUser(obj);
				ifAddEvent(obj);
				ifGetEvents(obj);
				ifSearchFriend(obj);
				ifAddFriend(obj);
				ifFriendRequest(obj);
				ifFriendRequestResponse(obj);
				ifGetFriendList(obj);
				
				if (isQuit) { break; }
				Thread.sleep(10);
			} catch (IOException | ClassNotFoundException | InterruptedException e) {
				break;
			}
		}
	}
	
	private void ifCheckUser(Object obj) {
		if (obj instanceof CheckUser) {
			checkuser = (CheckUser)obj;
			client.setHaveReceivedLogin(true);
		}
	}
	
	private void ifAddUser(Object obj) {
		if (obj instanceof AddUser) {
			adduser = (AddUser)obj;  
			client.setHaveReceivedUser(true);
		}
	}
	
	private void ifAddEvent(Object obj){
		if (obj instanceof AddEvent) {
			addevent = (AddEvent)obj;
			client.setHaveReceivedAddEvent(true);
		}
	}
	
	private void ifGetEvents(Object obj) {
		if (obj instanceof GetEvents) {
			getevents = (GetEvents)obj;
			client.setHaveReceivedGetEvents(true);
		}
	}
	
	private void ifSearchFriend(Object obj) {
		if (obj instanceof SearchFriend) {
			searchfriend = (SearchFriend)obj;
			client.setHaveReceivedSearchFriend(true);
		}
	}
	
	private void ifAddFriend(Object obj) {
		if (obj instanceof AddFriend) {
			addfriend = (AddFriend)obj;
			client.setHaveReceivedAddFriend(true);
		}
	}

	private void ifFriendRequest(Object obj) {
		if (obj instanceof FriendRequest) {
			friendrequest = (FriendRequest)obj;
			client.receivedFriendRequest(friendrequest);
		}
	}
	
	private void ifGetFriendList(Object obj) {
		if (obj instanceof GetFriendList) {
			System.out.println("Got friends list");
			getfriendlist = (GetFriendList)obj;
			client.setHaveReceivedFriendList(true);
		}
	}
	
	private void ifFriendRequestResponse(Object obj) {
		if (obj instanceof FriendRequestResponse) {
			friendrequestresponse = (FriendRequestResponse)obj;
			client.receivedFriendRequestResponse(friendrequestresponse);
		}
	}
	
}