package com.databean;

import org.genericdao.PrimaryKey;

/**
 * @author Xuesong Zhang (Andrew ID: xuesongz)
 */

@PrimaryKey("username")
public class EmployeeBean {
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	
	public EmployeeBean() {
		
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


}
