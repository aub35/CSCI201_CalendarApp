package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//protocol should be to send username, password & check if there is a matching one in server database
//TODO: login screen GUI

public class Client extends Thread {

	private Socket s;
	private PrintWriter outputStream;
	private BufferedReader inputStream;
	
	
	public Client(String hostname, int port) {
		try {
			s = new Socket(hostname, port);
			outputStream = new PrintWriter(s.getOutputStream());
			InputStreamReader isr = new InputStreamReader(s.getInputStream());
			inputStream = new BufferedReader(isr);
//			checkUser();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		ReceiveData rd = new ReceiveData(inputStream, this);
		rd.start();
		SendData sd = new SendData(outputStream, this);
		sd.start();
	}
	
	private void checkUser() {
		sendInformation();
		if (receiveInformation()) {
			System.out.println("User exists");
		} else {
			System.out.println("User does not exist");
		}
	}
	
	private void sendInformation() {
		String username = "user";
		String password = "password";
		outputStream.println(username);
		outputStream.println(password);
		outputStream.flush();
	}
	
	private boolean receiveInformation() {
		try {
			return Boolean.parseBoolean(inputStream.readLine());
		} catch (IOException e) {
			System.out.println("Failed to receive information");
			return false;
		}
	}
}
