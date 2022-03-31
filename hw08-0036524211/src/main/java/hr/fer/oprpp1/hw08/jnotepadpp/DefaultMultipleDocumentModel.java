package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SingleDocumentModel> listSingleDocumentModels;
	private SingleDocumentModel currentDocument;
	private List<MultipleDocumentListener> multipleDocumentListeners;
	
	public DefaultMultipleDocumentModel() {
		this.listSingleDocumentModels = new ArrayList<>();
		this.currentDocument = null;
		this.multipleDocumentListeners = new ArrayList<>();
		
		//this.addMultipleDocumentListener(multipleDocListener);
		
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				DefaultMultipleDocumentModel dmdm = DefaultMultipleDocumentModel.this;
				SingleDocumentModel previous= dmdm.currentDocument;
				dmdm.currentDocument = null;
				//SingleDocumentModel currentModel = null;
				if(dmdm.getNumberOfDocuments() != 0) {
					dmdm.currentDocument = dmdm.getDocument(dmdm.getSelectedIndex());
				}
				dmdm.currentDocument.setModified(previous.isModified());
				for(var l : multipleDocumentListeners) {
					l.currentDocumentChanged(previous, dmdm.currentDocument);
				}
				
			}
			
		});
		
	}
	

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return this.listSingleDocumentModels.iterator();
	}

	@Override
	public JComponent getVisualComponent() {
		return this;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDoc = new DefaultSingleDocumentModel(null, "");
		this.listSingleDocumentModels.add(newDoc);
		this.currentDocument = newDoc;
		newDoc.addSingleDocumentListener(singlListener);
		this.add(new JScrollPane(newDoc.getTextComponent()));
		this.setTabComponentAt(this.getNumberOfDocuments()-1, newPanel(newDoc, "redDisk.png", "(unnamed)"));
		this.setToolTipTextAt(this.getNumberOfDocuments()-1, "unnamed");
		this.setSelectedIndex(this.getNumberOfDocuments()-1);
		newDoc.setModified(true);
		return newDoc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return this.currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if(!Files.exists(path)) 
			throw new IllegalArgumentException("File does't exist.");
		
		if(Files.isDirectory(path))
			throw new IllegalArgumentException("Cannot opet directory.");
		
		byte[] textArea = null;
		try {
			textArea = Files.readAllBytes(path);
		}catch(IOException e) {
			throw new RuntimeException("Cannot read text.");
		}
		
		SingleDocumentModel newDoc = new DefaultSingleDocumentModel(path, 
				new String(textArea, StandardCharsets.UTF_8));
		
		this.listSingleDocumentModels.add(newDoc);
		this.currentDocument = newDoc;
		
		newDoc.addSingleDocumentListener(singlListener);
		
		this.add(new JScrollPane(newDoc.getTextComponent()));
		String docName = path.toString().substring(path.toString().lastIndexOf("\\") + 1);
		this.setTabComponentAt(this.getNumberOfDocuments()-1, 
				newPanel(newDoc, "greenDisk.png", docName));
		this.setToolTipTextAt(this.getNumberOfDocuments()-1, path.toString());
		this.setSelectedIndex(this.getNumberOfDocuments()-1);
		newDoc.setModified(false);
		
		return newDoc;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if(newPath == null)
			newPath = model.getFilePath();
		
		if(model.getFilePath()==null || (!model.getFilePath().equals(newPath))){
			model.setFilePath(newPath);
			for(var l : multipleDocumentListeners) {
				l.currentDocumentChanged(model, model);
			}
		}
		
		byte[] text = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		
		try {
			Files.write(newPath, text);
		}catch(IOException e) {
			throw new RuntimeException("Exception while writing.");
		}
		
		model.setModified(false);
		
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = this.listSingleDocumentModels.indexOf(model);
		this.listSingleDocumentModels.remove(index);
		this.remove(index);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		multipleDocumentListeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		multipleDocumentListeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return this.listSingleDocumentModels.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return this.listSingleDocumentModels.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path) { 
		if(path == null)
			throw new NullPointerException("Path cannot be null!");
		for(SingleDocumentModel m : listSingleDocumentModels) {
			if(m.getFilePath().equals(path)) {
				return m;
			}
		}
		return null;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		return this.listSingleDocumentModels.indexOf(doc);
	}
	
	private SingleDocumentListener singlListener = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			DefaultMultipleDocumentModel dmdm = DefaultMultipleDocumentModel.this;
			String icon;
			if(model.isModified()) {
				icon = "redDisk.png";
			}else {
				icon = "greenDisk.png";
			}
			
			if(model.getFilePath() == null) {
				dmdm.setTabComponentAt(dmdm.listSingleDocumentModels.indexOf(model), 
						newPanel(model, icon, "unnamed"));
			}else {
				String docName = model.getFilePath().toString().substring(model.getFilePath().toString().lastIndexOf("\\") + 1);
				dmdm.setTabComponentAt(dmdm.listSingleDocumentModels.indexOf(model), 
						newPanel(model, icon, docName));
			}
			
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			DefaultMultipleDocumentModel dmdm = DefaultMultipleDocumentModel.this;
			dmdm.setToolTipTextAt(listSingleDocumentModels.indexOf(model), model.getFilePath().toString());
			
		}
		
	};
	
	private JPanel newPanel(SingleDocumentModel model, String icon, String docName) {
		JPanel jp = new JPanel();
		jp.setOpaque(false);
		jp.add(new JLabel(loadIcon(icon)));
		jp.add(new JLabel(docName));
		closeDoc.putValue(Action.NAME, "x");
		jp.add(new CloseButton(closeDoc, model));
		return jp;
	}
	
	private Action closeDoc = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMultipleDocumentModel dmdm = DefaultMultipleDocumentModel.this;
			
			if(!dmdm.getCurrentDocument().isModified()) {
				dmdm.closeDocument(((CloseButton)e.getSource()).getSingleDocumentModel());
			}else {
				int response = JOptionPane.showConfirmDialog(dmdm, 
						"Document is not saved! Exit anyway?", 
						"Warning!", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if(response == JOptionPane.YES_OPTION) {
					dmdm.closeDocument(((CloseButton)e.getSource()).getSingleDocumentModel());
				}
			}
		}
		
	};
	
	private ImageIcon loadIcon(String icon) {
		byte[] bytes = null;
		try (InputStream is = this.getClass().getResourceAsStream("icons/"+icon);){
			if(is == null)
				throw new NullPointerException("No picture here.");
			
			bytes = is.readAllBytes();
		}catch(IOException e) {
			throw new RuntimeException("Exception while loading icon.");
		}
		
		ImageIcon imageIcon = new ImageIcon(bytes);
		Image image = imageIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}
	
}
