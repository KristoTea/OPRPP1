package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StudentRecordTest {
	
	@Test
	public void testEquals() {
		StudentRecord student1 = new StudentRecord("0036524211", "Krišto", "Tea", 5);
		StudentRecord student2 = new StudentRecord("0036524211", "Blabla", "Bla", 4);
		assertEquals(true, student1.equals(student2));
	}

}
