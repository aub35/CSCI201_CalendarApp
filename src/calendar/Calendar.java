package calendar;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Calendar {

	private Vector<Event> events;
	
	public Calendar() {

	}
	
	public void addEvent(Event e) {
		if (events.size() == 0) {
			events.add(0, e);
		} else if (events.size() == 1) {
			Collections.sort(events, new EventComp());
		}
	}
	
	public int getLength() {
		return events.size();
	}
}

class EventComp implements Comparator<Event> {

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

