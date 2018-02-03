package de.markuskfrank.cryptocur.main.bussineslogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.markuskfrank.cryptocur.main.model.Account;
import de.markuskfrank.cryptocur.main.model.CurrencyValueMarket;
import de.markuskfrank.cryptocur.main.model.Currencys;
import de.markuskfrank.cryptocur.main.model.Transaction;
import de.markuskfrank.cryptocur.main.model.User;
import de.markuskfrank.cryptocur.main.technical.XMLFileSaver;
import de.markuskfrank.cryptocur.main.view.Observer;

public class MainControler {

	private final User user;
	private final List<Observer> oberservers;

	private final BackgroundUpdate updater;

	public MainControler(final User user) {
		this.user = user;
		oberservers = new ArrayList<>();
		this.updater = new BackgroundUpdate(this);
		updater.start();
	}

	public void addAccount(String accountName) {
		Account tmpAc = new Account(accountName, Currencys.EUR);
		user.addAccount(tmpAc);
		updateSelectedAccount(tmpAc);
		save();
	}

	public void save() {
		Logger.getLogger("MainControler").log(Level.INFO, "Save");;
		try {
			XMLFileSaver.persistUserToXML(user, user.getPassword());
		} catch ( Exception e) {
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

	

	private void updateSelectedAccount(Account account) {
		for (Observer ob : oberservers) {
			ob.updateSelected(account);
		}
	}

	public void updateCurrencys() {
		CurrencyValueMarket.getMarket().updateCurrencys();
		updateUI();
	}

	private void updateUI() {
		for (Observer ob : oberservers) {
			ob.notifyDataSetChanged();
		}
	}

	public Set<Currencys> getAllUserCurrencys() {
		return user.getAllUserCurrencys();
	}

	public User getCurrentUser() {
		return user;
	}

	public void deleteTransaction(Account account, Transaction transaction) {
		account.removeTransaction(transaction);
		save();
		setSelectedAccount(account.getAccountName());
	}

}
