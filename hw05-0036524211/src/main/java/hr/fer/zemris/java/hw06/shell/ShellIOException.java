package hr.fer.zemris.java.hw06.shell;

/**
 * Custom exception for MyShell.
 * @author teakr
 *
 */
public class ShellIOException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShellIOException() {
		super();
	}

	public ShellIOException(String message) {
		super(message);
	}

}
