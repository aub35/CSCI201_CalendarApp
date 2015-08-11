package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import calendar.MyDate;
import calendar.MyEvent;
import calendar.User;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private MyClient c;
	JButton addEventButton, logoutButton, previousButton, nextButton, addFriendButton, checkRequestButton;
	JPanel centerPanel, rightPanel, calPanel, dayPanel, switchDatePanel, monthPanel, leftPanel, infoPanel;
	JDialog addEventWindow, addFriendWindow;
	JScrollPane jsp, friendScrollPane, eventScrollPane;
	GridBagConstraints gbc;
	JLabel [] hourLabels, eventLabels, weekdayLabels;
	JLabel [] monthDayLabels;
	JLabel currDateLabel, currUserLabel, modeLabel;
	JTextArea friendsTextArea, eventTextArea;
	MyDate currDate;
	User currUser;
	//Vector<MyEvent> events;
	boolean monthlyMode;
	JRadioButton dailyRadioButton, monthlyRadioButton;
	ButtonGroup modeButtonGroup;
	int daysInMonth;

	
	
	public MainWindow(MyClient c) {
		this.c = c;
		//TODO
		currDate = c.getCurrDate();
		//currDate = new MyDate(0, 0, 9, 8, 2015);
		//currUser = new User("a", "a", "a", false);
		// events
		
		monthlyMode = false;
		createGUI();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 500);
		addEventHandlers();
		displayFriends(currUser);
		displayImportantEvents();
		
		if (currUser.isGuest()){
			addFriendButton.setEnabled(false);;
			checkRequestButton.setEnabled(false);
		}
	}
	
	private void createGUI(){
		
		//centerPanel
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		//TODO add current date Label and username
		infoPanel = new JPanel();
		currDateLabel = new JLabel(currDate.displayDate());
		infoPanel.add(currDateLabel);
		currUser = c.getCurrUser();
		currUserLabel = new JLabel("Current User: " + currUser.getUsername());
		infoPanel.add(currUserLabel);
		centerPanel.add(infoPanel);
		
		
		//Panel for Calendar
		calPanel = new JPanel();
		calPanel.setLayout(new BorderLayout());
		
		//Daily Mode
		dayPanel = new JPanel();
		dayPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		hourLabels = new JLabel[24];
		eventLabels = new JLabel[24];
		
		for (int i=0; i<24; i++){
			JLabel label = new JLabel("<html>"+i+":00<br/><br/><br/><br/></html>");
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.weightx =  1;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.BOTH;
			hourLabels[i] = label;
			dayPanel.add(label, gbc);
		}
		
		for (int i=0; i<24; i++){
			JLabel label = new JLabel(" ");
			label.setOpaque(true);
			gbc.gridx = 1;
			gbc.weightx =  3;
			gbc.gridy = i;
			gbc.gridwidth = 3;
			gbc.fill = GridBagConstraints.BOTH;
			eventLabels[i] = label;
			dayPanel.add(label, gbc);
		}
		
		jsp = new JScrollPane(dayPanel);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		calPanel.add(jsp, BorderLayout.CENTER);
		centerPanel.add(calPanel);
		
		eventTextArea = new JTextArea();
		eventTextArea.setRows(10);
		eventTextArea.setEditable(false);
		//eventTextArea.setCursor(null);
		//eventTextArea.setOpaque(false);
		//eventTextArea.setFocusable(false);
		eventScrollPane = new JScrollPane(eventTextArea);
		eventScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		centerPanel.add(eventScrollPane);
		
		switchDatePanel = new JPanel();
		previousButton = new JButton("Previous");
		nextButton = new JButton("Next");
		switchDatePanel.add(previousButton);
		switchDatePanel.add(nextButton);
		centerPanel.add(switchDatePanel);
		
		add(centerPanel);
		
		// monthly Panel
		createMonthPanel();
		
		
		// rightPanel
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		addEventButton =new JButton("Add Event");
		rightPanel.add(addEventButton);
		addFriendButton = new JButton("Add Friend");
		rightPanel.add(addFriendButton);
		checkRequestButton = new JButton("Check Friend Request");
		rightPanel.add(checkRequestButton);
		logoutButton = new JButton("Log out");
		rightPanel.add(logoutButton);
		friendsTextArea = new JTextArea();
		friendsTextArea.setEditable(false);
		rightPanel.add(friendsTextArea);
		//friendScrollPane = new JScrollPane(friendScrollPane);
		//rightPanel.add(friendScrollPane);
		
		
		add(rightPanel, BorderLayout.EAST);
		
		
		
		//leftPanel
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		modeLabel = new JLabel("Mode: ");
		leftPanel.add(modeLabel);
		
		modeButtonGroup  = new ButtonGroup();
		dailyRadioButton = new JRadioButton("Daily");
		dailyRadioButton.setSelected(true);
		monthlyRadioButton = new JRadioButton("Monthly");
		modeButtonGroup.add(dailyRadioButton);
		modeButtonGroup.add(monthlyRadioButton);
		
		leftPanel.add(dailyRadioButton);
		leftPanel.add(monthlyRadioButton);
		
		add(leftPanel, BorderLayout.WEST);
		
	}
		
	
	public void displayEvent(MyEvent e){
		int start = e.getStart().getHour();
		int end = e.getEnd().getHour();
		String displayText = "<HTML>"+e.getName();
		displayText += "<BR>Start: " + e.getStart().toString();
		displayText += "<BR>End: " + e.getEnd().toString();
		if (!e.getLocation().isEmpty()) {
			displayText += "<BR>@" + e.getLocation();
		}
		if (e.isImportant()){
			displayText += "<BR>"+ "<font color=\"red\"> important </font>";
		}
		displayText += "</HTML>";
		eventLabels[start].setText(displayText);
		for (int i=start; i<end; i++){
			eventLabels[i].setBackground(Color.lightGray);
		}		
	}
	
	public void clearEvents(){
		for (int i=0; i<24; i++){
			eventLabels[i].setText(" ");
			eventLabels[i].setBackground(dayPanel.getBackground());
			revalidate();
			repaint();
		}
	}
	
	
	private void addEventHandlers(){
		
		addEventButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new AddEventWindow(c);	
			}
		});
		
		addFriendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new AddFriendWindow(c);
			}
		});
		
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.logout();
			}
		});
		
		previousButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clearEvents();
				//TODO need work here
				// change currDate value to previous
				c.previousDay();
				currDate = c.getCurrDate();
				currDateLabel.setText(currDate.displayDate());
			}
			
		});
		
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				clearEvents();
				//TODO need work here
				// change currDate value to next one
				// get previous day events vector
				c.nextDay();
				currDate = c.getCurrDate();
				currDateLabel.setText(currDate.displayDate());
			}
		});
		
		monthlyRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				monthlyMode = true;
				displayMonthly();
			}
		});
		
		dailyRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				monthlyMode = false;
				displayDaily();
			}
		});
		
		for (int i=0; i<daysInMonth; i++){
			monthDayLabels[i].addMouseListener(new LabelClicked(i+1));
		}
		
		checkRequestButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				User u = c.getCurrUser();
				Vector<User> friendRequests = u.getFriendRequests();
				if (friendRequests.size() == 0) {
					JOptionPane.showMessageDialog(MainWindow.this, "No requests", "Friend Request",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					for (int i = 0; i < friendRequests.size(); i++) {
						int selection = JOptionPane.showConfirmDialog(MainWindow.this, friendRequests.elementAt(i).getUsername() + 
								" wants to be your friend!", "Friend Request", 
								JOptionPane.YES_NO_OPTION);
						if (selection == JOptionPane.YES_OPTION) {
							c.acceptFriendRequest(friendRequests.elementAt(i));
						} else if (selection == JOptionPane.NO_OPTION) {
							c.denyFriendRequest(friendRequests.elementAt(i));
						}
					}
				}
				
			}
		});
		
		
	}
	
	private void createMonthPanel(){
		monthPanel  = new JPanel();
		monthPanel.setLayout(new GridLayout(7,7));
		weekdayLabels = new JLabel[7];
		
		weekdayLabels[0] = new JLabel("Sun");
		weekdayLabels[1] = new JLabel("Mon");
		weekdayLabels[2] = new JLabel("Tue");
		weekdayLabels[3] = new JLabel("Wed");
		weekdayLabels[4] = new JLabel("Thu");
		weekdayLabels[5] = new JLabel("Fri");
		weekdayLabels[6] = new JLabel("Sat");
		
		for (int i=0; i<7; i++){
			monthPanel.add(weekdayLabels[i]);
		}
		configureMonthLabels(currDate.getMonth(), currDate.getYear());
		
		
	}
	
	private void configureMonthLabels(int month, int year){
		MyDate firstDayOfMonth = new MyDate(0, 0, 1, month, year);
		int first = MyDate.getDayOfWeek(firstDayOfMonth);
		
		if (first==7){
			first = 0;
		}
		
		for (int i=0; i<first; i++){
			JLabel label = new JLabel("");
			if (monthPanel==null){
				System.out.println("here");
			}
			monthPanel.add(label);
		}
		
		if (MyDate.isEndOfMonth(month, 28)){
			daysInMonth = 28;
		}
		else if (MyDate.isEndOfMonth(month, 30)){
			daysInMonth = 30;
		}
		else {
			daysInMonth = 31;
		}
		monthDayLabels = new JLabel[daysInMonth];
		for (int i=0; i<daysInMonth; i++){
			monthDayLabels[i] = new JLabel(""+(i+1));
			monthPanel.add(monthDayLabels[i]);
		}

		
	}
	
	
	public void displayMonthly(){
		//if (!monthlyMode){
		//	return;
		//}
		calPanel.remove(jsp);
		calPanel.add(monthPanel);
		
		displayImportantEvents();
		validate();
		repaint();
	}

	public void displayDaily(){
		calPanel.remove(monthPanel);
		
		clearEvents();
		c.getDaysEvents(currDate, currDate);
		calPanel.add(jsp);
		
		displayImportantEvents();
		validate();
		repaint();
	}
	
	public void displaySentFriendRequest() {
		JOptionPane.showMessageDialog(MainWindow.this, "Sent Friend Request", "Friend Request",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public int confirmFriendRequest(User adder) {
		return JOptionPane.showConfirmDialog(MainWindow.this, "Accept Friend request from " + adder.getUsername() + " ? ",
				"Friend Request", JOptionPane.YES_NO_OPTION); 
	}
	
	public void deniedFriendRequest(User added) {
		JOptionPane.showMessageDialog(MainWindow.this, added.getUsername() + " denied your request!", 
	"Friend Request", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void acceptedFriendRequest(User added) {
		JOptionPane.showMessageDialog(MainWindow.this, added.getUsername() + " accepted your request!", 
	"Friend Request", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public void displayFriends(User u){
		friendsTextArea.setText("");
		Vector<User> friends = u.getFriends();
		for (int i=0; i<friends.size(); i++){
			friendsTextArea.append(friends.get(i).getUsername() + "\n");
		}
	}
	
	class LabelClicked extends MouseAdapter{
		int day;
		public LabelClicked(int day){
			this.day = day;
		}
		public void mouseClicked(MouseEvent me){
			currDate = new MyDate(0, 0, day, currDate.getMonth(), currDate.getYear());
			c.dayClicked(currDate);
			currDateLabel.setText(currDate.displayDate());
			dailyRadioButton.setSelected(true);
			monthlyMode = false;
			displayDaily();
			
		}
	}
	
	public void displayImportantEvents (){
		eventTextArea.setText("");
		Vector<MyEvent> events;
		if (dailyRadioButton.isSelected()){
			events = c.getEvents(currDate, currDate);
			for (int i=0; i<events.size();i++){
				eventTextArea.append(events.get(i).toString());
			}
		}
		else{

			MyDate startDate = new MyDate(0, 0, 1, currDate.getMonth(), currDate.getYear());
			MyDate endDate = MyDate.getEndOfMonth(startDate);
			events = c.getEvents(startDate, endDate);
			for (int j=0; j<events.size();j++){
					eventTextArea.append(events.get(j).toString());

				}
			}
	}
	
/*	
	public static void main (String[] args){
		
		MyClient c = new MyClient("localhost", 1111);
		MainWindow mainWindow = new MainWindow(c);
		MyEvent e = new MyEvent(new MyDate(0, 3, 1, 1, 2015), new MyDate(0, 6, 1, 1, 2015), "meeting", "vkc", true);
		mainWindow.displayEvent(e);
		//mainWindow.displayMonth();
	}
*/


}