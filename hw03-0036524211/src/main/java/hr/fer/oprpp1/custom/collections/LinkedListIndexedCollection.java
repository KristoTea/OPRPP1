package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Razred kolekcija objekata spremanih u povezanu listu, implementira sučelje Collection.
 * 
 * @author teakr
 *
 */
public class LinkedListIndexedCollection<T> implements List<T>{

	/**
	 * Privatna klasa koja predstavlja jedan čvor u povezanoj listi.
	 * 
	 * @author teakr
	 *
	 */
	 private static class ListNode<T>{
		 private ListNode<T> previous;
		 private ListNode<T> next;
		 private T element;
	 }
	 
	 /**
	  * Trenutni broj elemenata u polju.
	  */
	 private int size;
	 
	 /**
	  * Prvi čvor liste.
	  */
	 private ListNode<T> first;
	 
	 /**
	  * Zadnji čvor liste.
	  */
	 private ListNode<T> last;
	 
	 /**
	  * Varijabla koja broji broj promjena nad strukturom kolekcije.
	  */
	 private long modificationCount = 0;
	 
	 /**
	  * Konstruktor koji kreira LinkedListIndexedCollection bez čvorova.
	  */
	 public LinkedListIndexedCollection() {
		 this.size = 0;
		 this.first = null;
		 this.last = null;
	 }
	 
	 /**
	  * Konstruktor koji kreira LinkedListIndexedCollection čiji su čvorovi elementi predane kolekcije.
	  * 
	  * @param other Kolekcija čiji će elementi biti čvorovi u LinkedListIndexedCollection
	  */
	 public LinkedListIndexedCollection(Collection<? extends T> other) {
		 this.first = null;
		 this.last = null;
		 this.addAll(other);
	 }
	 
	 @Override
	 /**
	  * @throws NullPointerException ako se kao argument preda null pokazivač.
	  */
	 public void add(T value) {
		 if(value == null) 
				throw new NullPointerException("Argument value must be non-null reference!");
		 
		 ListNode<T> node = new ListNode<>();
		 node.element = value;
		 
		 if(this.first == null && this.last == null) {
			 first = node;
			 last = node;
		 }else {
			 node.previous = this.last;
			 node.next = null;
			 this.last.next = node;
			 this.last = node;
		 }
			 
		 size++;
		 modificationCount++;
	 }
	 
	 @Override
	 /**
	  * @throws IndexOutOfBoundsException ako se kao argument preda broj manji od nula, 
	  * odnosno veći od veličine umanjene za 1.
	  */
	 public T get(int index) {
		 if(index < 0 || index > size-1) 
			 throw new IndexOutOfBoundsException("Argument index must be between 0 and (size - 1)");

		 ListNode<T> helper = null;
		 
		 if(index < size) {
			 helper = first;
			 for(int i = 0; i < index; i++) {
				 helper = helper.next;
			 }
			 return helper.element;
		 }else {
			 helper = last;
			 for(int i = size-1; i > index; i--) {
				 helper = helper.previous;
			 }
			 return helper.element;
		 }				 
	 }
	 
	 @Override
	 public void clear() {
		 this.size = 0;
		 this.first = null;
		 this.last = null;
		 this.modificationCount++;
	 }
	 
	 
	 @Override
	 /**
	  * @throws NullPointerException ako se kao prvi argument preda null pokazivač.
	  * @throws IndexOutOfBoundsException ako se kao argument preda broj manji od nula, 
	  * odnosno veći od veličine umanjene za 1.
	  */
	 public void insert(T value, int position) {
		 if(value == null) 
				throw new NullPointerException("Argument value must be non-null reference!");
		if(position < 0 || position > size)
				throw new IndexOutOfBoundsException("Argument position must be between 0 and (size - 1)");
		 
		 ListNode<T> newNode = new ListNode<>();
		 newNode.element = value;
		 
		 if(position == 0) {
			 newNode.next = first;
			 newNode.previous = null;
			 this.first = newNode;
		 }else if(position == size-1) {
			 newNode.next = null;
			 newNode.previous = last;
			 last = newNode;
		 }else {
			 ListNode<T> helper = first;
			 for(int i = 0; i < position - 1; i++) {
				 helper = helper.next;
			 }
			 newNode.next = helper.next;
			 helper.next.previous = newNode;
			 helper.next = newNode;
			 newNode.previous = helper;
		 }
		 
		 size++;
		 modificationCount++;
	 }
	 
	 
	 @Override
	 /**
	  * @param value Element liste.
	  * @return poziciju na kojoj se nalazi traženi element, -1 ako se element ne nalazi u listi.
	  */
	 public int indexOf(Object value) {
		 ListNode<T> helper = first;
		 for(int i = 0; i < size; i++) {
			 if(helper.element.equals(value))
				 return i;
			 else
				 helper = helper.next;
		 }
		 
		 return -1;
	 }
	 
	 
	 @Override
	 /**
	  * @throws IndexOutOfBoundsException ako se kao argument preda broj manji od nula, 
	  * odnosno veći od veličine umanjene za 1.
	  */
	 public void remove(int index) {
		 if(index < 0 || index > size-1 )
				throw new IndexOutOfBoundsException("Argument index must be between 0 and (size - 1)");
		 
		 ListNode<T> helper = first;
		 for(int i = 0; i < index; i++) {
			 helper = helper.next;
		 }
		 
		 if(helper == this.first && helper == this.last) {
			 this.first = null;
			 this.last = null;
		 }else if(helper == this.first) {
			 this.first = helper.next;
			 helper.next.previous = null;
		 }else if(helper == this.last) {
			 helper.previous.next = null;
			 this.last = helper.previous;
		 }else {
			 helper.previous.next = helper.next;
			 helper.next.previous = helper.previous;
		 }
		  
		 size--;
		 modificationCount++;
	 }
	 

	 @Override
	 public int size() {
		 return size;
	 }
		
	 @Override
	 public boolean contains(Object value) {
		 return this.indexOf(value) != -1;
	 }
		
	 @Override
	 public boolean remove(Object value) {
		ListNode<T> helper = this.first;
		for(int i = 0; i < this.size; i++) {
			if(helper.element.equals(value)) {
				this.remove(i);
				this.modificationCount++;
				return true;
			}else {
				helper = helper.next;
			}	
		}
		return false;
	 }
		
	 @Override
	 public Object[] toArray() {
		 ListNode<T> helperNode = this.first;
		 Object[] helperArray = new Object[this.size];
		 for(int i = 0; i < this.size-1; i++) {
			 helperArray[i] = helperNode.element;
			 helperNode = helperNode.next;
		 }
		return helperArray;	
	 }
		

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new LLICElementsGetter<>(this);
	}
	
	/**
	 * Razred koji implementira sučelje ElementsGetter te metode prilagođava strukturi LinkedListIndexedCollection.
	 * @author teakr
	 *
	 */
	private static class LLICElementsGetter<T> implements ElementsGetter<T>{
		
		private ListNode<T> currentNode;
		private LinkedListIndexedCollection<T> linkedList;
		private long initialModificationCount;
		
		public LLICElementsGetter(LinkedListIndexedCollection<T> linkedList) {
			this.linkedList = linkedList;
			this.currentNode = linkedList.first;
			this.initialModificationCount = linkedList.modificationCount;
		}

		@Override
		public boolean hasNextElement() {
			if(this.initialModificationCount != linkedList.modificationCount)
				throw new ConcurrentModificationException("The structure of the original collection has changed!");
			
			return currentNode != null;			
		}

		@Override
		public T getNextElement() {
			if(!this.hasNextElement()) 
				throw new NoSuchElementException("No more elements in collection.");

			T helper;
			helper = currentNode.element;
			currentNode = currentNode.next;
			return helper;
		}
		
	}
}
