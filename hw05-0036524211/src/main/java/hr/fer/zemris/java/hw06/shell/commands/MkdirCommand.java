package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class MkdirCommand implements ShellCommand{
	
	private List<String> commandDescription;
	
	public MkdirCommand() {
		commandDescription = new ArrayList<String>();
		commandDescription.add("The mkdir command takes a single argument:");
		commandDescription.add(": directory name, and creates the appropriate");
		commandDescription.add("directory structure");
		
		commandDescription = Collections.unmodifiableList(commandDescription);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.startsWith("\"")) {
			arguments = arguments.substring(1, arguments.length()-1);
			arguments = arguments.replace("\\\"", "\"");
			arguments = arguments.replace("\\\\", "\\");
		}
		
		Path path = Paths.get(arguments);
		
		if(Files.exists(path)) {
			env.writeln(arguments + " already exists.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.createDirectories(path);
		}catch(IOException e) {
			env.writeln("Error while creating directory.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
