package de.markuskfrank.cryptocur.main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.markuskfrank.cryptocur.main.bussineslogic.MainControler;
import de.markuskfrank.cryptocur.main.model.Account;

public class ListPanel extends CryptoPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -185521290590716945L;
	private JPanel listContainer;
	private static final double WIDTH = 0.3;

	public ListPanel(MainControler controler) {
		super(controler);
		this.setLayout(new BorderLayout());
		this.addComponentListener(this);
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog(null, "Enter Account name:");
				if(result.isEmpty() || result =="" || controler.accountExists(result)){
					JOptionPane.showMessageDialog(null, "Account name not valid", "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					controler.addAccount(result);
				}
			}
		});
		
		listContainer = new JPanel(new GridLayout(9, 1));
		
		loadAccounts();
		
		
		this.add(listContainer,BorderLayout.CENTER);
		this.add(addButton, BorderLayout.NORTH);
	}
	
	private void loadAccounts() {
		this.remove(listContainer);
		listContainer.removeAll();
		JButton button;
		for(Account account : controler.getAccounts()){
			button = new JButton(account.getAccountName());
			System.out.println(account.getAccountName());
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					controler.setSelectedAccount(e.getActionCommand());
				}
			});
			
			listContainer.add(button);
		}
		this.add(listContainer, BorderLayout.CENTER);
		this.repaint();
		this.updateUI();
		
	}

	@Override
	protected void updatePanel() {
		loadAccounts();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.setPreferredSize(new Dimension((int) (this.getTopLevelAncestor().getWidth()*WIDTH), 300));
				
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
