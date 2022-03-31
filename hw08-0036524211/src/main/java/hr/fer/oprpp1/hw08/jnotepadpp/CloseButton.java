package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Insets;

import javax.swing.Action;
import javax.swing.JButton;

import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

public class CloseButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Action action;
	private SingleDocumentModel singleDocumentModel;
	
	public CloseButton(Action action, SingleDocumentModel model) {
		super(action);
		//this.action = action;
		this.setMargin(new Insets(0, 0, 0, 0));
		this.singleDocumentModel = model;
	}

	public SingleDocumentModel getSingleDocumentModel() {
		return singleDocumentModel;
	}

}
