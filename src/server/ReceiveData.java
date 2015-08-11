package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import calendar.User;
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
	private MyServer server;
	private ServerClientListener scl;
	
	ReceiveData(ObjectInputStream inputStream, MyServer server, ServerClientListener scl) {
		this.inputStream = inputStream;
		this.server = server;
		this.scl = scl;
	}
	
	public void run() {
		Object obj;
		while (true) {
			try {
				obj = inputStream.readObject();
				System.out.println("Server received: " + obj);
				
				ifCheckUser(obj);
				ifAddUser(obj);
				ifAddEvent(obj);
				ifGetEvents(obj);
				ifSearchFriend(obj);
				ifAddFriend(obj);
				ifFriendRequest(obj);
				ifFriendRequestResponse(obj);
				ifGetFriendList(obj);
//				ifUserFriendRequest(obj);
				ifQuitUser(obj);
				
			} catch (IOException | ClassNotFoundException e) {
				break;
			}
			
		}
	}
	//methods for receiving different data types
	private void ifCheckUser(Object obj) {
		if (obj instanceof CheckUser) {

			CheckUser cu = (CheckUser)obj;
			server.checkUser(cu);
			if (cu.doesExist()) {
				server.addConnection(cu.getUser(), scl);
			}
			scl.sendBackCheckUser(cu);
		}
	}
	
	private void ifAddUser(Object obj) {
		if (obj instanceof AddUser) {
			
			AddUser au = (AddUser)obj;
			server.addUser(au);
			if (au.isSuccessfulAdd()) {
				server.addConnection(au.getUser(), scl);				
			}
			scl.sendBackAddUser(au);
		}
	}
	
	private void ifAddEvent(Object obj) {
		if (obj instanceof AddEvent) {
			
			AddEvent ae = (AddEvent)obj;
			server.addEvent(ae);
			scl.sendBackAddEvent(ae);
		}
	}
	
	private void ifGetEvents(Object obj) {
		if (obj instanceof GetEvents) {
			GetEvents ge = (GetEvents)obj;
			server.getEvents(ge);
			scl.sendBackGetEvent(ge);
		}
	}
	
	private void ifQuitUser(Object obj) {
		if (obj instanceof User) {
			User u = (User)obj;
			server.quitUser(u);
		}
	}
	
	private void ifSearchFriend(Object obj) {
		if (obj instanceof SearchFriend) {
			SearchFriend sf = (SearchFriend)obj;
			server.searchForFriend(sf);
			scl.sendBackSearchFriend(sf);
		}
	}
	
	private void ifAddFriend(Object obj) {
		if (obj instanceof AddFriend) {
			AddFriend af = (AddFriend)obj;
			server.addFriend(af);
			scl.sendBackAddFriend(af);
		}
	}
	
	private void ifFriendRequest(Object obj) {
		if (obj instanceof FriendRequest) {
			FriendRequest fr = (FriendRequest)obj;
			server.sendFriendRequest(fr);
		}
	}
	
	private void ifFriendRequestResponse(Object obj) {
		if (obj instanceof FriendRequestResponse) {
			FriendRequestResponse frr = (FriendRequestResponse)obj;
			server.sendFriendRequestResponse(frr);
		}
	}
	
	private void ifGetFriendList(Object obj) {
		if (obj instanceof GetFriendList) {
			GetFriendList gfl = (GetFriendList)obj;
			server.getFriendList(gfl);
			scl.sendBackGetFriendList(gfl);
			System.out.println("Server sent back friend list");
		}
	}
}
