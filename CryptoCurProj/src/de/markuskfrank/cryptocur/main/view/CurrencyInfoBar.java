package de.markuskfrank.cryptocur.main.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.markuskfrank.cryptocur.main.bussineslogic.MainControler;
import de.markuskfrank.cryptocur.main.model.Currencys;

public class CurrencyInfoBar extends CryptoPanel {
	
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
		List<Currencys> cur = controler.getAllUserCurrencys();
		this.setLayout(new GridLayout(1, cur.size()));
		
		JPanel temp;
		JLabel curLable, curValue;
		String valueString ="nA";
		for(Currencys aCur : cur){
			curLable = new JLabel(aCur.toString());
			curLable.setForeground(Color.WHITE);
			try {
				valueString = controler.getCurrencyValueIn(aCur, baseCurrency)+" "+baseCurrency;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			curValue = new JLabel(valueString);
			curValue.setForeground(Color.WHITE);
			
			temp = new JPanel();
			temp.add(curLable);
			temp.add(curValue);
			this.add(temp);
			temp.setBackground(Color.DARK_GRAY);
			
		}
		
	}

}
