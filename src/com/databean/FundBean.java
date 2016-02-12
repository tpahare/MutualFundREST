package com.databean;

/**
 * @author faisalshahnewaz (andrew id: sfaisal)
 */
import org.genericdao.PrimaryKey;

@PrimaryKey("fundid")
public class FundBean {

	private int fundid;
	private String fundName;
	private String ticker;
	
	public int getFundid() {
		return fundid;
	}
	
	public void setFundid(int fundid) {
		this.fundid = fundid;
	}
	
	public String getFundName() {
		return fundName;
	}
	
	public void setFundName(String name) {
		this.fundName = name;
	}
	
	public String getTicker() {
		return ticker;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
}
