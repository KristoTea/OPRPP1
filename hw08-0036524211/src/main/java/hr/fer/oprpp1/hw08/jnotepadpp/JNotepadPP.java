package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;


public class JNotepadPP extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MultipleDocumentModel mulDocModel;
	private String selectedText;
	private JToolBar simpleStatusBar;
	private FormLocalizationProvider formLocalizationProvider;
	private JLabel clockLabel;
	private Clock clock;
	private JMenu changeCaseMenu;
	private JMenu sortMenu;
	

	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("JNotepad++");
		setLocation(0, 0);
		setSize(600, 600);
		
		this.mulDocModel = new DefaultMultipleDocumentModel();
		this.mulDocModel.addMultipleDocumentListener(mulListener); 
		
		simpleStatusBar = new JToolBar();
		
		this.selectedText = "";
		this.formLocalizationProvider = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		this.changeCaseMenu = new LJMenu("changecase", LocalizationProvider.getInstance());
		this.sortMenu = new LJMenu("sort", LocalizationProvider.getInstance());
		this.addWindowListener(windowListener);
		initGUI();
		
	}

	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(mulDocModel.getVisualComponent(), BorderLayout.CENTER);
		
		createActions();
		createMenus();
		createToolbars();
	}
	
	private MultipleDocumentListener mulListener = new MultipleDocumentListener() {

		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			//currentModel.setModified(true);
			
			if(currentModel == null) {
				JNotepadPP.this.setTitle("Notepad++");
				((JLabel)simpleStatusBar.getComponent(0)).setText("length: ");
				JPanel secondPart = (JPanel)simpleStatusBar.getComponent(1);
				((JLabel)secondPart.getComponent(0)).setText("Ln:");
				((JLabel)secondPart.getComponent(1)).setText("Col:");
				((JLabel)secondPart.getComponent(2)).setText("Sel:");
			}else if(currentModel.getFilePath() == null) {
				JNotepadPP.this.setTitle("(unnamed) - Notepad++");
			}else if(currentModel != null) {
				JNotepadPP.this.setTitle(currentModel.getFilePath().toString() +" - Notepad++");
			}
			
			if(previousModel != null) {
				CaretListener[] carListeners = previousModel.getTextComponent().getCaretListeners();
				for(var l : carListeners) {
					previousModel.getTextComponent().removeCaretListener(l);
				}
			}
			
			if(currentModel != null) {
				mulDocModel.getCurrentDocument().getTextComponent().addCaretListener(caretListener);
				JTextArea text = mulDocModel.getCurrentDocument().getTextComponent();
				try {
					simpleStatusBarHelper(text);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!currentModel.equals(previousModel)) {
				currentModel.setModified(true);
			}
			
		}
		
		
		@Override
		public void documentAdded(SingleDocumentModel model) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void documentRemoved(SingleDocumentModel model) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private WindowListener windowListener = new WindowAdapter() {

		@Override
		public void windowClosing(WindowEvent e) {
			
			Iterator<SingleDocumentModel> it = mulDocModel.iterator();
			
			while(it.hasNext()) {
				SingleDocumentModel doc = it.next();
				if(doc.isModified()) {
					int response = JOptionPane.showConfirmDialog(JNotepadPP.this,
							"File: " + (doc.getFilePath() == null ? "unnamed" : doc.getFilePath().toString().substring(doc.getFilePath().toString().lastIndexOf("/") +1)) 
							+ "is not saved. Do you want to save it?",
							"Warning!",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if(response == JOptionPane.YES_OPTION) {
						if(doc.getFilePath() == null) {
							saveDoc(doc);
						}else {
							mulDocModel.saveDocument(doc, doc.getFilePath());
						}
					}else if(response == JOptionPane.NO_OPTION) {
						break;
					}else if(response == JOptionPane.CLOSED_OPTION || response == JOptionPane.CANCEL_OPTION){
						return;
					}
							
				}
				
				clock.pause();
				dispose();
			}
			
		}
	};
	
	private class Clock {
		private boolean stop;
		
		public Clock() {
			stop = false;
			Thread thread = new Thread(){
				
				public void run() {
					try {
						while(!stop) {
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
							LocalDateTime now = LocalDateTime.now();
							clockLabel.setText(dtf.format(now));
							if(stop) break;
							sleep(1000);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			};
			
			thread.setDaemon(true);
			thread.start();
			
		}
		
		public void pause() {
			stop = true;
		}

	}

	
	private Action createNewDocument = new LocalizableAction("new", LocalizationProvider.getInstance()) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			mulDocModel.createNewDocument();
		}
		
	};
	
	private Action openExistingDocument = new LocalizableAction("open", LocalizationProvider.getInstance()){

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Datoteka "+fileName.getAbsolutePath()+" ne postoji!", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			mulDocModel.loadDocument(filePath);
		}
		
	};
	
	private Action saveDocument = new LocalizableAction("save", LocalizationProvider.getInstance()){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(mulDocModel.getCurrentDocument().getFilePath() == null) {
				saveAsDocument.actionPerformed(e);
			}else {
				mulDocModel.saveDocument(mulDocModel.getCurrentDocument(), mulDocModel.getCurrentDocument().getFilePath());
			}
			
		}
		
	};
	
	private Action saveAsDocument = new LocalizableAction("saveAs", LocalizationProvider.getInstance()){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			saveDoc(mulDocModel.getCurrentDocument());
		}
		
	};
	
	private Action closeCurrentDocument = new LocalizableAction("closeTab", LocalizationProvider.getInstance()){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			mulDocModel.closeDocument(mulDocModel.getCurrentDocument());
		}
		
	};
	
	private Action cutText = new LocalizableAction("cut", LocalizationProvider.getInstance()){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = mulDocModel.getCurrentDocument().getTextComponent(); 
			int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
			int offset = Math.min(textArea.getCaret().getDot(),textArea.getCaret().getMark());
			
			try {
				textArea.getDocument().remove(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		
	};
	
	private Action copyText = new LocalizableAction("copy", LocalizationProvider.getInstance()){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = mulDocModel.getCurrentDocument().getTextComponent(); 
			int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
			int offset = Math.min(textArea.getCaret().getDot(),textArea.getCaret().getMark());
			
			try {
				selectedText = textArea.getText(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		
	};
	
	private Action pasteText = new LocalizableAction("paste", LocalizationProvider.getInstance()){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = mulDocModel.getCurrentDocument().getTextComponent(); 
			
			try {
				textArea.getDocument().insertString(textArea.getCaret().getDot(), selectedText, null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		
	};
	
	private Action getStatisticalInfo = new LocalizableAction("statistic", LocalizationProvider.getInstance()){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(mulDocModel.getCurrentDocument() == null) 
				return;
			int x = mulDocModel.getCurrentDocument().getTextComponent().getText().toString().length();
			int y = mulDocModel.getCurrentDocument().getTextComponent().getText().toString().replaceAll("\\s+", "").length();
			int z = 1;
			for(Character c : mulDocModel.getCurrentDocument().getTextComponent().getText().toString().toCharArray()) {
				if(c.equals('\n')) {
					z++;
				}
			}
			StringBuilder sb = new StringBuilder();
			sb.append("Your document has ");
			sb.append(x);
			sb.append(" characters, ");
			sb.append(y);
			sb.append(" non-blank characters and ");
			sb.append(z);
			sb.append(" lines.");
			
			JOptionPane.showMessageDialog(JNotepadPP.this,
					sb.toString(), "Statistical Info", JOptionPane.INFORMATION_MESSAGE);
		}
		
	};
	
	private Action exitingApl = new LocalizableAction("exit", LocalizationProvider.getInstance()){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadPP.this.windowListener.windowClosing(null);
			
		}
	
	};
	
	private void saveDoc(SingleDocumentModel s) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Ništa nije snimljeno.", 
					"Upozorenje", 
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		
//		if(jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
//			return;
//		}

		
		Path path = jfc.getSelectedFile().toPath();
		
		if(Files.exists(path)) {
			int response = JOptionPane.showConfirmDialog(JNotepadPP.this, 
					"Već postoji datoteka s tim imenom. Želite li nastaviti?", 
					"Upozorenje!", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if(response == JOptionPane.NO_OPTION) return;
		}
		
		mulDocModel.saveDocument(s, path);
		
		JOptionPane.showMessageDialog(
				JNotepadPP.this, 
				"Datoteka je snimljena.", 
				"Informacija", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private CaretListener caretListener = new CaretListener() {

		@Override
		public void caretUpdate(CaretEvent e) {
			JTextArea text = (JTextArea) e.getSource();
			try {
				simpleStatusBarHelper(text);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		
	};
	
	private void simpleStatusBarHelper(JTextArea text) throws BadLocationException {
		JLabel length = (JLabel)simpleStatusBar.getComponent(0);
		length.setText("length:" + text.getText().toString().length());
		
		JPanel secondPanel = (JPanel)simpleStatusBar.getComponent(1);
		JLabel ln = (JLabel)secondPanel.getComponent(0);
		JLabel col = (JLabel)secondPanel.getComponent(1);
		JLabel sel = (JLabel)secondPanel.getComponent(2);
		
		int lnInt = 1;
		lnInt += text.getLineOfOffset(text.getCaretPosition());
		ln.setText("Ln:" +lnInt);
		
		int colInt = 1;
		colInt += text.getCaretPosition() - text.getLineStartOffset(lnInt - 1);
		col.setText("Col:" + colInt);
		
		int selInt = Math.abs(text.getCaret().getDot()-text.getCaret().getMark());
		changeCaseMenu.setEnabled(false);
		sortMenu.setEnabled(false);
		if(selInt > 0) {
			changeCaseMenu.setEnabled(true);
			sortMenu.setEnabled(true);
		}
		sel.setText("Sel:"+selInt);
	}
	
	
	private Action setLanguageAction(String key) {
		Action action = new LocalizableAction(key, LocalizationProvider.getInstance()) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage(this.getKey());
			}
			
		};
		
		return action;
		
	}
	
	private Action changeCase(String change) {
		Action action = new LocalizableAction(change, LocalizationProvider.getInstance()) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea text = mulDocModel.getCurrentDocument().getTextComponent();
				int len = Math.abs(text.getCaret().getDot()-text.getCaret().getMark());
				int offset = Math.min(text.getCaret().getDot(), text.getCaret().getMark());
				
				try {
					String selectedText = text.getText(offset, len);
					
					if(change.equals("uppercase")) {
						selectedText = selectedText.toUpperCase();
					}else if(change.equals("lowercase")) {
						selectedText = selectedText.toLowerCase();
					}else {
						char[] charArray = selectedText.toCharArray();
						for(int i = 0; i < charArray.length; i++) {
							char c = charArray[i];
							if(Character.isLowerCase(c)) {
								charArray[i] = Character.toUpperCase(c);
							}else {
								charArray[i] = Character.toLowerCase(c);
							}
						}
						selectedText = String.valueOf(charArray);
					}
					
					text.getDocument().remove(offset, len);
					text.getDocument().insertString(offset, selectedText, null);
					
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		};
		return action;
	}
	
	
	private Action sort(String sort) {
		Action action = new LocalizableAction(sort, LocalizationProvider.getInstance()) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea text = mulDocModel.getCurrentDocument().getTextComponent();
				Locale locale = new Locale(LocalizationProvider.getInstance().getCurrentLanguage());
				Collator collator = Collator.getInstance(locale);
				int dot = text.getCaret().getDot();
				int mark = text.getCaret().getMark();
				
				int firstLine = Math.min(dot, mark);
				int lastLine = Math.max(dot, mark);

				try {
					firstLine = text.getLineOfOffset(firstLine);
					lastLine = text.getLineOfOffset(lastLine);
					for(int i = firstLine; i <= lastLine; i++) {
						int start = text.getLineStartOffset(i);
						int end = text.getLineEndOffset(i);
						if(start >= end) {
							break;
						}

						List<String> lista = Arrays.stream(text.getText(start, end-start).replace("\n", "").split("")).collect(Collectors.toList());
						if(sort.equals("ascending")) {
							lista.sort(collator);
						}else {
							lista.sort(collator.reversed());
						}
						
						String sorted = "";
						for(int j = 0; j < lista.size(); j++) {
							sorted += lista.get(j);
						}
						sorted += "\n";
						
						text.getDocument().remove(start, end-start);
						text.getDocument().insertString(start, sorted, null);
					}
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		};
		return action;
	}
	
	private Action unique = new LocalizableAction("unique", LocalizationProvider.getInstance()) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea text = mulDocModel.getCurrentDocument().getTextComponent();
			int dot = text.getCaret().getDot();
			int mark = text.getCaret().getMark();
			
			int firstLine = Math.min(dot, mark);
			int lastLine = Math.max(dot, mark);

			try {
				firstLine = text.getLineOfOffset(firstLine);
				lastLine = text.getLineOfOffset(lastLine);
				for(int i = firstLine; i <= lastLine; i++) {
					int start = text.getLineStartOffset(i);
					int end = text.getLineEndOffset(i);
					if(start >= end) {
						break;
					}

					List<String> lista = Arrays.stream(text.getText(start, end-start).replace("\n", "").split("")).collect(Collectors.toList());
					List<String> u = new ArrayList<>();
					for(int j = 0; j < lista.size(); j++) {
						if(!u.contains(lista.get(j))) {
							u.add(lista.get(j));
						}
					}
					
					String result = "";
					for(int j = 0; j < u.size(); j++) {
						result += u.get(j);
					}
					result += "\n";
					text.getDocument().remove(start, end-start);
					text.getDocument().insertString(start, result, null);
				}
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	};
	
	
	private void createActions() {
		
		createNewDocument.putValue(Action.NAME, "New");
		createNewDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocument.putValue(Action.MNEMONIC_KEY,KeyEvent.VK_N);
//		createNewDocument.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("newDescription"));
//		createNewDocument.putValue(Action.SHORT_DESCRIPTION, "Used to create new empty document.");
		
		openExistingDocument.putValue(Action.NAME, "Open");
		openExistingDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openExistingDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openExistingDocument.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("openDescription"));
		
		saveDocument.putValue(Action.NAME, "Save");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("saveDescription"));
		
		saveAsDocument.putValue(Action.NAME, "Save as");
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocument.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("saveAsDescription"));
		
		closeCurrentDocument.putValue(Action.NAME, "Close tab");
		closeCurrentDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeCurrentDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		closeCurrentDocument.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("closeTabDescription"));
		
		cutText.putValue(Action.NAME, "Cut");
		cutText.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutText.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutText.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("cutDescription"));
		
		copyText.putValue(Action.NAME, "Copy");
		copyText.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyText.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyText.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("copyDescritpion"));
		
		pasteText.putValue(Action.NAME, "Paste");
		pasteText.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteText.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteText.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("pasteDescription"));
		
		getStatisticalInfo.putValue(Action.NAME, "Statistic info");
		getStatisticalInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		getStatisticalInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		getStatisticalInfo.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("statisticDescription"));
		
		exitingApl.putValue(Action.NAME, "Exit");
		exitingApl.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitingApl.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
		exitingApl.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("exitDescription"));
		
	}
	
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new LJMenu("file", LocalizationProvider.getInstance());
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(createNewDocument));
		fileMenu.add(new JMenuItem(openExistingDocument));
		fileMenu.add(new JMenuItem(saveDocument));
		fileMenu.add(new JMenuItem(saveAsDocument));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeCurrentDocument));
		fileMenu.add(new JMenuItem(exitingApl));
		
		JMenu editMenu = new LJMenu("edit", LocalizationProvider.getInstance());
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(cutText));
		editMenu.add(new JMenuItem(copyText));
		editMenu.add(new JMenuItem(pasteText));
		
		JMenu infoMenu = new JMenu("Info");
		infoMenu.add(infoMenu);
		
		infoMenu.add(new JMenuItem(getStatisticalInfo));
		
		JMenu languageMenu = new LJMenu("language", LocalizationProvider.getInstance());
		languageMenu.add(new JMenuItem(setLanguageAction("hr")));
		languageMenu.add(new JMenuItem(setLanguageAction("en")));
		languageMenu.add(new JMenuItem(setLanguageAction("de")));
		menuBar.add(languageMenu);
		
		changeCaseMenu.setEnabled(false);
		changeCaseMenu.add(new JMenuItem(changeCase("uppercase")));
		changeCaseMenu.add(new JMenuItem(changeCase("lowercase")));
		changeCaseMenu.add(new JMenuItem(changeCase("invertcase")));
		menuBar.add(changeCaseMenu);
		
		sortMenu.setEnabled(false);
		sortMenu.add(new JMenuItem(sort("ascending")));
		sortMenu.add(new JMenuItem(sort("descending")));
		sortMenu.add(unique);
		menuBar.add(sortMenu);
		
		this.setJMenuBar(menuBar);
	}
	
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Uredi");
		toolBar.setFloatable(true);
		
		toolBar.add(new JButton(createNewDocument));
		toolBar.add(new JButton(openExistingDocument));
		toolBar.add(new JButton(saveDocument));
		toolBar.add(new JButton(saveAsDocument));
		//toolBar.add(new JButton(closeCurrentDocument));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutText));
		toolBar.add(new JButton(copyText));
		toolBar.add(new JButton(pasteText));
		toolBar.addSeparator();
		toolBar.add(new JButton(getStatisticalInfo));
		//toolBar.add(new JButton(exitingApl));
		
		simpleStatusBar.setFloatable(true);
		simpleStatusBar.add(new JLabel("length:"));
		JPanel secPanel = new JPanel();
		secPanel.add(new JLabel("Ln:"));
		secPanel.add(new JLabel("Col:"));
		secPanel.add(new JLabel("Sel:"));
		simpleStatusBar.add(secPanel);
		
		clockLabel = new JLabel();
		this.clock = new Clock();
		simpleStatusBar.add(clockLabel);
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
		this.getContentPane().add(simpleStatusBar, BorderLayout.PAGE_END);
	}


	public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new JNotepadPP().setVisible(true);
				}
			});
	}

}
