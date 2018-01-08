package de.markuskfrank.cryptocur.main.technical;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import de.markuskfrank.cryptocur.main.model.Currencys;

public class CryptonatorCurrencyGetter implements CurrencyGetter {

	private static final String URLString = "https://api.cryptonator.com/api/ticker/";
	private static CryptonatorCurrencyGetter singelton;
	private final String GET_INFO = "price";
	
	private CryptonatorCurrencyGetter() {
		singelton = this;
	}
	
	public static synchronized CryptonatorCurrencyGetter getCurrencyGetter(){
		if(singelton == null){
			new CryptonatorCurrencyGetter();
		}
		return singelton;
	}
	
	private  String getCurrencyAsHTML(String cur) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(URLString+cur+"/");
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      
	      JSONObject jsonObj = new JSONObject(result.toString());
	      return jsonObj.getJSONObject("ticker").getString(GET_INFO);
	   }
	
	@Override
	public double getCurrency(Currencys targtCur, Currencys sourceCur) throws Exception {
		return Double.parseDouble(getCurrencyAsHTML(targtCur.toString()+"-"+sourceCur.toString()));
	}

	
}
