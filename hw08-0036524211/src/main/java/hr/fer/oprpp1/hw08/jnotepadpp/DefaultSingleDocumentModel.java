package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private Path filePath;
	private JTextArea textArea;
	private boolean isModified;
	private List<SingleDocumentListener> listeners;
	
	
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		this.filePath = filePath;
		this.textArea = new JTextArea(textContent);
		this.isModified = false;
		this.listeners = new ArrayList<SingleDocumentListener>();
		textArea.getDocument().addDocumentListener(docListener);
	}

	@Override
	public JTextArea getTextComponent() {
		return this.textArea;
	}

	@Override
	public Path getFilePath() {
		return this.filePath;
	}

	@Override
	public void setFilePath(Path path) {
		if(path == null)
			throw new IllegalArgumentException("Path cannot be null.");
		
		this.filePath = path;
		
		for(var l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void setModified(boolean modified) {
		this.isModified = modified;
		
		for(var l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.remove(l);
	}
	
	private DocumentListener docListener = new DocumentListener() {

		@Override
		public void insertUpdate(DocumentEvent e) {
			DefaultSingleDocumentModel.this.setModified(true);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			DefaultSingleDocumentModel.this.setModified(true);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {

		}
		
	};

}
