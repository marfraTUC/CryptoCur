package de.markuskfrank.cryptocur.main;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

import de.markuskfrank.cryptocur.main.bussineslogic.MainControler;
import de.markuskfrank.cryptocur.main.model.User;
import de.markuskfrank.cryptocur.main.technical.XMLFileSaver;
import de.markuskfrank.cryptocur.main.view.MainFrame;

public class Main {

	public static void main(String[] args) {
		Logger logger = Logger.getGlobal();
		logger.setLevel(Level.FINEST);
		User user;
		
		try {
			user = XMLFileSaver.loadFromXML("marfra");
		} catch (Exception e) {
			e.printStackTrace();
			user = new User("marfra", "Markus", "Frank");
		}
		
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
		    defaults.put("Table.alternateRowColor", Color.lightGray);
		
		new MainFrame(new MainControler(user));
		
		
	}

}
