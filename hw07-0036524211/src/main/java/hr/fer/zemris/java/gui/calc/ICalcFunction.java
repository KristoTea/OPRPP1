package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Sučelje modelira jednu funkciju/operaciju kalkulatora.
 * @author teakr
 *
 */
public interface ICalcFunction {
	
	/**
	 * Izvođenje određene funkcije nad predanim vrijenostima.
	 * @param model
	 */
	void execute(CalcModel model);

}
