package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Action;

public class LocalizationProvider extends AbstractLocalizationProvider{
	
	private String language;
	private ResourceBundle bundle;
	private static LocalizationProvider instance = new LocalizationProvider();
	
	private LocalizationProvider() {
		super();
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);

	}
	
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	public void setLanguage(String language) { //pazi s ovim instance
		LocalizationProvider.getInstance().language = language;
		Locale locale = Locale.forLanguageTag(language);
		LocalizationProvider.getInstance().bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
		//bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
		this.fire();
	}
	

	@Override
	public String getString(String text) {
		return LocalizationProvider.getInstance().bundle.getString(text);
	}

	@Override
	public String getCurrentLanguage() {
		return LocalizationProvider.getInstance().language;
	}


}
