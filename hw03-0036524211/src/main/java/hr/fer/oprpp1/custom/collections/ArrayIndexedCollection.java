package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Kolekcija objekata spremana u polje.
 * @author teakr
 *
 */
public class ArrayIndexedCollection<T> implements List<T>{
	
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * Broj elemenata trenutno u polju.
	 */
	private int size;
	
	/**
	 * Polje koje sadrži elemente kolekcije.
	 */
	private T[] elements;
	
	/**
	 * Varijabla koja broji broj promjena nad strukturom kolekcije.
	 */
	private long modificationCount = 0;

	/**
	 * Konstruktor kojim se kreira ArrayIndexedCollection veličine 16 elemenata.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 *  Konstruktor kojim se kreira ArrayIndexedCollection veličine zadane preko predanog parametra.
	 *  
	 * @param initialCapacity Inicijalna veličina polja koju želimo. 
	 * @throws IllegalArgumentException ako se za inicijalnu veličinu preda broj manji ili jednak nuli.
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity <= 0)
			throw new IllegalArgumentException("Initial capacity must be larger or equal 1");
			
		size = 0;
		elements = (T[])new Object[initialCapacity];
	}
	
	/**
	 * Konstruktor kojim se kreira ArrayIndexedCollection s elementima predane kolekcije.
	 * 
	 * @param other Kolekcija čiji će elementi biti dio ArrayIndexedCollection.
	 * @throws NullPointerException ako je predani argument prazna kolekcija.
	 */
	public ArrayIndexedCollection(Collection<? extends T> other) {
		this(other, DEFAULT_CAPACITY);
	}
	
	/**
	 * Konstruktor kojim se kreira ArrayIndexedCollection s elementima predane kolekcije i veličinom zadanom 
	 * preko initialCapacity. Ako je veličina predane količine veća od initialCapacity veličina
	 * ArrayIndexedCollection biti će postavljena na veličinu predane kolekcije.
	 * 
	 * @param other Kolekcija čiji će elementi biti dio ArrayIndexedCollection
	 * @param initialCapacity Inicijalna veličina ArrayIndexedCollection
	 * 
	 * @throws NullPointerException ako je predani argument prazna kolekcija.
	 * @throws IllegalArgumentException ako je za inicijalnu vrijednost predan broj manji ili jednak nula.
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends T> other, int initialCapacity) {
		if(other == null)
			throw new NullPointerException("Argument other must be non-null reference!");
		if(initialCapacity <= 0)
			throw new IllegalArgumentException("Initial capacity must be larger than 0!");
		
		size = other.size();
		
		if(initialCapacity < other.size()) {
			elements = (T[])new Object[other.size()];
		}else {
			elements = (T[])new Object[initialCapacity];
		}
			
		this.addAll(other);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @throws NullPointerException ako se kao parametar preda null pokazivač.
	 */
	public void add(T value) {
		if(value == null) 
			throw new NullPointerException("Argument value must be non-null reference!");
		
		if(size == elements.length) {
			T[] helper = (T[])new Object[size*2];
			for(int i = 0; i < size; i++) {
				helper[i] = elements[i];
			}
			elements = helper;
		}
		
		elements[size] = value;
		size++;
		modificationCount++;
	}

	@Override
	/**
	 * @throws IndexOutOfBoundsException ako kao index predamo broj manji od nule ili veći od veličine veličine kolekcije.
	 */
	public T get(int index) {
		if((index < 0) || (index > this.size-1))
			throw new IndexOutOfBoundsException("Argument index must be between 0 and (size - 1)");
		
		return elements[index];
	}
	
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
		modificationCount++;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @throws NullPointerException ako je predani element null pokazivač.
	 * @throws IndexOutOfBoundsException ako kao argument gdje želimo pozicionirati novi element predamo 
	 * broj manji od nule ili veći od veličine veličine kolekcije
	 */
	public void insert(T value, int position) {
		if(value == null) 
			throw new NullPointerException("Argument other must be non-null reference!");
		if(position < 0 || position > this.size-1)
			throw new IndexOutOfBoundsException("Argument position must be between 0 and (size - 1)");
		
		if(size == elements.length) {
			T[] helper = (T[])new Object[size*2];
			for(int i = 0; i < size; i++) {
				helper[i] = elements[i];
			}
			elements = helper;
		}
		
		for(int i = size; i > position; i--) {
			this.elements[i]=this.elements[i-1];
		}
		
		elements[position] = value;
		size++;
		modificationCount++;
	}
	
	@Override
	/**
	 * @throws NullPointerException ako se za vrijednost elementa preda null pokazivač.
	 */
	public int indexOf(Object value) {
		if(value == null)
			throw new NullPointerException("Argument value must be non-null reference!");
		
		for(int i = 0; i < this.size; i++) {
			if(elements[i].equals(value))
				return i;
		}
		
		return -1;
	}
	
	@Override
	/**
	 * @throws IndexOutOfBoundsException ako se kao argument preda broj manji od 
	 * nule i veći od veličine kolekcije smanjene za 1.
	 */
	public void remove(int index) {
		if(index < 0 || index > size - 1)
			throw new IndexOutOfBoundsException("Argument index must be between 0 and (size - 1)");
		
		for(int i = index; i < size-1; i++) {
			this.elements[i] = this.elements[i+1];
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
		return indexOf(value) != -1;
	}
	
	@Override
	public boolean remove(Object value) {
		if(indexOf(value) == -1)
			return false;
		
		this.remove(indexOf(value));
		modificationCount++;
		return true;
	}
	
	@Override
	public Object[] toArray() {
		Object[] helper = new Object[size];
		
		for(int i = 0; i < size; i++) {
			helper[i] = elements[i];
		}
		
		return helper;
	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new AICElementsGetter<>(this);
	}
	
	/**
	 * Razred koji implementira sučelje ElementsGetter te metode prilagođava strukturi ArrayIndexedCollection.
	 * @author teakr
	 *
	 */
	private static class AICElementsGetter<T> implements ElementsGetter<T>{
		
		private ArrayIndexedCollection<T> array;
		private int currentIndex = 0;
		private long initialModificationCount;
		
		public AICElementsGetter(ArrayIndexedCollection<T> array) {
			this.array = array;
			this.initialModificationCount = array.modificationCount;
		}

		@Override
		public boolean hasNextElement() {
			if(this.initialModificationCount != array.modificationCount)
				throw new ConcurrentModificationException("The structure of the original collection has changed!");
			
			return currentIndex < array.size;

		}

		@Override
		public T getNextElement() {
			if(!this.hasNextElement()) 
				throw new NoSuchElementException("No more elements in collection!");
		
			currentIndex++;
			return array.get(currentIndex-1);
		}
	
	}
	


}
