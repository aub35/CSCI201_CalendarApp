package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AddFriendWindow extends JDialog{
	MyClient c;
	JPanel usernamePanel, buttonPanel;
	JLabel usernameLabel, errorLabel;
	JTextField usernameTextField;
	JButton searchButton;
	
	public AddFriendWindow(MyClient c){
		this.c = c;
		setModal(true);
		createGUI();
		addEventHandlers();
		setVisible(true);
		
	}
	
	public void createGUI(){
		setTitle("Add Friend Window");
		setSize(400, 150);
		
		setLayout(new GridLayout(3,1));
		
		usernamePanel = new JPanel();
		usernameLabel = new JLabel("Username: ");
		usernameTextField = new JTextField();
		usernameTextField.setColumns(15);;
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameTextField);
		add(usernamePanel);

		
		buttonPanel = new JPanel();
		searchButton = new JButton("Search Friend");
		buttonPanel.add(searchButton);
		add(buttonPanel);
		
		errorLabel = new JLabel("error message here");
		add(errorLabel);
	}
	
	private void addEventHandlers(){
		usernameTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {  }
			public void insertUpdate(DocumentEvent arg0) {
				System.out.println("Something chagned insret");
			}
			public void removeUpdate(DocumentEvent arg0) {
				System.out.println("Something changed remove");
			}
			
		});
	}
}
