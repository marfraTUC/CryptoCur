package de.markuskfrank.cryptocur.main.view.tables;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import de.markuskfrank.cryptocur.main.bussineslogic.MainControler;
import de.markuskfrank.cryptocur.main.model.Account;
import de.markuskfrank.cryptocur.main.model.Transaction;
import de.markuskfrank.cryptocur.main.view.TransactionFrame;

public class AccountTransactionTableModel extends AbstractTableModel {

	private static final int DELETE = 6;
	private static final int EDIT = 5;
	private static final int PRICE = 4;
	private static final int CURRENCY_OUT = 3;
	private static final int AMOUNT = 2;
	private static final int CURRENCY_IN = 1;
	private static final int ACTION = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3932972187798303594L;
	private String[] columnNames = { "Action", "Currency In", "Amount", "Currency Out", "Price", "Edit", "Delete" };
	private final Account account;
	private final MainControler controler;
	private static final DecimalFormat formatter = new DecimalFormat("#.#####");

	public AccountTransactionTableModel(final Account account, final MainControler controler) {
		this.account = account;
		this.controler = controler;

	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return account.getTransactions().size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case ACTION:
			return account.getTransactions().get(rowIndex).getAction();
		case CURRENCY_IN:
			return account.getTransactions().get(rowIndex).getBuyCurrency();
		case AMOUNT:
			return formatter.format(account.getTransactions().get(rowIndex).getAmount());
		case CURRENCY_OUT:
			return account.getTransactions().get(rowIndex).getPayCurrency();
		case PRICE:
			return formatter.format(account.getTransactions().get(rowIndex).getPrice());
		case EDIT:
			return getEditButton(account.getTransactions().get(rowIndex));
		case DELETE:
			return getDeleteButton(account.getTransactions().get(rowIndex));

		default:
			return "nA";
		}
	}

	private Object getDeleteButton(Transaction transaction) {
		JButton deleteButton = new JButton("delete");
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Do you realy want to delete the Transaction?", "Confirm Delete",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
					controler.deleteTransaction(account, transaction);
				}
			}
		});
		return deleteButton;
	}

	private Object getEditButton(Transaction transaction) {
		JButton editButton = new JButton("edit");
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TransactionFrame(transaction, account, controler);
			}
		});
		return editButton;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

}
