package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Dimension;

/** 
 * Sučelje korišteno prilikom računanja 
 * dimenzija cjelokupnog rasporeda.
 * @author teakr
 *
 */
public interface Size {
	
	Dimension size(Component comp);

}
