package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {

	private ServerSocket ss;
	
	public ServerListener(ServerSocket ss) {
		this.ss =ss;
	}
	
	public void run() {
		while (true) {
			
			try {
				System.out.println("Waiting for connection");
				Socket s = ss.accept();
				ServerClientListener sl = new ServerClientListener(s, this);
				sl.start();
				System.out.println("Connected");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
