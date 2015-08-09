package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import calendar.Date;
import calendar.Event;

public class MainWindow extends JFrame {
	private Client c;
	JButton addEventButton, logoutButton, previousButton, nextButton;
	JPanel centerPanel, rightPanel, dayPanel, switchDatePanel;
	JDialog EventWindow;
	JScrollPane jsp;
	GridBagConstraints gbc;
	JLabel [] hourLabels;
	JLabel [] eventLabels;
	Date currDate;
	Vector<Event> events;
	
	public MainWindow(Client c) {
		this.c = c;
		//currDate
		// events...
		
		createGUI();
		addActionAdapters();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		addEventHandlers();
	}
	
	private void createGUI(){
		
		//centerPanel
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		//Panel for Calendar
		//Daily Mode
		dayPanel = new JPanel();
		dayPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		hourLabels = new JLabel[24];
		eventLabels = new JLabel[24];
		
		for (int i=0; i<24; i++){
			JLabel label = new JLabel(i+":00");
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.weightx =  1;
			gbc.anchor = GridBagConstraints.CENTER;
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
			gbc.fill = GridBagConstraints.HORIZONTAL;
			eventLabels[i] = label;
			dayPanel.add(label, gbc);
		}
		
		jsp = new JScrollPane(dayPanel);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		centerPanel.add(jsp);
		
				
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
		

		
		
		// rightPanel
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		addEventButton =new JButton("Add Event");
		rightPanel.add(addEventButton);
		logoutButton = new JButton("Log out");
		rightPanel.add(logoutButton);
		add(rightPanel, BorderLayout.EAST);
	}
	
	private void addActionAdapters(){
		addEventButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				EventWindow = new AddEventWindow(c);				
			}
		});
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.logout();
			}
		});
	}
	
	
	
	public void displayEvent(Event e){
		int start = e.getStart().getHour();
		int end = e.getEnd().getHour();
		String displayText = "<HTML>"+e.getName();
		displayText += "<BR>Start: " + e.getStart().toString();
		displayText += "<BR>End: " + e.getEnd().toString();
		displayText += "<BR>@" + e.getLocation();
		if (e.isImportant()){
			displayText += "<BR>"+ "important"+ "</HTML>";
		}
		displayText += "</HTML>";
		eventLabels[start].setText(displayText);
		for (int i=start; i<end; i++){
			eventLabels[i].setBackground(Color.lightGray);
		}		
	}
	
	public void clearBoard(){
		for (int i=0; i<24; i++){
			eventLabels[i].setText(" ");
			eventLabels[i].setBackground(dayPanel.getBackground());
			this.revalidate();
			this.repaint();
		}
	}
	
	
	private void addEventHandlers(){
		previousButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clearBoard();
				//need work here
				// change currDate value to previous
				// get previous day events vector
				//for (int i=0; i<events.size(); i++){
					//displayEvent(events.get(i));
				//}			
			}
			
		});
		
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				clearBoard();
				// need work here
				// change currDate value to next one
				// get previous day events vector
				//for (int i=0; i<events.size(); i++){
					//displayEvent(events.get(i));
				//}
			}
		});
	}
	
	
	
	public static void main (String[] args){
		Client c = new Client("localhost", 3097);
		MainWindow mainWindow = new MainWindow(c);
		Event e = new Event(new Date(0, 0 , 1, 1, 1, 2015, false), new Date(0, 2, 1, 1, 1, 2015, false), "meeting", "vkc", true);
		mainWindow.displayEvent(e);

	}

}