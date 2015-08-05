package client;

import java.io.BufferedReader;
import java.io.IOException;

public class ReceiveData extends Thread {

	private BufferedReader inputStream;
	private Client client;
	
	ReceiveData(BufferedReader inputStream, Client client) {
		this.inputStream = inputStream;
		this.client = client;
	}
	
	public void run() {
		String s;
		while (true) {
			try {
				s = inputStream.readLine();
				System.out.println("Client received: " + s);
				if (s.equals("QUIT")) { break; }
			} catch (IOException e) {
				break;
			}
			
		}
	}
}
