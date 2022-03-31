package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CopyCommand implements ShellCommand{
	
	private List<String> commandDescription;
	
	public CopyCommand() {
		commandDescription = new ArrayList<String>();
		commandDescription.add("The copy command expects two arguments: ");
		commandDescription.add("source file name and destination file name.");
		commandDescription.add("If destination file exists, shell asks user");
		commandDescription.add("is it allowed to overwrite it. opy command ");
		commandDescription.add("works only with files (no directories). If ");
		commandDescription.add("the second argument is directory, it assumes");
		commandDescription.add("that user wants to copy the original file into");
		commandDescription.add("that directory using the original file name.");
		
		commandDescription = Collections.unmodifiableList(commandDescription);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String sPath1;
		String sPath2;
		
		if(arguments.contains("\"")) {
			if(arguments.startsWith("\"") ) {
				sPath1 = arguments.substring(1, arguments.indexOf('\"', 1));
				sPath1 = sPath1.replace("\\\"", "\"");
				sPath1 = sPath1.replace("\\\\", "\\");
				if(arguments.endsWith("\"")) {
					sPath2 = arguments.substring(arguments.indexOf('\"', 1) + 3, arguments.length()-1);
					sPath2 = sPath2.replace("\\\"", "\"");
					sPath2 = sPath2.replace("\\\\", "\\");
				}else {
					sPath2 = arguments.substring(arguments.indexOf('\"', 1) + 2, arguments.length());
				}
			}else {
				sPath1 = arguments.substring(0, arguments.indexOf('\"')-1).trim();
				sPath2 = arguments.substring(arguments.indexOf('\"')+1, arguments.length()-1);
				sPath2 = sPath2.replace("\\\"", "\"");
				sPath2 = sPath2.replace("\\\\", "\\");
			}
		}else {
			sPath1 = arguments.split(" ")[0];
			sPath2 = arguments.split(" ")[1];
		}
		
		
		Path path1 = Paths.get(sPath1);
		Path path2 = Paths.get(sPath2);
		
		if(!Files.exists(path1)) {
			env.writeln(sPath1 + " doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(path1)) {
			env.writeln(sPath1 + " is directory. Cannot be coppied.");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.exists(path2)) {
			env.writeln(sPath2 + " doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.exists(path2) && !Files.isDirectory(path2)) {
			env.writeln(sPath2 + " file already exist. Do you want to overwrite it? [y/n]");
			String answer = env.readLine();
			if(answer.toLowerCase().equals("n")) {
				return ShellStatus.CONTINUE;
			}
		} 
		
		if(Files.exists(path2) && Files.isDirectory(path2)) {
			if(sPath1.lastIndexOf('/') != -1) {
				sPath2 += sPath1.substring(sPath1.lastIndexOf('/'), sPath1.length()-1);
			}else {
				sPath2 +=("/"+ sPath1);
			}
			
			path2 = Paths.get(sPath2);
			
			try {
				Files.createFile(path2);
			}catch(IOException e) {
				env.writeln("Error wile creating new file.");
				return ShellStatus.CONTINUE;
			}
		}
		
		
		try(InputStream is = Files.newInputStream(path1); OutputStream op = Files.newOutputStream(path2)){
			byte[] buf = new byte[4096];
			while(true) {
				int len = is.read(buf);
				if(len < 1)
					break;
				op.write(buf, 0, len);
			}
		}catch(IOException e) {
			env.writeln("Error while reading/writing.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
