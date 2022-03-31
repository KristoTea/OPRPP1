package hr.fer.oprpp1.hw04.db;

/**
 * Razred koji nudi različite implementacije IFieldValueGetter
 * @author teakr
 *
 */
public class FieldValueGetters {
	
	public static final IFieldValueGetter FIRST_NAME = s -> s.getFirstName();
	public static final IFieldValueGetter LAST_NAME = s -> s.getLastName();
	public static final IFieldValueGetter JMBAG = s-> s.getJmbag();

}
