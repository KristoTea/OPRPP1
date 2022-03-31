package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {
	
	StudentRecord record = new StudentRecord("0036524211", "Krišto", "Tea", 5);
	
	@Test
	public void testGetFirstName() {
		assertEquals("0036524211", FieldValueGetters.JMBAG.get(record));
	}
	
	@Test
	public void testGetLastName() {
		assertEquals("Krišto", FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	public void testGetJmbagName() {
		assertEquals("Tea", FieldValueGetters.FIRST_NAME.get(record));
	}

}
