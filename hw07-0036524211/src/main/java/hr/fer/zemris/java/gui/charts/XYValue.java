package hr.fer.zemris.java.gui.charts;

/**
 * Razred modelira par x i y vrijednosti.
 * @author teakr
 *
 */
public class XYValue {
	
	private int x;
	private int y;
	
	/**
	 * Konstruktor.
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}


}
