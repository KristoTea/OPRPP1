package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * Razred koji modelira ponašanje strukture mape.
 * @author teakr
 *
 * @param <K>
 * @param <V>
 */
public class Dictionary<K, V> {
	
	/**
	 * Privatni razred koji modelira jedan element unutra mape, tj.par ključ-vrijednost.
	 * @author teakr
	 *
	 * @param <S>
	 * @param <T>
	 */
	private static class DictionaryElement<S, T>{
		private S key;
		private T value;
		
		public DictionaryElement(S key, T value){
			this.key = Objects.requireNonNull(key);
			this.value = value;
		}
	}
	
	/**
	 * Parametrizirana struktura polja koja služi za pohranu elemenata mape.
	 */
	private ArrayIndexedCollection<DictionaryElement<K, V>> array;
	
	/**
	 * Konstruktor.
	 */
	public Dictionary() {
		this.array = new ArrayIndexedCollection<DictionaryElement<K, V>>();
	}
	
	/**
	 * Provjerava ima li Dictionary elemenata.
	 * @return true ako nema elemenata, false inače.
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Provjerava kolika je veličina Dictionary-a.
	 * @return broj elemenata spremljenih u dictionary.
	 */
	public int size() {
		return array.size();
	}
	
	/**
	 * Briše sve elemente.
	 */
	public void clear() {
		array.clear();
	}
	
	/**
	 * Strukturi dodaje novi element.
	 * @param key
	 * @param value
	 * @return Ako je u mapi već postojao ključ vraća staru vrijednost, inače null.
	 */
	public V put(K key, V value) {
		ElementsGetter<DictionaryElement<K, V>> iterator = array.createElementsGetter();
		while(iterator.hasNextElement()) {
			DictionaryElement<K, V> element = iterator.getNextElement();
			if(element.key.equals(key)) {
				V oldValue = element.value;
				element.value = value;
				return oldValue;
			}
		}
		array.add(new DictionaryElement<K, V>(key, value));
		return null;
	}

	/**
	 * Dohvaća vrijednost za traženi ključ.
	 * @param key
	 * @return vrijednost ako ključ postoji, inače null.
	 */
	public V get(Object key) {
		ElementsGetter<DictionaryElement<K, V>> iterator = array.createElementsGetter();
		while(iterator.hasNextElement()) {
			DictionaryElement<K, V> element = iterator.getNextElement();
			if(element.key.equals(key)) {
				return element.value;
			}
		}
		return null;
	}
	
	/**
	 * Uklanja element sa zadanim ključem.
	 * @param key
	 * @return vrijednost spremljenu pod tim ključem, ako traženi ključ nije postojao vraća null.
	 */
	public V remove(K key) {
		ElementsGetter<DictionaryElement<K, V>> iterator = array.createElementsGetter();
		while(iterator.hasNextElement()) {
			DictionaryElement<K, V> element = iterator.getNextElement();
			if(element.key.equals(key)) {
				V oldValue = element.value;
				this.array.remove(element);
				return oldValue;
			}
		}
		
		return null;
	}

}
