package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje, definira metodu accepts.
 * @author teakr
 *
 */
public interface IFilter {
	
	/**
	 * Metoda provjera prihvaća li predani podatak određeni izraz iz podupita.
	 * @param record primljeni zapis o studentu.
	 * @return true ako prihvaća, false inače.
	 */
	public boolean accepts(StudentRecord record);

}
