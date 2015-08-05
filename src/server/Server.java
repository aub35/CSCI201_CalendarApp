package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
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
			if (value.getLength() == 0) {
				value.addEvent(e, 0);
			} else if (value.getLength() == 1) {
				
			}
		}
	}
	
}

class eventComp implements Comparator<Event> {

	public int compare(Event e1, Event e2) {
		Date e1Start = e1.getStart();
		Date e2Start = e2.getStart();
		if (e1Start.isEqualTo(e2Start)) {
			
		} else { 
			//if they have the same year
			if (e1Start.getYear() == e2Start.getYear()) {
				//if they have the same month
				if (e1Start.getMonth() == e2Start.getMonth()) {
					//if they have the same day
					if (e1Start.getDayOfMonth() == e2Start.getDayOfMonth()) {
						int e1Hour = e1Start.getHour();
						int e2Hour = e2Start.getHour();
						if (!e1Start.isAm()) { e1Hour += 12; }
						if (!e2Start.isAm()) { e2Hour += 12; }
						//if they have the same hour
						if(e1Hour == e2Hour) {
							return (e1Start.getMinute() - e2Start.getMinute());
						} else { // the dates have differing hours
							return (e1Hour - e2Hour);
						}
					} else { // the dates have differing days
						return (e1Start.getDayOfMonth() - e2Start.getDayOfMonth());
					}
				} else { //the dates have differing months
					return (e1Start.getMonth() - e2Start.getMonth());
				}
			} else { //the dates have differing years
				return (e1Start.getYear() - e2Start.getYear());
			}
		}
		return 0;
	}
	
}
