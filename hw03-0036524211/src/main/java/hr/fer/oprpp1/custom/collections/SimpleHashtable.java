package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Razred modelira strukturu hash tablice.
 * @author teakr
 *
 * @param <K>
 * @param <V>
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{
	
	private static final int DEFAULT_NO_SLOTS = 16;
	
	/**
	 * Statički ugnježđeni razred koji predstavlja element u tablici.
	 * @author teakr
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static class TableEntry<K,V>{
		private K key;
		private V value;
		private TableEntry<K,V> next;
		
		public TableEntry(K key, V value) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
			this.next = null;
		}
		
		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
		
		public K getKey() {
			return key;
		}

		@Override
		public String toString() {
			return this.key + "=" + this.value;
		}
		
	}
	
	/**
	 * Hash tablica.
	 */
	private TableEntry<K,V>[] table;
	
	/**
	 * Broj pohranjenih elemenata.
	 */
	private int size;
	
	/**
	 * Varijabla koja broji promjene nad strukturom podataka.
	 */
	private long modificationCount = 0;
	
	/**
	 * Defaultni konstruktor. Kreira tablicu s 16 slotova.
	 */
	public SimpleHashtable() {
		this(DEFAULT_NO_SLOTS);
	}
	
	/**
	 * Konstruktor s definiranim kapacitetom. Kreira tablicu sa zadanim brojem slotova.
	 * @param capacity
	 * @throws IllegalArgumentException ako je za kapacitet predana vrijednost manja od 1.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1)
			throw new IllegalArgumentException("Argument must be greater or equal 1.");
		
		double x = Math.log(capacity) / Math.log(2);
		if(x % 1 == 0) {
			this.table = new TableEntry[capacity];
		}else {
			this.table = new TableEntry[(int) Math.pow(2, Math.ceil(x))];
		}
		
		this.size = 0;
		this.table = (TableEntry<K,V>[]) this.table;
		
	}
	
	/**
	 * Metoda stavlja element u tablicu. Ako je postojao element s tim ključem, mijenja staru vrijednost u predanu.
	 * @param key ključ elementa koji dodajemo.
	 * @param value vrijednost koju dodajemo.
	 * @return ako je u tablici postojao element s tim ključem vraća postojeću vrijednost, inače null.
	 * @throws NullPointerException ako je za ključ predana null vrijednost.
	 */
	public V put(K key, V value) {
		if(key == null)
			throw new NullPointerException("Key must not be null.");
		
		if(this.size / table.length >= 0.75) {
			this.increaseCapacity();
		}
		
		return this.putElement(key, value);

	}
	
	/**
	 * Dohvaća vrijednost spremljenu pod predanim ključem.
	 * @param key
	 * @return vrijednost povezanu s ključem koji je predan ako postoji, inače null.
	 */
	public V get(Object key) {
		if(key == null)
			return null;
		
		int hash = Math.abs(key.hashCode()) % table.length;
		
		if(table[hash] == null)
			return null;
		
		TableEntry<K,V> element = table[hash];
		while(element != null) {
			if(element.key.equals(key))
				return element.value;
			element = element.next;
		}
		
		return null;
	}
	
	/**
	 * Vraća broj elemenata u tablici.
	 * @return
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Provjerava postoji li predani ključ u tablici.
	 * @param key
	 * @return true ako postoji, inače false.
	 */
	public boolean containsKey(Object key) {
		if(key == null)
			return false;
		
		int hash = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> element = table[hash];
		
		while(element != null) {
			if(element.key == key)
				return true;
			element = element.next;
		}
		
		return false;
	}
	
	/**
	 * Provjerava postoji li predana vrijednost u tablici.
	 * @param value
	 * @return true ako postoji, inače false.
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V>[] array = this.toArray();
		
		for(int i = 0; i < array.length; i++) {
			if(array[i].value.equals(value))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Uklanja element pod predanim ključem.
	 * @param key
	 * @return vrijednost koja je bila pod uklonjenim elementom, ako element nije postojao null.
	 */
	public V remove(Object key) {
		if(key == null)
			return null;
		
		int hash = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> element = table[hash];
		V oldValue = null;

		if(element.key.equals(key)) {
			oldValue = element.value;
			table[hash] = element.next;
			this.size--;
			this.modificationCount++;
			element = null;
		}else {
			while(element.next != null) {
				if(element.next.key.equals(key)) {
					oldValue = element.value;
					element.next = element.next.next;
					this.size--;
					this.modificationCount++;
					break;
				}
				element = element.next;
			}
		}
		
		return oldValue;
	}
	
	/**
	 * Provjerava je li tablica prazna.
	 * @return true ako je tablica prazna, inače false.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Cijelu hash tablicu zapisuje na potreban način.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		TableEntry<K, V>[] array = this.toArray();
		for(int i = 0; i < array.length; i++) {
			sb.append(array[i].toString() + ", ");
		}
		
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Pretvara cijelu tablicu u polje.
	 * @return polje s elementima tablice.
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray(){
		TableEntry<K,V>[] array = (TableEntry<K,V>[]) new TableEntry[this.size];
		int index = 0;
		
		for(int i = 0; i < table.length; i++) {
			TableEntry<K,V> element = table[i];
			while(element != null) {
				array[index] = element;
				index++;
				element = element.next;
			}
		}
		
		return array;
	}
	
	/**
	 * Uklanja sve elemente tablice.
	 */
	public void clear() {
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		
		modificationCount++;
		size = 0;	
	}
	
	/**
	 * Privatna metoda koja udvostrućuje veličnu tablice.
	 */
	@SuppressWarnings("unchecked")
	private void increaseCapacity() {
		TableEntry<K,V>[] array = this.toArray();
		this.clear();
		
		table = (TableEntry<K,V>[]) new TableEntry[table.length*2];
		
		for(int i = 0; i < array.length; i++) {
			this.putElement(array[i].key, array[i].value);
		}
	}
	
	/**
	 * Privatna metoda koja obavlja put metodu.
	 * @param key
	 * @param value
	 * @return
	 */
	private V putElement(K key, V value) {
		int hash = Math.abs(key.hashCode()) % table.length;
		
		if(table[hash] == null) {
			table[hash] = new TableEntry<K,V>(key, value);
			this.size++;
			this.modificationCount++;
			return null;
		}else {
			TableEntry<K,V> element = table[hash];
			while(element.next != null ||element.key.equals(key)) {
				if(element.key.equals(key)) {
					V oldValue = element.value;
					element.setValue(value);
					return oldValue;
				}
				element = element.next;
			}
			element.next = new TableEntry<>(key, value);
			this.size++;
			this.modificationCount++;
			return null;
		}
	}

	@Override
	public Iterator<SimpleHashtable.TableEntry<K,V>> iterator() {
		return new IteratorImpl(this);
	}

	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		private SimpleHashtable<K, V> hashTable;
		private TableEntry<K,V>[] array;
		private int index;
		private boolean calledNext;
		private long initialModificationCount;
		
		public IteratorImpl(SimpleHashtable<K, V> hashTable) {
			this.hashTable = hashTable;
			this.array = (TableEntry<K,V>[]) hashTable.toArray();
			this.index = 0;
			this.calledNext = false;
			this.initialModificationCount = hashTable.modificationCount;
		}
		
		public boolean hasNext() {
			if(initialModificationCount != hashTable.modificationCount)
				throw new ConcurrentModificationException("The structure of the original hashTable has changed!");
			
			return index < array.length;
		}
		
		public SimpleHashtable.TableEntry<K, V> next() {
			if(!this.hasNext()) 
				throw new NoSuchElementException("Iterator has returned all values already!");
			
			calledNext = true;
			return array[index++];
		}
		
		public void remove() {
			if(!this.calledNext) 
				throw new IllegalStateException("The next method has not yet been called");
			
			hashTable.remove(array[index-1].key);
			initialModificationCount++;
			calledNext = false;
		}
		
	}
	
	//pomoćna metoda samo kako bih ispravno testirala rad konstruktora
	/**
	 * Metoda vraća broj slotova tablice.
	 * @return broj slotova tablice.
	 */
	public int capacity() {
		return this.table.length;
	}
	

}
