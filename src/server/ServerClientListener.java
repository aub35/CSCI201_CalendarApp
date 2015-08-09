package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import resources.AddEvent;
import resources.AddUser;
import resources.CheckUser;
import resources.GetEvents;

public class ServerClientListener extends Thread {

	private Socket socket;
	private MyServer server;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	public ServerClientListener(Socket s, MyServer server) {
		socket = s;
		this.server = server;
	}
	
	public void run() {
		try {
			//get output stream
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			SendData sd = new SendData(outputStream, server);
			sd.start();
			//get input stream
			inputStream = new ObjectInputStream(socket.getInputStream());
			ReceiveData rd = new ReceiveData(inputStream, server, this);
			rd.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBackCheckUser(CheckUser cu) {
		try {
			outputStream.writeObject(cu);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void sendBackAddUser(AddUser au) {
		try {
			outputStream.writeObject(au);
			outputStream.flush();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBackAddEvent(AddEvent ae) {
		try {
			outputStream.writeObject(ae);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBackGetEvent(GetEvents ge) {
		try {
			outputStream.writeObject(ge);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
