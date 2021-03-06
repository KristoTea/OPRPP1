package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;


public class ForLoopNode extends Node{
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression; //can be null
	
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression; 
	}
	
	public ElementVariable getVariable() {
		return variable;
	}
	
	public Element getStartExpression() {
		return startExpression;
	}
	
	public Element getEndExpression() {
		return endExpression;
	}
	
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		//nadjacavanje --> {$FOR ... $} ... {$END$}
		return "ForLoopNode [variable=" + variable + ", startExpression=" + startExpression + ", endExpression="
				+ endExpression + ", stepExpression=" + stepExpression + ", toString()=" + super.toString() + "]";
	}
	

}
