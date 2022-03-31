package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class Node {
	
	ArrayIndexedCollection array;
	
	void addChildNode(Node child) {
		if(array.isEmpty())
			array = new ArrayIndexedCollection();
		array.add(child);
	}
	
	int numberOfChildren() {
		return array.size();
	}
	
	Node getChild(int index) {
		return (Node) array.get(index);
	}

}
