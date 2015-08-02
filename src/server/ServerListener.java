package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerListener extends Thread {

	private ServerSocket ss;
	
	public ServerListener(ServerSocket ss) {
		this.ss =ss;
	}
	
	public void run() {
		while (true) {
			
			try {
				System.out.println("Waiting for connection");
				ss.accept();
				System.out.println("Connected");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
