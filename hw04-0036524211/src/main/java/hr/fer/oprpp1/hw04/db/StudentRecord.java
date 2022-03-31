package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Razred koji modelira zapis jednog studenta.
 * @author teakr
 *
 */
public class StudentRecord {
	
	private String jmbag;
	
	private String lastName;
	
	private String firstName;
	
	private int finalGrade;

	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(finalGrade, firstName, jmbag, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return jmbag.equals(other.getJmbag());
	}
	
	

}
