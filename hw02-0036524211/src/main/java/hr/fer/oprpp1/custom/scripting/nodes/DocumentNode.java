package hr.fer.oprpp1.custom.scripting.nodes;

public class DocumentNode extends Node{

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DocumentNode) {
			if(this.toString().equals(obj.toString()))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.numberOfChildren(); i++)
			sb.append(this.getChild(i).toString());
		return sb.toString();
	}

	
	
}
