package client;

import java.io.PrintWriter;
import java.util.Scanner;

public class SendData extends Thread {

	PrintWriter outputStream;
	Client client;
	
	SendData(PrintWriter outputStream, Client client) {
		this.outputStream = outputStream;
		this.client = client;
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
