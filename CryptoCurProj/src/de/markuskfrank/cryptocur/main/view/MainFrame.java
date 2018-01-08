package de.markuskfrank.cryptocur.main.view;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import de.markuskfrank.cryptocur.main.bussineslogic.MainControler;
import de.markuskfrank.cryptocur.main.model.Account;

public class MainFrame extends JFrame implements Observer, ComponentListener{
	
	private final MainControler controler;
	private List<CryptoPanel> panels; 
	
	public MainFrame(MainControler controler) {
		super("Crypto Curr");
		this.addComponentListener(this);
		this.controler = controler;
		controler.addObserver(this);
		initFrame();
	}

	private void initFrame() {
		panels = new ArrayList<CryptoPanel>();
		CryptoPanel list = new ListPanel(controler);
		CryptoPanel main = new MainPanel(controler);
		CryptoPanel infoBar = new CurrencyInfoBar(controler);
		
		this.add(list, BorderLayout.WEST);
		this.add(main, BorderLayout.CENTER);
		this.add(infoBar, BorderLayout.NORTH);
		
		panels.add(list);
		panels.add(main);
		panels.add(infoBar);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void updateSelected(Account account) {
		for(CryptoPanel panel : panels){
			panel.selectedAccount(account);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		for(CryptoPanel panel : panels){
			panel.componentResized(e);
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyDataSetChanged() {
		for(CryptoPanel panel : panels){
			panel.notifyDataSetSchanged();
		}
		
	}

}
