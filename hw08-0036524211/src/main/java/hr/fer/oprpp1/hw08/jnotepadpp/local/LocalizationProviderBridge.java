package hr.fer.oprpp1.hw08.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider{

	private boolean connected;
	private ILocalizationProvider provider;
	private ILocalizationListener listener = () -> this.fire();
	
	
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}
	
	public void disconnect() {
		this.removeLocalizationListener(listener);
		connected = false;
	}
	
	public void connect() {
		if(connected == false) 
			this.addLocalizationListener(listener);
		connected = true;
	}
	
	@Override
	public String getString(String text) {
		return provider.getString(text);
	}
	@Override
	public String getCurrentLanguage() {
		return provider.getCurrentLanguage();
	}
	
}
