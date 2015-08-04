package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import calendar.User;
import calendar.Calendar;

//TODO : hard coded port
//add parsing existing users


public class Server {

	private ServerSocket ss;
	private static ServerListener sl;
	
	private Map<User, Calendar> userMap;
	
	public Server(int port) {
		
		//instantiate GUI for server
		try {
			userMap = new HashMap<User, Calendar>();
			ss = new ServerSocket(port);
			sl = new ServerListener(ss);
			sl.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addUser(User u) {
		Calendar c = new Calendar();
		userMap.put(u, c);
	}
	
	
}
