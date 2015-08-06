package server;

import java.io.BufferedReader;
import java.io.IOException;

public class ReceiveData extends Thread {

	private BufferedReader inputStream;
	private Server server;
	
	ReceiveData(BufferedReader inputStream, Server server) {
		this.inputStream = inputStream;
		this.server = server;
	}
	
	public void run() {
		String s;
		while (true) {
			try {
				s = inputStream.readLine();
				System.out.println("Server received: " + s);
				if (s.equals("QUIT")) { break; }
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
