package de.markuskfrank.cryptocur.main.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.markuskfrank.cryptocur.main.bussineslogic.MainControler;
import de.markuskfrank.cryptocur.main.model.CurrencyValueMarket;
import de.markuskfrank.cryptocur.main.model.Currencys;

public class CurrencyInfoBar extends CryptoPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3926999411625460412L;
	private static final Currencys baseCurrency = Currencys.EUR;
	
	public CurrencyInfoBar(final MainControler controler) {
		super(controler);
		updatePanel();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
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
	protected void updatePanel() {
		this.removeAll();
		Set<Currencys> cur = controler.getAllUserCurrencys();
		this.setLayout(new GridLayout(1, cur.size()));
		
		JPanel temp;
		JLabel curLable, curValue;
		String valueString ="nA";
		for(Currencys aCur : cur){
			curLable = new JLabel(aCur.toString());
			curLable.setForeground(Color.WHITE);
			try {
				valueString = CurrencyValueMarket.getMarket().getCurrencyValueIn(aCur, baseCurrency)+" "+baseCurrency;
				curValue = new JLabel(valueString);
				curValue.setForeground(Color.WHITE);
				
			} catch (Exception e) {
				curValue = new JLabel("nA");
				curValue.setForeground(Color.WHITE);
			}
			
			temp = new JPanel();
			temp.add(curLable);
			temp.add(curValue);
			this.add(temp);
			temp.setBackground(Color.DARK_GRAY);
			this.updateUI();
			
		}
		
	}

}
