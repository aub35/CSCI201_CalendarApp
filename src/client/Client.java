package client;

import java.io.IOException;
import java.net.Socket;

public class Client {

	private Socket s;
	
	public Client(String hostname, int port) {
		try {
			s = new Socket(hostname, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
