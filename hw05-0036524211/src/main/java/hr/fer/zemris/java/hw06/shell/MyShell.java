package hr.fer.zemris.java.hw06.shell;

/**
 * Class represents command-line program.
 * @author teakr
 *
 */
public class MyShell {

	public static void main(String[] args) {
		System.out.println("Welcome to MyShell v 1.0");
		ShellStatus status = ShellStatus.CONTINUE;
		MyShellEnvironment environment = new MyShellEnvironment();
		
		while(status == ShellStatus.CONTINUE) {
			String line = environment.readLine();
			String command;
			String arguments;
			
			if(line.indexOf(' ') != -1) {
				command =  line.substring(0, line.indexOf(' '));
				arguments = line.substring(line.indexOf(' ') + 1);
			}else {
				command = line.trim();
				arguments = "";
			}
			
			ShellCommand shellCommand = environment.commands().get(command);
			if(shellCommand == null) {
				environment.writeln("Unknown command: " + command + ". Try again.");
				continue;
			}
			status = shellCommand.executeCommand(environment, arguments);
		}
		
	}

}
