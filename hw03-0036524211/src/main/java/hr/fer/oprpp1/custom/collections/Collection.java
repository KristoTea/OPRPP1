package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje koje predstavlja generalnu kolekciju objekata.
 * 
 * @author teakr
 *
 */
public interface Collection<T> {

	/**
	 * Metoda provjerava je li kolekcija prazna.
	 * @return 
	 * 
	 * @return true ako je kolekcija ne sadrži elemente, inače false
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Metoda provjerava veličinu kolekcije, tj. od koliko se elemenata kolekcija sastoji.
	 * 
	 * @return broj elemenata u kolekciji
	 */
	int size();
	
	/**
	 * Metoda prima Objekt value i dodaje ga kolekciji.
	 * 
	 * @param value Objekt koji se dodaje kolekciji.
	 */
	void add(T value);
	
	/**
	 * Metoda provjerava pripada li primljeni Objekt value kolekciji.
	 * 
	 * @param value Objekt kojem provjerava postojanje unutar kolekcije.
	 * @return true ako predani objekt postoji u kolekciji, inače false
	 */
	boolean contains(Object value);
	
	/**
	 * Metoda uklanja primljeni Objekt value iz kolekcije.
	 * 
	 * @param value Objekt koji se uklanja iz kolekcije.
	 * @return true ako je objekt postojao u kolekciji i uspješno je uklonjen, inače false
	 */
	boolean remove(Object value);
	
	/**
	 * Metoda stvara novo polje čija veličina odgovara veličini kolekcije, popunjava ga elementima kolekcije
	 * te vraća novostvoreno polje.
	 * 
	 * @return polje sačinjeno od elemenata kolekcije.
	 */
	Object[] toArray();
	
	/**
	 * Metoda za svaki element kolekcije poziva Processor.process za svaki element kolekcije.
	 * 
	 * @param processor Objekt razreda Processor.
	 */
	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> getter = this.createElementsGetter();
		while(getter.hasNextElement()) {
			T helper = getter.getNextElement();
			processor.process(helper);
		}
	}
	
	/**
	 * Metoda trenutnoj kolekciji dodaje sve elemente iz predane other kolekcije.
	 * 
	 * @param other Kolekcija koja se dodaje postojećoj.
	 */
	default void addAll(Collection<? extends T> other) {
		
		/**
		 * Razred koji dodaje elemente u kolekciju, nasljeđuje razred Processor.
		 * @author teakr
		 *
		 */
		class MyAddProcessor<R extends T>  implements Processor<R>{
			
			@Override
			public void process(R value) {
				add(value);
			}
		}
		other.forEach(new MyAddProcessor<T>());
	}
	
	/**
	 * Metoda uklanja sve elemente iz kolekcije.
	 */
	void clear();
	
	/**
	 * Metoda ne prima ništa, a vraća referencu na novostvoreni objekt tipa ElementsGetter
	 * @return
	 */
	ElementsGetter<T> createElementsGetter();
	
	/**
	 * Metoda dohvaća redom sve elemente iz predane kolekcije te u trenutnu kolekciju 
	 * na kraj dodaje sve elemente koje je predani tester prihvatio.
	 * @param col Kolekcija čiji se elementi provjeravaju.
	 * @param tester Tester kojim ćemo testirati elemente predane kolekcije.
	 */
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> getter = col.createElementsGetter();
		
		while(getter.hasNextElement()) {
			T helper = getter.getNextElement();
			if(tester.test(helper)) 
				this.add(helper);
		}
	}

}
