package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerClientListener extends Thread {

	private Socket socket;
	private Server server;
	private PrintWriter outputStream;
	private BufferedReader inputStream;
	
	public ServerClientListener(Socket s, Server server) {
		socket = s;
		this.server = server;
	}
	
	public void run() {
		try {
			//get output stream
			outputStream = new PrintWriter(socket.getOutputStream());
			SendData sd = new SendData(outputStream, server);
			sd.start();
			//get input stream
			InputStreamReader isr = new InputStreamReader(socket.getInputStream());
			inputStream = new BufferedReader(isr);
			ReceiveData rd = new ReceiveData(inputStream, server);
			rd.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
