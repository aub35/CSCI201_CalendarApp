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
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import calendar.MyDate;
import calendar.MyEvent;
import calendar.User;

public class MainWindow extends JFrame {
	private MyClient c;
	JButton addEventButton, logoutButton, previousButton, nextButton, addFriendButton;
	JPanel centerPanel, rightPanel, calPanel, dayPanel, switchDatePanel, monthPanel, leftPanel, infoPanel;
	JDialog addEventWindow, addFriendWindow;
	JScrollPane jsp, friendScrollPane;
	GridBagConstraints gbc;
	JLabel [] hourLabels, eventLabels, weekdayLabels;
	JLabel [] monthDayLabels;
	JLabel currDateLabel, currUserLabel, modeLabel;
	JTextArea friendsTextArea;
	MyDate currDate;
	Vector<MyEvent> events;
	boolean monthlyMode;
	JRadioButton dailyRadioButton, monthlyRadioButton;
	ButtonGroup modeButtonGroup;
	int daysInMonth;

	
	
	public MainWindow(MyClient c) {
		this.c = c;
		//TODO
		//currDate = c.getCurrDate();
		currDate = new MyDate(0, 0, 9, 8, 2015);
		// events
		
		monthlyMode = false;
		createGUI();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		addEventHandlers();
	}
	
	private void createGUI(){
		
		//centerPanel
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		//TODO add current date Label and username
		infoPanel = new JPanel();
		currDateLabel = new JLabel(currDate.displayDate());
		infoPanel.add(currDateLabel);
		currUserLabel = new JLabel();
		infoPanel.add(currUserLabel);
		add(infoPanel);
		
		
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
		
				
		JTextArea eventTextArea = new JTextArea();
		eventTextArea.setRows(10);
		eventTextArea.setEditable(false);
		//eventTextArea.setCursor(null);
		//eventTextArea.setOpaque(false);
		//eventTextArea.setFocusable(false);
		centerPanel.add(eventTextArea);
		
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
				addEventWindow = new AddEventWindow(c);				
			}
		});
		
		addFriendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addFriendWindow = new AddFriendWindow(c);
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
			}
			
		});
		
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				clearEvents();
				//TODO need work here
				// change currDate value to next one
				// get previous day events vector
				c.nextDay();
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
		int first = firstDayOfMonth.getDayOfWeek(firstDayOfMonth);
		
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
		
		if (firstDayOfMonth.isEndOfMonth(month, 28)){
			daysInMonth = 28;
		}
		else if (firstDayOfMonth.isEndOfMonth(month, 30)){
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
		
		validate();
		repaint();
	}

	public void displayDaily(){
		calPanel.remove(monthPanel);
		calPanel.add(jsp);
		validate();
		repaint();
	}
	
	public void displayFriend(User u){
		friendsTextArea.append(u.getName() + "\n" + u.getUsername() + "\n");
	}
	
	class LabelClicked extends MouseAdapter{
		int day;
		public LabelClicked(int day){
			this.day = day;
		}
		public void mouseClicked(MouseEvent me){
			currDate = new MyDate(0, 0, day, currDate.getMonth(), currDate.getYear());
			currDateLabel.setText(currDate.displayDate());
			c.getDaysEvents(currDate);
			dailyRadioButton.setSelected(true);
			monthlyMode = false;
			displayDaily();
			
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