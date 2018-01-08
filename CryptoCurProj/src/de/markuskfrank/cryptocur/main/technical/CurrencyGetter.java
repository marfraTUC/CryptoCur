package de.markuskfrank.cryptocur.main.technical;

import de.markuskfrank.cryptocur.main.model.Currencys;

public interface CurrencyGetter {

	double getCurrency(Currencys targtCur, Currencys sourceCur) throws Exception;

	//double getCurrency(String curString) throws Exception;
}
