package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	private ILocalizationProvider provider;
	private ILocalizationListener listener = () -> this.putValue(Action.NAME, provider.getString(key));
	
	
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.key = key;
		this.provider = provider;
		provider.addLocalizationListener(listener);
		this.putValue(Action.NAME, provider.getString(key));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public String getKey() {
		return key;
	}
	

}
