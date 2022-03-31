package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class SymbolCommand implements ShellCommand{
	
	private List<String> commandDescription;
	
	public SymbolCommand() {
		commandDescription = new ArrayList<String>();
		commandDescription.add("Command symbol takes one or two arguments.");
		commandDescription.add("If started with single argument - symbol name,");
		commandDescription.add("command takes it and write in shell. If command");
		commandDescription.add("has second argument it is character that will");
		commandDescription.add("replace existed symbol.");
		
		commandDescription = Collections.unmodifiableList(commandDescription);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String symbol;
		Character oldSymbol;
		Character newSymbol = ' ';
		boolean changed = false;
		
		if(arguments.contains(" ")) {
			symbol = arguments.split(" ")[0];
			if(arguments.split(" ")[1].length() != 1) {
				env.writeln("Symbol must be character.");
				return ShellStatus.CONTINUE;
			}
			newSymbol = arguments.split(" ")[1].charAt(0);
			changed = true;
		}else {
			symbol = arguments;
		}
		
		switch(symbol) {
			case "PROMPT":
				oldSymbol = env.getPromptSymbol();
				if(changed) {
					env.setPromptSymbol(newSymbol);
				}
				break;
			case "MORELINES":
				oldSymbol = env.getMorelinesSymbol();
				if(changed){
					env.setMorelinesSymbol(newSymbol);
				}
				break;
			case "MULTILINE":
				oldSymbol = env.getMultilineSymbol();
				if(changed) {
					env.setMultilineSymbol(newSymbol);
				}
				break;
			default:
				env.writeln("Unknown symbol.");
				return ShellStatus.CONTINUE;
		}
		
		if(changed) {
			env.writeln("Symbol for " + symbol + " changed from '" + oldSymbol + "' to '"+ newSymbol + "'");
		}else {
			env.writeln("Symbol for " + symbol + " is '" + oldSymbol + "'");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
