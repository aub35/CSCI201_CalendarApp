package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import resources.AddUser;
import resources.CheckUser;

public class ReceiveData extends Thread {

	private ObjectInputStream inputStream;
	private Client client;
	
	CheckUser checkuser;
	AddUser adduser;
	
	ReceiveData(ObjectInputStream inputStream, Client client) {
		this.inputStream = inputStream;
		this.client = client;
	}
	
	public void run() {
		Object obj;
		while (true) {
			try {
				obj = inputStream.readObject();
				System.out.println("Client received: " + obj);
				
				ifCheckUser(obj);
				ifAddUser(obj);
				
			} catch (IOException | ClassNotFoundException e) {
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
			System.out.println("Client received user");
			adduser = (AddUser)obj;
		}
	}
}
