package de.markuskfrank.cryptocur.main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	private static final double WIDTH = 0.25;
	private final JPanel container = new JPanel(new BorderLayout());
	private final JLabel logo;
	private BufferedImage image;

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
		
		container.add(listContainer, BorderLayout.CENTER);
		container.add(addButton, BorderLayout.NORTH);
		
		
		try {
			image = ImageIO.read(getClass().getResource("logo.png"));
		} catch (IOException e1) {
			image = null;
			e1.printStackTrace();
		}
	
		logo = new JLabel();
		logo.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				logo.setIcon(new ImageIcon(image.getScaledInstance(logo.getWidth(), (int)(logo.getWidth()*0.19), Image.SCALE_SMOOTH)));
								
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		this.add(container,BorderLayout.CENTER);
		this.add(logo, BorderLayout.NORTH);
	}
	
	private void loadAccounts() {
		container.remove(listContainer);
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
		container.add(listContainer, BorderLayout.CENTER);
		container.repaint();
		container.updateUI();
		
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
