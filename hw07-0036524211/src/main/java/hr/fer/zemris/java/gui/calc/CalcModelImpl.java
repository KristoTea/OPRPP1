package hr.fer.zemris.java.gui.calc;

//import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Razred koji implementira funkcionalnosti sučelja CalcModel.
 * @author teakr
 *
 */
public class CalcModelImpl implements CalcModel{
	
	private boolean isEditable = true;
	private boolean isPositive = true;
	private String enteredNumberString = "";
	private double enteredNumberDouble = 0;
	private String frozenValue = null;
	
	private OptionalDouble activeOperand = OptionalDouble.empty();
	private DoubleBinaryOperator pendingOperator = null;
	
	private List<CalcValueListener> listeners = new ArrayList<>();
	private List<Double> stack = new ArrayList<>();
	
	//DecimalFormat df = new DecimalFormat("###.#");
	
	public List<Double> getStack() {
		return stack;
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);	
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return enteredNumberDouble;
	}

	@Override
	public void setValue(double value) {
		this.enteredNumberDouble = value;
		this.enteredNumberString = String.format("%f", value);
		this.isEditable = false;
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		this.enteredNumberDouble = 0;
		this.enteredNumberString = "";
		this.isPositive = true;
		this.isEditable = true;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		clear();
		this.frozenValue = null;
		this.activeOperand = OptionalDouble.empty();
		this.pendingOperator = null;
		this.stack = null;
		notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable)
			throw new CalculatorInputException("Model is not editable.");
		
		if(isPositive) {
			enteredNumberString = "-" + enteredNumberString;
		}else {
			enteredNumberString = enteredNumberString.replace("-", "");
		}
		
		enteredNumberDouble *= (-1.0); 
		
		isPositive = !isPositive;
		
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable)
			throw new CalculatorInputException("Model is not editable.");
		
		if(enteredNumberString.contains("."))
			throw new CalculatorInputException("Model already has decimal point.");
		
		if(enteredNumberString.equals(""))
			throw new CalculatorInputException("Empty string.");
		
		if(enteredNumberString.equals("-"))
			throw new CalculatorInputException("Minus.");
		
		enteredNumberString = enteredNumberString + ".";
		
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable)
			throw new CalculatorInputException("Model is not editable.");
		
		if(enteredNumberString.equals("0") && digit == 0 && !enteredNumberString.contains("."))return;
		
		if(enteredNumberString.length() >= 308)
			throw new CalculatorInputException("Too big number.");
		
		freezeValue(null);
		
		enteredNumberString += digit;
		
		try {
			enteredNumberDouble = Double.parseDouble(enteredNumberString);
		}catch(Exception e) {
			enteredNumberString = enteredNumberString.substring(0, enteredNumberString.length()-1);
			throw new CalculatorInputException("Number is not parsable.");
		}
		
		notifyListeners();
		
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand.isPresent();
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(activeOperand.isEmpty())
			throw new IllegalStateException();
		
		return activeOperand.getAsDouble();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = OptionalDouble.of(activeOperand);
		this.isEditable = false;
		notifyListeners();
	}

	@Override
	public void clearActiveOperand() {
		if(this.activeOperand.isPresent()) {
			this.activeOperand = OptionalDouble.empty();
		}
		this.isEditable = true;
		notifyListeners();
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperator;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperator = op;
		this.isEditable = true;
		notifyListeners();
	}
	
	
	@Override
	public void freezeValue(String value) {
		frozenValue = value;
		this.isEditable = false;
	}

	@Override
	public boolean hasFrozenValue() {
		return !frozenValue.isBlank();
	}
	
	/**
	 * Pomoćna privatna metoda, obavještava sve slušače o promjeni.
	 */
	private void notifyListeners() {
			for(CalcValueListener l : listeners)
				l.valueChanged(this);
	}

	@Override
	public String toString() {
		if(frozenValue != null)
			return frozenValue.replace(",", ".");
		
		if(enteredNumberString.equals("") || enteredNumberString.equals("0") || enteredNumberString.equals("-") || enteredNumberString.equals("+")) {
			if(isPositive) {
				return "0";
			}else {
				return "-0";
			}
		}
		
		if(enteredNumberString.startsWith("."))
			enteredNumberString = "0" + enteredNumberString;
		
		if(enteredNumberString.startsWith("-."))
			enteredNumberString = "-0" + enteredNumberString.substring(1);
		
		if(enteredNumberString.startsWith("0") && !(enteredNumberString.contains(",") || enteredNumberString.contains(".")))
			enteredNumberString = enteredNumberString.substring(1);
		
		enteredNumberString = enteredNumberString.replace(",", ".");
		
//		if(enteredNumberString.contains(".")) {
//			String help = enteredNumberString.
//		}
		//return String.valueOf(enteredNumberDouble);
		return enteredNumberString;
	}
	

}
