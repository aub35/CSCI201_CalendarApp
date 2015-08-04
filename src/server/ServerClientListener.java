package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerClientListener extends Thread {

	private Socket socket;
	private ServerListener serverListener;
	private PrintWriter outputStream;
	private BufferedReader inputStream;
	
	public ServerClientListener(Socket s, ServerListener sl) {
		socket = s;
		serverListener = sl;
	}
	
	public void run() {
		try {
			//get output stream
			outputStream = new PrintWriter(socket.getOutputStream());
			//get input stream
			InputStreamReader isr = new InputStreamReader(socket.getInputStream());
			inputStream = new BufferedReader(isr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
