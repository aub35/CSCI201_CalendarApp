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
import resources.SearchFriend;
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
	private Map<User, MyCalendar> guestUserMap;
	int guestIndex = 0;
	 
	public MyServer(int port) {
		
		//instantiate GUI for server
		try {
			userMap = new HashMap<User, MyCalendar>();
			guestUserMap = new HashMap<User, MyCalendar>();
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
		MyCalendar c = new MyCalendar();
		if (!au.isGuest()) {
			for (User key : userMap.keySet()) {
				if (username.equals(key.getUsername())) {
					au.setSuccessfulAdd(false);
					return;
				}		
			}
			u = new User(au.getUsername(), au.getPassword(), au.getName(), false);
			userMap.put(u, c);
		} else {
			u = new User("Guest", "", "Guest", true);
			u.setGuestIndex(guestIndex);
			guestIndex++;
			guestUserMap.put(u, c);
		}
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
		if (!u.isGuest()) {
			for (User key : userMap.keySet()) {
				if (User.isEqual(key, u)) {
					value = userMap.get(key);
				}
			}				
		} else {
			for (User key : guestUserMap.keySet()) {
				if (User.isGuestEqual(key, u)) {
					value = guestUserMap.get(key);
				}
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
		if (!u.isGuest()) {
			for (User key : userMap.keySet()) {
				if (User.isEqual(key, u)) {
					value = userMap.get(key);
				}
			}
		} else {
			for (User key : guestUserMap.keySet()) {
				if (User.isGuestEqual(key, u)) {
					value = guestUserMap.get(key);
				}
			}
		}
		if (value != null) {
			if (MyDate.isSameDay(start, end)) {
				Vector<MyEvent> events = value.getDaysEvent(start);
				ge.setEvents(events);
			}
			ge.setSuccessfulGet(true);
		} else {
			System.out.println("Server determined value is null");
		}
	}
	
	public void searchForFriend(SearchFriend sf) {
		
	}

	public void quitUser(User u) {
		if (u.isGuest()) {
			for (User key : guestUserMap.keySet()) {
				if (User.isGuestEqual(key, u)) {
					guestUserMap.remove(key);
					System.out.println("Size of Guest map: " + guestUserMap.size());
				}
			}
		}
	}
}

