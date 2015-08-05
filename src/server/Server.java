package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import calendar.User;
import calendar.Calendar;
import calendar.Date;
import calendar.Event;

//TODO : hard coded port
//add parsing existing users
//guest user interface


public class Server {

	private ServerSocket ss;
	private static ServerListener sl;
	
	private Map<User, Calendar> userMap;
	
	public Server(int port) {
		
		//instantiate GUI for server
		try {
			userMap = new HashMap<User, Calendar>();
			ss = new ServerSocket(port);
			sl = new ServerListener(ss, this);
			sl.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addUser(User u) {
		Calendar c = new Calendar();
		userMap.put(u, c);
	}
	
	public Date currentTime() {
		LocalTime lt = LocalTime.now();
		int minute = lt.getMinute();
		int hour = lt.getHour();
		boolean isAm = true;
		if (lt.isAfter(LocalTime.NOON)) { 
			hour -= 12; 
			isAm = false;
		}
		
		LocalDate ld = LocalDate.now();
		int dayOfMonth = ld.getDayOfMonth();
		int dayOfWeek = ld.getDayOfWeek().getValue(); //1 is monday, 7 is sunday
		int month = ld.getMonthValue();
		int year = ld.getYear();
		
		
		return new Date(minute, hour, dayOfWeek, dayOfMonth, month, year, isAm);		
	}
	
	public void addEvent(User u, Event e) {
		Calendar value = userMap.get(u);
		if (value != null) {
			return;
		}
	}
	
}

