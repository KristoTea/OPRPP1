package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CharsetsCommand implements ShellCommand{
	
	private List<String> commandDescription;
	
	public CharsetsCommand() {
		commandDescription = new ArrayList<String>();
		commandDescription.add("Command charsets takes no arguments and lists");
		commandDescription.add("names of supported charsets for Java platform.");
		commandDescription.add("A single charset name is written per line.");
		
		commandDescription = Collections.unmodifiableList(commandDescription);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		//SortedMap<String,Charset> charsets = Charset.availableCharsets();
		//charsets.forEach((s,c) -> env.writeln(s));
		Charset.availableCharsets().forEach((s,c) -> env.writeln(s));
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
