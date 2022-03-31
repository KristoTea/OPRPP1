package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Razred koji modelira cjelokupan izgled i 
 * funkcionalnost te main program kalkulatora.
 * @author teakr
 *
 */
public class Calculator extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CalcModelImpl calcModel;
	private List<UnaryButton> unaryButtons;
	
	/**
	 * Konstruktor.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		calcModel = new CalcModelImpl();
		unaryButtons = new ArrayList<UnaryButton>();
		setSize(600, 300);
		initGUI();
		//pack();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		
		cp.add(screen(calcModel.toString()), new RCPosition(1,1));
		cp.add(button("=", CalcFunction.EQUALS), new RCPosition(1,6));
		cp.add(button("clr", calc -> calc.clear()), new RCPosition(1,7));
		cp.add(button("7", calc -> calc.insertDigit(7)), new RCPosition(2,3));
		cp.add(button("8", calc -> calc.insertDigit(8)), new RCPosition(2,4));
		cp.add(button("9", calc -> calc.insertDigit(9)), new RCPosition(2,5));
		cp.add(button("/", CalcFunction.binaryOperator((x,y) -> x / y)), new RCPosition(2,6));
		cp.add(button("reset", calc -> calc.clearAll()), new RCPosition(2,7));
		cp.add(button("4", calc -> calc.insertDigit(4)), new RCPosition(3,3));
		cp.add(button("5", calc -> calc.insertDigit(5)), new RCPosition(3,4));
		cp.add(button("6", calc -> calc.insertDigit(6)), new RCPosition(3,5));
		cp.add(button("*", CalcFunction.binaryOperator((x,y) -> x * y)), new RCPosition(3,6));
		cp.add(button("push", CalcFunction.PUSH), new RCPosition(3,7));
		cp.add(button("1", calc -> calc.insertDigit(1)), new RCPosition(4,3));
		cp.add(button("2", calc -> calc.insertDigit(2)), new RCPosition(4,4));
		cp.add(button("3", calc -> calc.insertDigit(3)), new RCPosition(4,5));
		cp.add(button("-", CalcFunction.binaryOperator((x,y) -> x - y)), new RCPosition(4,6));
		cp.add(button("pop", CalcFunction.POP), new RCPosition(4,7));
		cp.add(button("0", calc -> calc.insertDigit(0)), new RCPosition(5,3));
		cp.add(button("+/-", calc -> calc.swapSign()), new RCPosition(5,4));
		cp.add(button(".", calc -> calc.insertDecimalPoint()), new RCPosition(5,5));
		cp.add(button("+", CalcFunction.binaryOperator((x,y) -> x + y)), new RCPosition(5,6));
		
		unaryButtons = getListUnaryButtons();
		int j = 0;
		for (int i = 2; i <= 5; i++) {
			cp.add(unaryButtons.get(j++), new RCPosition(i,1));
			cp.add(unaryButtons.get(j++), new RCPosition(i,2));
		}
		
		JCheckBox checkBox = new JCheckBox("Inv");
		checkBox.addItemListener(l -> {
			for(UnaryButton ub : unaryButtons){
				ub.inverse();
			}
		});
		cp.add(checkBox, new RCPosition(5,7));
		
	}
	
	private JLabel screen(String text) {
		JLabel label = new JLabel(text);
		label.setBackground(Color.YELLOW);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		label.setBorder(border);
		label.setOpaque(true);
		//label.setFont(new Font("Arial", Font.BOLD, 18));
		label.setFont(label.getFont().deriveFont(30f));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		calcModel.addCalcValueListener(model -> label.setText(model.toString()));
		return label;
	}
	
	private JButton button(String text, ICalcFunction function) {
		JButton button = new JButton(text);
		//button.setFont(new Font("Arial", Font.ITALIC, 18));
		button.setFont(button.getFont().deriveFont(30));
		button.setBackground(Color.LIGHT_GRAY);
		button.setFont(getFont());
		if(function != null) {
			button.addActionListener(l -> function.execute(calcModel));
		}
		return button;
	}
	
	private List<UnaryButton> getListUnaryButtons(){
		List<UnaryButton> helper = new ArrayList<>();
		
		helper.add(new UnaryButton("1/x", "1/x",
			l -> CalcFunction.INVERSE.execute(calcModel), l -> CalcFunction.INVERSE.execute(calcModel)));
		helper.add(new UnaryButton("sin", "arcsin",
			l -> CalcFunction.SIN.execute(calcModel), l -> CalcFunction.ARCSIN.execute(calcModel)));
		helper.add(new UnaryButton("log", "10^x",
				l -> CalcFunction.LOG.execute(calcModel), l -> CalcFunction.EXP10.execute(calcModel)));
		helper.add(new UnaryButton("cos", "arccos",
				l -> CalcFunction.COS.execute(calcModel), l -> CalcFunction.ARCSIN.execute(calcModel)));
		helper.add(new UnaryButton("ln", "e^x",
				l -> CalcFunction.LN.execute(calcModel), l -> CalcFunction.EXPE.execute(calcModel)));
		helper.add(new UnaryButton("tan", "arctan",
				l -> CalcFunction.TAN.execute(calcModel), l -> CalcFunction.ARCTAN.execute(calcModel)));
		helper.add(new UnaryButton("x^n", "x^(1/n)",
				l -> CalcFunction.POW.execute(calcModel), l -> CalcFunction.INVPOW.execute(calcModel)));
		helper.add(new UnaryButton("ctg", "arcctg",
				l -> CalcFunction.CTG.execute(calcModel), l -> CalcFunction.ARCCTG.execute(calcModel)));
		
		return helper;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}

}
