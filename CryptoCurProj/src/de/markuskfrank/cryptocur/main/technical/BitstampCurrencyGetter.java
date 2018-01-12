package de.markuskfrank.cryptocur.main.technical;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import de.markuskfrank.cryptocur.main.model.Currencys;

public class BitstampCurrencyGetter implements CurrencyGetter {

	private static final String URLString = "https://www.bitstamp.net/api/v2/ticker/";
	private static BitstampCurrencyGetter singelton;
	private final String GET_INFO = "last";
	
	private BitstampCurrencyGetter() {
		singelton = this;
	}
	
	public static synchronized BitstampCurrencyGetter getCurrencyGetter(){
		if(singelton == null){
			new BitstampCurrencyGetter();
		}
		return singelton;
	}
	
	public  String getCurrencyAsHTML(String cur) throws Exception {
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
	      Thread.sleep(100);
	      JSONObject jsonObj = new JSONObject(result.toString());
	      return jsonObj.getString(GET_INFO);
	   }
	
	@Override
	public double getCurrency(Currencys targtCur, Currencys sourceCur) throws Exception {
		return Double.parseDouble(getCurrencyAsHTML(targtCur.toString()+sourceCur.toString()));
	}

}
