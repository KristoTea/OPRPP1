package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	@Test
	public void testDefaultConstructor() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
	}
	
	@Test
	public void testConstructorWithOtherCollection() {
		Collection other = new Collection();
		other.add(1);
		other.add(2);
		other.add(3);
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(other);
		assertEquals(3, list.size());
	}
	
	@Test
	public void testAddElement() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		assertEquals(1, list.size());
		assertEquals(false, list.isEmpty());
	}
	
	@Test
	public void testAddElementThrowsNullPointerException() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		try {
			list.add(null);
		}catch(NullPointerException e) {
			
		}
	}
	
	@Test
	public void testGetElement() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		assertEquals(2, list.get(1));
	}
	
	@Test
	public void testGetElementThrowsIndexOutOfBoundsException() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		try {
			list.get(10);
		}catch(IndexOutOfBoundsException e) {
			
		}
	}
	
	@Test
	public void testClearAllElementsFromCollection() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		list.clear();
		assertEquals(0, list.size());
	}
	
	@Test
	public void testInsertMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		list.insert(10, 1);
		assertEquals(true, list.contains(10));
		assertEquals(1, list.indexOf(10));
	}
	
	@Test
	public void testInsertMethodThrowsNullPointerException() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		try {
			list.insert(null, 0);
		}catch(NullPointerException e) {
			
		}
	}
	
	@Test
	public void testInsertMethodThrowsIndexOutOfBoundsException() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		try {
			list.insert(5, 5);
		}catch(IndexOutOfBoundsException e) {
			
		}
	}
	
	@Test
	public void testIndexOfMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		assertEquals(0, list.indexOf(1));
	}
	
	@Test
	public void testIndexOfMethodWithWrongElement() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		assertEquals(-1, list.indexOf(10));
	}
	
	@Test
	public void testRemoveMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		list.remove(0);
//		LinkedListIndexedCollection test = new LinkedListIndexedCollection();
//		test.add(2);
//		test.add(3);
		assertEquals(2, list.get(0));
		assertEquals(3, list.get(1));
	}
	
	@Test
	public void testRemoveMethodThrowsIndexOutOfBoundsException() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		try {
			list.remove(-1);
		}catch(IndexOutOfBoundsException e){
			
		}
		
	}
	
	@Test
	public void testSizeMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		assertEquals(3, list.size());
	}
	
	@Test
	public void testContainsMethodReturnTrue() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		assertEquals(true, list.contains(2));
	}
	
	@Test
	public void testContainsMethodReturnFalse() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		assertEquals(false, list.contains(10));
	}
	
	@Test
	public void testRemoveGivenObject() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		list.remove(2);
		assertEquals(2, list.size());
		//je li izbrisao dobar?
	}
	
	
}
