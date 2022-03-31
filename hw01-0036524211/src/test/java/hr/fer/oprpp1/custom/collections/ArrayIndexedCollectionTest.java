package hr.fer.oprpp1.custom.collections;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	
	@Test
	public void testDefaultConstructor() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertEquals(16, array.getFullSize());
	}
	
	@Test
	public void testConstructorWithInitialCapacity() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(5);
		assertEquals(5, array.getFullSize());
	}
	
	@Test
	public void testConstructorWithInitialCapacityThrowsIllegalArgumentException() {
		try {
			@SuppressWarnings("unused")
			ArrayIndexedCollection array = new ArrayIndexedCollection(-5);
		}catch(IllegalArgumentException e) {
		}
	}
	
	@Test
	public void testConstructorWithOtherCollection() {
		Collection other = new Collection();
		other.add(1);
		other.add(2);
		other.add(3);
		ArrayIndexedCollection array = new ArrayIndexedCollection(other);
//		assertEquals(1, array.get(0));
//		assertEquals(2, array.get(1));
//		assertEquals(3, array.get(2));
		assertEquals(3, array.getFullSize());
	}
	
	@Test
	public void testConstructorWithOtherCollectionThrowsNullPointerException() {
		Collection other = new Collection();
		try {
			@SuppressWarnings("unused")
			ArrayIndexedCollection array = new ArrayIndexedCollection(other);
		}catch(NullPointerException e) {
			
		}
	}
	
	@Test
	public void testConstructorWithOtherCollectionAndInitialCapacity() {
		Collection other = new Collection();
		other.add(1);
		other.add(2);
		other.add(3);
		ArrayIndexedCollection array = new ArrayIndexedCollection(other, 10);
		assertEquals(3, array.size());
		assertEquals(10, array.getFullSize());
	}
	
	@Test
	public void testConstructorWithOtherCollectionAndInitialCapacityThrowsNullPointerException() {
		Collection other = null;
		try {
			@SuppressWarnings("unused")
			ArrayIndexedCollection array = new ArrayIndexedCollection(other, 10);
		}catch(NullPointerException e){
			
		}
		
	}
	
	@Test
	public void testConstructorWithOtherCollectionAndInitialCapacityThrowsIllegalArgumentException() {
		Collection other = new Collection();
		other.add(1);
		other.add(2);
		other.add(3);
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(other, -10));
		
	}
	
	@Test
	public void testAddElement() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		//int[] test = {1};
		assertEquals(1, array.get(0));
	}
	
	@Test
	public void testAddElementThrowsNullPointerException() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> array.add(null));
	}
	
	@Test
	public void testGetElement() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		assertEquals(2, array.get(1));
	}
	
	@Test
	public void testGetElementThrowsIndexOutOfBoundsException() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
	}
	
	@Test
	public void testClearAllArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		array.clear();
		assertEquals(0, array.size());
	}
	
	@Test
	public void testInsertElementOnExactPosition() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		array.insert(10, 1);
		//int[] test = new int[] {1, 10, 2 ,3};
		assertEquals(10, array.get(1));
	}
	
	@Test
	public void testInsertElementOnExactPositionThrowsNullPointerException() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		try {
			array.insert(null, 1);
		}catch(NullPointerException e) {
			
		}
	}
	
	@Test
	public void testInsertElementOnExactPositionThrowsIndexOutOfBoundsException() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		try {
			array.insert(10, 20);
		}catch(IndexOutOfBoundsException e) {
			
		}
	}
	
	@Test
	public void testIndexOfMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		assertEquals(1, array.indexOf(2));
		assertEquals(-1, array.indexOf(10));
	}
	
	@Test
	public void testIndexMethodThrowsNullPointerException() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		try {
			array.indexOf(null);
		}catch(NullPointerException e) {
			
		}
	}
	
	@Test
	public void testRemoveElementOnGivenIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		array.remove(1);
//		ArrayIndexedCollection test = new ArrayIndexedCollection();
//		array.add(1);
//		array.add(3);
		assertEquals(1, array.get(0));
		assertEquals(3, array.get(1));
	}
	
	@Test
	public void testRemoveElementOnGivenIndexThrowsIndexOutOfBoundsException() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		try {
			array.remove(-1);
		}catch(IndexOutOfBoundsException e) {
			
		}
	}
	
	@Test
	public void testSizeMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		assertEquals(3, array.size());
	}
	
	@Test
	public void testContainsMethodReturnTrue() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		assertEquals(true, array.contains(2));
	}
	
	@Test
	public void testContainsMethodReturnFalse() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(2);
		array.add(3);
		assertEquals(false, array.contains(-2));
	}
	
//	@Test
//	public void testRemoveGivenObject() {
//		ArrayIndexedCollection array = new ArrayIndexedCollection();
//		array.add(1);
//		array.add(2);
//		array.add(3);
//		assertEquals(true, array.remove(2));
//	}
	
}
