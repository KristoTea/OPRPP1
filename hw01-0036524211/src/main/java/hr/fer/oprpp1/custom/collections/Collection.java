package hr.fer.oprpp1.custom.collections;

/**
 * Razred koji predstavlja generalnu kolekciju objekata.
 * 
 * @author teakr
 *
 */
public class Collection {

	/**
	 * Metoda provjerava je li kolekcija prazna.
	 * 
	 * @return true ako je kolekcija ne sadrži elemente, inače false
	 */
	boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Metoda provjerava veličinu kolekcije, tj. od koliko se elemenata kolekcija sastoji.
	 * 
	 * @return broj elemenata u kolekciji
	 */
	int size() {
		return 0;
	}
	
	/**
	 * Metoda prima Objekt value i dodaje ga kolekciji.
	 * 
	 * @param value Objekt koji se dodaje kolekciji.
	 */
	void add(Object value) {
		
	}
	
	/**
	 * Metoda provjerava pripada li primljeni Objekt value kolekciji.
	 * 
	 * @param value Objekt kojem provjerava postojanje unutar kolekcije.
	 * @return true ako predani objekt postoji u kolekciji, inače false
	 */
	boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Metoda uklanja primljeni Objekt value iz kolekcije.
	 * 
	 * @param value Objekt koji se uklanja iz kolekcije.
	 * @return true ako je objekt postojao u kolekciji i uspješno je uklonjen, inače false
	 */
	boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Metoda stvara novo polje čija veličina odgovara veličini kolekcije, popunjava ga elementima kolekcije
	 * te vraća novostvoreno polje.
	 * 
	 * @return polje sačinjeno od elemenata kolekcije.
	 */
	Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Metoda za svaki element kolekcije poziva Processor.process za svaki element kolekcije.
	 * 
	 * @param processor Objekt razreda Processor.
	 */
	void forEach(Processor processor) {
		
	}
	
	/**
	 * Metoda trenutnoj kolekciji dodaje sve elemente iz predane other kolekcije.
	 * 
	 * @param other Kolekcija koja se dodaje postojećoj.
	 */
	void addAll(Collection other) {
		
		/**
		 * Razred koji dodaje elemente u kolekciju, nasljeđuje razred Processor.
		 * @author teakr
		 *
		 */
		class MyAddProcessor extends Processor{
			
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new MyAddProcessor());
	}
	
	/**
	 * Metoda uklanja sve elemente iz kolekcije.
	 */
	void clear() {
		
	}

}
