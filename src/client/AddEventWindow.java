package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import calendar.Date;
import calendar.Event;

public class AddEventWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	JLabel nameLabel, dateLabel, startTimeLabel, endTimeLabel, locationLabel, errorLabel, locationLanel;
	JButton addEventButton;
	JTextField nameTextField, yearTextField, monthTextField, dayTextField, startTimeTextField, endTimeTextField, locationTextField;
	JComboBox<Integer> startHourComboBox, startMinuteComboBox, endHourComboBox, endMinuteComboBox;
	JPanel namePanel, datePanel, startTimePanel, endTimePanel, locationPanel, amPmPanel, buttonPanel;
	JRadioButton amButton, pmButton;
	JCheckBox importantCheckBox;
	ButtonGroup bg;
	private Client client;
	
	public AddEventWindow(Client c){
		this.client = c;
		setModal(true);
		createGUI();
		addActionAdapters();
		setVisible(true);
	}
	public void createGUI(){
		setTitle("Add Event Window");
		setSize(400, 300);
		
		setLayout(new GridLayout(7, 1));
		initComboBoxes();
		
		namePanel = new JPanel();
		nameLabel = new JLabel("Name");
		namePanel.add(nameLabel);
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		namePanel.add(nameTextField);
		add(namePanel);
		
		datePanel = new JPanel();
		dateLabel = new JLabel("Date(YYYY-MM-DD): ");
		datePanel.add(dateLabel);
		yearTextField = new JTextField();
		yearTextField.setColumns(4);
		datePanel.add(yearTextField);
		datePanel.add(new JLabel(" - "));
		monthTextField = new JTextField();
		monthTextField.setColumns(2);
		datePanel.add(monthTextField);
		datePanel.add(new JLabel(" - "));
		dayTextField = new JTextField();
		dayTextField.setColumns(2);
		datePanel.add(dayTextField);
		add(datePanel);
		
		startTimePanel = new JPanel();
		startTimeLabel = new JLabel("Event Start Time ");
		startTimePanel.add(startTimeLabel);
		startTimePanel.add(startHourComboBox);
		startTimePanel.add(new JLabel(" : "));
		startTimePanel.add(startMinuteComboBox);
		add(startTimePanel);
		
		endTimePanel = new JPanel();
		endTimeLabel = new JLabel("Event End Time ");
		endTimePanel.add(endTimeLabel);
		endTimePanel.add(endHourComboBox);
		endTimePanel.add(new JLabel(" : "));
		endTimePanel.add(endMinuteComboBox);
		add(endTimePanel);
		
		//amPmPanel = new JPanel();
		//amButton = new JRadioButton("AM");
		//pmButton = new JRadioButton("PM");
		//bg = new ButtonGroup();
		//amPmPanel.add(amButton);
		//amPmPanel.add(pmButton);
		//bg.add(amButton);
		//bg.add(pmButton);
		//add(amPmPanel);
		
		locationPanel = new JPanel();
		locationLabel = new JLabel("Location: ");
		locationTextField = new JTextField(15);
		locationPanel.add(locationLabel);
		locationPanel.add(locationTextField);
		add(locationPanel);
		
		
		buttonPanel = new JPanel();
		addEventButton = new JButton("Add");
		buttonPanel.add(addEventButton);
		
		importantCheckBox = new JCheckBox("important");
		buttonPanel.add(importantCheckBox);
		buttonPanel.add(addEventButton);
		
		add(buttonPanel);
		
		errorLabel = new JLabel("error message here");
		add(errorLabel);
	}
	
	private void initComboBoxes() {
		Integer options[] = new Integer[24];
		for (int i = 0; i <= 23; i++) {
			options[i] = i;
		}
		startHourComboBox = new JComboBox<Integer>(options);
		endHourComboBox = new JComboBox<Integer>(options);
		options = new Integer[4];
		for (int j = 0; j < 4; j++) {
			options[j] = j*15;
		}
		startMinuteComboBox = new JComboBox<Integer>(options);
		endMinuteComboBox = new JComboBox<Integer>(options);
	}
	
	public void addActionAdapters(){
		addEventButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int year = Integer.parseInt(yearTextField.getText());
				int month = Integer.parseInt(monthTextField.getText());
				int day = Integer.parseInt(dayTextField.getText());
				int startMinute = (int)startMinuteComboBox.getSelectedItem();
				int startHour = (int)startHourComboBox.getSelectedItem();
				int endMinute = (int)endMinuteComboBox.getSelectedItem();
				int endHour = (int)endHourComboBox.getSelectedItem();
				//boolean isAm;
				//if (amButton.isSelected()) {
					//isAm = true;
				//} else {
				//	isAm = false;
				//}
				Date start = new Date(startMinute, startHour, 0, day, month, year, false);
				Date end = new Date(endMinute, endHour, 0, day, month, year, false);
				String location = locationTextField.getText();
				boolean isImportant = importantCheckBox.isSelected();
				System.out.println("Date: " + start);
				client.addEvent(new Event(start, end, nameTextField.getText(), location, isImportant));
				AddEventWindow.this.setVisible(false);
			}
			
		});
	}

}
