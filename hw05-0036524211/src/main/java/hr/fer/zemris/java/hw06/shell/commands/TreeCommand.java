package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class TreeCommand implements ShellCommand{
	
	private List<String> commandDescription;
	
	public TreeCommand() {
		commandDescription = new ArrayList<String>();
		commandDescription.add("The tree command expects a single argument:");
		commandDescription.add("directory name and prints a tree. Each directory");
		commandDescription.add("level shifts output two charatcers to the right.");
		
		commandDescription = Collections.unmodifiableList(commandDescription);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.startsWith("\"")) {
			arguments = arguments.substring(1, arguments.length()-1);
			arguments = arguments.replace("\\\"", "\"");
			arguments = arguments.replace("\\\\", "\\");
		}
		
		File file = new File(arguments);
		
		if(!file.exists()) {
			env.writeln("File " + file.getName() + " doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		
		if(!file.isDirectory()) {
			env.writeln("File " + file.getName() +" isn't directory.");
			return ShellStatus.CONTINUE;
		}
		
		StringBuilder sb = new StringBuilder();
		
		searchTree(file, sb, 0);
		
		env.write(sb.toString());
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}
	
	private void searchTree(File file, StringBuilder sb, int level) {
		if(!file.isDirectory()) {
			return;
		}
		
		File[] children = file.listFiles();
		
		if(children.length == 0)
			return;
		
		for(File f : children) {
			sb.append(String.format("%s%s\n", " ".repeat(level*2), f.getName()));
			level++;
			searchTree(f, sb, level);
			level--;
		}
	}

}
