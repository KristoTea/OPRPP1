package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje koje definira metodu koja nad predanim objektom odrađuje određene operacije.
 * @author teakr
 *
 */
public interface Processor {
	
	/**
	 * Metoda procesira/obrađuje određeni objekt.
	 * 
	 * @param value Objekt koji će se procesirati
	 */
	public void process(Object value);

}
