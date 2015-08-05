package server;

import java.io.PrintWriter;
import java.util.Scanner;

public class SendData extends Thread {

	private PrintWriter outputStream;
	private Server server;
	
	SendData(PrintWriter outputStream, Server server) {
		this.outputStream = outputStream;
		this.server = server;
	}
	
	public void run() {
		Scanner scan = new Scanner(System.in);
		String s;
		while (true) {
			s = scan.nextLine();
			outputStream.println(s);
			outputStream.flush();
			if (s.equals("QUIT")) { break; }
		}
		scan.close();
	}
}
