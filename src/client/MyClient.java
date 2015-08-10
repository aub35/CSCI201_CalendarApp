package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import calendar.MyDate;
import calendar.MyEvent;
import calendar.User;
import resources.AddEvent;
import resources.AddUser;
import resources.CheckUser;
import resources.GetEvents;

//protocol should be to send username, password & check if there is a matching one in server database
//TODO: login screen GUI
//log out

public class MyClient extends Thread {

	private Socket s;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private ReceiveData rd;
	
	private static MainWindow mainwindow;
	private static LoginWindow loginwindow;
	
	private boolean isGuest;
	private boolean haveReceivedLogin, haveReceivedUser, haveReceivedAddEvent,
	haveReceivedGetEvents = false;
	private User user;
	
	//constructor
	public MyClient(String hostname, int port) {
		try {
			s = new Socket(hostname, port);
			outputStream = new ObjectOutputStream(s.getOutputStream());
			inputStream = new ObjectInputStream(s.getInputStream());
			openLoginWindow();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//private variable setters
	public void setHaveReceivedLogin(boolean haveReceivedLogin) {
		this.haveReceivedLogin = haveReceivedLogin;
	}

	public void setHaveReceivedUser(boolean haveReceivedUser) {
		this.haveReceivedUser = haveReceivedUser;
	}

	public void setHaveReceivedAddEvent(boolean haveReceivedAddEvent) {
		this.haveReceivedAddEvent = haveReceivedAddEvent;
	}
	
	public void setHaveReceivedGetEvents(boolean haveReceivedGetEvents) {
		this.haveReceivedGetEvents = haveReceivedGetEvents;
	}
	
	public void setIsGuest(boolean isGuest) {
		this.isGuest = isGuest;
	}
	
	//run method
	public void run() {
		rd = new ReceiveData(inputStream, this);
		rd.start();
		System.out.println("Reached end of client thread");
	}
	
	//functionality for talking to server
	//checking user for login
	public void checkUser(String username, String password) {
		try {
			outputStream.writeObject(new CheckUser(username, password));
			outputStream.flush();
			while (!haveReceivedLogin) { 
				Thread.sleep(100);
			}
			Thread.sleep(100);
			CheckUser checkuser = rd.checkuser;
			if (checkuser.doesExist()) {
				user = checkuser.getUser();
				login();
			} else {
				System.out.println("Unsuccessful login");
				//add error message
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			rd.checkuser = null;
			haveReceivedLogin = false;
		}
	}
	
	//adding a user
	public void addUser(String username, String password, String name) {
		try {
			if (!isGuest) {
				outputStream.writeObject(new AddUser(username, password, name, null, false));				
			} else {
				outputStream.writeObject(new AddUser(username, password, name, null, true));
			}
			outputStream.flush();
			outputStream.reset();
			while (!haveReceivedUser) {
				Thread.sleep(100);
			}
			Thread.sleep(100);
			AddUser au = rd.adduser;
			if (au.isSuccessfulAdd()) {
				user = au.getUser();
				login();
			} else {
				System.out.println("Did not create user");
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			haveReceivedUser = false;
			rd.adduser = null;
		}
	}
	
	//add an event
	public void addEvent(MyEvent e) {
		try {
			outputStream.writeObject(new AddEvent(user, e));
			outputStream.flush();
			while (!haveReceivedAddEvent) {
				Thread.sleep(100);
			}
			Thread.sleep(100);
			AddEvent ae = rd.addevent;
			if (ae.isSuccessfulAdd()) {
				System.out.println("Successfully added event");
				getDaysEvents(user.getCurrDate());
			} else {
				System.out.println("Unsuccessful add");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} finally {
			rd.addevent = null;
			haveReceivedAddEvent = false;
		}
	}
	
	//get day's events
	public void getDaysEvents(MyDate date) {
		try {
			
//			user.setCurrDate(date);
			GetEvents ge2 = new GetEvents(date, date, user);
			outputStream.writeObject(ge2);
			outputStream.flush();
			outputStream.reset();
			System.out.println("Sent getEvents");
			while (!haveReceivedGetEvents) {
				Thread.sleep(100);
			}
			System.out.println("Received getEvents");
			Thread.sleep(100);
			GetEvents ge = rd.getevents;
			if (ge.isSuccessfulGet()) {
				System.out.println("Original sent with : " + user.getCurrDate());
				System.out.println("Received ge with : " + ge.getStart());
				Vector<MyEvent> events = ge.getEvents();
				System.out.println("Successfully got " + events.size() + " events"); 
				for (int i = 0; i < events.size(); i++) {
					mainwindow.displayEvent(events.get(i));
				}
			} else {
				System.out.println("Unsuccessful getting events");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} finally {
			rd.getevents = null;
			haveReceivedGetEvents = false;
		}
	}
	
	//functionality for talking to GUI
	public void login() {
		closeLoginWindow();
		openMainWindow();
		user.setCurrDate(MyDate.getTodaysDate());
		getDaysEvents(user.getCurrDate());
	}
	
	public void logout() {
		closeMainWindow();
		openLoginWindow();
		user = null;
	}
	
	public void nextDay() {
		MyDate next = MyDate.getNextDay(user.getCurrDate());
		user.setCurrDate(next);
		System.out.println("New date: " + user.getCurrDate());
		getDaysEvents(user.getCurrDate());
	}
	
	public void previousDay() {
		MyDate prev = MyDate.getPrevDay(user.getCurrDate());
		user.setCurrDate(prev);
		System.out.println("New date: " + user.getCurrDate());
		getDaysEvents(user.getCurrDate());
	}
	
	public void quit() {
		try {
			rd.setQuit(true);
			Thread.sleep(20);
			s.close();
		} catch (IOException | InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	//GUI functionality
	private void closeLoginWindow() {
		loginwindow.dispose();
	}
	
	private void openLoginWindow() {
		loginwindow = new LoginWindow(this);
	}
	
	private void closeMainWindow() {
		mainwindow.dispose();
	}
	
	private void openMainWindow() {
		mainwindow = new MainWindow(this);
	}
	
}
