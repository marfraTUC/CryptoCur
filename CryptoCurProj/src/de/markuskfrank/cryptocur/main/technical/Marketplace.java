package de.markuskfrank.cryptocur.main.technical;

public class Marketplace {
	
	public static final int BITSTAMP = 0;
	public static final int CRYPTONATOR = 1;
	
	public static CurrencyGetter getMarketplace(int tag){
		switch (tag) {
		case 0:
			return BitstampCurrencyGetter.getCurrencyGetter();
		case 1:
			return CryptonatorCurrencyGetter.getCurrencyGetter();

		default:
			return BitstampCurrencyGetter.getCurrencyGetter();
		}
	}
}
