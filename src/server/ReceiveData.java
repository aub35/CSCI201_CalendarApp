package server;

import java.io.IOException;
import java.io.ObjectInputStream;

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
			System.out.println("Server GetEvents date: " + ge.getUser().getCurrDate());
			server.getEvents(ge);
			scl.sendBackGetEvent(ge);
			System.out.println("Sent back " + ge); 
		}
	}
}
