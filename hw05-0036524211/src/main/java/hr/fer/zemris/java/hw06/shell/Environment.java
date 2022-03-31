package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Interface represents environment used for communication with client.
 * @author teakr
 *
 */
public interface Environment {
	
	/**
	 * Method reads line from shell/standard input.
	 * @return string read from shell.
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Method writes given text on standard output.
	 * @param text
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Method writes given line text on standard output.
	 * @param text
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Methods returns all commands as unmodified sorted map.
	 * @return map of shell commands.
	 */
	SortedMap<String, ShellCommand> commands();
	
	Character getMultilineSymbol();
	
	void setMultilineSymbol(Character symbol);
	
	Character getPromptSymbol();
	
	void setPromptSymbol(Character symbol);
	
	Character getMorelinesSymbol();
	
	void setMorelinesSymbol(Character symbol);


}
