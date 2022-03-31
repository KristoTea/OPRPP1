package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Razred EmptyStackException koji generira iznimku u slučaju praznog stoga.
 * @author teakr
 *
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Prazni konstruktor.
	 */
	public EmptyStackException() {
		
	}
	
	/**
	 * Konstruktor koji stvara iznimku koja ispisuje poruku greške.
	 * @param message Poruka koja će biti poslana u slučaju iznimke.
	 */
	public EmptyStackException(String message) {
		super(message);
	}

}
