package client;

import java.awt.BorderLayout;
import java.awt.Color;
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

import calendar.Date;
import calendar.Event;

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
	
	private void createGUI(){
		
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
			gbc.weightx =  1;
			gbc.anchor = GridBagConstraints.CENTER;
			hourLabels[i] = label;
			dayPanel.add(label, gbc);
		}
		
		for (int i=0; i<24; i++){
			JLabel label = new JLabel(" ");
			label.setOpaque(true);
			gbc.gridx = 1;
			gbc.weightx =  3;
			gbc.gridy = i;
			gbc.gridwidth = 3;
			gbc.fill = GridBagConstraints.HORIZONTAL;
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
	
	private void addActionAdapters(){
		addEventButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				EventWindow = new AddEventWindow();				
			}
			
		});
	}
	
	
	public void displayEvent(int start, int end){
		//int start  = e.getStart().getHour();
		//int end = e.getEnd().getHour();
		for (int i=start; i<end; i++){
			eventLabels[i].setBackground(Color.cyan);
		}
		
	}
	
	public void displayEvent(Event e){
		int start = e.getStart().getHour();
		int end = e.getEnd().getHour();
		String displayText = e.getName() + "  @" + e.getLocation();
		eventLabels[start].setText(displayText);
		for (int i=start; i<end; i++){
			eventLabels[i].setBackground(Color.cyan);
		}
		
	}
	
	public static void main (String[] args){
		MainWindow mainWindow = new MainWindow();
		mainWindow.displayEvent(3, 11);
		Event e = new Event(new Date(0, 0 , 1, 1, 1, 2015, false), new Date(0, 2, 1, 1, 1, 2015, false), "meeting", "vkc", false);
		mainWindow.displayEvent(e);
	}
}
