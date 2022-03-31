package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementacija uređivača razmještaja za kalkulator.
 * @author teakr
 *
 */
public class CalcLayout implements LayoutManager2{
	
	/**
	 * Varijabla predstavlja razmak između komponenti.
	 */
	private int space;
	
	/**
	 * Mapa povezanih komponenti s njihovim ograničenjima, tj. pozicijama.
	 */
	private Map<RCPosition, Component> components;
	
	/**
	 * Prazni konstruktor, bez razmaka između elemenata.
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * Konstruktor.
	 * @param space razmak izemđu komponenti.
	 */
	public CalcLayout(int space) {
		this.space = space;
		components = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		RCPosition position = null;
		
		for(RCPosition p : components.keySet()) {
			if(components.get(p).equals(comp)) {
				position = p;
				break;
			}
		}
		
		components.remove(position);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize((c) -> c.getPreferredSize(), parent);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize((c) -> c.getMinimumSize(), parent);
	}

	@Override
	public void layoutContainer(Container parent) {
		
		int compWidth = (int) ((parent.getWidth() - 6*space)/7 + 0.5);
		int compHeight = (int) ((parent.getHeight() - 4*space)/5 + 0.5);
		
		int restWidth = parent.getWidth() - (6*space + 7*compWidth);
		int restHeight = parent.getHeight() - (4*space + 5*compHeight);
		
		//postavljanje prvog reda
		Component comp1 = components.get(new RCPosition(1,1));
		Component comp2 = components.get(new RCPosition(1,6));
		Component comp3 = components.get(new RCPosition(1,7));
		
		switch(restWidth) {
			case 0:
				if(comp1 != null) comp1.setBounds(0,0, 5*compWidth + 4*space, compHeight);
				if(comp2 != null) comp2.setBounds(5*(compWidth + space), 0, compWidth, compHeight);
				if(comp3 != null) comp3.setBounds(6*(compWidth + space), 0, compWidth, compHeight);
				break;
			case 1:
				if(comp1 != null) comp1.setBounds(0,0, 5*compWidth + 4*space, compHeight);
				if(comp2 != null) comp2.setBounds(5*(compWidth + space), 0, compWidth, compHeight);
				if(comp3 != null) comp3.setBounds(6*(compWidth + space), 0, compWidth+1, compHeight);
				break;
			case 2:
				if(comp1 != null) comp1.setBounds(0,0, 5*compWidth + 4*space + 1, compHeight);
				if(comp2 != null) comp2.setBounds(5*(compWidth + space) + 1, 0, compWidth, compHeight);
				if(comp3 != null) comp3.setBounds(6*(compWidth + space) + 1, 0, compWidth +1, compHeight);
				break;
			case 3:
				if(comp1 != null) comp1.setBounds(0,0, 5*compWidth + 4*space + 2 , compHeight);
				if(comp2 != null) comp2.setBounds(5*(compWidth + space) + 2, 0, compWidth, compHeight);
				if(comp3 != null) comp3.setBounds(6*(compWidth + space) +2 , 0, compWidth + 1, compHeight);
				break;
			case 4:
				if(comp1 != null) comp1.setBounds(0,0, 5*compWidth + 4*space + 2, compHeight);
				if(comp2 != null) comp2.setBounds(5*(compWidth + space) + 2, 0, compWidth + 1, compHeight);
				if(comp3 != null) comp3.setBounds(6*(compWidth + space) + 3, 0, compWidth + 1, compHeight);
				break;
			case 5:
				if(comp1 != null) comp1.setBounds(0,0, 5*compWidth + 4*space + 3, compHeight);
				if(comp2 != null) comp2.setBounds(5*(compWidth + space) + 3, 0, compWidth + 1, compHeight);
				if(comp3 != null) comp3.setBounds(6*(compWidth + space) + 4, 0, compWidth + 1, compHeight);
				break;
			case 6:
				if(comp1 != null) comp1.setBounds(0,0, 5*compWidth + 4*space + 4, compHeight);
				if(comp2 != null) comp2.setBounds(5*(compWidth + space) + 4, 0, compWidth + 1, compHeight);
				if(comp3 != null) comp3.setBounds(6*(compWidth + space) + 5, 0, compWidth + 1, compHeight);
				break;
			default: break;
		}
		
		int addedHeight = 0;
		int counterHeight =  restHeight;
		
		for(int i = 2; i <= 5; i++) {
			int counterWidth = restWidth;
			int addedWidth = 0;
			int addH = 0;
			int addW = 0;
			for(int j = 1; j <= 7; j++) {
				
				if(counterHeight >= 5)
					addH = 1;
				
				if(counterWidth >= 7) {
					addW = 1;
					counterWidth = counterWidth%7;
				}
					
				Component comp = components.get(new RCPosition(i,j));
				if(comp != null)
					comp.setBounds((j-1)*(compWidth + space) + addedWidth, (i-1)*(compHeight + space) + addedHeight, compWidth+addW, compHeight+addH);
				
				if(addW == 1)
					addedWidth++;
				
				counterWidth += restWidth; 
			}
			
			if(addH == 1)
				addedHeight++;
			
			if(counterHeight >= 5)
				counterHeight += counterHeight%5;
		}
		
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null || constraints == null)
			throw new NullPointerException("You need to deliver component and constaint.");
		
		if(!(constraints instanceof String || constraints instanceof RCPosition))
			throw new IllegalArgumentException("Constraint should be instance of String or RCPosition class.");
		
		RCPosition position;
		
		if(constraints instanceof String) {
			position = RCPosition.parse((String) constraints);
		}else {
			position = (RCPosition) constraints;
		}
		
		if(position.getColumn() < 1 || position.getColumn() > 7 || position.getRow() < 1 || position.getRow() > 5)
			throw new CalcLayoutException("Unsupported position.");
		
		if(position.getRow() == 1 && position.getColumn() > 1 && position.getColumn() < 6)
			throw new CalcLayoutException("Unsupported position.");
		
		if(components.containsKey(position))
			throw new CalcLayoutException("Already have element on that position.");
		
		components.put(position, comp);
		
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize((c) -> c.getMaximumSize(), target);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Privatna metoda za traženje veličine komponente.
	 * @param s
	 * @param c
	 * @return
	 */
	private Dimension getLayoutSize(Size s, Container c) {
		int width = 0;
		int height = 0;
		
		for(Map.Entry<RCPosition, Component> entry : components.entrySet()) {
			int componentWidth = s.size(entry.getValue()).width;
			int componentHeight = s.size(entry.getValue()).height;
			
			if(entry.getKey().getRow() == 1 && entry.getKey().getColumn() == 1) {
				componentWidth = (componentWidth - 4*space)/5;
			}
				
			if(componentWidth > width)
				width = componentWidth;
			
			if(componentHeight > height)
				height = componentHeight;
		}
		
		Insets inset = c.getInsets();
		return new Dimension(7*width + 6*space + inset.right + inset.left, 
				5*height + 4*space + inset.top + inset.bottom);
	}

}
