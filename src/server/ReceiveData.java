package server;

import java.io.IOException;
import java.io.ObjectInputStream;

import resources.AddUser;
import resources.CheckUser;

public class ReceiveData extends Thread {

	private ObjectInputStream inputStream;
	private Server server;
	private ServerClientListener scl;
	
	ReceiveData(ObjectInputStream inputStream, Server server, ServerClientListener scl) {
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
			
			System.out.println("Received AddUser");
			AddUser au = (AddUser)obj;
			server.addUser(au);
			scl.sendBackAddUser(au);
		}
	}
}
