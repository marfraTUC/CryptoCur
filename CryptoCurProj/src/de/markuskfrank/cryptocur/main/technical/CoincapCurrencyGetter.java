package de.markuskfrank.cryptocur.main.technical;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import de.markuskfrank.cryptocur.main.model.Currencys;

public class CoincapCurrencyGetter implements CurrencyGetter {

	private static final String URLString = "http://coincap.io/page/";
	private static CoincapCurrencyGetter singelton;

	private CoincapCurrencyGetter() {
		singelton = this;
	}

	public static synchronized CoincapCurrencyGetter getCurrencyGetter() {
		if (singelton == null) {
			new CoincapCurrencyGetter();
		}
		return singelton;
	}

	private String getCurrencyAsHTML(Currencys cur, Currencys target) throws Exception {
		StringBuilder result = new StringBuilder();
		
		URL url = new URL(buildURL(cur));
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		Thread.sleep(100);
		JSONObject jsonObj = new JSONObject(result.toString());
		return jsonObj.getString(getTragetStringFor(target));
	}

	private String buildURL(Currencys cur) {
		StringBuilder sb = new StringBuilder(URLString);
		sb.append(cur);
		sb.append("/");
		return sb.toString();
	}

	@Override
	public double getCurrency(Currencys targtCur, Currencys sourceCur) throws Exception {
		return Double.parseDouble(getCurrencyAsHTML(targtCur, sourceCur));
	}

	private String getTragetStringFor(Currencys destCur) {
		switch (destCur) {
		case EUR:
			return "price_eur";
		case USD:
			return "price_usd";
		case ETH:
			return "price_eth";
		case BTC:
			return "price_btc";

		default:
			return "";
		}
	}

}
