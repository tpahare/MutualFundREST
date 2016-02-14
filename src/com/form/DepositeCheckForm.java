package com.form;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositeCheckForm extends FormBean {
	private String username;
	private String cash;
	private String action;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isPresent() {
		return action != null;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (username == null || username.trim().length() == 0) {
			errors.add("Username not present");
		}
		if (username.matches(".*[<>\"].*")) {
			errors.add("Username may not contain angle brackets or quotes");
		}
		
		if (cash == null || cash.trim().length() == 0) {
			errors.add("Amount is required");
		}
		
		if (cash.matches(".*[<>\"].*")) {
			errors.add("Amount may not contain angle brackets or quotes");
		}
		
		if (errors.size() > 0) {
			return errors;
		}
		try {
			if(cash.matches(".*\\s+.*")) {
				errors.add("Amount should be a number, no white spaces");
				return errors;
			}
			if (Float.parseFloat(cash) <= 0) {
				errors.add("Amount can not be negetive");
				return errors;
			}
			if (Float.parseFloat(cash) > 1000000) {
				errors.add("Amount should be Less than one million");
				return errors;
			}
			if (!action.equals("DepositCheck")) {
				errors.add("Invalid Action");
				return errors;
			}
			
			return errors;
		} catch (Exception e) {
			errors.add("Amount should be a number");
			return errors;
		}
		
		

	}

}
