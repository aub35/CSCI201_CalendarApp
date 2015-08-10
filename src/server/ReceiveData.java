package server;

import java.io.IOException;
import java.io.ObjectInputStream;

import calendar.MyDate;
import calendar.User;
import resources.AddEvent;
import resources.AddUser;
import resources.CheckUser;
import resources.GetEvents;

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
			scl.sendBackCheckUser(cu);
		}
	}
	
	private void ifAddUser(Object obj) {
		if (obj instanceof AddUser) {
			
			AddUser au = (AddUser)obj;
			server.addUser(au);
			scl.sendBackAddUser(au);
			System.out.println("Sent add user");
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
}
