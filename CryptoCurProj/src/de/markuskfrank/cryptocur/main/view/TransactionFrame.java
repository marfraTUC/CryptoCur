package de.markuskfrank.cryptocur.main.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.markuskfrank.cryptocur.main.bussineslogic.MainControler;
import de.markuskfrank.cryptocur.main.model.Account;
import de.markuskfrank.cryptocur.main.model.Currencys;
import de.markuskfrank.cryptocur.main.model.TradeActions;
import de.markuskfrank.cryptocur.main.model.Transaction;

public class TransactionFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7085158405627350605L;
	private final JComboBox<TradeActions> action;
	private final JComboBox<Currencys> curIn, curOut;
	private final JTextArea amountIn, amountOut;
	private final MainControler controler;
	private final Account account;
	private final Transaction transaction; 


	public TransactionFrame(Transaction transaction, Account account, MainControler controler) {
		super("New Transactions");
		curIn = new JComboBox<Currencys>(Currencys.values());
		curOut = new JComboBox<Currencys>(Currencys.values());
		action = new JComboBox<>(TradeActions.values());
		
		this.controler = controler;
		this.account = account;
		this.transaction = transaction;
		
		amountIn = new JTextArea();
		amountOut = new JTextArea();
		
		init();
		setTransactionsData();

	}


	public TransactionFrame(Account account, MainControler controler) {
		super("New Transactions");
		curIn = new JComboBox<Currencys>(Currencys.values());
		curOut = new JComboBox<Currencys>(Currencys.values());
		action = new JComboBox<>(TradeActions.values());
		
		this.controler = controler;
		this.account = account;
		
		amountIn = new JTextArea();
		amountOut = new JTextArea();
		this.transaction = null;
		init();

	}
	
	
	private void setTransactionsData() {
		curIn.setSelectedItem(transaction.getBuyCurrency());
		curOut.setSelectedItem(transaction.getPayCurrency());
		action.setSelectedItem(transaction.getAction());
		amountIn.setText(""+transaction.getAmount());
		amountOut.setText(""+transaction.getPrice());
	}

	private void init() {
		JPanel transaction = new JPanel(new BorderLayout());
		transaction.add(new JLabel("New Transaction"), BorderLayout.NORTH);
		JButton close, save;

		close = new JButton("cancel");
		close.addActionListener(this);

		save = new JButton("save");
		save.addActionListener(this);

		JPanel container = new JPanel(new GridLayout(6, 2, 20, 20));

		container.add(new JLabel("Action"));
		container.add(action);
		container.add(new JLabel("Currency reciving"));
		container.add(curIn);
		container.add(new JLabel("Amount reciving:"));
		container.add(amountIn);
		container.add(new JLabel("Currency giving"));
		container.add(curOut);
		container.add(new JLabel("Amount giving"));
		container.add(amountOut);
		container.add(close);
		container.add(save);

		transaction.add(container);

		this.add(transaction);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private boolean checkInput() {
		try {
			Double.parseDouble(amountIn.getText());
			Double.parseDouble(amountOut.getText());
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("cancel")) {
			this.dispose();
		} else if (e.getActionCommand().equals("save")) {
			if (checkInput()) {
				if(transaction == null){
					createNewTransaction();
				}else{
					updateTransaction();
				}

			}
		}
	}


	private void updateTransaction() {
		transaction.setAction((TradeActions) action.getSelectedItem());
		transaction.setBuyCurrency((Currencys) curIn.getSelectedItem());
		transaction.setPayCurrency((Currencys) curOut.getSelectedItem());
		transaction.setAmount(Double.parseDouble(amountIn.getText()));
		transaction.setPrice(Double.parseDouble(amountOut.getText()));
		controler.save();
		controler.setSelectedAccount(account.getAccountName());
		this.dispose();	
	}


	private void createNewTransaction() {
		Transaction trans = new Transaction((TradeActions) action.getSelectedItem(),
				(Currencys) curIn.getSelectedItem(), Double.parseDouble(amountIn.getText()),
				(Currencys) curOut.getSelectedItem(), Double.parseDouble(amountOut.getText()) );
		controler.addNewTransction(account, trans);
		this.dispose();		
	}



}
