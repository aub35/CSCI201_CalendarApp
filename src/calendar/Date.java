package calendar;

import java.io.Serializable;
import java.time.LocalDate;

public class Date implements Serializable {

	private static final long serialVersionUID = 1L;
	//for day of week, 1 is monday, 7 is sunday
	private int minute, hour, dayOfMonth, dayOfWeek, month, year;
	private boolean isAm;
	
	public static boolean isSameDay(Date d1, Date d2) {
		if (d1.getYear() != d2.getYear()) { return false; }
		else if (d1.getMonth() != d2.getMonth()) { return false; }
		else if (d1.getDayOfMonth() != d2.getDayOfMonth()) { return false; }
		return true;
	}
	
	public static Date getTodaysDate() {
		int minute = 0, hour = 0, dayOfWeek = 0;
		LocalDate ld = LocalDate.now();
		int year = ld.getYear();
		int month = ld.getMonthValue();
		int day = ld.getDayOfMonth();
		Date d =  new Date(minute, hour, dayOfWeek, day, month, year, true);
		System.out.println("Date: " + d);
		return d;
	}
	
	public int getMinute() {
		return minute;
	}

	public int getHour() {
		return hour;
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}
	
	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public boolean isAm() {
		return isAm;
	}

	public Date(int minute, int hour, int dayOfWeek, int dayOfMonth,
			int month, int year, boolean isAm) {
		this.minute = minute;
		this.hour = hour;
		this.dayOfMonth = dayOfMonth;
		this.dayOfWeek = dayOfWeek;
		this.month = month;
		this.year = year;
	}
	
	public boolean isEqualTo(Date other) {
		if (this.year != other.getYear() || this.month != other.getMonth() || 
				this.dayOfMonth != other.getDayOfMonth() || this.dayOfWeek != other.getDayOfWeek() ||
				this.hour != other.getHour() || this.minute != other.getMinute()) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		String str = month + "/" + dayOfMonth + "/" + year;
		str += "\n" + hour + ":" + minute;
		return str;
	}
	
}
