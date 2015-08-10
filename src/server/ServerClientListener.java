package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import resources.AddEvent;
import resources.AddFriend;
import resources.AddUser;
import resources.CheckUser;
import resources.GetEvents;
import resources.SearchFriend;

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
			outputStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void sendBackAddUser(AddUser au) {
		try {
			outputStream.writeObject(au);
			outputStream.flush();		
			outputStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBackAddEvent(AddEvent ae) {
		try {
			outputStream.writeObject(ae);
			outputStream.flush();
			outputStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBackGetEvent(GetEvents ge) {
		try {
			outputStream.writeObject(ge);
			outputStream.flush();
			outputStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBackSearchFriend(SearchFriend sf) {
		try {
			outputStream.writeObject(sf);
			outputStream.flush();
			outputStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBackAddFriend(AddFriend af) {
		try {
			outputStream.writeObject(af);
			outputStream.flush();
			outputStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
