package com.databean;

/**
 * @author faisalshahnewaz (andrew id: sfaisal)
 */
import org.genericdao.PrimaryKey;

@PrimaryKey("fundid,pricedate")
public class FundPriceHistoryBean {
	private int fundid;
	private String pricedate;
	private long price;
	
	public FundPriceHistoryBean() {
		
	}
	
	public int getFundid() {
		return fundid;
	}
	
	public void setFundid(int fundid) {
		this.fundid = fundid;
	}
	
    public String getPricedate() {
		return pricedate;
	}
	
	public void setPricedate(String pricedate) {
		this.pricedate = pricedate;
	}
	
	public long getPrice() {
		return price;
	}
	
	public void setPrice(long price) {
		this.price = price;
	}
	
}
