package client;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AddFriendWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	MyClient c;
	JPanel usernamePanel, buttonPanel;
	JLabel usernameLabel, errorLabel;
	JTextField usernameTextField;
	JList<String> usernameList;
	DefaultListModel<String> dlm;
	String[] usernames;
	JButton addButton;
	
	public AddFriendWindow(MyClient c){
		this.c = c;
		setModal(true);
		createGUI();
		addEventHandlers();
		setVisible(true);
		
	}
	
	public void createGUI(){
		setTitle("Add Friend Window");
		setSize(500, 250);
		
		setLayout(new GridLayout(4,1));
		
		usernamePanel = new JPanel();
		usernameLabel = new JLabel("Username: ");
		usernameTextField = new JTextField();
		usernameTextField.setColumns(15);;
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameTextField);
		add(usernamePanel);

		
		buttonPanel = new JPanel();
		addButton = new JButton("Add Friend");
		buttonPanel.add(addButton);
		add(buttonPanel);
		
		dlm = new DefaultListModel<String>();
		usernameList = new JList<String>(dlm);
		usernameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane jsp = new JScrollPane(usernameList);
		jsp.setPreferredSize(new Dimension(500, 250));
		add(jsp);
		
		errorLabel = new JLabel("error message here");
		add(errorLabel);
	}
	
	private void addEventHandlers(){
		usernameTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {  }
			public void insertUpdate(DocumentEvent arg0) {
				Vector<String> result = c.searchForFriend(usernameTextField.getText());
				updateUsernameList(result);				
			}
			public void removeUpdate(DocumentEvent arg0) {
				if (!usernameTextField.getText().isEmpty()) {
					Vector<String> result = c.searchForFriend(usernameTextField.getText());
					updateUsernameList(result);
				} else {
					dlm.clear();
				}
			}
		});
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (usernameList.isSelectionEmpty()) {
					errorLabel.setText("<html><font color=\"red\">No user selected! Please select one from list above</font></html>");
				} else {
					String username = usernameList.getSelectedValue();
					c.addFriend(username);
					AddFriendWindow.this.dispose();
				}
			}
		});
		
		
	}
	
	public void updateUsernameList(Vector<String> usernames) {
		dlm.clear();
		String myUsername = c.getUsername();
		for (int i = 0; i < usernames.size(); i++) {
			String str = usernames.elementAt(i);
			if (!str.equals(myUsername)) {
				dlm.addElement(str);				
			}
		}
	}
	

	
}
