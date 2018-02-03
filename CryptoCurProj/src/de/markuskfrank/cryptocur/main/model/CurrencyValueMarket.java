package de.markuskfrank.cryptocur.main.model;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.markuskfrank.cryptocur.main.technical.CurrencyGetter;
import de.markuskfrank.cryptocur.main.technical.Marketplace;

public class CurrencyValueMarket {

	private static CurrencyValueMarket market;
	private Map<Currencys, Map<Currencys, Double>> currencyValue;
	private final CurrencyGetter marketplace;

	private CurrencyValueMarket() {
		this.currencyValue = new HashMap<>();
		marketplace = Marketplace.getMarketplace(Marketplace.CRYPTOCOMPARE);
		market = this;
	}

	public synchronized static CurrencyValueMarket getMarket() {
		if (market == null) {
			new CurrencyValueMarket();
		}
		return market;
	}

	public Double getCurrencyValueIn(Currencys cryptoCurrency, Currencys baseCurrency) throws Exception {
		if (!currencyValue.containsKey(baseCurrency)) {
			currencyValue.put(baseCurrency, new HashMap<>());
		}
		if (!currencyValue.get(baseCurrency).containsKey(cryptoCurrency)) {
			Double value = marketplace.getCurrency(cryptoCurrency, baseCurrency);
			currencyValue.get(baseCurrency).put(cryptoCurrency, value);
		}
		return currencyValue.get(baseCurrency).get(cryptoCurrency);
	}

	public void updateCurrencys() {
		Map<Currencys, Map<Currencys, Double>> res = new HashMap<>();
		for (Currencys baseCurrency : currencyValue.keySet()) {
			res.put(baseCurrency, new HashMap<>());
			for (Currencys targetCurrency : currencyValue.get(baseCurrency).keySet()) {
				if (targetCurrency != baseCurrency) {
					try {
						Double value = marketplace.getCurrency(baseCurrency, targetCurrency);
						res.get(baseCurrency).put(baseCurrency, value);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Logger.getGlobal().log(Level.WARNING,
								"Could not get value for " + targetCurrency + " for base currency " + baseCurrency, e);
					}
				}
			}
		}
		currencyValue = res;
	}
}
