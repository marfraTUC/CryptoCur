package de.markuskfrank.cryptocur.main.technical;

public class Marketplace {
	
	public static final int BITSTAMP = 0;
	public static final int CRYPTONATOR = 1;
	public static final int COINCAP = 2;
	public static final int CRYPTOCOMPARE = 3;
	
	public static CurrencyGetter getMarketplace(int tag){
		switch (tag) {
		case 0:
			return BitstampCurrencyGetter.getCurrencyGetter();
		case 1:
			return CryptonatorCurrencyGetter.getCurrencyGetter();
		case 2:
			return CoincapCurrencyGetter.getCurrencyGetter();
		case 3:
			return CryptoCompareCurrencyGetter.getCurrencyGetter();

		default:
			return BitstampCurrencyGetter.getCurrencyGetter();
		}
	}
}
