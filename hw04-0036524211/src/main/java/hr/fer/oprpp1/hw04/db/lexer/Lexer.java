package hr.fer.oprpp1.hw04.db.lexer;

import java.util.Objects;

/**
 * Razred koji modelira ponašanje leksičkog analizatora.
 * @author teakr
 *
 */
public class Lexer {
	
	private char[] data; 

	private Token token; 
	
	private int currentIndex; 

	/**
	 * Konstruktor.
	 * @param text ulazni tekst koji se tokenizira.
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text, "You put null reference for input string");

		this.data = text.toCharArray();
		this.currentIndex = 0;
	}
	

	/**
	 * Metoda generira i vraća sljedeći token.
	 * @return sljedeći generirani token.
	 */
	public Token nextToken() {
		
		if(this.getToken() != null && this.getToken().getType() == TokenType.EOF)
			throw new LexerException("No tokens available.");
		
		skipBlanks();
		
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);		
			return token;
		}
		
		if(data[currentIndex] == '\"') {
			StringBuilder sb = new StringBuilder();
			if(currentIndex != data.length-1)
				currentIndex++;
			while(currentIndex < data.length-1 && data[currentIndex] != '\"') {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			if(data[currentIndex] == '\"')
				currentIndex++;
			token = new Token(TokenType.LITERAL, sb.toString());
			return token;
		}
		
		skipBlanks();
		
		if(Character.isLetter(data[currentIndex])) {
			StringBuilder sb = new StringBuilder();
			sb.append(data[currentIndex]);
			if(currentIndex != data.length-1)
				currentIndex++;
			while(currentIndex < data.length && Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
					
			}
			token = new Token(TokenType.KEYWORD, sb.toString());
			return token;
		}
		
		skipBlanks();
		
		if(!Character.isLetter(data[currentIndex]) && data[currentIndex] != '\"') {
			StringBuilder sb = new StringBuilder();
			sb.append(data[currentIndex]);
			if(currentIndex != data.length-1)
				currentIndex++;
			while(currentIndex < data.length && 
					!Character.isLetter(data[currentIndex]) && data[currentIndex] != '\"') {
				sb.append(data[currentIndex]);
				currentIndex++;
					
			}
			token = new Token(TokenType.OPERATOR, sb.toString().trim());
			return token;
		}
		skipBlanks();
		
		return token;
	
	}

	
	/**
	 * Pomoćna privatna metoda koja uklanja praznine.
	 */
	private void skipBlanks() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			if(c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
		
	}

	/**
	 * Metoda koja vraća zadnji generiranoi token.
	 * @return token.
	 */
	public Token getToken() {
		return token;
	}


}
