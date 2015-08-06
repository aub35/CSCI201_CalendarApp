package client;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddEventWindow extends JDialog {
	JLabel nameLabel, dateLabel, startTimeLabel, endTimeLabel, locationLabel, errorLabel;
	JButton addEventButton;
	JTextField nameTextField, dateTextField, startTimeTextField, endTimeTextField, locationTextField;
	JPanel namePanel, datePanel, startTimePanel, endTimePanel, locationPanel;
	
	public AddEventWindow(){
		setModal(true);
		createGUI();
		addActionAdapters();
		setVisible(true);
	}
	public void createGUI(){
		setTitle("Add Event Window");
		setSize(400, 300);
		
		setLayout(new GridLayout(6, 1));
		
		
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
		dateTextField = new JTextField();
		dateTextField.setColumns(10);
		datePanel.add(dateTextField);
		add(datePanel);
		
		startTimePanel = new JPanel();
		startTimeLabel = new JLabel("Event Start Time(HH:MM): ");
		startTimeTextField = new JTextField();
		startTimeTextField.setColumns(10);
		startTimePanel.add(startTimeLabel);
		startTimePanel.add(startTimeTextField);
		add(startTimePanel);
		
		endTimePanel = new JPanel();
		endTimeLabel = new JLabel("Event End Time(HH:MM): ");
		endTimeTextField = new JTextField();
		endTimeTextField.setColumns(10);
		endTimePanel.add(endTimeLabel);
		endTimePanel.add(endTimeTextField);
		add(endTimePanel);
		
		addEventButton = new JButton("Add");
		add(addEventButton);
		
		errorLabel = new JLabel();
		add(errorLabel);
	}
	
	public void addActionAdapters(){
		
	}

}
