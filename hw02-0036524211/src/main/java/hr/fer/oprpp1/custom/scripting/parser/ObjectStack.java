package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Razred koji stvara kolekciju nalik stogu.
 * @author teakr
 *
 */
public class ObjectStack {
	
	/**
	 * 
	 */
	private ArrayIndexedCollection stackData;
	
	/**
	 * Prazni konstruktor.
	 */
	public ObjectStack() {
		this.stackData = new ArrayIndexedCollection();
	}
	
	/**
	 * Metoda provjerava je li stog prazan.
	 * @return True ako je stog prazan, inače False.
	 */
	public boolean isEmpty() {
		return stackData.isEmpty();
	}
	
	/**
	 * metoda koja provjerava veličinu stoga.
	 * @return broj elemenata na stogu
	 */
	public int size() {
		return stackData.size();
	}
	
	/**
	 * Metoda dodaje element na stog.
	 * @param value vrijednost elementa koju dodajemo stogu
	 */
	public void push(Object value) {
		stackData.add(value);
	}
	
	/**
	 * Metoda uklanja zadnji element na stogu.
	 * @return element koji je uklonjen sa stoga.
	 * @throws EmptyStackException u slučaju da želimo ukloniti element sa stoga, a stog je prazan.
	 */
	public Object pop() {
		if(this.size() == 0)
			throw new EmptyStackException("Can't pop any Object, the stack is empty");
		Object last = this.stackData.get(this.size()-1);
		this.stackData.remove(this.size()-1);
		return last;
	}
	
	/**
	 * Provjerava koji je zadnji element na stogu.
	 * @return zadnji element na stogu.
	 * @throws EmptyStackException u slučaju da želimo ukloniti element sa stoga, a stog je prazan.
	 */
	public Object peek() {
		if(this.isEmpty())
			throw new EmptyStackException("Can't pop any Object, the stack is empty");
		Object last = this.stackData.get(stackData.size()-1);
		return last;
	}
	
	/**
	 * Metoda uklanja sve elemente sa stoga.
	 */
	public void clear() {
		stackData.clear();
	}

}
