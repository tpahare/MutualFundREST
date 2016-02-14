package com.form;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RequestCheckForm extends FormBean {
//	private String username;
	private String cashValue;
//	private String action;

//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}

	public String getCashValue() {
		return cashValue;
	}

	public void setCashValue(String cashValue) {
		this.cashValue = cashValue;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		
		System.out.print("Step1:" + cashValue);
		
		if (cashValue == null || cashValue.trim().length() == 0) {
			errors.add("Amount is required");
			return errors;
		}
		
		System.out.print("Step2:" + cashValue);
		
		if (cashValue.matches(".*[<>\"].*")) {
			errors.add("Amount may not contain angle brackets or quotes");
			return errors;
		}
		try{
//			System.out.println("---");
			
			if(cashValue.matches(".*\\s+.*")) {
				errors.add("Amount should be a number, no white spaces");
				return errors;
			}
//			System.out.println("---");
			if (Float.parseFloat(cashValue.trim()) <= 0) {
				errors.add("Amount should be more than zero");
				return errors;
			}
//			System.out.println("---");
			if(Float.parseFloat(cashValue.trim()) > 1000000){
				errors.add("Amount cannot be more than $1,000,000");
				return errors;
			}
/*//			System.out.println("---");
			BigDecimal amountBD = new BigDecimal(cashValue);
			if (amountBD.scale() > 2){
				errors.add("Amount should have at most two decimal places");
				return errors;
			}*/
			
		} catch(Exception ex){
			errors.add("Please enter a valid amount");
			return errors;
		}
		
		return errors;

	}

}
