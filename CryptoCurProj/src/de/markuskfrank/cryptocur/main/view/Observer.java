package de.markuskfrank.cryptocur.main.view;

import de.markuskfrank.cryptocur.main.model.Account;

public interface Observer {

	public void updateSelected(Account account);

	public void notifyDataSetChanged();
	
}
