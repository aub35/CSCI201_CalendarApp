package client;

import java.util.Comparator;

public class Main {

	int num;
	
	public Main(int num) {
		this.num = num;
	}
	
	public static void main (String[] args) {
		new ClientPortGUI();
	}
}

class numComp implements Comparator<Main> {

	public int compare(Main m1, Main m2) {
		return m1.num - m2.num;
	}
	
}
