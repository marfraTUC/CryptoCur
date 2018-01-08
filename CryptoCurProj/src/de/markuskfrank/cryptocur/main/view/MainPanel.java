package de.markuskfrank.cryptocur.main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import de.markuskfrank.cryptocur.main.bussineslogic.MainControler;

public class MainPanel extends CryptoPanel {

	private final JPanel header = new JPanel(new GridLayout(2, 2));
	private final JLabel name = new JLabel("not set");
	private final JLabel investment = new JLabel("0.00");
	private final JLabel currency = new JLabel("nAv");
	private final JLabel earing = new JLabel("0.00");
	private JComboBox action, curIn, curOut;
	private JTextArea amountIn, amountOut;
	private JButton transaction;
	private JButton close;
	private JButton update;
	private JTable values;
	private JTable transactions;
	private JPanel dataContainer;

	public MainPanel(MainControler controler) {
		super(controler);
		this.setLayout(new BorderLayout());
		init();

	}

	private void init() {
		JLabel nameLabel = new JLabel("Account:");
		JLabel valueLabel = new JLabel("Investment:");
		JLabel earningLabel = new JLabel("Profit:");
		JPanel nameRow = new JPanel();
		JPanel curRow = new JPanel();
		JPanel buttonRow = new JPanel();
		JPanel earningRow = new JPanel();

		nameRow.add(nameLabel);
		nameRow.add(name);

		investment.setForeground(Color.RED);

		curRow.add(valueLabel);
		curRow.add(investment);
		curRow.add(currency);

		earningRow.add(earningLabel);
		earningRow.add(earing);

		addCloseButton(buttonRow);
		addTransactionButton(buttonRow);
		addUpdateButton(buttonRow);

		
		header.add(nameRow);
		header.add(buttonRow);
		header.add(curRow);
		header.add(earningRow);
		this.add(header, BorderLayout.NORTH);

		dataContainer = new JPanel(new GridLayout(2, 1));

		this.add(dataContainer);

	}

	private void addTransactionButton(JPanel buttonRow) {
		transaction = new JButton("new Transaction");
		transaction.setActionCommand("transaction");
		transaction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new NewTransactionFrame(selectedAccount, controler);
			}
		});

		transaction.setEnabled(false);
		buttonRow.add(transaction);
	}

	private void addCloseButton(JPanel buttonRow) {
		close = new JButton("close");
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the account?",
						"Close Account?", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					controler.closeAccount(selectedAccount);
				}
			}
		});

		close.setEnabled(false);
		buttonRow.add(close);
	}
	
	private void addUpdateButton(JPanel buttonRow) {
		update = new JButton("update");
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controler.updateCurrencys();
			}
		});

		update.setEnabled(false);
		buttonRow.add(update);
	}

	private void updateAccountInfo() {
		
		name.setText(selectedAccount.getAccountName());
		investment.setText("" + selectedAccount.getValue());
		currency.setText(selectedAccount.getCurrency().toString());
		
		dataContainer.removeAll();
		
		AccountValuesTableModel valueModel = new AccountValuesTableModel(selectedAccount.getAccountCurrencys(),
				controler, selectedAccount);
		
		JTable accountValueTable = new JTable(valueModel);
		//accountValueTable.setDefaultRenderer(Object.class, new AccountCellRenderer());
		dataContainer.add(new JScrollPane(accountValueTable));
		
		JTable accountTransactionTable = new JTable(new AccountTransactionTableModel(controler, selectedAccount));
		//accountTransactionTable.setDefaultRenderer(Object.class, new AccountCellRenderer());
		dataContainer.add(new JScrollPane(accountTransactionTable));
		
		double profit = valueModel.getTotalValue() + selectedAccount.getValue();
		earing.setText(profit + " "+selectedAccount.getCurrency().toString());
		if (profit > 0) {
			earing.setForeground(new Color(76, 153, 0));
		} else {
			earing.setForeground(Color.RED);
		}

	}

	@Override
	protected void updatePanel() {
		if (selectedAccount != null) {
			updateAccountInfo();
			close.setEnabled(true);
			update.setEnabled(true);
			transaction.setEnabled(true);

		} else {
			JOptionPane.showMessageDialog(null, "Could not load Account", "Error", JOptionPane.ERROR_MESSAGE);
			close.setEnabled(false);
			transaction.setEnabled(false);
			update.setEnabled(false);
			name.setText("not set");
			investment.setText("0.00");
			currency.setText("nAv");
		}
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
