package calendar;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Calendar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Event> events;
	
	public Calendar() {

	}
	
	public void addEvent(Event e) {
		events.add(e);
		Collections.sort(events, new EventComp());
		for (int i = 0; i < events.size(); i++) {
			System.out.println(e.getStart());
		}
	}
	
	public int getLength() {
		return events.size();
	}
	
	public Vector<Event> getDaysEvent(Date date) {
		Vector<Event> result = new Vector<Event>();
		int index = 0;
		DateComp dc = new DateComp();
		Date before = events.elementAt(index).getStart();
		while (dc.compare(before, date) < 0) {
			index++;
			before = events.elementAt(index).getStart();
		}
		if (Date.isSameDay(before, date)) {
			result.add(events.elementAt(index));
		}
		return result;
	}
}

class DateComp implements Comparator<Date> {
	
	public int compare(Date d1, Date d2) {
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
						if (!d1.isAm()) { e1Hour += 12; }
						if (!d2.isAm()) { e2Hour += 12; }
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

class EventComp implements Comparator<Event> {

	public int compare(Event e1, Event e2) {
		Date e1Start = e1.getStart();
		Date e2Start = e2.getStart();
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
	}
	
}

