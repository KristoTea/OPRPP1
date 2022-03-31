package hr.fer.oprpp1.hw04.db.lexer;


/**
 * Razred koji modelira tokene.
 * @author teakr
 *
 */
public class Token {
	
	/**
	 * Vrsta tokena.
	 */
	private TokenType tokenType;
	
	/**
	 * Vrijednost tokena.
	 */
	private String value;
	
	/**
	 * Konstruktor.
	 * @param type vrsta tokena.
	 * @param value vrijednost tokena.
	 */
	public Token(TokenType type, String value) {
		this.tokenType = type;
		this.value = value;
	}
	
	/**
	 * Metoda dohvaća vrijednost tokena.
	 * @return vrijednost tokena.
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Metoda dohvaća tip tokena.
	 * @return tip tokena.
	 */
	public TokenType getType() {
		return this.tokenType;
	}

}
