package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	JLabel usernameLabel, passwordLabel, errorLabel, nameLabel;
	JTextField usernameTextField, passwordTextField, nameTextField;
	JPanel usernamePanel, passwordPanel, buttonPanel, errorPanel, namePanel;
	JButton loginButton, createUserButton, guestLoginButton;
	
	private MyClient c;
	
	public LoginWindow(MyClient c){
		createGUI();
		addActionAdapters();
		setVisible(true);
		this.c = c;
	}
	
	private void createGUI(){
		setSize(500, 300);
		setTitle("Login Window");

		setLayout(new GridLayout(5,1));

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
		
		namePanel = new JPanel();
		nameLabel = new JLabel("Name: ");
		namePanel.add(nameLabel);
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		namePanel.add(nameTextField);
		add(namePanel);
		
		buttonPanel = new JPanel();
		loginButton = new JButton("Log In");
		buttonPanel.add(loginButton);
		createUserButton = new JButton("Sign Up");
		buttonPanel.add(createUserButton);
		guestLoginButton = new JButton("Login as a Guest");
		buttonPanel.add(guestLoginButton);
		add(buttonPanel);
		
		errorPanel = new JPanel();
		errorLabel = new JLabel("");
		errorPanel.add(errorLabel);
		add(errorPanel);
	}
	
	
	public void addActionAdapters(){
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				c.quit();
			}
		});
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.setIsGuest(false);
				if (c.checkUser(usernameTextField.getText(), passwordTextField.getText())){
					//Successful login
				}
				else {
					errorLabel.setText("<html><font color=\"red\">Unsuccessful Login</font></html>");
				}
			}
		});
		
		createUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.addUser(usernameTextField.getText(), passwordTextField.getText(), 
						nameTextField.getText());
			}
		});
		
		guestLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.setIsGuest(true);
				c.addUser(usernameTextField.getText(), passwordTextField.getText(), "Guest");
			}
		});
	}
	
}
