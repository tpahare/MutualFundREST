package com.databean;

/**
 * @author faisalshahnewaz (andrew id: sfaisal)
 */
import org.genericdao.PrimaryKey;

@PrimaryKey("fundid")
public class FundBean {

	private int fundid;
	private String name;
	private String symbol;
	
	public int getFundid() {
		return fundid;
	}
	
	public void setFundid(int fundid) {
		this.fundid = fundid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
