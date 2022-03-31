package hr.fer.zemris.java.gui.charts;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.BasicStroke;

import javax.swing.JComponent;

/**
 *Razred koji obavlja iscrtavanje stupčastog grafa 
 *pomoću podataka dobivenih kroz konstruktor.
 * @author teakr
 *
 */
public class BarChartComponent extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BarChart bc;
	
	public BarChartComponent(BarChart bc) {
		this.bc = bc;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform pocetna = g2d.getTransform();
		
		
		int yMaxLength = Integer.toString(bc.getMaxY()).length();
		String format = "%" + yMaxLength + "d";
		int fontMetrics = g.getFontMetrics().getHeight();
		int leftLength = 20 * yMaxLength + 20;
		double distanceX = (1.0*(this.getHeight()-leftLength)/this.getHeight())/bc.getXyValues().size();
		
		g2d.setColor(Color.BLACK);
		g2d.drawString(bc.getxDescription(), (int) ((this.getWidth()/2) - (this.getWidth() * 0.07)), this.getHeight() -5);
		//leftLength + this.getWidth()*distanceX*(i+0.5)
		//(int)(leftLength - yMaxLength -20)
		//AffineTransform at = new AffineTransform(pocetna);
		//at.getQuadrantRotateInstance(3);
		//at.rotate(Math.PI/2);
		//g2d.setTransform(at);
		g2d.setTransform(AffineTransform.getQuadrantRotateInstance(3));
		g2d.setFont(new Font("Arial", Font.BOLD, fontMetrics));
		//g2d.drawString(bc.getyDescription(), (int)(-this.getHeight()/2 - this.getHeight()*0.1), (int)(leftLength/2 - yMaxLength));
		//g2d.drawString(bc.getyDescription(),(int)(-this.getHeight()/2), (int)(-(leftLength - yMaxLength - 20)/2));
		g2d.drawString(bc.getyDescription(), (int)(-this.getWidth()/2 - (this.getWidth() * 0.05)), (int)(leftLength/2 - yMaxLength));
		g2d.setTransform(pocetna);
		
		g2d.setFont(new Font("Arial", Font.BOLD, 12));
		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(leftLength, (int) (this.getHeight() - 40), 
				(int) (leftLength + bc.getXyValues().size()*distanceX*this.getWidth() + 10), (int) (this.getHeight() - 40));
		g2d.drawLine(leftLength, (int) (this.getHeight()*0.02), 
				leftLength, (int) (this.getHeight() - 40));
		
		int[] xPoints = new int[3];
		xPoints[0] = (int)(leftLength + bc.getXyValues().size()*distanceX*this.getWidth()+10);
		xPoints[1] = xPoints[0];
		xPoints[2] = xPoints[0] + 10;
		
		int[] yPoints = new int[3];
		yPoints[0] = (int) (this.getHeight() - 45);
		yPoints[1] = (int) (this.getHeight() - 35);
		yPoints[2] = (int) (this.getHeight() - 40);
		
		//g2d.drawPolygon(xPoints, yPoints, 3);
		g2d.fillPolygon(xPoints, yPoints, 3);

		xPoints[0] = leftLength - 5;
		xPoints[1] = leftLength + 5;
		xPoints[2] = leftLength;
		
		yPoints[0] = (int) (this.getHeight()*0.02);
		yPoints[1] = yPoints[0];
		yPoints[2] = (int) (this.getHeight()*0.02 - 10); 
		
		//g2d.drawPolygon(xPoints, yPoints, 3);
		g2d.fillPolygon(xPoints, yPoints, 3);
		
		for(int i = 0; i <= bc.getXyValues().size(); i++) {
			g2d.setColor(Color.GRAY);
			g2d.drawLine((int)(leftLength + this.getWidth()*distanceX*i), (int)(this.getHeight() - 40),
					(int)(leftLength + this.getWidth()*distanceX*i), (int)(this.getHeight() - 33));
			
			g2d.setColor(Color.BLACK);
			if(i < bc.getXyValues().size()) {
				g2d.drawString(String.format("%d", bc.getXyValues().get(i).getX()), 
						(int)(leftLength + this.getWidth()*distanceX*(i+0.5)), (int)(this.getHeight() - 25));
			}
			
			g2d.setColor(Color.ORANGE);
			if(i != 0) {
				g2d.drawLine((int)(leftLength + this.getWidth()*distanceX*i), (int)(this.getHeight() - 40),
						(int)(leftLength + this.getWidth()*distanceX*i), (int)(this.getHeight()*0.02));
			}
		}
		
		int j = 0;
		double d = (1.0*(this.getHeight() - 40)/this.getHeight()) / (bc.getMaxY() - bc.getMinY() +1);
		
		for(int i = bc.getMinY(); i < bc.getMaxY() + bc.getDistance(); i += bc.getDistance()) { //<=
			g2d.setColor(Color.GRAY);
			g2d.drawLine((int)(leftLength - 7), (int)(this.getHeight() -40 -j*d*this.getHeight()), 
					(int)(leftLength), (int)(this.getHeight() -40 -j*d*this.getHeight()));
			g2d.setColor(Color.BLACK);
			g2d.drawString(String.format(format, i), 
					(int)(leftLength - yMaxLength -20), (int)(this.getHeight() - 35 - d*j*this.getHeight()));
		
			g2d.setColor(Color.ORANGE);
			if(j != 0) {
				g2d.drawLine((int)(leftLength), 
						(int)(this.getHeight() -40 - j*d*this.getHeight()),
						(int)(leftLength + bc.getXyValues().size()*distanceX*this.getWidth() + 10), 
						(int)(this.getHeight() -40 - j*d*this.getHeight()));
			}
			j += bc.getDistance();
		}
		
		g2d.setColor(new Color(255,99,71));
		for(int i = 0; i < bc.getXyValues().size(); i++) {
			g2d.fillRect((int)(leftLength +2 + this.getWidth()*distanceX*i), 
					(int)(this.getHeight() - 40 - this.getHeight()*d*(bc.getXyValues().get(i).getY() - bc.getMinY())),
					(int)(this.getWidth()*distanceX - 2),
					(int)(this.getHeight()*d*(bc.getXyValues().get(i).getY() - bc.getMinY())));
			
		}
		
	
	}

	
}
