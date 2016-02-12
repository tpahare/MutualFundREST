package com.databean;

/**
 * @author faisalshahnewaz (andrew id: sfaisal)
 */
import org.genericdao.PrimaryKey;

@PrimaryKey("customerid,fundid")
public class PositionBean {
	private int customerid;
	private int fundid;
	private long shares;
	
	public PositionBean() {
		
	}
	
	public int getCustomerid() {
		return customerid;
	}
	
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	
	public int getFundid() {
		return fundid;
	}
	
	public void setFundid(int fundid) {
		this.fundid = fundid;
	}
	
	public long getShares() {
		return shares;
	}
	
	public void setShares(long shares) {
		this.shares = shares;
	}
	
}
