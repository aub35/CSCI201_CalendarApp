package client;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientPortGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel descriptionLabel, hostLabel, portLabel, errorLabel;
	private JTextField hostTextField, portTextField;
	private JButton connectButton;
	JPanel hostPanel, portPanel;
	
	public ClientPortGUI(){
		super ("Client Port GUI");
		createGUI();
		addEventHandlers();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	private void createGUI(){
		setSize(500, 300);
		setLayout(new GridLayout(5, 1));
		
		descriptionLabel = new JLabel("Enter the host IP and the port number of the server");
		add(descriptionLabel);
		
		
		hostPanel = new JPanel();
		hostPanel.setLayout(new FlowLayout ());
		hostLabel = new JLabel("Host IP");
		hostPanel.add(hostLabel);
		hostTextField = new JTextField();
		hostTextField.setText("localhost");
		hostPanel.add(hostTextField);
		add(hostPanel);
		
		portPanel = new JPanel();
		portPanel.setLayout(new FlowLayout ());
		portLabel = new JLabel("Port Number");
		portPanel.add(portLabel);
		portTextField = new JTextField();
		portTextField.setText("8080");
		portPanel.add(portTextField);
		add(portPanel);
		
		connectButton = new JButton("Connect");
		add(connectButton);
		
		errorLabel = new JLabel("error message here");
		add(errorLabel);
	}
	
	private void addEventHandlers() {
		connectButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int port = Integer.parseInt(portTextField.getText());
				MyClient c = new MyClient("localhost", port);
				c.start();
				ClientPortGUI.this.dispose();
			}
		});
	}
}

