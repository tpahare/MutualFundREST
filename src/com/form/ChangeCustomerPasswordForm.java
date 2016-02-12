/**
 * @author tpahare
 */
package com.form;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ChangeCustomerPasswordForm extends FormBean{
//	 	private String username;
//	    private String oldPassword;
	    private String newPassword;
	    private String confirmPassword;
	    private String action;

//	    public String getUsername() {
//	        return username;
//	    }

//	    public String getOldPassword() {
//	        return oldPassword;
//	    }
	    
	    public String getNewPassword() {
	        return newPassword;
	    }
	    
	    public String getConfirmPassword() {
	    	return confirmPassword;
	    }
	    public String getAction() {
	        return action;
	    }
//	    public void setUsername(String s)  { username = s.trim(); }
//	    public void setOldPassword(String s)  { oldPassword = s.trim(); }
	    public void setNewPassword(String s)  { newPassword = s; }
	    public void setConfirmPassword(String s) { confirmPassword = s; }
	    public void setAction(String s)    { action   = s;        }
	    public boolean isPresent() {
	        return action != null;
	    }

	    public List<String> getValidationErrors() {
	        List<String> errors = new ArrayList<String>();
//	        if (username == null || username.trim().length() == 0)
//	            errors.add("Username is required");
//	        if (oldPassword == null || oldPassword.length() == 0)
//	            errors.add("Old password is required");
	        if (newPassword == null || newPassword.length() == 0)
	            errors.add("New password is required");
	        if (confirmPassword == null || confirmPassword.length() == 0)
	            errors.add("Confirm password is required");
	        if (action == null)
	            errors.add("Button is required");

	        if (errors.size() > 0)
	            return errors;
	        if(!newPassword.equals(confirmPassword)){
	        	errors.add("Passwords don't match");
	        	return errors;
	        }
	        if (!action.equals("Change"))
	            errors.add("Invalid button");
	        
			
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
			
			if(confirmPassword.matches(".*\\s+.*")) {
				errors.add("Password can not contain any white space");
				return errors;
			}

	        return errors;
	    }
}
