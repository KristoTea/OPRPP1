package hr.fer.oprpp1.hw04.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.oprpp1.hw04.db.*;

public class DatabaseDemo {

	public static void main(String[] args) {

		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("src/main/resources/database/database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StudentDatabase database = new StudentDatabase(lines);
		
		try (Scanner sc = new Scanner(System.in)) {
			while(true) {
				System.out.print("> ");
				String line = sc.nextLine();
				
				if(line.trim().toLowerCase().equals("exit")) {
					System.out.println("Goodbye!");
					System.exit(0);
				}
				
				if(!line.trim().startsWith("query")) {
					System.out.println("Unknown query.");
					continue;
				}
				
				QueryParser parser = null;
				try {
					parser = new QueryParser(line.trim().substring(5));
				}catch(Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				
				
				List<StudentRecord> input = null;
				if(parser.isDirectQuery()) {
					input = new ArrayList<StudentRecord>();
					input.add(database.forJMBAG(parser.getQueriedJMBAG()));
				}else {
					input = database.filter(new QueryFilter(parser.getQuery()));
				}
							
				List<String> output = RecordFormater.format(input);
				output.forEach(System.out::println);
				
			}
			
		}
	}
	
	public static class RecordFormater{
		
		public static List<String> format(List<StudentRecord> input){
			List<String> output = new ArrayList<>();
			if(input.isEmpty()) {
				output.add("Records selected: 0");
				return output;
			}
			
			int lastNameLength = (input.stream().mapToInt(r -> r.getLastName().length()).max()).getAsInt();
			int firstNameLength =(input.stream().mapToInt(r -> r.getFirstName().length()).max()).getAsInt();
			
			StringBuilder sb = new StringBuilder();
			sb.append("+============+");
			sb.append("=".repeat(lastNameLength+2));
			sb.append("+");
			sb.append("=".repeat(firstNameLength+2));
			sb.append("+===+");
			String header = sb.toString();

			output.add(header);
			
			for(StudentRecord record : input) {
				StringBuilder sbuilder = new StringBuilder();
				sbuilder.append("| ");
				sbuilder.append(record.getJmbag());
				sbuilder.append(" | ");
				sbuilder.append(record.getLastName());
				sbuilder.append(" ".repeat(lastNameLength - record.getLastName().length()));
				sbuilder.append(" | ");
				sbuilder.append(record.getFirstName());
				sbuilder.append(" ".repeat(lastNameLength - record.getFirstName().length()));
				sbuilder.append(" | ");
				sbuilder.append(record.getFinalGrade());
				sbuilder.append(" |");
				output.add(sbuilder.toString());
			}
			
			output.add(header);
			output.add("Records selected: " + input.size());
			return output;
		}
	}

}
