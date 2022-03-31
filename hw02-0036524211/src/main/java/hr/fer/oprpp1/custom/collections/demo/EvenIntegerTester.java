package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;

/**
 * Razred koji nasljeđuje sučelje Tester, provjerava je li broj paran.
 * @author teakr
 *
 */
public class EvenIntegerTester implements Tester{

	@Override
	/**
	 * Metoda provjerava je li predani Object paran Integer.
	 * @param obj predani objekt
	 * @return true ako je Objekt paran broj, inače false
	 */
	public boolean test(Object obj) {
		if(!(obj instanceof Integer)) return false;
		Integer i = (Integer)obj;
		return i % 2 == 0;
	}

}
