package hr.fer.oprpp1.custom.scripting.lexer;

import java.util.Objects;

import hr.fer.oprpp1.hw02.prob1.Token;

public class SmartScriptLexer {
	
	private char[] data; 
	private Token token; 
	private int currentIndex;
	private SmartScriptLexerState state;
	
	public SmartScriptLexer(String text) {
		Objects.requireNonNull(text, "You put null reference for input string");

		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = SmartScriptLexerState.BASIC;
	}
	
	//implementacija slična iz prethodnog zadatka
	public Token nextToken() {
		return null;
	}
	
	public Token getToken() {
		return token;
	}

}
