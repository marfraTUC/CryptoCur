package de.markuskfrank.cryptocur.main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.markuskfrank.cryptocur.main.model.Currencys;
import de.markuskfrank.cryptocur.main.model.User;
import de.markuskfrank.cryptocur.main.technical.XMLFileSaver;

public class LoginDialog {

	private JTextField username;
	private JPasswordField password;
	private JPanel content;
	
	private JTextField newUsername;
	private JTextField firstname;
	private JTextField lastname;
	private JPasswordField newPassword;
	private JPasswordField newPasswordConf;
	private JComboBox<Currencys> baseCurrency;
	
	private JPanel newUserContent;
	
	public LoginDialog() {
		initDialog();

	}

	private void initDialog() {
		content = new JPanel(new BorderLayout(5, 5));

		JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
		label.add(new JLabel("Username", SwingConstants.RIGHT));
		label.add(new JLabel("Password", SwingConstants.RIGHT));
		content.add(label, BorderLayout.WEST);

		JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
		username = new JTextField("or enter new one!");
		username.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				username.setText("");
				
			}
		});
		controls.add(username);
		password = new JPasswordField();
		password.setPreferredSize(new Dimension(200, 30));
		controls.add(password);
		content.add(controls, BorderLayout.CENTER);
	}


	private void initNewUserDialog() {
		newUserContent = new JPanel(new BorderLayout(5, 5));

		JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
		label.add(new JLabel("Firstname", SwingConstants.RIGHT));
		label.add(new JLabel("Lastname", SwingConstants.RIGHT));
		label.add(new JLabel("Username", SwingConstants.RIGHT));
		label.add(new JLabel("Base Currency", SwingConstants.RIGHT));
		label.add(new JLabel("Password", SwingConstants.RIGHT));
		label.add(new JLabel("Confirm Password", SwingConstants.RIGHT));
		
		newUserContent.add(label, BorderLayout.WEST);

		JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
		firstname = new JTextField();
		controls.add(firstname);
		lastname = new JTextField();
		controls.add(lastname);
		newUsername = new JTextField(username.getText());
		controls.add(newUsername);
		baseCurrency = new JComboBox<Currencys>(Currencys.values());
		controls.add(baseCurrency);
		newPassword = new JPasswordField();
		newPassword.setText(new String(password.getPassword()));;
		newPassword.setPreferredSize(new Dimension(200, 30));
		controls.add(newPassword);
		newPasswordConf = new JPasswordField();
		newPasswordConf.setPreferredSize(new Dimension(200, 30));
		controls.add(newPasswordConf);
		
		newUserContent.add(controls, BorderLayout.CENTER);
	}
	
	private void initLegacyProblemDialog() {
		newUserContent = new JPanel(new BorderLayout(5, 5));

		JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
		label.add(new JLabel("Base Currency", SwingConstants.RIGHT));
		label.add(new JLabel("Password", SwingConstants.RIGHT));
		label.add(new JLabel("Confirm Password", SwingConstants.RIGHT));
		
		newUserContent.add(label, BorderLayout.WEST);

		JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
		baseCurrency = new JComboBox<Currencys>(Currencys.values());
		controls.add(baseCurrency);
		newPassword = new JPasswordField();
		newPassword.setText(new String(password.getPassword()));;
		newPassword.setPreferredSize(new Dimension(200, 30));
		controls.add(newPassword);
		newPasswordConf = new JPasswordField();
		newPasswordConf.setPreferredSize(new Dimension(200, 30));
		controls.add(newPasswordConf);
		
		newUserContent.add(controls, BorderLayout.CENTER);
		newUserContent.add(new JLabel("Please pick a password to protect your Account!"), BorderLayout.NORTH);
	}

	public User getUser() throws Exception {
		User res = null;
		int action = JOptionPane.OK_OPTION;
		int errorCounter = 5;
		while( res == null){
			action = JOptionPane.showConfirmDialog(null, content, "login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(action == JOptionPane.CANCEL_OPTION){
				return null;
			}
			if(XMLFileSaver.detectLegacyProblem(username.getText())){
				initLegacyProblemDialog();
				int updateUser = JOptionPane.showConfirmDialog(null, newUserContent, "Please Update Your Account!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				while(updateUser != JOptionPane.CANCEL_OPTION){
					if(new String(newPassword.getPassword()).isEmpty()){
						JOptionPane.showMessageDialog(null, "Enter password", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(!new String(newPassword.getPassword()).equals(new String(newPasswordConf.getPassword()))){
						JOptionPane.showMessageDialog(null, "Passwords to not match!", "Error", JOptionPane.ERROR_MESSAGE);
					}else{
						return XMLFileSaver.updateLegacyUser(username.getText(), new String(newPassword.getPassword()),(Currencys) baseCurrency.getSelectedItem());
					}
					updateUser = JOptionPane.showConfirmDialog(null, newUserContent, "Please Update Your Account!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					
				}
				
			}
			if(!XMLFileSaver.doesUserExist(username.getText())){
				int newUser = JOptionPane.showConfirmDialog(null, "Username not found! Do you want to create a new user?", "User not found", JOptionPane.ERROR_MESSAGE);
				if (newUser == JOptionPane.YES_OPTION){
					initNewUserDialog();
					int newUserCreate = JOptionPane.showConfirmDialog(null, newUserContent, "Create New User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					while(newUserCreate != JOptionPane.CANCEL_OPTION){
						if(newUserCreate == JOptionPane.YES_OPTION){
							if(firstname.getText().equals("")){
								JOptionPane.showMessageDialog(null, "Enter Firstname", "Error", JOptionPane.ERROR_MESSAGE);
							}else if(lastname.getText().equals("")){
								JOptionPane.showMessageDialog(null, "Enter Lastname", "Error", JOptionPane.ERROR_MESSAGE);
							}else if(username.getText().equals("")){
								JOptionPane.showMessageDialog(null, "Enter username", "Error", JOptionPane.ERROR_MESSAGE);
							}else if(new String(newPassword.getPassword()).isEmpty()){
								JOptionPane.showMessageDialog(null, "Enter password", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else if(!new String(newPassword.getPassword()).equals(new String(newPasswordConf.getPassword()))){
								JOptionPane.showMessageDialog(null, "Passwords to not match!", "Error", JOptionPane.ERROR_MESSAGE);
							}else{
								
								res = new User(username.getText(), firstname.getText(), lastname.getText(), (Currencys) baseCurrency.getSelectedItem(), new String(newPassword.getPassword()));
								XMLFileSaver.persistUserToXML(res, new String(newPassword.getPassword()));
								return res;
							}
							newUserCreate = JOptionPane.showConfirmDialog(null, newUserContent, "Create New User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						}
					}
					
				}
			}else{
				try {
					res = XMLFileSaver.loadUserFromXML(username.getText(), new String(password.getPassword())); 
					res.setPassword(new String(password.getPassword()));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Wrong Password, Please try again!", "Wrong Password", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			
			if(errorCounter >0){
				errorCounter--;
			}else{
				JOptionPane.showMessageDialog(null, "To many wrong attempts. Try harder!", "Good By", JOptionPane.ERROR_MESSAGE);
				return null;
			}
				
		}
		
		    
		return res;
	}


}
