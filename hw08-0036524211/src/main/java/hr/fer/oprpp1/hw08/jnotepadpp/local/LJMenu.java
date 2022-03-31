package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JLabel;
import javax.swing.JMenu;

public class LJMenu extends JMenu{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String key;
	private ILocalizationProvider provider;
	private ILocalizationListener listener = () -> this.setText(provider.getString(key));
	
	public LJMenu(String key, ILocalizationProvider provider) {
		this.key = key;
		this.provider = provider;
		provider.addLocalizationListener(listener);
		this.setText(provider.getString(key));
	}

}
