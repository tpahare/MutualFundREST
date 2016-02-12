package com.form;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class BuyFundForm extends FormBean {
	private String fundsymbol;
	private String money;
	private String action;
	
	public String getFundsymbol() {
		return fundsymbol;
	}
	public String getMoney() {
		return money;
	}
	public String getAction() {
		return action;
	}
	public void setFundsymbol(String s) {
		fundsymbol = s;
	}
	public void setMoney(String s) {
		money = s;
	}
	public void setAction(String s) {
		action = s;
	}
	public boolean isPresent() {
		return action != null;
	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (fundsymbol == null || fundsymbol.trim().length() == 0) {
			errors.add("Fund Symbol is required");
			return errors;
		}
		if (fundsymbol.matches(".*[<>\"].*")) {
			errors.add("Fund Symbol may not contain angle brackets or quotes");
			return errors;
		}
		if (money == null || money.trim().length() == 0) {
			errors.add("Dollar Amount is required");
			return errors;
		}
		if (money.matches(".*[<>\"].*")) {
			errors.add("Dollar Amount may not contain angle brackets or quotes");
			return errors;
		}
		/*if (money.trim().length() > 5) {
			errors.add("Money length cannot exceed 5 characters");
		}*/
		if (!action.equals("BuyFund")) {
			errors.add("Invalid Action");
		}
		return errors;
	}
}
