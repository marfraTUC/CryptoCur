package de.markuskfrank.cryptocur.main;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import de.markuskfrank.cryptocur.main.bussineslogic.MainControler;
import de.markuskfrank.cryptocur.main.model.User;
import de.markuskfrank.cryptocur.main.view.LoginDialog;
import de.markuskfrank.cryptocur.main.view.MainFrame;

public class Main {

	public static void main(String[] args) {
		Logger logger = Logger.getGlobal();
		logger.setLevel(Level.FINEST);
		User user;
		
		user = loadUserData();
		if(user == null){
			JOptionPane.showMessageDialog(null, "Error while loading user -> Exit now!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		setUISettings();
		
		new MainFrame(new MainControler(user));
		
	}

	private static User loadUserData() {
		User user = null;
		
		try {
			user = new LoginDialog().getUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	private static void setUISettings() {
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", Color.lightGray);
	}

}
