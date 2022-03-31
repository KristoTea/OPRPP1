package hr.fer.oprpp1.custom.collections;

/**
 * Kolekcija objekata spremanih u povezanu listu.
 * 
 * @author teakr
 *
 */
public class LinkedListIndexedCollection extends Collection{

	/**
	 * Privatna klasa koja predstavlja jedan čvor u povezanoj listi.
	 * 
	 * @author teakr
	 *
	 */
	 private static class ListNode{
		 private ListNode previous;
		 private ListNode next;
		 private Object element;
	 }
	 
	 /**
	  * Trenutni broj elemenata u polju.
	  */
	 private int size;
	 
	 /**
	  * Prvi čvor liste.
	  */
	 private ListNode first;
	 
	 /**
	  * Zadnji čvor liste.
	  */
	 private ListNode last;
	 
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
	 public LinkedListIndexedCollection(Collection other) {
		 this.first = null;
		 this.last = null;
		 this.addAll(other);
	 }
	 
	 @Override
	 /**
	  * @throws NullPointerException ako se kao argument preda null pokazivač.
	  */
	 void add(Object value) {
		 if(value == null) 
				throw new NullPointerException("Argument value must be non-null reference!");
		 
		 ListNode node = new ListNode();
		 node.element = value;
		 
		 if(this.first == null && this.last == null) {
			 this.first = node;
			 this.last = node;
		 }else {
			 node.previous = this.last;
			 node.next = null;
			 this.last.next = node;
			 this.last = node;
		 }
			 
		 this.size++;
	 }
	 
	 /**
	  * Metoda vraća element u listi na zadanom indexu.
	  * 
	  * @param index Indeks elementa kojeg želimo.
	  * @return element na traženom indexu.
	  * 
	  * @throws IndexOutOfBoundsException ako se kao argument preda broj manji od nula, 
	  * odnosno veći od veličine umanjene za 1.
	  */
	 Object get(int index) {
		 if(index < 0 || index > this.size-1) 
			 throw new IndexOutOfBoundsException("Argument index must be between 0 and (size - 1)");

		 ListNode helper = null;
		 
		 if(index < this.size) {
			 helper = this.first;
			 for(int i = 0; i < index; i++) {
				 helper = helper.next;
			 }
			 return helper.element;
		 }else {
			 helper = this.last;
			 for(int i = this.size-1; i > index; i--) {
				 helper = helper.previous;
			 }
			 return helper.element;
		 }				 
	 }
	 
	 @Override
	 void clear() {
		 this.size = 0;
		 this.first = null;
		 this.last = null;
	 }
	 
	 /**
	  * Meroda dodaje predani objekt na traženu poziciju u listi.
	  * 
	  * @param value Element koji želimo dodati u listu.
	  * @param position Mjesto u listi na koje želimo staviti novi element.
	  * 
	  * @throws NullPointerException ako se kao prvi argument preda null pokazivač.
	  * @throws IndexOutOfBoundsException ako se kao argument preda broj manji od nula, 
	  * odnosno veći od veličine umanjene za 1.
	  */
	 void insert(Object value, int position) {
		 if(value == null) 
				throw new NullPointerException("Argument value must be non-null reference!");
		if(position < 0 || position > size)
				throw new IndexOutOfBoundsException("Argument position must be between 0 and (size - 1)");
		 
		 ListNode newNode = new ListNode();
		 newNode.element = value;
		 
		 if(position == 0) {
			 newNode.next = this.first;
			 newNode.previous = null;
			 this.first = newNode;
		 }else if(position == this.size-1) {
			 newNode.next = null;
			 newNode.previous = this.last;
			 this.last = newNode;
		 }else {
			 ListNode helper = this.first;
			 for(int i = 0; i < position - 1; i++) {
				 helper = helper.next;
			 }
			 newNode.next = helper.next;
			 helper.next.previous = newNode;
			 helper.next = newNode;
			 newNode.previous = helper;
		 }
		 
		
		 this.size++;
	 }
	 
	 /**
	  * Metoda vraća indeks na kojem se nalazi predani element.
	  * 
	  * @param value Element liste.
	  * @return poziciju na kojoj se nalazi traženi element, -1 ako se element ne nalazi u listi.
	  */
	 int indexOf(Object value) {
		 ListNode helper = this.first;
		 for(int i = 0; i < this.size; i++) {
			 if(helper.element.equals(value))
				 return i;
			 else
				 helper = helper.next;
		 }
		 
		 return -1;
	 }
	 
	 /**
	  * Metoda uklanja element sa zadane pozicije.
	  * 
	  * @param index Pozicija na kojoj se nalazi element kojeg želimo ukloniti.
	  * 
	  * @throws IndexOutOfBoundsException ako se kao argument preda broj manji od nula, 
	  * odnosno veći od veličine umanjene za 1.
	  */
	 void remove(int index) {
		 if(index < 0 || index > size-1 )
				throw new IndexOutOfBoundsException("Argument index must be between 0 and (size - 1)");
		 
		 ListNode helper = this.first;
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
	 }
	 

	 @Override
	 int size() {
		 return size;
	 }
		
	 @Override
	 boolean contains(Object value) {
		 return this.indexOf(value) != -1;
	 }
		
	 @Override
	 boolean remove(Object value) {
		ListNode helper = first;
		for(int i = 0; i < size; i++) {
			if(helper.element.equals(value)) {
				this.remove(i);
				return true;
			}else {
				helper = helper.next;
			}	
		}
		return false;
	 }
		
	 @Override
	 Object[] toArray() {
		 ListNode helperNode = this.first;
		 Object[] helperArray = new Object[size];
		 for(int i = 0; i < size-1; i++) {
			 helperArray[i] = helperNode.element;
			 helperNode = helperNode.next;
		 }
		return helperArray;	
	 }
		
	 @Override
	 void forEach(Processor processor) {
		 ListNode helper = first;
		 for(int i = 0; i < size-1; i++) {
			 processor.process(helper.element);
			 helper = helper.next;
		 }
	 }
	 
}
