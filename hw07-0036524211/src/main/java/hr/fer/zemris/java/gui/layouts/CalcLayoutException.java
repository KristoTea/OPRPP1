package hr.fer.zemris.java.gui.layouts;


/**
 * Iznimka izazvana dodavanjem komponente na 
 * nelegalnu poziciju unutar cijelog rasporeda.
 * @author teakr
 *
 */
public class CalcLayoutException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public CalcLayoutException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Konstruktor.
	 * @param message poruka koja opisuje grešku.
	 */
	public CalcLayoutException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	

}
