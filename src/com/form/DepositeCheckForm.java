package com.form;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositeCheckForm extends FormBean {
//	private String username;
	private String amount;
	private String action;

//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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
//		if (username == null || username.trim().length() == 0) {
//			errors.add("Username not present");
//		}
//		if (username.matches(".*[<>\"].*")) {
//			errors.add("Username may not contain angle brackets or quotes");
//		}
		
		System.out.print("Step1:" + amount);
		
		if (amount == null || amount.trim().length() == 0) {
			errors.add("Amount is required");
		}
		
		System.out.print("Step2:" + amount);
		
		if (amount.matches(".*[<>\"].*")) {
			errors.add("Amount may not contain angle brackets or quotes");
		}
		
		if (errors.size() > 0) {
			return errors;
		}
		try {
			if(amount.matches(".*\\s+.*")) {
				errors.add("Amount should be a number, no white spaces");
				return errors;
			}
			if (Float.parseFloat(amount) <= 0) {
				errors.add("Amount can not be negetive");
				return errors;
			}
			if (Float.parseFloat(amount) > 1000000) {
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
