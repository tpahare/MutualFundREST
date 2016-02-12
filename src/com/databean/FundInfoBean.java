package com.databean;

public class FundInfoBean {
	int fundid;
	String ticker;
	String fundname;
	long share;
	long amount;
	public FundInfoBean(int fundid, String ticker, String fundname, long share, long amount) {
		this.fundid = fundid;
		this.ticker = ticker;
		this.fundname = fundname;getClass();
		this.share = share;
		this.amount = amount;
	}
	public void setAmount(long amount) {
		amount = amount;
	}
	public long getAmount() {
		return amount;
	}
	public void setFundid(int fundid) {
		fundid = fundid;
	}
	public int getFundid() {
		return fundid;
	}
	public void setTicker(String ticker) {
		ticker = ticker; 
	}
	public String getTicker() {
		return ticker;
	}
	public void setFundname(String fundname) {
		fundname = fundname;
	}
	public String getFundname() {
		return fundname;
	}
	public void setShare(double share) {
		share = share;
	}
	public long getShare() {
		return share;
	}
}
