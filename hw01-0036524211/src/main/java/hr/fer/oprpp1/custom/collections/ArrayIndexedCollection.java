package hr.fer.oprpp1.custom.collections;

/**
 * Kolekcija objekata spremana u polje.
 * @author teakr
 *
 */
public class ArrayIndexedCollection extends Collection{
	
	/**
	 * Broj elemenata trenutno u polju.
	 */
	private int size;
	
	/**
	 * Polje koje sadrži elemente kolekcije.
	 */
	private Object[] elements;
	
	private static final int DEFAULT_CAPACITY = 16;

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
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity <= 0)
			throw new IllegalArgumentException("Initial capacity must be larger or equal 1");
			
		size = 0;
		elements = new Object[initialCapacity];
	}
	
	/**
	 * Konstruktor kojim se kreira ArrayIndexedCollection s elementima predane kolekcije.
	 * 
	 * @param other Kolekcija čiji će elementi biti dio ArrayIndexedCollection.
	 * @throws NullPointerException ako je predani argument prazna kolekcija.
	 */
	public ArrayIndexedCollection(Collection other) {
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
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if(other == null)
			throw new NullPointerException("Argument other must be non-null reference!");
		if(initialCapacity <= 0)
			throw new IllegalArgumentException("Initial capacity must be larger than 0");
		
		size = 0;
		
		if(initialCapacity < other.size()) {
			elements = new Object[other.size()];
		}else {
			elements = new Object[initialCapacity];
		}
		
		this.addAll(other);
	}
	
	@Override
	/**
	 * @throws NullPointerException ako se kao parametar preda null pokazivač.
	 */
	void add(Object value) {
		if(value == null) 
			throw new NullPointerException("Argument value must be non-null reference!");
		
		if(size == elements.length) {
			Object[] helper = new Object[size*2];
			for(int i = 0; i < size; i++) {
				helper[i] = elements[i];
			}
			this.elements = helper;
		}
		
		elements[size] = value;
		size++;
	}

	/**
	 * Metoda pronalazi objekt na zadanom indeksu.
	 * 
	 * @param index pozicija za koju provjeravamo element.
	 * @return objekt na primljenoj poziciji.
	 * 
	 * @throws IndexOutOfBoundsException ako kao index predamo broj manji od nule ili veći od veličine veličine kolekcije.
	 */
	Object get(int index) {
		if((index < 0) && (index > size-1))
			throw new IndexOutOfBoundsException("Argument index must be between 0 and (size - 1)");

		return elements[index];
	}
	
	@Override
	void clear() {
		for(int i = 0; i < this.size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Metoda dodaje novi element na određenu poziciju.
	 * 
	 * @param value Vrijednost elementa kojeg želimo dodati u polje.
	 * @param position Pozicija na koju želimo dodati novi element.
	 * 
	 * @throws NullPointerException ako je predani element null pokazivač.
	 * @throws IndexOutOfBoundsException ako kao argument gdje želimo pozicionirati novi element predamo 
	 * broj manji od nule ili veći od veličine veličine kolekcije
	 */
	void insert(Object value, int position) {
		if(value == null) 
			throw new NullPointerException("Argument other must be non-null reference!");
		if(position < 0 || position > size-1)
			throw new IndexOutOfBoundsException("Argument position must be between 0 and (size - 1)");
		
		if(size == elements.length) {
			Object[] helper = new Object[size*2];
			for(int i = 0; i < size; i++) {
				helper[i] = elements[i];
			}
			this.elements = helper;
		}
		
		for(int i = this.size; i > position; i--) {
			this.elements[i] = this.elements[i-1];
		}
		
		elements[position] = value;
		size++;
	}
	
	/**
	 * Metoda vraća poziciju na kojoj se nalazi predani element.
	 * 
	 * @param value Vrijednost elementa kojeg tražimo u listi.
	 * @return -1 ako se predani element ne nalazi u listi, inače poziciju na kojoj se predani element nalazi.
	 * 
	 * @throws NullPointerException ako se za vrijednost elementa preda null pokazivač.
	 */
	int indexOf(Object value) {
		if(value == null)
			throw new NullPointerException("Argument value must be non-null reference!");
		
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value))
				return i;
		}
		return -1;
	}
	
	/**
	 * Metoda uklanja element s predane pozicije.
	 * 
	 * @param index Pozicija s koje želimo ukloniti element.
	 * 
	 * @throws IndexOutOfBoundsException ako se kao argument preda broj manji od 
	 * nule i veći od veličine kolekcije smanjene za 1.
	 */
	void remove(int index) {
		if(index < 0 || index > size - 1)
			throw new IndexOutOfBoundsException("Argument index must be between 0 and (size - 1)");
		
		for(int i = index; i < size-1; i++) {
			elements[i] = elements[i+1];
		}
		size--;
	}
	
	@Override
	int size() {
		return this.size;
	}
	
	@Override
	boolean contains(Object value) {
		return this.indexOf(value) != -1;
	}
	
	@Override
	boolean remove(Object value) {
		if(!contains(value))
			return false;
		
		this.remove(this.indexOf(value));
		return true;
	}
	
	@Override
	Object[] toArray() {
		Object[] helper = new Object[size];
		
		for(int i = 0; i < size; i++) {
			helper[i] = elements[i];
		}
		
		return helper;
	}
	
	@Override
	void forEach(Processor processor) {
		for(int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Metoda vraća ukupnu veličinu zauzet prostor za stvaranje kolekcije.
	 * @return veličina zauzetog prostora
	 */
	public int getFullSize() {
		return elements.length;
	}

}
