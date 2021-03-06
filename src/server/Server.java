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
		userMap.put(u, new Calendar());
		au.setSuccessfulAdd(true);
		au.setUser(u);
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
	
	public void addEvent(AddEvent ae) {
		User u = ae.getU();
		Event e = ae.getE();
		Calendar value = userMap.get(u);
		if (value != null) {
			value.addEvent(e);
		}
		ae.setSuccessfulAdd(true);
	}
	
	public void getEvents(GetEvents ge) {
		User u = ge.getUser();
		Date start = ge.getStart();
		Date end = ge.getEnd();
		Calendar value = userMap.get(u);
		if (value != null) {
			if (Date.isSameDay(start, end)) {
				Vector<Event> events = value.getDaysEvent(start);
				ge.setEvents(events);
			}
		}
	}
	
	//MAKE SURE DATE INCLUDES THE LATEST TIME IN THE DAY
	public Vector<Event> getDayEvent(User u, Date date) {
		Vector<Event> result = new Vector<Event>();
		Calendar value = userMap.get(u);
		if (value != null && value.getLength() != 0) {
			//value
		}
		return result;
	}
	
}

