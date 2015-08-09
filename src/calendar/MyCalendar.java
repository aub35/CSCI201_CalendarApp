package calendar;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class MyCalendar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<MyEvent> events;
	
	public MyCalendar() {
		events = new Vector<MyEvent>();
	}
	
	public void addEvent(MyEvent e) {
		for (int j = 0; j < events.size(); j++) {
			System.out.println(e.getStart() + " - ");
		}
		System.out.println();
		events.add(e);
		Collections.sort(events, new EventComp());
		for (int i = 0; i < events.size(); i++) {
			MyEvent tempE = events.elementAt(i);
			System.out.print(tempE.getStart() + " - ");
		}
		System.out.println();
	}
	
	public int getLength() {
		return events.size();
	}
	
	public Vector<MyEvent> getDaysEvent(MyDate date) {
		System.out.println("Date To Compare: " + date);
		Vector<MyEvent> result = new Vector<MyEvent>();
		if (events.size() == 0) {
			return result;
		}
		int index = 0;
		DateComp dc = new DateComp();
		MyDate before = events.elementAt(index).getStart();
		while (dc.compare(before, date) < 0) {
			index++;
			System.out.println("Before Iteration: " + index);
			if (index >= events.size()) { break; }
			before = events.elementAt(index).getStart();
		}
		while (MyDate.isSameDay(before, date)) {
			System.out.println("Same Day Iteration: " + index);
			result.add(events.elementAt(index));
			index++;
			if (index >= events.size()) { break; }
			before = events.elementAt(index).getStart();
		}
		return result;
	}
}

class DateComp implements Comparator<MyDate> {
	
	public int compare(MyDate d1, MyDate d2) {
		if (d1.isEqualTo(d2)) {
			return 0;
		} else { 
			//if they have the same year
			if (d1.getYear() == d2.getYear()) {
				//if they have the same month
				if (d1.getMonth() == d2.getMonth()) {
					//if they have the same day
					if (d1.getDayOfMonth() == d2.getDayOfMonth()) {
						int e1Hour = d1.getHour();
						int e2Hour = d2.getHour();
						//if they have the same hour
						if(e1Hour == e2Hour) {
							return (d1.getMinute() - d2.getMinute());
						} else { // the dates have differing hours
							return (e1Hour - e2Hour);
						}
					} else { // the dates have differing days
						return (d1.getDayOfMonth() - d2.getDayOfMonth());
					}
				} else { //the dates have differing months
					return (d1.getMonth() - d2.getMonth());
				}
			} else { //the dates have differing years
				return (d1.getYear() - d2.getYear());
			}
		}
	}
	
}

class EventComp implements Comparator<MyEvent> {

	public int compare(MyEvent e1, MyEvent e2) {
		MyDate e1Start = e1.getStart();
		MyDate e2Start = e2.getStart();
		if (e1Start.isEqualTo(e2Start)) {
			return 0;
		} else { 
			//if they have the same year
			if (e1Start.getYear() == e2Start.getYear()) {
				//if they have the same month
				if (e1Start.getMonth() == e2Start.getMonth()) {
					//if they have the same day
					if (e1Start.getDayOfMonth() == e2Start.getDayOfMonth()) {
						int e1Hour = e1Start.getHour();
						int e2Hour = e2Start.getHour();
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
	}
	
}

