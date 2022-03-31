package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Razred koji modelira bazu podataka studenata.
 * @author teakr
 *
 */
public class StudentDatabase {
	
	/**
	 * Lista svih studenata.
	 */
	private List<StudentRecord> studentsList;
	
	/**
	 * Mapa svih studenata, mapiramo po jmbagu.
	 */
	private Map<String, StudentRecord> studentsMap;
	
	/**
	 * Konstruktor.
	 * @param rows
	 */
	public StudentDatabase(List<String> rows) {
		studentsList = new ArrayList<StudentRecord>();
		studentsMap = new HashMap<String, StudentRecord>();
		
		for (String row : rows) {
			String[] data = row.split("\t");
			if(data.length != 4)
				throw new RuntimeException();
			
			if(studentsMap.get(data[0]) == null) {
				int grade = Integer.parseInt(data[3]);
				if(grade < 1 || grade > 5)
					throw new IllegalArgumentException("Grade need to be in between 1 and 5.");
				StudentRecord record = new StudentRecord(data[0], data[1], data[2], grade);
				studentsList.add(record);
				studentsMap.put(data[0], record);
			}else
				throw new IllegalArgumentException("Already have " + data[0] + " jmbag in database.");
		}
	}
	
	/**
	 * Metoda za predani jmbag vraća studenta.
	 * @param jmbag
	 * @return studenta
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentsMap.get(jmbag);
	}
	
	/**
	 * Metoda vraća listu svih studenta koje predani filter prihvati.
	 * @param filter
	 * @return lista studenata
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> filteredStudents = new ArrayList<>();
		for(StudentRecord record : studentsList) {
			if(filter.accepts(record)) {
				filteredStudents.add(record);
			}
		}
		return filteredStudents;
		
	}

	public List<StudentRecord> getStudentsList() {
		return studentsList;
	}

	public Map<String, StudentRecord> getStudentsMap() {
		return studentsMap;
	}
	

}
