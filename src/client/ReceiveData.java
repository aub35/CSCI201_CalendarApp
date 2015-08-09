package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import resources.AddEvent;
import resources.AddUser;
import resources.CheckUser;
import resources.GetEvents;

public class ReceiveData extends Thread {

	private ObjectInputStream inputStream;
	private MyClient client;
	private boolean isQuit = false;
	
	CheckUser checkuser;
	AddUser adduser;
	AddEvent addevent;
	GetEvents getevents;
	
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
				
				if (isQuit) { break; }
				Thread.sleep(10);
			} catch (IOException | ClassNotFoundException | InterruptedException e) {
				break;
			}
		}
	}
	
	private void ifCheckUser(Object obj) {
		if (obj instanceof CheckUser) {
			client.setHaveReceivedLogin(true);
			checkuser = (CheckUser)obj;
		}
	}
	
	private void ifAddUser(Object obj) {
		if (obj instanceof AddUser) {
			client.setHaveReceivedUser(true);
			adduser = (AddUser)obj;  
		}
	}
	
	private void ifAddEvent(Object obj){
		if (obj instanceof AddEvent) {
			client.setHaveReceivedAddEvent(true);
			addevent = (AddEvent)obj;
		}
	}
	
	private void ifGetEvents(Object obj) {
		if (obj instanceof GetEvents) {
			client.setHaveReceivedGetEvents(true);
			getevents = (GetEvents)obj;
		}
	}
	
	
}
