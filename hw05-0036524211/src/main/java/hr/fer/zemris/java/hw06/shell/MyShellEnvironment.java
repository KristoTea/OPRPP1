package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * Class representation of environment.
 * @author teakr
 *
 */
public class MyShellEnvironment implements Environment{
	private Character PROMPTSYMBOL = '>';
	private Character MORELINESSYMBOL = '\\';
	private Character MULTILINESYMBOL = '|';
	private SortedMap<String, ShellCommand> commands = null;
	private Scanner sc = new Scanner(System.in);

	@Override
	public String readLine() throws ShellIOException {
		System.out.println(this.getPromptSymbol());
		try {
			String line = sc.nextLine();
			while(line.lastIndexOf(this.getMorelinesSymbol()) == (line.length() - 1)) {
				System.out.print(this.getMultilineSymbol() + " ");
				line = line.substring(0, line.length()-1);
				line += sc.nextLine();
			}
			return line;
		}catch(Exception e) {
			throw new ShellIOException(e.getMessage());
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		}catch(Exception e) {
			throw new ShellIOException(e.getMessage());
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		}catch(Exception e) {
			throw new ShellIOException(e.getMessage());
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		if(commands == null) {
			commands = new TreeMap<String, ShellCommand>();
			
			commands.put("charsets", new CharsetsCommand());
			commands.put("cat", new CatCommand());
			commands.put("ls", new LsCommand());
			commands.put("tree", new TreeCommand());
			commands.put("copy", new CopyCommand());
			commands.put("mkdir", new MkdirCommand());
			commands.put("hexdump", new HexdumpCommand());
			commands.put("help", new HelpCommand());
			commands.put("exit", new ExitCommand());
			commands.put("symbol", new SymbolCommand());
			
			commands = Collections.unmodifiableSortedMap(commands);
		}
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTILINESYMBOL;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINESYMBOL = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPTSYMBOL = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINESSYMBOL;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINESSYMBOL = symbol;
	}

}
