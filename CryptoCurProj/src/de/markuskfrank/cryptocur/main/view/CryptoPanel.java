package de.markuskfrank.cryptocur.main.view;

import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import de.markuskfrank.cryptocur.main.bussineslogic.MainControler;
import de.markuskfrank.cryptocur.main.model.Account;

public abstract class CryptoPanel extends JPanel implements ComponentListener {
	
	protected Account selectedAccount;
	protected final MainControler controler; 
	
	public CryptoPanel(MainControler controler) {
		this.controler = controler;
	}
	
	public void selectedAccount(Account account){
		selectedAccount = account;
		updatePanel();
	}

	protected abstract void updatePanel();

	public void notifyDataSetSchanged() {
		updatePanel();
	}


}
