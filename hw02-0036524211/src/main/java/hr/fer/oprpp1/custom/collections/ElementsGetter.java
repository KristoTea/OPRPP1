package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje koje definira novu vrstu objekta ElementsGetter 
 * čija je zadaća vraćati element po element na korisnikov zahtjev.
 * @author teakr
 *
 */
public interface ElementsGetter {
	
	/**
	 * Metoda provjerava ima li preostalih elemenata u kolekciji.
	 * @return true ako postoji objekt zadnjeg pregledanog, inače false.
	 */
	boolean hasNextElement();
	
	/**
	 * Metoda dohvaća sljedeći element u kolekciji.
	 * @return sljedeći element kolekcije.
	 */
	Object getNextElement();
	
	/**
	 * Metoda nad svim preostalim elementima kolekcije poziva predani processor
	 * @param p processor koji će se pozvati nad svakim preostalim elementom.
	 */
	default void processRemaining(Processor p) {
		while(this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}

}
