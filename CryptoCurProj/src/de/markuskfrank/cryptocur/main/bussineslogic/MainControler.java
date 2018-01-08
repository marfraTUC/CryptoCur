package de.markuskfrank.cryptocur.main.bussineslogic;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import de.markuskfrank.cryptocur.main.model.Account;
import de.markuskfrank.cryptocur.main.model.Currencys;
import de.markuskfrank.cryptocur.main.model.Transaction;
import de.markuskfrank.cryptocur.main.model.User;
import de.markuskfrank.cryptocur.main.technical.CurrencyGetter;
import de.markuskfrank.cryptocur.main.technical.Marketplace;
import de.markuskfrank.cryptocur.main.technical.XMLFileSaver;
import de.markuskfrank.cryptocur.main.view.Observer;

public class MainControler {

	private final User user;
	private final List<Observer> oberservers;
	private Map<Currencys, Map<Currencys, Double>> currencyValue;
	private final CurrencyGetter marketplace;
	private final BackgroundUpdate updater;

	public MainControler(final User user) {
		this.user = user;
		oberservers = new ArrayList<>();
		this.currencyValue = new HashMap<>();
		marketplace = Marketplace.getMarketplace(Marketplace.CRYPTONATOR);
		this.updater = new BackgroundUpdate(this);
		updater.start();
	}

	public void addAccount(String accountName) {
		Account tmpAc = new Account(accountName, Currencys.EUR);
		user.addAccount(tmpAc);
		updateSelectedAccount(tmpAc);
		save();
	}

	private void save() {
		System.out.println("save");
		try {
			XMLFileSaver.persistToXML(user);
		} catch (FileNotFoundException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Collection<Account> getAccounts() {
		return user.getAccounts();
	}

	public void setSelectedAccount(String accountName) {
		if (accountName != null) {
			updateSelectedAccount(user.getAccount(accountName));
		} else {
			updateSelectedAccount(null);
		}
	}

	public void addObserver(Observer ob) {
		oberservers.add(ob);
	}

	public boolean accountExists(String accountName) {
		return user.accountExists(accountName);
	}

	public void closeAccount(Account selectedAccount) {
		user.removeAccount(selectedAccount);
		save();
		setSelectedAccount(null);

	}

	public void addNewTransction(Account account, Transaction transactions) {
		user.addNewTransaction(account, transactions);
		save();
		updateUI();
	}

	public Double getCurrencyValueIn(Currencys cryptoCurrency, Currencys baseCurrency) throws Exception {
		if(!currencyValue.containsKey(baseCurrency)){
			currencyValue.put(baseCurrency, new HashMap<>());
		}
		if (!currencyValue.get(baseCurrency).containsKey(cryptoCurrency)) {
			Double value = marketplace.getCurrency(cryptoCurrency, baseCurrency);
			currencyValue.get(baseCurrency).put(cryptoCurrency, value);
		}
		return currencyValue.get(baseCurrency).get(cryptoCurrency);
	}

	private void updateSelectedAccount(Account account) {
		for (Observer ob : oberservers) {
			ob.updateSelected(account);
		}
	}

	public void updateCurrencys() {
		Map<Currencys, Map<Currencys, Double>> res = new HashMap<>(); 
		for (Currencys baseCurrency : currencyValue.keySet()) {
			res.put(baseCurrency, new HashMap<>());
			for (Currencys targetCurrency : currencyValue.get(baseCurrency).keySet()) {
				try {
					Double value = marketplace.getCurrency(baseCurrency, targetCurrency);
					res.get(baseCurrency).put(baseCurrency, value);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		currencyValue = res;
		updateUI();
	}

	private void updateUI() {
		for (Observer ob : oberservers) {
			ob.notifyDataSetChanged();
		}
	}

	public List<Currencys> getAllUserCurrencys() {
		return user.getAllUserCurrencys();
	}

}
