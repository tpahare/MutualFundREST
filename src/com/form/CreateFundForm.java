/**
 * @author tpahare
 */
package com.form;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean {
	private String name;
	private String symbol;
	//private String action; REMOVE ALL ACTIONS NOW

	public String getName() {
		return name;
	}

	public void setName(String fundName) {
		this.name = fundName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String ticker) {
		this.symbol = ticker;
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
		if (name == null || name.trim().length() == 0) {
			errors.add("Fund Name is required");
		}
		if (name.matches(".*[<>\"].*")) {
			errors.add("Fund name may not contain angle brackets or quotes");
		}
		if (symbol == null || symbol.trim().length() == 0) {
			errors.add("Ticker is required");
		}
		if (symbol.matches(".*[<>\"].*")) {
			errors.add("Ticker may not contain angle brackets or quotes");
		}
		if (symbol.trim().length() > 5) {
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
