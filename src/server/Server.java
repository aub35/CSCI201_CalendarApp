package server;

import java.io.IOException;
import java.net.ServerSocket;

//TODO : hard coded port


public class Server {

	private ServerSocket ss;
	private static ServerListener sl;
	
	public Server(int port) {
		
		//instantiate GUI for server
		try {
			ss = new ServerSocket(port);
			sl = new ServerListener(ss);
			sl.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
