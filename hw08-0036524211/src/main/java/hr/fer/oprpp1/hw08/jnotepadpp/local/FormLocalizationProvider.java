package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge{
	
	private JFrame frame;

	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		this.frame = frame;
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				FormLocalizationProvider.this.connect();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				FormLocalizationProvider.this.disconnect();
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {
	
			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
			
		});
	}

}
