package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Razred modelira ograničenja, tj. poziciju
 * komponenti unutar cijelog GUI-a.
 * Čuva x i y vrijednosti pozicije.
 * 
 * @author teakr
 *
 */
public class RCPosition {
	
	private int row;
	private int column;
	
	
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}


	public int getRow() {
		return row;
	}


	public int getColumn() {
		return column;
	}
	
	
	@Override
	public String toString() {
		return this.getRow() + "," + this.getColumn();
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}


	/**
	 * Javna statička metoda koja za pročitani 
	 * tekst vraća RCPosition objekt. U slučaju
	 * da pročitani tekst nije kompatibilan s 
	 * RCPosition baca IllegalArgumentException.
	 * @param text
	 * @return RCPosition koju je predstavljao ulazni tekst.
	 * @throws IllegalArgumentException ako ulazni tekst 
	 * nije moguće parsiratu u RCPosition objekt. 
	 */
	public static  RCPosition parse(String text) {
		String[] helper = text.split(",");
		if(helper.length != 2)
			throw new IllegalArgumentException("Wrong input!");
		
		int r;
		int c;
		
		try {
			r = Integer.parseInt(helper[0].trim());
			c = Integer.parseInt(helper[1].trim());
		}catch(Exception e) {
			throw new IllegalArgumentException("Wrong input!");
		}
		
		return new RCPosition(r, c);
	}

}
