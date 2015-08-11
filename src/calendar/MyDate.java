package calendar;

import java.io.Serializable;
import java.time.LocalDate;

public class MyDate implements Serializable {

	private static final long serialVersionUID = 2L;
	private int minute, hour, dayOfMonth, month, year;
	
	public MyDate(int minute, int hour, int dayOfMonth,
			int month, int year) {
		this.minute = minute;
		this.hour = hour;
		this.dayOfMonth = dayOfMonth;
		this.month = month;
		this.year = year;
	}
	
	public static int getDayOfWeek(MyDate d) {
		LocalDate ld = LocalDate.of(d.getYear(), d.getMonth(), d.getDayOfMonth());
		return ld.getDayOfWeek().getValue();
	}
	
	public static boolean isSameDay(MyDate d1, MyDate d2) {
		if (d1.getYear() != d2.getYear()) { return false; }
		else if (d1.getMonth() != d2.getMonth()) { return false; }
		else if (d1.getDayOfMonth() != d2.getDayOfMonth()) { return false; }
		return true;
	}
	
	public static MyDate getTodaysDate() {
		int minute = 0, hour = 0;
		LocalDate ld = LocalDate.now();
		int year = ld.getYear();
		int month = ld.getMonthValue();
		int day = ld.getDayOfMonth();
		MyDate d =  new MyDate(minute, hour, day, month, year);
		return d;
	}
	
	public static boolean isEndOfMonth(int month, int dayOfMonth) {
		if (month == 2) {
			if (dayOfMonth == 28) {
				return true;
			}
		} else if (month == 1 || month == 3 || month == 5 || month == 7 ||
				month == 8 || month == 10 || month == 12) {
			if (dayOfMonth == 31) {
				return true;
			}
		} else {
			if (dayOfMonth == 30) {
				return true;
			}
		}
		return false;
	}
	
	public static MyDate getEndOfMonth(MyDate currDate) {
		int month = currDate.getMonth();
		int day;
		if (month == 2) {
			day = 28; 
		} else if (month == 1 || month == 3 || month == 5 || month == 7 ||
				month == 8 || month == 10 || month == 12) {
			day = 31;
		} else {
			day = 30;
		}
		return new MyDate(currDate.getMinute(), currDate.getHour(), day,
				currDate.getMonth(), currDate.getYear());
	}
		
	public static MyDate getNextDay(MyDate currDate) {
		if (MyDate.isEndOfMonth(currDate.getMonth(), currDate.getDayOfMonth())) {
			int month;
			if (currDate.getMonth() == 12) { month = 1; }
			else { month = currDate.getMonth() + 1; }
			currDate.setMonth(month);
			currDate.setDayOfMonth(1);
		} else {
			int day = currDate.getDayOfMonth() + 1;
			currDate.setDayOfMonth(day);
		}		
		return currDate;
	}
	
	public static MyDate getPrevDay(MyDate currDate) {
		if (currDate.getDayOfMonth() == 1) {
			if (currDate.getMonth() == 3) {
				currDate.setMonth(2);
				currDate.setDayOfMonth(28);
			} else if (currDate.getMonth() == 5 || currDate.getMonth() == 7 || 
					currDate.getMonth() == 10 || currDate.getMonth() == 12) {
				currDate.setMonth(currDate.getMonth() - 1);
				currDate.setDayOfMonth(30);
			} else {
				currDate.setMonth(currDate.getMonth() - 1);
				currDate.setDayOfMonth(31);
			}
		} else {
			currDate.setDayOfMonth(currDate.getDayOfMonth() - 1);
		}
		return currDate;
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
	
	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}
	
	public boolean isEqualTo(MyDate other) {
		if (this.year != other.getYear() || this.month != other.getMonth() || 
				this.dayOfMonth != other.getDayOfMonth() || 
				this.hour != other.getHour() || this.minute != other.getMinute()) {
			return false;
		}
		return true;
	}
	
	
	public String toString() {
		String str = String.format("%02d/%02d/%04d", month, dayOfMonth, year);
		//String str = month + "/" + dayOfMonth + "/" + year;
		str += "\n";
		str += String.format("%02d:%02d", hour, minute);
		//str += "\n" + hour + ":" + minute;
		return str;
	}

	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	public String displayDate(){
		String str = String.format("%02d/%02d/%04d", month, dayOfMonth, year);
		return str;
	}
	
}
