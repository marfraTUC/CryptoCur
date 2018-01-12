package de.markuskfrank.cryptocur.main.view;

import java.util.Map;

import javax.swing.table.AbstractTableModel;

import de.markuskfrank.cryptocur.main.model.Account;
import de.markuskfrank.cryptocur.main.model.CurrencyValueMarket;
import de.markuskfrank.cryptocur.main.model.Currencys;

public class AccountValuesTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5107592367839112803L;
	private final Object[] cur;
	private final Map<Currencys, Double> values;
	private String[] columnNames = { "Currency", "Amount","Crypto Value", "Total Value in:" };
	private final Account account;
	
	public AccountValuesTableModel(Map<Currencys, Double> values, Account account) {
		if(values.containsKey(account.getCurrency())){
			values.remove(account.getCurrency());
		}
		this.cur = values.keySet().toArray();;
		this.values = values;
		this.account = account;
		
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return cur.length;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return cur[rowIndex];
		case 1:
			return values.get(cur[rowIndex]);
		case 2:
			try {
				return CurrencyValueMarket.getMarket().getCurrencyValueIn((Currencys)cur[rowIndex], account.getCurrency());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		case 3:
			try {
				return (CurrencyValueMarket.getMarket().getCurrencyValueIn((Currencys)cur[rowIndex], account.getCurrency()) * values.get(cur[rowIndex]));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		default:
			return "nA";
		}
	}

	 public String getColumnName(int col) {
	      return columnNames[col];
	    }

	public double getTotalValue() {
		double res = 0;
		for(int i = 0; i < cur.length; i++){
			try {
				res+= (double) getValueAt(i, 3);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return res;
	}
	
}
