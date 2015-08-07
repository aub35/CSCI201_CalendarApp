package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import resources.AddUser;
import resources.CheckUser;

//protocol should be to send username, password & check if there is a matching one in server database
//TODO: login screen GUI
//log out

public class Client extends Thread {

	private Socket s;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private ReceiveData rd;
	
	private boolean haveReceivedLogin, haveReceivedUser = false;
	
	public Client(String hostname, int port) {
		try {
			s = new Socket(hostname, port);
			outputStream = new ObjectOutputStream(s.getOutputStream());
			inputStream = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setHaveReceivedLogin(boolean haveReceivedLogin) {
		this.haveReceivedLogin = haveReceivedLogin;
	}

	public void setHaveReceivedUser(boolean haveReceivedUser) {
		this.haveReceivedUser = haveReceivedUser;
	}


	public void run() {
		rd = new ReceiveData(inputStream, this);
		rd.start();
	}
	
	public void checkUser(String username, String password) {
		try {
			outputStream.writeObject(new CheckUser(username, password));
			outputStream.flush();
			while (!haveReceivedLogin) { 
				Thread.sleep(100);
			}
			CheckUser checkuser = rd.checkuser;
			if (checkuser.doesExist()) {
				System.out.println("Successful login");
			} else {
				System.out.println("Unsuccessful login");
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addUser(String username, String password, String name) {
		try {
			outputStream.writeObject(new AddUser(username, password, name, null));
			outputStream.flush();
			while (!haveReceivedUser) {
				Thread.sleep(100);
			}
			AddUser au = rd.adduser;
			if (au.isSuccessfulAdd()) {
				System.out.println("Have successfully created User");
			} else {
				System.out.println("Did not create user");
			}
			haveReceivedUser = false;
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
