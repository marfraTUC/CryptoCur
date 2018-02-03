package de.markuskfrank.cryptocur.main.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Account {

	private String accountName;
	private List<Transaction> transactions;
	private Currencys currency;
	
	public Account(){
		transactions = new ArrayList<Transaction>();
	}
	
	public Account(String accountName, Currencys currency) {
		this.accountName = accountName;
		this.currency = currency;
		transactions = new ArrayList<Transaction>();
	}
	
	public void add(Transaction action){
		if(transactions == null){
			transactions = new ArrayList<Transaction>();
		}
		transactions.add(action);
	}
	
	@XmlElement  
	public List<Transaction> getTransactions(){
		return transactions;
	}

	@XmlElement  
	public String getAccountName() {
		return accountName;
	}
	
	public Map<Currencys, Double> getAccountCurrencys(){
		HashMap<Currencys, Double> result = new HashMap<>();
		
		for(Transaction transaction : transactions){
			if(!result.containsKey(transaction.getBuyCurrency())){
				result.put(transaction.getBuyCurrency(), 0.00);
			}
			if(!result.containsKey(transaction.getPayCurrency())){
				result.put(transaction.getPayCurrency(), 0.00);
			}
		}

		Double tmp; 
		for(Transaction transaction : transactions){

			switch (transaction.getAction()) {
			case buy:
			
				tmp = result.get(transaction.getBuyCurrency());
				result.put(transaction.getBuyCurrency(), new Double(tmp + transaction.getAmount()));
				
				
				tmp = result.get(transaction.getPayCurrency());
				result.put(transaction.getPayCurrency(), new Double(tmp - transaction.getPrice()));
				break;
			case sell:
				tmp = result.get(transaction.getBuyCurrency());
				result.put(transaction.getBuyCurrency(), new Double(tmp + transaction.getAmount()));
				
				tmp = result.get(transaction.getPayCurrency());
				result.put(transaction.getPayCurrency(), new Double(tmp - transaction.getPrice()));
			
				break;
			case transfer:
				tmp = result.get(transaction.getBuyCurrency());
				result.put(transaction.getBuyCurrency(), new Double(tmp + transaction.getAmount() - transaction.getPrice()));
				
				break;
			default:
				break;
			}
		}
		//Logger.getGlobal().log(Level.SEVERE, "getAccountCurrencys"+ result.toString());
		
		return result;
	}
	
	public double getInvestmentOnly(Currencys cur){
		double res = 0;
		for(Transaction transaction : transactions){
			if(transaction.getPayCurrency() == cur){
				res += transaction.getPrice();
			}
		}
		
		return res;
	}
	
	public double getReturnOnly(Currencys baseCurrency) {
		double res = 0;
		for(Transaction transaction : transactions){
			if(transaction.getBuyCurrency() == baseCurrency){
				res += transaction.getAmount();
			}
		}
		
		return res;
	}

	@XmlAttribute  
	public double getValue() {		
		try{
			return getAccountCurrencys().get(currency);
		}catch(Exception e){
			e.printStackTrace();
			return 0.00;
		}
	}

	@XmlElement  
	public Currencys getCurrency() {
		return currency;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void setCurrency(Currencys currency) {
		this.currency = currency;
	}

	public double getCurrentValue(Currencys baseCurrency) {
		double res = 0;
		for (Currencys aCurrencys : getAccountCurrencys().keySet()) {
			if (aCurrencys != baseCurrency) {
				try {
					res += getAccountCurrencys().get(aCurrencys)
							* CurrencyValueMarket.getMarket().getCurrencyValueIn(aCurrencys, baseCurrency);
				} catch (Exception e) {
					Logger.getGlobal().log(Level.WARNING,
							"Could not get value for " + aCurrencys + " for base currency " + baseCurrency, e);
					// e.printStackTrace();
				}
			}
		}
		return res;
		
	}

	public void removeTransaction(Transaction transaction) {
		transactions.remove(transaction);
	}


	
}
