package de.markuskfrank.cryptocur.main.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.markuskfrank.cryptocur.main.bussineslogic.MainControler;
import de.markuskfrank.cryptocur.main.model.Currencys;
import de.markuskfrank.cryptocur.main.model.User;

public class InfoFooterBar extends CryptoPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3926999411625460411L;
	private static final Currencys baseCurrency = Currencys.EUR;
	private final JLabel investment, currentValue, profit, increase, update;
	private static final DecimalFormat formatter = new DecimalFormat("#.##");
	private static final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	private final User user;

	public InfoFooterBar(final MainControler controler) {
		super(controler);
		investment = new JLabel("NA");
		currentValue = new JLabel("NA");
		profit = new JLabel("NA");
		increase = new JLabel("NA");
		update = new JLabel("NA");
		
		investment.setForeground(Color.WHITE);
		currentValue.setForeground(Color.WHITE);
		profit.setForeground(Color.WHITE);
		increase.setForeground(Color.WHITE);
		update.setForeground(Color.WHITE);
		
		this.user = controler.getCurrentUser();
		init();
		updatePanel();

	}

	private void init() {
		this.setLayout(new GridLayout(1, 5));

		JPanel tmpP = new JPanel();
		JLabel tmpL = new JLabel("Last update:");
		tmpL.setForeground(Color.WHITE);
		tmpP.add(tmpL);
		tmpP.add(update);
		tmpP.setBackground(Color.DARK_GRAY);
		this.add(tmpP);

		tmpP = new JPanel();
		tmpL = new JLabel("Investment:");
		tmpL.setForeground(Color.WHITE);
		tmpP.add(tmpL);
		tmpP.add(investment);
		tmpP.setBackground(Color.DARK_GRAY);
		this.add(tmpP);

		tmpP = new JPanel();
		tmpL = new JLabel("Current Value:");
		tmpL.setForeground(Color.WHITE);
		tmpP.add(tmpL);
		tmpP.add(currentValue);
		tmpP.setBackground(Color.DARK_GRAY);
		this.add(tmpP);

		tmpP = new JPanel();
		tmpL = new JLabel("Total Profit:");
		tmpL.setForeground(Color.WHITE);
		tmpP.add(tmpL);
		tmpP.add(profit);
		tmpP.setBackground(Color.DARK_GRAY);
		this.add(tmpP);

		tmpP = new JPanel();
		tmpL = new JLabel("Profit:");
		tmpL.setForeground(Color.WHITE);
		tmpP.add(tmpL);
		tmpP.add(increase);
		tmpP.setBackground(Color.DARK_GRAY);
		this.add(tmpP);

		this.setBackground(Color.DARK_GRAY);
	}

	@Override
	protected void updatePanel() {

		double investments, values,returnedMoney, totalProfit;
		
		update.setText(df.format(System.currentTimeMillis()));
		
		investments = user.getTotalInvestment(baseCurrency);
		returnedMoney = user.getTotalReturn(baseCurrency);
		investment.setText(formatter.format(investments)+ " " + baseCurrency.toString());
		
		values = user.getCurrenctValue(baseCurrency);
		currentValue.setText(formatter.format(values)+ " " + baseCurrency.toString());
		
		totalProfit = (investments - returnedMoney  - values)*-1;
		profit.setText(formatter.format(totalProfit)+ " " + baseCurrency.toString());
		
		
		if(totalProfit > 0){
			profit.setForeground(Color.GREEN);
		}else {
			profit.setForeground(Color.RED);
		}
		
		if((totalProfit) < 0){
			increase.setText("-"+formatter.format(( Math.abs((values+returnedMoney) / investments - 1))*100 )+" %");
			increase.setForeground(Color.RED);
		}else {
			increase.setText(formatter.format(((values+returnedMoney) / Math.abs(investments)-1) * 100)+" %");
			increase.setForeground(Color.GREEN);
		}
		
		this.updateUI();

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

}
