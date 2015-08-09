package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import calendar.User;
import resources.AddEvent;
import resources.AddUser;
import resources.CheckUser;
import resources.GetEvents;
import calendar.MyCalendar;
import calendar.MyDate;
import calendar.MyEvent;

//TODO : hard coded port
//add parsing existing users
//guest user interface


public class MyServer {

	private ServerSocket ss;
	private static ServerListener sl;
	
	private Map<User, MyCalendar> userMap;
	
	public MyServer(int port) {
		
		//instantiate GUI for server
		try {
			userMap = new HashMap<User, MyCalendar>();
			ss = new ServerSocket(port);
			sl = new ServerListener(ss, this);
			sl.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void checkUser(CheckUser cu) {
		String username = cu.getUsername();
		String password = cu.getPassword();
		for (User key : userMap.keySet()) {
			String keyUsername = key.getUsername();
			String keyPassword = key.getPassword();
			if (username.equals(keyUsername)) {
				if (password.equals(keyPassword)) {
					cu.setDoesExist(true);
					cu.setUser(key);
					return;
				} else {
					cu.setDoesExist(false);
					return;
				}
			}
		}
	}
	
	public void addUser(AddUser au) {
		String username = au.getUsername();
		User u;
		if (!username.equals("Guest")) {
			for (User key : userMap.keySet()) {
				if (username.equals(key.getUsername())) {
					au.setSuccessfulAdd(false);
					return;
				}		
			}
			u = new User(au.getUsername(), au.getPassword(), au.getName(), false);
		} else {
			u = new User("Guest", "", "Guest", true);
		}
		MyCalendar c = new MyCalendar();
		userMap.put(u, c);
		au.setSuccessfulAdd(true);
		au.setUser(u);
	}
	
	public MyDate currentTime() {
		LocalTime lt = LocalTime.now();
		int minute = lt.getMinute();
		int hour = lt.getHour();		
		LocalDate ld = LocalDate.now();
		int dayOfMonth = ld.getDayOfMonth();
		int month = ld.getMonthValue();
		int year = ld.getYear();
		
		
		return new MyDate(minute, hour, dayOfMonth, month, year);		
	}
	
	public void addEvent(AddEvent ae) {
		User u = ae.getU();
		MyEvent e = ae.getE();
		MyCalendar value = null;
		for (User key : userMap.keySet()) {
			if (User.isEqual(key, u)) {
				value = userMap.get(key);
			}
		}	
		if (value != null) {
			value.addEvent(e);
			ae.setSuccessfulAdd(true);
		}
	}
	
	public void getEvents(GetEvents ge) {
		User u = ge.getUser();
		MyDate start = u.getCurrDate();
		MyDate end = u.getCurrDate();
		MyCalendar value = null;
		for (User key : userMap.keySet()) {
			if (User.isEqual(key, u)) {
				value = userMap.get(key);
			}
		}		
		if (value != null) {
			System.out.println("GetEvents date: " + start);
			if (MyDate.isSameDay(start, end)) {
				Vector<MyEvent> events = value.getDaysEvent(start);
				ge.setEvents(events);
			}
			ge.setSuccessfulGet(true);
		} else {
			System.out.println("Server determined value is null");
		}
	}
/*	
	//MAKE SURE DATE INCLUDES THE LATEST TIME IN THE DAY
	public Vector<Event> getDayEvent(User u, Date date) {
		Vector<Event> result = new Vector<Event>();
		Calendar value = userMap.get(u);
		if (value != null && value.getLength() != 0) {
			//value
		}
		return result;
	}
*/	
}

