package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class SendData extends Thread {

	private ObjectOutputStream outputStream;
	private Server server;
	
	SendData(ObjectOutputStream outputStream, Server server) {
		this.outputStream = outputStream;
		this.server = server;
	}
	
	public void run() {
		Scanner scan = new Scanner(System.in);
		String s;
		while (true) {
			s = scan.nextLine();
			try {
				outputStream.writeObject(s);
				outputStream.flush();
				if (s.equals("QUIT")) { break; }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		scan.close();
	}
}
