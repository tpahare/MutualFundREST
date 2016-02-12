package com.form;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class BuyFundFromResearchFundForm extends FormBean{

	private String money;
	private String action;
	
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
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
		
		if (money == null || money.trim().length() == 0) {
			errors.add("Money Not Present");
		}
		if (money.matches(".*[<>\"].*")) {
			errors.add("Money may not contain angle brackets or quotes");
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
