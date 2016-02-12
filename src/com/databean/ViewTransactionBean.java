package com.databean;


public class ViewTransactionBean {
	private int transactionid;
	private int cid;
	private int fundid;
	private String executedate;
	private double shares;
	private String transactiontype;
	private double amount;
	private String fundname;
	private double price;
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ViewTransactionBean() {
		
	}

	public int getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}
	
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getFundid() {
		return fundid;
	}

	public void setFundid(int fundid) {
		this.fundid = fundid;
	}

	public String getExecutedate() {
		return executedate;
	}

	public void setExecutedate(String executedate) {
		this.executedate = executedate;
	}

	public double getShares() {
		return shares;
	}

	public void setShares(double shares) {
		this.shares = shares;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getFundname() {
		return fundname;
	}

	public void setFundname(String fundname) {
		this.fundname = fundname;
	}
	
}
