package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.oprpp1.custom.scripting.elems.Element;

public class EchoNode extends Node{
	
	private Element[] elements;

	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	public Element[] getElements() {
		return elements;
	}

	@Override
	public String toString() {
		//nadjacavanje? {?=...?}
		return "EchoNode [elements=" + Arrays.toString(elements) + ", toString()=" + super.toString() + "]";
	}


}
