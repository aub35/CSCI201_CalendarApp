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
import resources.AddFriend;
import resources.AddUser;
import resources.CheckUser;
import resources.FriendRequest;
import resources.FriendRequestResponse;
import resources.GetEvents;
import resources.GetFriendList;
import resources.SearchFriend;
import trie.MyTrie;
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
	private Map<User, ServerClientListener> connections;
	private MyTrie usernameList;
	int guestIndex = 0;
	
	static public MySQLDriver msql;
	 
	public MyServer(int port) {
		
		//instantiate GUI for server
		try {
			userMap = new HashMap<User, MyCalendar>();
			guestUserMap = new HashMap<User, MyCalendar>();
			connections = new HashMap<User, ServerClientListener>();
			usernameList = new MyTrie();
			ss = new ServerSocket(port);
			sl = new ServerListener(ss, this);
			sl.start();
			
			/*
			msql = new MySQLDriver();
			msql.createDatabase();
			msql.createTable();
			*/
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
	
	public void addConnection(User u, ServerClientListener scl) {
		connections.put(u, scl);
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
			usernameList.add(au.getUsername());
			//sendUser(u); // sends user to database
		} else {
			u = new User("Guest", "", "Guest", true);
			u.setGuestIndex(guestIndex);
			guestIndex++;
			guestUserMap.put(u, c);
		}
		au.setSuccessfulAdd(true);
		au.setUser(u);
	}
	
	public static void sendUser(User u) {
		msql.connectToDB();
		if ( !msql.doesExist(u.getUsername()) ) {
			msql.add(u.getUsername(), u.getPassword(), u.getName());
		}
		msql.stop();
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
		MyDate start = ge.getStart();
		MyDate end = ge.getEnd();
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
			Vector<MyEvent> events = new Vector<MyEvent>();
			if (MyDate.isSameDay(start, end)) {
				if (ge.isOnlyImportantEvents()){
					events = value.getDaysEvent(start, true);
				} else {
					events = value.getDaysEvent(start, false);
				}
			} else {
				events = value.getMonthsEvents(start, end);
			}
			ge.setEvents(events);
			ge.setSuccessfulGet(true);
		}
	}
	
	public void searchForFriend(SearchFriend sf) {
		String toSearch = sf.getUsername();
		Vector<String> result = usernameList.findUsernames(toSearch);
		sf.setUsernameList(result);
		sf.setSuccesfulSearch(true);
	}

	public void addFriend(AddFriend af) {
		String toSearch = af.getUsername();
		User adder = af.getAdder();
		for (User key : userMap.keySet()) {
			if (key.getUsername().equals(toSearch)) {
				af.setAdder(adder);
				af.setRequestedUser(key);
				key.addFriendRequest(adder);
				af.setSuccessfulAdd(true);
			}
		}
	}
	
	public void getFriendList(GetFriendList gfl) {
		User u = gfl.getU();
		for (User key : userMap.keySet()) {
			if (User.isEqual(key, u)) {
				gfl.setFriends(key.getFriends());
				gfl.setFriendRequests(key.getFriendRequests());
				gfl.setSuccessfulGet(true);
			}
		}
	}
	
	public void sendFriendRequestResponse(FriendRequestResponse frr) {
		User adder = frr.getAdder();
		User added = frr.getAdded();
		for (User key : userMap.keySet()) {
			if (User.isEqual(key, adder)) {
				if (frr.isDidAccept()) {	
					key.addFriend(added);
				}
			} else if (User.isEqual(key, added)) {
				if (frr.isDidAccept()){
					key.addFriend(adder);					
				}
				key.removeFriendRequest(adder);
			}
		}
		for (User key : connections.keySet()) {
			if (User.isEqual(key, adder)) {
				ServerClientListener scl = connections.get(key);
				scl.sendBackFriendRequestResponse(frr);
			}
		}
	}
		
	public void sendFriendRequest(FriendRequest fr){
		User requestedUser = fr.getRequestedUser();
		for (User key : connections.keySet()) {
			if (User.isEqual(requestedUser, key)) {
				ServerClientListener scl = connections.get(key);
				scl.sendFriendRequest(fr);
			}
		}
	}
	
	public void quitUser(User u) {
		if (u.isGuest()) {
			for (User key : guestUserMap.keySet()) {
				if (User.isGuestEqual(key, u)) {
					guestUserMap.remove(key);
				}
			}
		} else {
			for (User key : connections.keySet()) {
				if (User.isEqual(key, u)) {
					connections.remove(key);
				}
			}
		}
	}
}

