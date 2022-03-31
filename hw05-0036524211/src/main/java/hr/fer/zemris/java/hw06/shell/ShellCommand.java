package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface represents functionality of shell commands.
 * @author teakr
 *
 */
public interface ShellCommand {
	
	/**
	 * Command for given argument through out environment executes right functionality.
	 * @param env environment used for communication with client.
	 * @param arguments defined for each command.
	 * @return shell status, TERMINATE for exit command, CONTINUE otherwise.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Method returns command name.
	 * @return command name.
	 */
	String getCommandName();
	
	/**
	 * Method returns command description as string list.
	 * @return command description.
	 */
	List<String> getCommandDescription();


}
