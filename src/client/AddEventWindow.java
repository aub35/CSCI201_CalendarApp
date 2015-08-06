package client;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddEventWindow extends JDialog {
	JLabel nameLabel, dateLabel;
	JButton addEventButton;
	JTextField nameTextField, dateTextField;
	
	public AddEventWindow(){
		setModal(true);
		createGUI();
		setVisible(true);
	}
	public void createGUI(){
		setTitle("Add Event Window");
		setSize(400, 300);
		
		setLayout(new GridLayout(5, 1));
		
		nameLabel = new JLabel("Name");
		add(nameLabel);
		
		nameTextField = new JTextField();
		add(nameTextField);
		
		dateLabel = new JLabel("Date(YYYY-MM-DD): ");
		add(dateLabel);
		
		dateTextField = new JTextField();
		add(dateTextField);
		
		
		addEventButton = new JButton("Add");
		add(addEventButton);
		
		
		
	}
}
