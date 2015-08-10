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
import resources.AddFriend;
import resources.AddUser;
import resources.CheckUser;
import resources.FriendRequest;
import resources.GetEvents;
import resources.SearchFriend;

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
	haveReceivedGetEvents, haveReceivedSearchFriend, haveReceivedAddFriend,
	haveReceivedFriendRequestConfirmation = false;
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
	
	public void setHaveReceivedFriendRequestConfirmation(boolean haveReceivedFriendRequestConfirmation) {
		this.haveReceivedFriendRequestConfirmation = haveReceivedFriendRequestConfirmation;
	}

	public void setIsGuest(boolean isGuest) {
		this.isGuest = isGuest;
	}
	
	public void setHaveReceivedSearchFriend(boolean haveReceivedSearchFriend) {
		this.haveReceivedSearchFriend = haveReceivedSearchFriend;
	}

	public void setHaveReceivedAddFriend(boolean haveReceivedAddFriend) {
		this.haveReceivedAddFriend = haveReceivedAddFriend;
	}
	
	public String getUsername() {
		return user.getUsername();
	}
	
	//run method
	public void run() {
		rd = new ReceiveData(inputStream, this);
		rd.start();
	}
	
	//functionality for talking to server
	//checking user for login
	public boolean checkUser(String username, String password) {
		boolean successfulLogin=false;
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
				Vector<User> friendRequests = user.getFriendRequests();
				for (int i = 0; i < friendRequests.size(); i++) {
					System.out.println(friendRequests.elementAt(i).getUsername());
				}
				login();
				successfulLogin=true;
			} else {
				System.out.println("Unsuccessful login");
				successfulLogin=false;
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			rd.checkuser = null;
			haveReceivedLogin = false;
		}
		return successfulLogin;
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
	
	@SuppressWarnings("finally")
	public Vector<String> searchForFriend(String username) {
		Vector<String> result = new Vector<String>();
		try {
			outputStream.writeObject(new SearchFriend(username, user));
			outputStream.flush();
			outputStream.reset();
			while (!haveReceivedSearchFriend) {
				Thread.sleep(100);
			}
			Thread.sleep(10);
			SearchFriend sf = rd.searchfriend;
			if (sf.isSuccesfulSearch()) {
				result = sf.getUsernameList();
			} else {
				System.out.println("Failed to get Friends list");
			}
		} catch (IOException | InterruptedException ie) {
			ie.printStackTrace();
		} finally {
			rd.searchfriend = null;
			haveReceivedSearchFriend = false;
			return result;
		}
	}
	
	public void addFriend(String username) {
		try {
			outputStream.writeObject(new AddFriend(username, user));
			outputStream.flush();
			outputStream.reset();
			while (!haveReceivedAddFriend) {
				Thread.sleep(10);
			}
			Thread.sleep(10);
			AddFriend af = rd.addfriend;
			if(af.isSuccessfulAdd()) {
				sendFriendRequest(af);
				System.out.println("Sent friend request");
			} else {
				System.out.println("Unsuccessful add");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			rd.addfriend = null;
			haveReceivedAddFriend = false;			
		}
	}
	
	//send friend request
	private void sendFriendRequest(AddFriend af) {
		try {
			outputStream.writeObject(new FriendRequest(af.getAdder(), af.getRequestedUser()));
			outputStream.flush();
			outputStream.close();
			while (!haveReceivedFriendRequestConfirmation) {
				Thread.sleep(10);;
			} 
			Thread.sleep(10);
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	//get day's events
	public void getDaysEvents(MyDate date) {
		try {
			GetEvents ge2 = new GetEvents(date, date, user);
			outputStream.writeObject(ge2);
			outputStream.flush();
			outputStream.reset();
			while (!haveReceivedGetEvents) {
				Thread.sleep(100);
			}
			Thread.sleep(100);
			GetEvents ge = rd.getevents;
			if (ge.isSuccessfulGet()) {
				Vector<MyEvent> events = ge.getEvents();
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
		user.setCurrDate(MyDate.getTodaysDate());
		closeLoginWindow();
		openMainWindow();
		getDaysEvents(user.getCurrDate());
	}
	
	public void logout() {
		closeMainWindow();
		openLoginWindow();
		try {
			outputStream.writeObject(user);
			outputStream.flush();
			outputStream.reset();
		} catch(IOException ie) {
			ie.printStackTrace();
		}
		user = null;
	}
	
	public void dayClicked(MyDate currentDate) {
		user.setCurrDate(currentDate);
		getDaysEvents(user.getCurrDate());
	}
	
	public void nextDay() {
		MyDate next = MyDate.getNextDay(user.getCurrDate());
		user.setCurrDate(next);
		getDaysEvents(user.getCurrDate());
	}
	
	public void previousDay() {
		MyDate prev = MyDate.getPrevDay(user.getCurrDate());
		user.setCurrDate(prev);
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
	
	public MyDate getCurrDate(){
		return user.getCurrDate();
	}
	
	public User getCurrUser(){
		return user;
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
