package de.markuskfrank.cryptocur.main.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
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
	private Currencys baseCurrency;
	private String id;
	private boolean isEncrypted;
	private String password; 

	public User() {
		accountList = new TreeMap<String, Account>();
	}

	public User(String username, String firstname, String lastname, Currencys baseCurrency, String password) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.baseCurrency = baseCurrency;
		this.id = new Random().ints(6).toString();
		accountList = new TreeMap<String, Account>();
		this.password = password;
	}

	public User(String id, String username, String firstname, String lastname, Currencys baseCurrency) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.baseCurrency = baseCurrency;
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
	
	

	public boolean isEncrypted() {
		return isEncrypted;
	}

	public void setEncrypted(boolean isEncrypted) {
		this.isEncrypted = isEncrypted;
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
	
	@XmlElement
	public Currencys getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(Currencys baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public void addNewTransaction(Account account, Transaction transactions) {
		accountList.get(account.getAccountName()).add(transactions);
	}

	public Set<Currencys> getAllUserCurrencys() {
		Set<Currencys> res = new HashSet<Currencys>();
		for (Account aAccount : accountList.values()) {
			for (Currencys currency : aAccount.getAccountCurrencys().keySet()) {
				res.add(currency);
			}
			res.remove(aAccount.getCurrency());
		}
		return res;
	}

	public Double getTotalInvestment(Currencys baseCurrency) {
		double result = 0;
		for (Account aAccount : accountList.values()) {
			if (aAccount.getCurrency() == baseCurrency) {
				result += aAccount.getInvestmentOnly(baseCurrency);
			}
		}
		return result;
	}
	
	public Double getTotalReturn(Currencys baseCurrency) {
		double result = 0;
		for (Account aAccount : accountList.values()) {
			if (aAccount.getCurrency() == baseCurrency) {
				result += aAccount.getReturnOnly(baseCurrency);
			}
		}
		return result;
	}
	
	public Double getUnreturnedMoney(Currencys baseCurrency) {
		double result = 0;
		for (Account aAccount : accountList.values()) {
			if (aAccount.getCurrency() == baseCurrency) {
				if(aAccount.getAccountCurrencys().get(baseCurrency) > 0 )
				result += aAccount.getAccountCurrencys().get(baseCurrency);
			}
		}
		return result;
	}

	public double getCurrenctValue(Currencys baseCurrency) {
		double res = 0;
		for (Account aAccount : accountList.values()) {
			if (aAccount.getCurrency() == baseCurrency) {
				res += aAccount.getCurrentValue(baseCurrency);
			}
		}
		return res;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}

}
