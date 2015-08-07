package client;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainWindow extends JFrame {
	JButton addEventButton;
	JPanel centerPanel, rightPanel, dayPanel;
	JDialog EventWindow;
	JScrollPane jsp;
	GridBagConstraints gbc;
	JLabel [] hourLabels;
	JLabel [] eventLabels;
	
	public MainWindow() {
		createGUI();
		addActionAdapters();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
	}
	
	public void createGUI(){
		
		//centerPanel
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		//Panel for Calendar
		//Daily Mode
		dayPanel = new JPanel();
		dayPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		hourLabels = new JLabel[24];
		eventLabels = new JLabel[24];
		
		for (int i=0; i<24; i++){
			JLabel label = new JLabel(i+":00");
			gbc.gridx = 0;
			gbc.gridy = i;
			hourLabels[i] = label;
			dayPanel.add(label, gbc);
		}
		
		for (int i=0; i<24; i++){
			JLabel label = new JLabel("...");
			gbc.gridx = 2;
			gbc.gridy = i;
			gbc.gridwidth = 5;
			eventLabels[i] = label;
			dayPanel.add(label, gbc);
		}
		
		jsp = new JScrollPane(dayPanel);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		centerPanel.add(jsp);
		
		JTextArea eventTextArea = new JTextArea();
		eventTextArea.setRows(10);
		centerPanel.add(eventTextArea);
		
		add(centerPanel);

		
		
		// rightPanel
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
