package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Razred modelira gumb kalkulatora za koji vežemo 
 * mogućnost izvođenja normalne i inverzne funkcije.
 * @author teakr
 *
 */
public class UnaryButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String normalName;
	private String inverseName;
	private ActionListener normalOperation;
	private ActionListener inverseOperation;
	
	/**
	 * Konstruktor.
	 * @param normalName
	 * @param inverseName
	 * @param normalOperation
	 * @param inverseOperation
	 */
	public UnaryButton(String normalName, String inverseName, ActionListener normalOperation,
			ActionListener inverseOperation) {
		super(normalName);
		this.normalName = normalName;
		this.inverseName = inverseName;
		this.normalOperation = normalOperation;
		this.inverseOperation = inverseOperation;
		
		this.setBackground(Color.LIGHT_GRAY);
		
		this.addActionListener(normalOperation);
	}

	/**
	 * Funkcija koja prebacuje funkciju vezanu uz gumb s normalne u inverznu i obrnuto.
	 */
	public void inverse() {
		if(this.getActionListeners()[0].equals(normalOperation)) {
			this.removeActionListener(normalOperation);
			this.addActionListener(inverseOperation);
			this.setText(inverseName);
		}else {
			this.removeActionListener(inverseOperation);
			this.addActionListener(normalOperation);
			this.setText(normalName);
		}
	}
	
}
