package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class LsCommand implements ShellCommand{
	
	private List<String> commandDescription;
	
	public LsCommand() {
		commandDescription = new ArrayList<String>();
		commandDescription.add("Command ls takes a single argument – directory –");
		commandDescription.add("and writes a directory listing. The output");
		commandDescription.add("consists of 4 columns. First column indicates");
		commandDescription.add("if current object is directory (d), readable (r),");
		commandDescription.add("writable (w) and executable (x). Second column");
		commandDescription.add("Second column contains object size in bytes.");
		commandDescription.add("Follows file creation date/time and finally file name");
		
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
			env.writeln("File " + path.getFileName() +" doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.isDirectory(path)) {
			env.writeln("File " + path.getFileName() +" isn't directory.");
			return ShellStatus.CONTINUE;
		}
		
		File file = path.toFile();
		File[] childrens = file.listFiles();
		
		if(childrens.length == 0) {
			env.writeln("Directory is empty.");
			return ShellStatus.CONTINUE;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(File f : childrens) {
			BasicFileAttributeView faView = Files.getFileAttributeView(
					path.toAbsolutePath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes;
			try {
				attributes = faView.readAttributes();
			} catch (IOException e) {
				env.writeln("Cannot read directory.");
				return ShellStatus.CONTINUE;
			}
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

			String result = String.format("%s%s%s%s %10d %s %s", 
					f.isDirectory() ? "d" : "-",
					f.canRead() ? "r" : "-",
				    f.canWrite() ? "w" : "-",
				    f.canExecute() ? "x" : "-",
				    f.length(),
				    formattedDateTime,
				    f.getName()
					);
			
			env.writeln(result);
			
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
