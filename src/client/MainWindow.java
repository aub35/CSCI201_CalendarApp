package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame {
	JButton addEventButton;
	JPanel rightPanel;
	JDialog EventWindow;
	
	public MainWindow() {
		createGUI();
		addActionAdapters();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
	}
	
	public void createGUI(){
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		addEventButton =new JButton("Add Event");
		
		rightPanel.add(addEventButton);

		add(rightPanel, BorderLayout.EAST);
	}
	
	public void addActionAdapters(){
		addEventButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				EventWindow = new AddEventWindow();				
			}
			
		});
	}
	
	
	
	public static void main (String[] args){
		new MainWindow();
	}
}
