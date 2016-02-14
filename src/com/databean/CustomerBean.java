package com.databean;

import org.genericdao.PrimaryKey;

/**
 * @author tpahare
 */

@PrimaryKey("cid")
public class CustomerBean {
	private int cid;
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private String addr_line1;
	private String addr_line2;
	private String city;
	private String state;
	private String zip;
	private long cash;
	
	
	public CustomerBean() {
		
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddr_line1() {
		return addr_line1;
	}

	public void setAddr_line1(String addrline1) {
		this.addr_line1 = addrline1;
	}

	public String getAddr_line2() {
		return addr_line2;
	}

	public void setAddr_line2(String addrline2) {
		this.addr_line2 = addrline2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public long getCash() {
		return cash;
	}

	public void setCash(long cash) {
		this.cash = cash;
	}


}
