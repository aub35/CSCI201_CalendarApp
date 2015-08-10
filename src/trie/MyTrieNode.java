package trie;

import java.util.Vector;

public class MyTrieNode {

	private char value;
	private boolean isLeaf, isEnd;
	private Vector<MyTrieNode> children;
	
	public MyTrieNode(char value) {
		this.value = value;
		isLeaf = true;
		children = new Vector<MyTrieNode>();
		isEnd = false;
	}
	
	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public char getValue() {
		return value;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	public void addChild(MyTrieNode mtn) {
		children.add(mtn);
	}
	
	public Vector<MyTrieNode> getChildren() {
		return children;
	}
	
	public MyTrieNode getChildAt(int index) {
		return children.elementAt(index);
	}
	
}

