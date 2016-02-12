package com.form;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ChangeEmployeePasswordForm extends FormBean {
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private String action;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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
		
		if(oldPassword == null||oldPassword.length() == 0){
			errors.add("Old Password is required");
		}
		
		if(newPassword == null || newPassword.length() == 0){
			errors.add("New Password is required");
		}
		
		if(confirmPassword == null || confirmPassword.length() == 0){
			errors.add("Confirm Password is required");
		}
		
		if(errors.size() > 0){
			return errors;
		}
		
		
		if(action == null || !action.equals("Change")){
			errors.add("Invalid action");
		}
		
		if(oldPassword.matches(".*[<>\"].*")){
			errors.add("Old Password may not contain angle brackets or quotes");
			return errors;
		}
		
		if(newPassword.matches(".*[<>\"].*")){
			errors.add("New Password may not contain angle brackets or quotes");
			return errors;
		}
		
		if(confirmPassword.matches(".*[<>\"].*")){
			errors.add("Confirm Password may not contain angle brackets or quotes");
			return errors;
		}
		
		if(newPassword.matches(".*\\s+.*")) {
			errors.add("Password can not contain any white space");
			return errors;
		}
		if(oldPassword.matches(".*\\s+.*")) {
			errors.add("Password can not contain any white space");
			return errors;
		}
		
		if(!newPassword.equals(confirmPassword)){
        	errors.add("Passwords don't match");
        	return errors;
        }
		if (newPassword.equals(oldPassword)) {
			errors.add("New Password cannot be same as Old Password");
			return errors;
		}
		
		return errors;
	}
}