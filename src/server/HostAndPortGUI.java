package server;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HostAndPortGUI extends JFrame {
	
	private JLabel descriptionLabel, hostLabel, portLabel, errorLabel;
	private JTextField hostTextField, portTextField;
	private JButton connectButton;
	JPanel hostPanel, portPanel;
	
	public HostAndPortGUI(){
		super ("Host and Port GUI");
		createGUI();
	}
	
	private void createGUI(){
		setSize(300, 300);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
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
		portPanel.add(portTextField);
		add(portPanel);
		
		connectButton = new JButton("Connect");
		add(connectButton);
		
	}
}
