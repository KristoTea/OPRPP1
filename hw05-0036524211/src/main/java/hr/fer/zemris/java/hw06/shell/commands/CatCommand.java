package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CatCommand implements ShellCommand{
	
	private List<String> commandDescription;
	
	public CatCommand() {
		commandDescription = new ArrayList<String>();
		commandDescription.add("Command cat takes one or two arguments.");
		commandDescription.add("The first argument is path to some file");
		commandDescription.add("and is mandatory. The second argument is");
		commandDescription.add("charset name that should be used to interpret");
		commandDescription.add("chars from bytes. If not provided, a default");
		commandDescription.add("platform charset should be used. This command");
		commandDescription.add("opens given file and writes its content to console.");
		
		commandDescription = Collections.unmodifiableList(commandDescription);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String sPath;
		String charsetName;
		
		if(arguments.startsWith("\"")) {
			sPath = arguments.substring(1, arguments.lastIndexOf("\""));
			sPath.replace("\\\"", "\"");
			sPath.replace("\\\\", "\\");
			if(arguments.lastIndexOf("\"") != (arguments.length() - 1)) {
				charsetName = arguments.substring(arguments.lastIndexOf("\"") + 1);
			}else {
				charsetName = Charset.defaultCharset().name();
			}
		}else {
			if(arguments.indexOf(' ') != -1) {
				sPath = arguments.substring(1, arguments.indexOf(' '));
				charsetName = arguments.substring(arguments.indexOf(' ') + 1);
			}else {
				sPath = arguments;
				charsetName = Charset.defaultCharset().name();
			}
		}
		
		Path path = Paths.get(sPath);
		
		if(!Files.exists(path)) {
			env.writeln("File " + sPath +" doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(path)) {
			env.writeln("File " + sPath + " is directory.");
			return ShellStatus.CONTINUE;
		}
		StringBuilder sb = new StringBuilder();
		try(InputStream is = Files.newInputStream(path)){
			byte[] buf = new byte[4096];
			while(true) {
				int i = is.read(buf);
				if(i < 1)
					break;
				sb.append(new String(Arrays.copyOf(buf, i), charsetName));
			}
			env.write(sb.toString());
		}catch(IOException e) {
			throw new ShellIOException("Unknown path.");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
