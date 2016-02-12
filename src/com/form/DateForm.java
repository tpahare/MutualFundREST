package com.form;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DateForm extends FormBean{
	private String pricedate;
	private String action;
	public String getPricedate() {
		return pricedate;
	}
	public void setPricedate(String date) {
		this.pricedate = date;
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
	public List<String> getValidationErrors(){
		List<String> errors = new ArrayList<String>();
		if(pricedate==null || pricedate.length()==0){
			errors.add("Date is missing");
		}
		if(errors.size()>0){
			return errors;
		}
		if(!action.equals("Transit")){
			errors.add("Invalid Date");
		}
		return errors;
	}
}
