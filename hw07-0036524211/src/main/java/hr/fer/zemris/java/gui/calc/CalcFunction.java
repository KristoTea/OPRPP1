package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Pomoćni razred, implementira sve funkcije koje kalkulator pokriva te može obavljati..
 * @author teakr
 *
 */
public class CalcFunction{
	
	public static final ICalcFunction EQUALS = (model) -> {
		if(model.isActiveOperandSet()) {
			model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
			model.freezeValue(String.format("%f", model.getValue()));
			model.clearActiveOperand();
		}
	};
	
	public static final ICalcFunction INVERSE = (model) -> model.setValue(1.0/model.getValue());
	public static final ICalcFunction SIN = (model) -> model.setValue(Math.sin(model.getValue()));
	public static final ICalcFunction ARCSIN = (model) -> model.setValue(Math.asin(model.getValue()));
	public static final ICalcFunction LOG = (model) -> model.setValue(Math.log10(model.getValue()));
	public static final ICalcFunction EXP10 = (model) -> model.setValue(Math.pow(10, model.getValue()));
	public static final ICalcFunction COS = (model) -> model.setValue(Math.cos(model.getValue()));
	public static final ICalcFunction ARCCOS = (model) -> model.setValue(Math.acos(model.getValue()));
	public static final ICalcFunction LN = (model) -> model.setValue(Math.log(model.getValue()));
	public static final ICalcFunction EXPE = (model) -> model.setValue(Math.exp(model.getValue()));
	public static final ICalcFunction TAN = (model) -> model.setValue(Math.tan(model.getValue()));
	public static final ICalcFunction ARCTAN = (model) -> model.setValue(Math.atan(model.getValue()));
	public static final ICalcFunction CTG = (model) -> model.setValue(1.0/Math.tan(model.getValue()));
	public static final ICalcFunction ARCCTG = (model) -> model.setValue(Math.atan(1.0/model.getValue()));
	public static final ICalcFunction POW = (model) -> {
		model.setActiveOperand(model.getValue());
		model.freezeValue(String.format("%f", model.getActiveOperand()));
		model.setPendingBinaryOperation((x, n) -> Math.pow(x, n));
		model.clear();
	};
	public static final ICalcFunction INVPOW = (model) -> {
		model.setActiveOperand(model.getValue());
		model.freezeValue(String.format("%f", model.getActiveOperand()));
		model.setPendingBinaryOperation((x, n) -> Math.pow(x, 1.0/n));
		model.clear();
	};

	public static final ICalcFunction binaryOperator(DoubleBinaryOperator operator) {
		ICalcFunction helper = (model) -> {
			if(!model.isActiveOperandSet()) {
				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation(operator);
				model.freezeValue(String.format("%f", model.getActiveOperand()));
				model.clear();
			}else {
				model.setActiveOperand(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				model.setPendingBinaryOperation(operator);
				model.freezeValue(String.format("%f", model.getActiveOperand()));
				model.clear();
			}
		};
		
		return helper;
	}
	
	public static final ICalcFunction POP = (model) -> {
		if(model.getStack().size() == 0) {
			model.freezeValue("Empty stack.");
			model.clear();
		}
		model.setValue(model.getStack().remove(model.getStack().size() - 1));
	};
	
	public static final ICalcFunction PUSH = (model) -> model.getStack().add(model.getValue());
}
