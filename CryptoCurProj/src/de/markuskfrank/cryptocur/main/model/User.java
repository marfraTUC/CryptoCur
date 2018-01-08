package de.markuskfrank.cryptocur.main.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement  
public class User {

	private final Map<String, Account> accountList;
	
	private String username;
	private String firstname;
	private String lastname;
	private String id;
	
	public User() {
		accountList = new TreeMap<String, Account>();
	}
	
	public User(String username, String firstname, String lastname) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.id = new Random().ints(6).toString();
		accountList = new TreeMap<String, Account>();
	}
	
	public User(String id, String username, String firstname, String lastname) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.id = id;
		accountList = new TreeMap<String, Account>();
	}

	public void addAccount(Account tmpAc) {
		accountList.put(tmpAc.getAccountName(), tmpAc);
		
	}

	public Collection<Account> getAccounts() {
		return accountList.values();
	}

	public Account getAccount(String accountName) {
		return accountList.get(accountName);
	}

	public boolean accountExists(String accountName) {
		return accountList.containsKey(accountName);
	}

	public void removeAccount(Account selectedAccount) {
		accountList.remove(selectedAccount.getAccountName());
	}

	@XmlElement  
	public Map<String, Account> getAccountList() {
		return accountList;
	}

	@XmlElement   
	public String getUsername() {
		return username;
	}

	@XmlElement    
	public String getFirstname() {
		return firstname;
	}

	@XmlElement    
	public String getLastname() {
		return lastname;
	}

	@XmlAttribute  
	public String getId() {
		return id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addNewTransaction(Account account, Transaction transactions) {
		accountList.get(account.getAccountName()).add(transactions);
	}

	public List<Currencys> getAllUserCurrencys() {
		ArrayList<Currencys> res = new ArrayList<>();
		for(Account aAccount : accountList.values()){
			for(Currencys currency: aAccount.getAccountCurrencys().keySet()){
				res.add(currency);
			}
			res.remove(aAccount.getCurrency());
		}
		return res;
	}
	
	
}
