package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SimpleHashtableTest {
	
	@Test
	public void testDefaultConstructor(){		
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<>();
		assertEquals(0, hashTable.size());
		assertEquals(16, hashTable.capacity());
	}
	
	@Test
	public void testConstructor(){		
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<>(6);
		assertEquals(0, hashTable.size());
		assertEquals(8, hashTable.capacity());
	}
	
	@Test
	public void testConstructorThrowsIllegalArgumentException(){
		try {
			@SuppressWarnings("unused")
			SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<>(-1);
		}catch(IllegalArgumentException e) {
			
		}
	}
	
	@Test
	public void testPutNullPointerException() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<>();
		assertThrows(NullPointerException.class, () -> hashTable.put(null, "value"));
	}
	
	@Test
	public void testIsEmptyTrue() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<>();
		assertEquals(true, hashTable.isEmpty());
	}
	
	@Test
	public void testIsEmptyFalse() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<>();
		hashTable.put(1, "jedan");
		assertEquals(false, hashTable.isEmpty());
	}
	
	@Test
	public void testClear() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<>();
		hashTable.put(1, "jedan");
		hashTable.put(2, "dva");
		assertEquals(false, hashTable.isEmpty());
		hashTable.clear();
		assertEquals(true, hashTable.isEmpty());
	}
	
	@Test
	public void testPutElementWithIncreasingNoSlots() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<>(2);
		assertEquals(2, hashTable.capacity());
		hashTable.put(1, "jedan");
		hashTable.put(2, "dva");
		assertEquals(2, hashTable.capacity());
		hashTable.put(3, "tri");
		assertEquals(4, hashTable.capacity());
	}
	
	@Test
	public void testPutExistingElement() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<>();
		hashTable.put(1, "jedan");
		hashTable.put(2, "dva");
		hashTable.put(2, "dvadva");
		assertEquals("dvadva", hashTable.get(2));
	}
	
	@Test
	public void testContainsKey() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<>();
		hashTable.put(1, "jedan");
		hashTable.put(2, "dva");
		assertEquals(true, hashTable.containsKey(2));
	}
	
	@Test
	public void testContainsValue() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<>();
		hashTable.put(1, "jedan");
		hashTable.put(2, "dva");
		assertEquals(true, hashTable.containsValue("dva"));
	}
	
}
