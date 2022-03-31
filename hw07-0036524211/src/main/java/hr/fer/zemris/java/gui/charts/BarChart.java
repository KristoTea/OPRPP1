package hr.fer.zemris.java.gui.charts;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred modelira objekt koji čuva sve 
 * informacije potrebne za oblikovanje grafa.
 * @author teakr
 *
 */
public class BarChart {
	
	private List<XYValue> xyValues = new ArrayList<>();
	private String xDescription;
	private String yDescription;
	private int minY;
	private int maxY;
	private int distance;
	
	/**
	 * Konstruktor.
	 * @param xyValues
	 * @param xDescription
	 * @param yDescription
	 * @param minY
	 * @param maxY
	 * @param distance
	 */
	public BarChart(List<XYValue> xyValues, String xDescription, String yDescription, int minY, int maxY, int distance) {
		if(minY < 0)
			throw new IllegalArgumentException("Minimal Y must be greater or equal 0.");
		
		if(minY >= maxY)
			throw new IllegalArgumentException("Minimal Y must be smaller than maximal.");
		
		for(XYValue xy : xyValues) {
			if(xy.getY() < minY)
				throw new IllegalArgumentException(xy.getY() + " is less than minimal y: " + minY);
		}
		
		while((maxY - minY) % distance != 0) {
			maxY++;
		}
		
		this.xyValues = xyValues;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = maxY;
		this.distance = distance;
	}


	public List<XYValue> getXyValues() {
		return xyValues;
	}


	public String getxDescription() {
		return xDescription;
	}


	public String getyDescription() {
		return yDescription;
	}


	public int getMinY() {
		return minY;
	}


	public int getMaxY() {
		return maxY;
	}


	public int getDistance() {
		return distance;
	}


}
