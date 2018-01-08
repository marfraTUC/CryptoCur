package de.markuskfrank.cryptocur.main.model;

import javax.xml.bind.annotation.XmlElement;

public class Transaction {

	private TradeActions action;
	private Currencys buyCurrency;
	private Currencys payCurrency;
	private double price;
	private double amount;

	public Transaction() {

	}

	public Transaction(TradeActions action, Currencys buyCur, double amount, Currencys giveCur, double price) {
		if (action == null || buyCur == null || giveCur == null) {
			throw new IllegalArgumentException();
		}
		this.action = action;
		this.payCurrency = giveCur;
		this.buyCurrency = buyCur;
		this.price = price;
		this.amount = amount;
	}

	@XmlElement
	public TradeActions getAction() {
		return action;
	}

	@XmlElement
	public Currencys getBuyCurrency() {
		return buyCurrency;
	}

	@XmlElement
	public Currencys getPayCurrency() {
		return payCurrency;
	}

	@XmlElement
	public double getPrice() {
		return price;
	}

	@XmlElement
	public double getAmount() {
		return amount;
	}

	public void setAction(TradeActions action) {
		this.action = action;
	}

	public void setBuyCurrency(Currencys buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	public void setPayCurrency(Currencys payCurrency) {
		this.payCurrency = payCurrency;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(action).append(",").append(buyCurrency).append(",").append(amount).append(",").append(payCurrency)
				.append(",").append(price);
		return result.toString();
	}

}
