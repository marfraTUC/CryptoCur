package de.markuskfrank.cryptocur.main.technical;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import de.markuskfrank.cryptocur.main.model.Currencys;

public class CryptoCompareCurrencyGetter implements CurrencyGetter {

	private static final String URLString = "https://min-api.cryptocompare.com/data/price?";
	private static CryptoCompareCurrencyGetter singelton;
	private final String GET_INFO = "price";
	
	private CryptoCompareCurrencyGetter() {
		singelton = this;
	}
	
	public static synchronized CryptoCompareCurrencyGetter getCurrencyGetter(){
		if(singelton == null){
			new CryptoCompareCurrencyGetter();
		}
		return singelton;
	}
	
	private  Double getCurrencyAsHTML(Currencys cur, Currencys target) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(buildURL(cur, target));
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
	      return jsonObj.getDouble(target.toString());
	   }
	
	private String buildURL(Currencys cur, Currencys target) {
		StringBuilder sb = new StringBuilder(URLString);
		sb.append("fsym=");
		sb.append(cur.toString());
		sb.append("&tsyms=");
		sb.append(target.toString());
		return sb.toString();
	}
	
	@Override
	public double getCurrency(Currencys targtCur, Currencys sourceCur) throws Exception {
		return getCurrencyAsHTML(targtCur, sourceCur);
	}

	
}
