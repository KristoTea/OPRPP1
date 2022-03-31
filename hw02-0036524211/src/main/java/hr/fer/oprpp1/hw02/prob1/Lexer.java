package hr.fer.oprpp1.hw02.prob1;

import java.util.Objects;

/**
 * Razred koji modelira ponašanje leksičkog analizatora.
 * @author teakr
 *
 */
public class Lexer {
	
	/**
	 * Ulazni tekst kao polje znakova.
	 */
	private char[] data; // ulazni tekst
	
	/**
	 * Zadnji generirani token.
	 */
	private Token token; // trenutni token
	
	/**
	 * Indeks prvog neobrađenog znaka.
	 */
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState state;

	/**
	 * Konstruktor.
	 * @param text ulazni tekst koji se tokenizira.
	 */
	// konstruktor prima ulazni tekst koji se tokenizira
	public Lexer(String text) {
		Objects.requireNonNull(text, "You put null reference for input string");

		
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
	}
	
	
	// generira i vraća sljedeći token
	// baca LexerException ako dođe do pogreške
	/**
	 * Metoda generira i vraća sljedeći token.
	 * @return sljedeći generirani token.
	 */
	public Token nextToken() {
		
		if(this.getToken() != null && this.getToken().getType() == TokenType.EOF)
			throw new LexerException("No tokens available.");
		
		while(state.equals(LexerState.BASIC)) {
		skipBlanks();
		
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);		
			return token;
		}
		
		if(Character.isLetter(data[currentIndex])) {
			StringBuilder sb = new StringBuilder();
			sb.append(data[currentIndex]);
			currentIndex++;
			while(currentIndex < data.length) {
				if(Character.isLetter(data[currentIndex])) {
					sb.append(data[currentIndex]);
					currentIndex++;
					continue;
				}else if(data[currentIndex] == '\\' && 
						(Character.isDigit(data[currentIndex+1]) || data[currentIndex+1] == '\\')){
							sb.append(data[currentIndex+1]);
							currentIndex += 2;
							continue;
						}
				break;
			}
			token = new Token(TokenType.WORD, sb.toString());
			return token;
		}
		
		skipBlanks();
		
		if(data[currentIndex]=='\\') {
			if(currentIndex == data.length-1)
				throw new LexerException("Cannot skip end.");
			
			currentIndex++;
			if(Character.isLetter(data[currentIndex]))
				throw new LexerException("Cannot skip letter.");
			
			StringBuilder sb = new StringBuilder();
			sb.append(data[currentIndex]);
			currentIndex++;
			
			while(currentIndex < data.length) {
				if(Character.isLetter(data[currentIndex])) {
					sb.append(data[currentIndex]);
					currentIndex++;
					continue;
				}else if(data[currentIndex] == '\\' && 
						(Character.isDigit(data[currentIndex+1]) || data[currentIndex+1] == '\\')){
							sb.append(data[currentIndex+1]);
							currentIndex += 2;
							continue;
						}
				break;
			}
			token = new Token(TokenType.WORD, sb.toString());
			return token;
		}
		
		skipBlanks();
		
		if(Character.isDigit(data[currentIndex])) {
			StringBuilder sb = new StringBuilder();
			sb.append(data[currentIndex]);
			currentIndex++;
			while(currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			try {
				long value = Long.parseLong(sb.toString());
				token = new Token(TokenType.NUMBER, value);
				return token;
			}catch (Exception ex) {
				throw new LexerException("Current token can't be parsed");
			}
		}
		
		skipBlanks();
		
		if(!Character.isDigit(data[currentIndex]) && 
				!Character.isLetter(data[currentIndex]) &&
				data[currentIndex]!='\\' &&
				data[currentIndex] != ' ') {
			if(data[currentIndex] == '#') {
				break;
			}
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			return token;
		}
		
		skipBlanks();
		}
		
		while(state.equals(LexerState.EXTENDED)) {
			skipBlanks();
			
			if(currentIndex >= data.length) {
				token = new Token(TokenType.EOF, null);		
				return token;
			}
			skipBlanks();
			if(data[currentIndex] == '#') {
				break;
			}
			StringBuilder sb = new StringBuilder();
			while(data[currentIndex] != ' ' && data[currentIndex] != '#' && currentIndex < data.length) {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			token = new Token(TokenType.WORD, sb.toString());
			return token;
			
		}
		
		if(data[currentIndex] == '#') {
			if(state.equals(LexerState.EXTENDED)) {
				setState(LexerState.BASIC);
			}else {
				setState(LexerState.EXTENDED);
			}
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			return token;
		}
			
		return token;

		
	}

	
	
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

	// vraća zadnji generirani token; može se pozivati
	// više puta; ne pokreće generiranje sljedećeg tokena
	/**
	 * Metoda koja vraća zadnji generiranoi token.
	 * @return token.
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Metoda koja postavlja stanje leksera.
	 * @param state stanje u koje treba postaviti lekser.
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state, "State cannot be null.");
		this.state = state;
	}



}
