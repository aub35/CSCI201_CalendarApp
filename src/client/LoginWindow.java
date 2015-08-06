package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginWindow extends JDialog {
	JLabel usernameLabel, passwordLabel, errorLabel;
	JTextField usernameTextField, passwordTextField;
	JPanel usernamePanel, passwordPanel, buttonPanel, errorPanel;
	JButton loginButton, createUserButton, guestLoginButton;
	
	
	public LoginWindow(){
		createGUI();
		addActionAdapters();
		setVisible(true);
	}
	
	private void createGUI(){
		setSize(500, 300);
		setTitle("Login Window");

		setLayout(new GridLayout(4,1));

		usernamePanel = new JPanel();
		usernameLabel = new JLabel("Username: ");
		usernamePanel.add(usernameLabel);
		usernameTextField = new JTextField();
		usernameTextField.setColumns(10);
		usernamePanel.add(usernameTextField);
		add(usernamePanel);
		
		passwordPanel = new JPanel();
		passwordLabel = new JLabel("Password: ");
		passwordPanel.add(passwordLabel);
		passwordTextField = new JTextField();
		passwordTextField.setColumns(10);
		passwordPanel.add(passwordTextField);
		add(passwordPanel);
		
		buttonPanel = new JPanel();
		loginButton = new JButton("Log In");
		buttonPanel.add(loginButton);
		createUserButton = new JButton("Sign Up");
		buttonPanel.add(createUserButton);
		guestLoginButton = new JButton("Login as a Guest");
		buttonPanel.add(guestLoginButton);
		add(buttonPanel);
		
		errorPanel = new JPanel();
		errorLabel = new JLabel("Error Message Here");
		errorPanel.add(errorLabel);
		add(errorPanel);
	}
	
	public void addActionAdapters(){

	}
	
	public static void main (String[] args){
		new LoginWindow();
	}
}
