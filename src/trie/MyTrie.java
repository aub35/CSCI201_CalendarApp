package trie;

import java.util.Vector;

public class MyTrie {

	private MyTrieNode root;
	private Vector<String> result;
	
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
					charIndex++;
					next = next.getChildAt(i);
					if (charIndex == username.length()) { 
						next.setEnd(true);
						return;
					}
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
			for (int i = 0; i < children.size(); i++) {
				if (children.elementAt(i).getValue() == letter) {
					next = next.getChildAt(i);
					charIndex++;
					if (charIndex == username.length()) {
						if (next.isEnd()) {
							return true;
						} else {
							return false;
						}
					}
					foundChild = true;
				}
			}
			if (!foundChild) {
				return false; 
			}
		}
		return false;
	}
	
	public Vector<String> findUsernames(String username) {
		MyTrieNode next = root;
		int charIndex = 0;
		result = new Vector<String>();
		String newUsername = "";
		//add case for first one
		
		while (!next.isLeaf()) {
			Vector<MyTrieNode> children = next.getChildren();
			char letter = username.charAt(charIndex);
			newUsername += letter;
			boolean foundChild = false;
			boolean reachedEnd = false;
			for (int i = 0; i < children.size(); i++) {
				if (children.elementAt(i).getValue() == letter) {
					foundChild = true;
					next = next.getChildAt(i);
					charIndex++;
					if (charIndex == username.length()) {
						if (next.isEnd()) {
							result.add(newUsername);							
						}
						reachedEnd = true;
					}
				}
			}
			if (!foundChild) {
				return result; 
			}
			if (reachedEnd) { break; }
		}
		while (!username.equals(newUsername)) {
			char letter = username.charAt(charIndex);
			newUsername += letter;
		}
		
		searchTrie(next, newUsername);
		return result;
	}
	
	private void searchTrie(MyTrieNode next, String newUsername) {
		if (next.isLeaf()) {
			if (next.isEnd()) {
				return;
			}
		} else {
			Vector<MyTrieNode> children = next.getChildren();
			for (int i = 0; i < children.size(); i++) {
				String name = newUsername + next.getChildAt(i).getValue();
				if (next.getChildAt(i).isEnd()) {
					result.add(name);
				}
				searchTrie(next.getChildAt(i), name);
			}
		}
		
		
	}

}
