package hr.fer.oprpp1.custom.collections;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DictionaryTest {
	
	@Test
	public void testNullKey(){
		Dictionary<String, String> dictionary = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> dictionary.put(null, "Ovo je svejedno"));
	}
	
	@Test
	public void testNullValue(){
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, null);
		assertEquals(null, dictionary.get(1));
	}
	
	@Test
	public void testIsEmptyTrue() {
		Dictionary<String, String> dictionary = new Dictionary<>();
		assertEquals(true, dictionary.isEmpty());
	}
	
	@Test
	public void testIsEmptyFalse() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "jedan");
		dictionary.put(2, "dva");
		assertEquals(false, dictionary.isEmpty());
	}
	
	@Test
	public void testSize() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "jedan");
		dictionary.put(2, "dva");
		assertEquals(2, dictionary.size());
	}
	
	@Test
	public void testClear() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "jedan");
		dictionary.put(2, "dva");
		assertEquals(false, dictionary.isEmpty());
		dictionary.clear();
		assertEquals(true, dictionary.isEmpty());
	}
	
	@Test
	public void testPutKeyExist() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "jedan");
		dictionary.put(2, "dva");
		assertEquals("dva", dictionary.get(2));
		dictionary.put(2, "dvadva");
		assertEquals("dvadva", dictionary.get(2));
	}
	
	@Test
	public void testGetKeyDoestExist() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "jedan");
		dictionary.put(2, "dva");
		assertEquals(null, dictionary.get(22));
	}
	
	@Test
	public void testRemove() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "jedan");
		dictionary.put(2, "dva");
		assertEquals("jedan", dictionary.remove(1));
	}
	
	@Test
	public void testRemoveKeyDoesntExist() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "jedan");
		dictionary.put(2, "dva");
		assertEquals(null, dictionary.remove(11));
	}
	

}
