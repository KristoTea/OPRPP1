package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HexdumpCommand implements ShellCommand{
	
	private List<String> commandDescription;
	
	public HexdumpCommand() {
		commandDescription = new ArrayList<String>();
		commandDescription.add("The hexdump command expects a single argument:");
		commandDescription.add("file name, and produces hex-output. Only a ");
		commandDescription.add("standard subset of characters is shown, ");
		commandDescription.add("for all other characters a '.' is printed instead");
		
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
		
		if(!Files.exists(path)) {
			env.writeln("File " + arguments + " doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		
		try(InputStream is = Files.newInputStream(path)){
			byte[] buf = new byte[4096];
			String[] exit = new String[4];
			int level = 0;
			
			while(true) {
				int len = is.read(buf);
				if(len < 1)
					break;
				exit[0] = String.format("%7d:", level);
				level += 16;
				
				for(int i = 0; i < len; i++) {
					if(i < 8) {
						exit[1] += String.format(" %02X", buf[i]);
					}else {
						exit[2] += String.format("%02X ", buf[i]);
					}
					
					if((int)buf[i] < 32 || (int)buf[i] > 127) {
						exit[3] += String.format("s", ".");
					}else {
						exit[3] += String.format("s", (char)buf[i]);
					}
				}
				
				for(int i = 1; i <= 2; i++) {
					 if(exit[i].length() < 24) {
						 exit[i] += " ".repeat(24 - exit[i].length());
					 }
				}
				env.writeln(exit[0] + exit[1] +"|" + exit[2] + "|" + exit[3]);
				
			}
		} catch (IOException e) {
			env.writeln("Error while reading file.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
