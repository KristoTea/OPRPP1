package hr.fer.zemris.java.gui.layouts;

import java.awt.Container;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class DemoFrame2  extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DemoFrame2() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		initGUI();
		//pack();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		cp.add(l("1"), new RCPosition(1,1));
		cp.add(l("2"), new RCPosition(1,6));
		cp.add(l("3"), new RCPosition(1,7));
		
		cp.add(l("1"), new RCPosition(2,1));
		cp.add(l("2"), new RCPosition(2,2));
		cp.add(l("3"), new RCPosition(2,3));
		cp.add(l("4"), new RCPosition(2,4));
		cp.add(l("5"), new RCPosition(2,5));
		cp.add(l("6"), new RCPosition(2,6));
		cp.add(l("7"), new RCPosition(2,7));
		
		cp.add(l("1"), new RCPosition(3,1));
		cp.add(l("2"), new RCPosition(3,2));
		cp.add(l("3"), new RCPosition(3,3));
		cp.add(l("4"), new RCPosition(3,4));
		cp.add(l("5"), new RCPosition(3,5));
		cp.add(l("6"), new RCPosition(3,6));
		cp.add(l("7"), new RCPosition(3,7));
	}
	
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new DemoFrame2().setVisible(true);
		});
	}


}
