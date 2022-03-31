package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HelpCommand implements ShellCommand{
	
	private List<String> commandDescription;
	
	public HelpCommand() {
		commandDescription = new ArrayList<String>();
		commandDescription.add("Command help takes zero or one argument.");
		commandDescription.add("If started with no arguments, it lists names");
		commandDescription.add("of all supported commands. If started with");
		commandDescription.add("single argument, it prints name and the description");
		commandDescription.add("of selected command or print appropriate error");
		commandDescription.add("message if no such command exists.");
		
		commandDescription = Collections.unmodifiableList(commandDescription);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isEmpty()) {
			env.commands().forEach((x, y) -> env.writeln(y.getCommandName()));
		}else {
			ShellCommand command = env.commands().get(arguments);
			if(command == null) {
				env.writeln("Unknown command.");
				return ShellStatus.CONTINUE;
			}
			command.getCommandDescription().stream().forEach(l -> env.writeln(l));
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
