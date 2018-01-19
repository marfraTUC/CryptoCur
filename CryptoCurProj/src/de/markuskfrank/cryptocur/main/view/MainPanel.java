package de.markuskfrank.cryptocur.main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.markuskfrank.cryptocur.main.bussineslogic.MainControler;

public class MainPanel extends CryptoPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1049758324663168269L;
	private final JPanel header = new JPanel(new GridLayout(2, 2));
	private final JLabel name = new JLabel("not set");
	private final JLabel moneyInMarket = new JLabel("0.00");
	private final JLabel currentValue = new JLabel("0.00"); 
	private final JLabel investment = new JLabel("0.00");
	private final JLabel earing = new JLabel("0.00");
	private final JLabel percent = new JLabel("0.00");
	private static final DecimalFormat formatter = new DecimalFormat("#.##");
	private JButton transaction;
	private JButton close;
	private JButton update;
	private JPanel dataContainer;
	private Thread updater;
	
	private final JLabel nameLabel = new JLabel("Account:");
	private final JLabel percentLabel = new JLabel(" ");
	private final JLabel valueLabel = new JLabel("Investment:");
	private final JLabel earningLabel = new JLabel("Profit:");
	private final JLabel currentValueLabel = new JLabel("Current Value:");
	private final JLabel moneyInMarketLabel = new JLabel("Money in Market:");
	private final JPanel nameRow = new JPanel();
	private final JPanel curRow = new JPanel(new GridLayout(1, 2));
	private final JPanel curRowLeft = new JPanel();
	private final JPanel curRowRight = new JPanel();
	private final JPanel buttonRow = new JPanel();
	private final JPanel earningRow = new JPanel(new GridLayout(1, 3));
	private final JPanel earningRowLeft = new JPanel();
	private final JPanel earningRowMiddel = new JPanel();
	private final JPanel earningRowRight = new JPanel();
	private final Color GREEN = new Color(34, 139, 34);
	private final Color BACKGROUND_LIGHT = new Color(230,230,230);
	private final Color BACKGROUND = Color.LIGHT_GRAY;
	private final Color RED = Color.RED;
	
	
	public MainPanel(MainControler controler) {
		super(controler);
		this.setLayout(new BorderLayout());
		updater = new Thread();
		init();

	}

	private void init() {
		nameRow.setBackground(BACKGROUND);
		curRow.setBackground(BACKGROUND_LIGHT);
		curRowLeft.setBackground(BACKGROUND_LIGHT);
		curRowRight.setBackground(BACKGROUND_LIGHT);
		buttonRow.setBackground(BACKGROUND);
		earningRow.setBackground(BACKGROUND_LIGHT);
		earningRowLeft.setBackground(BACKGROUND_LIGHT);
		earningRowMiddel.setBackground(BACKGROUND_LIGHT);
		earningRowRight.setBackground(BACKGROUND_LIGHT);
		
		curRow.add(curRowLeft);
		curRow.add(curRowRight);
		
		earningRow.add(earningRowLeft);
		earningRow.add(earningRowMiddel);
		earningRow.add(earningRowRight);

		nameRow.add(nameLabel);
		nameRow.add(name);

		//moneyInMarket.setForeground(Color.RED);
		//investment.setForeground(Color.RED);
		
		curRowLeft.add(valueLabel);
		curRowLeft.add(investment);
		
		curRowRight.add(moneyInMarketLabel);
		curRowRight.add(moneyInMarket);

		earningRowLeft.add(currentValueLabel);
		earningRowLeft.add(currentValue);
		
		earningRowMiddel.add(earningLabel);
		earningRowMiddel.add(earing);
		
		earningRowRight.add(percentLabel);
		earningRowRight.add(percent);
		

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
		close = new JButton("close account");
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

	private synchronized void addUpdateButton(JPanel buttonRow) {

		update = new JButton("update");
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				update.setText("update ...");
				update.updateUI();
				if (!updater.isAlive()) {
					updater = new Thread(new Runnable() {

						@Override
						public void run() {
							controler.updateCurrencys();
							update.setText("update");
							update.updateUI();
						}
					});
					updater.start();
				}
			}
		});

		update.setEnabled(false);
		buttonRow.add(update);
	}

	private void updateAccountInfo() {

		double investments = selectedAccount.getInvestmentOnly(selectedAccount.getCurrency());
		double returnedMoney = selectedAccount.getReturnOnly(selectedAccount.getCurrency());
		double values = selectedAccount.getCurrentValue(selectedAccount.getCurrency());
		
		name.setText(selectedAccount.getAccountName());
		investment.setText("" + formatter.format(investments)+" "+ selectedAccount.getCurrency().toString());
		moneyInMarket.setText("" + formatter.format(investments-returnedMoney)+" "+selectedAccount.getCurrency().toString());
		
		currentValue.setText(""+formatter.format(values)+ " " +selectedAccount.getCurrency().toString());
		
		dataContainer.removeAll();

		AccountValuesTableModel valueModel = new AccountValuesTableModel(selectedAccount.getAccountCurrencys(),
				selectedAccount);

		JTable accountValueTable = new JTable(valueModel);
		// accountValueTable.setDefaultRenderer(Object.class, new
		// AccountCellRenderer());
		dataContainer.add(new JScrollPane(accountValueTable));

		JTable accountTransactionTable = new JTable(new AccountTransactionTableModel(selectedAccount));
		// accountTransactionTable.setDefaultRenderer(Object.class, new
		// AccountCellRenderer());
		dataContainer.add(new JScrollPane(accountTransactionTable));

		double profit = valueModel.getTotalValue() + selectedAccount.getValue();
		earing.setText(formatter.format(profit) + " " + selectedAccount.getCurrency().toString());
		if (profit > 0) {
			earing.setForeground(GREEN);
			percent.setForeground(GREEN);
			percent.setText("+"+formatter.format(((values+returnedMoney) / Math.abs(investments)-1) * 100)+" %");
		} else {
			earing.setForeground(RED);
			percent.setForeground(RED);
			percent.setText("-"+formatter.format((investments / (values+returnedMoney) - 1)*100 )+" %");
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
			moneyInMarket.setText("0.00");
			percent.setText("0.00");
			currentValue.setText("0.00");
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
