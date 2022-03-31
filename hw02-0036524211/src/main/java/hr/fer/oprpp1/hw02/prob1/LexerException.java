package hr.fer.oprpp1.hw02.prob1;

/**
 * Razred koji modelira LexerException, nasljeđuje RuntimeException.
 * @author teakr
 *
 */
public class LexerException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Kontruktor koji ispisuje i primljenu poruku.
	 * @param message poruka koja će se ispisati korisniku prilikom bacanja iznimke.
	 */
	public LexerException(String message) {
		super(message);
	}
	
	

}
