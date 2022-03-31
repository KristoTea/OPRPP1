package hr.fer.oprpp1.custom.collections;

/**
 * Sučeljem kojim modeliramo objekte koji prime neki objekt te ispitaju je li taj objekt prihvatljiv ili nije 
 * @author teakr
 *
 */
public interface Tester<T> {
	
	/**
	 * Apstraktna metoda sučelja, prima objekt te ispituje je li objekt prihvatljiv ili ne.
	 * @param obj Object koji se ispituje
	 * @return true ako je objekt prihvatljiv, inače false
	 */
	boolean test(T obj);

}
