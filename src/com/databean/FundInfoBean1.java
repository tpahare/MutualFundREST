package com.databean;

public class FundInfoBean1 {

	String name;
	String shares;
	String price;
	
	public FundInfoBean1(String name, String share, String price) {
		this.name = name;
		this.shares = share;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShare() {
		return shares;
	}
	public void setShare(String share) {
		this.shares = share;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
