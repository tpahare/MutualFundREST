/**
 * @author tpahare
 */
package com.form;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean {
	private String fundName;
	private String ticker;
	//private String action; REMOVE ALL ACTIONS NOW

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	/*public String getAction() {
		return action;
	}*/

/*	public void setAction(String action) {
		this.action = action;
	}

	public boolean isPresent() {
		return action != null;
	}*/

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (fundName == null || fundName.trim().length() == 0) {
			errors.add("Fund Name is required");
		}
		if (fundName.matches(".*[<>\"].*")) {
			errors.add("Fund name may not contain angle brackets or quotes");
		}
		if (ticker == null || ticker.trim().length() == 0) {
			errors.add("Ticker is required");
		}
		if (ticker.matches(".*[<>\"].*")) {
			errors.add("Ticker may not contain angle brackets or quotes");
		}
		if (ticker.trim().length() > 5) {
			errors.add("Ticker length cannot exceed 5 characters");
		}
		if (errors.size() > 0) {
			return errors;
		}
		/*if (!action.equals("CreateFund")) {
			errors.add("Invalid Action");
		}*/
		return errors;

	}

}
