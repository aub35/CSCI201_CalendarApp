package trie;

import java.util.Vector;

public class MyTrie {

	private MyTrieNode root;
	
	public MyTrie() {
		root = new MyTrieNode((char)0);
		root.setLeaf(true);
		
	}
	
	public void add(String username) {
		MyTrieNode next = root;
		int charIndex = 0;
		char letter = username.charAt(charIndex);
		//add case for first one
		
		while (!next.isLeaf()) {
			Vector<MyTrieNode> children = next.getChildren();
			boolean foundChild = false;
			for (int i = 0; i < children.size(); i++) {
				if (children.elementAt(i).getValue() == letter) {
					System.out.println("Found value " + letter + " in trie.");
					charIndex++;
					next = next.getChildAt(i);
					letter = username.charAt(charIndex);
					foundChild = true;
				}
			}
			if (!foundChild) { break; }
		}
		while (charIndex < username.length()) {
			next.setLeaf(false);
			MyTrieNode mtn = new MyTrieNode(username.charAt(charIndex));
			next.addChild(mtn);
			charIndex++;
			next = mtn;
		}
		next.setEnd(true);
	}
	
	public boolean find(String username) {
		MyTrieNode next = root;
		int charIndex = 0;
		//add case for first one
		
		while (!next.isLeaf()) {
			Vector<MyTrieNode> children = next.getChildren();
			char letter = username.charAt(charIndex);
			boolean foundChild = false;
			System.out.print("Children for " + next.getValue() + " are: ");
			for (int i = 0; i < children.size(); i++) {
				System.out.print(children.elementAt(i).getValue() + " (" + children.elementAt(i).isLeaf() + ") ");
				if (children.elementAt(i).getValue() == letter) {
					next = next.getChildAt(i);
					charIndex++;
					if (charIndex == username.length()) {
						if (next.isEnd()) {
							System.out.println();
							return true;
						} else {
							System.out.println();
							return false;
						}
					}
					foundChild = true;
				}
			}
			System.out.println();
			if (!foundChild) {
				System.out.println("Couldn't find matching child");
				return false; 
			}
		}
		if (charIndex == (username.length() - 1)) { 
			return true; 
		}
		return false;
	}
	
	public static void main (String[] args) {
		MyTrie mt = new MyTrie();
		mt.add("Amy");
		mt.add("Amys");
		mt.add("Bob");
		mt.add("Amysie");
		mt.add("Bobert");
		System.out.println(mt.find("Amy"));
		System.out.println(mt.find("Bobe"));
		System.out.println(mt.find("Bob"));
	}
	
}
