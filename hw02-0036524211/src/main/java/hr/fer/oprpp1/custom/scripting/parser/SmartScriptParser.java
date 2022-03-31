package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;

public class SmartScriptParser {
	
	private String documentBody;
	private SmartScriptLexer lexer;
	private ObjectStack stack;
	private DocumentNode document;

	public SmartScriptParser(String documentBody) {
		this.documentBody = documentBody;
		lexer = new SmartScriptLexer(this.documentBody);
		document = new DocumentNode();
		stack = new ObjectStack();
		stack.push(document);
		this.Parse();
	}
	
	private void Parse() {
		//metoda kojoj se delegira parsiranje
	}

}
