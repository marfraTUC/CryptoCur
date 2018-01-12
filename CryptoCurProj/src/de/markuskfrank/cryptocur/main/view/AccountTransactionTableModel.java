package de.markuskfrank.cryptocur.main.view;

import javax.swing.table.AbstractTableModel;

import de.markuskfrank.cryptocur.main.model.Account;

public class AccountTransactionTableModel extends AbstractTableModel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3932972187798303594L;
	private String[] columnNames = { "Action", "Currency In", "Amount", "Currency Out", "Price" };
	private final Account account;
	
	public AccountTransactionTableModel(final Account account) {
		this.account = account;
		
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return account.getTransactions().size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return account.getTransactions().get(rowIndex).getAction();
		case 1:
			return account.getTransactions().get(rowIndex).getBuyCurrency();
		case 2:
			return account.getTransactions().get(rowIndex).getAmount();
		case 3:
			return account.getTransactions().get(rowIndex).getPayCurrency();
		case 4:
			return account.getTransactions().get(rowIndex).getPrice();
		default:
			return "nA";
		}
	}
	
	 public String getColumnName(int col) {
	      return columnNames[col];
	    }

}
