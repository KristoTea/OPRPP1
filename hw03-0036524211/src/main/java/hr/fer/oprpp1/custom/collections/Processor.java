package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje koje definira metodu koja nad predanim objektom odrađuje određene operacije.
 * @author teakr
 *
 */
public interface Processor<T> {
	
	/**
	 * Metoda procesira/obrađuje određeni objekt.
	 * 
	 * @param value Objekt koji će se procesirati
	 */
	public void process(T value);

}
