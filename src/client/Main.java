package client;

import java.util.Arrays;
import java.util.Comparator;

public class Main {

	int num;
	
	public Main(int num) {
		this.num = num;
	}
	
	public static void main (String[] args) {
		Client c = new Client("localhost", 8080);
		c.start();
/*		Main[] m = new Main[3];
		m[0] = new Main(30);
		m[1] = new Main(50);
		m[2] = new Main(10);
		Arrays.sort(m, new numComp());
		
		for (int i = 0; i < 3; i++) {
			System.out.println(m[i].num);
		}*/
	}
}

class numComp implements Comparator<Main> {

	public int compare(Main m1, Main m2) {
		return m1.num - m2.num;
	}
	
}
