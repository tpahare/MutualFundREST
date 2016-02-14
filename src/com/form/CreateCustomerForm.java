package com.form;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

/**
 * 
 * @author faisalshahnewaz
 *
 */
public class CreateCustomerForm extends FormBean{
	
	private String username;
	private String firstname;
	private String lastname;
	private String password;
//	private String confirmpassword;
	private String addr_line1;
	private String addr_line2;
	private String city;
	private String state;
	private String zip;
//	private String action;
	
//	public String getAction() {
//		return action;
//	}
//	public void setAction(String action) {
//		this.action = action;
//	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = trimAndConvert(username, "<>\"");
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = trimAndConvert(firstname, "<>\"");
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = trimAndConvert(lastname, "<>\"");
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddr_line1() {
		return addr_line1;
	}
	public void setAddr_line1(String addrline1) {
		this.addr_line1 = trimAndConvert(addrline1, "<>\"");
		System.out.println();
	}
	public String getAddr_line2() {
		return addr_line2;
	}
	public void setAddr_line2(String addrline2) {
		this.addr_line2 = trimAndConvert(addrline2, "<>\"");
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = trimAndConvert(city, "<>\"");
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = trimAndConvert(state, "<>\"");
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = trimAndConvert(zip, "<>\"");
	}
//	public String getConfirmpassword() {
//		return confirmpassword;
//	}
//	public void setConfirmpassword(String confirmpassword) {
//		this.confirmpassword = confirmpassword;
//	}
//	public boolean isPresent() {
//		return action != null;
//	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		
		
		if (firstname == null || firstname.length() == 0) {
			System.out.println("q1");
			errors.add("First Name is required");
		}
		
		if (lastname == null || lastname.length() == 0) {
			System.out.println("q2");
			errors.add("Last Name is required");
		}

		if (username == null || username.length() == 0) {
			System.out.println("q3");
			errors.add("Username is required");
		}
		
		if (password == null || password.length() == 0) {
			System.out.println("q4");
			errors.add("Password is required");
		}
		
//		if ( confirmpassword == null || confirmpassword.length() == 0) {
//			System.out.println("q5");
//			errors.add("Confirm Password is required");
//		}
		
		if ( addr_line1 == null || addr_line1.length() == 0) {
			System.out.println("q6");
			errors.add("Address is required");
		}
		
		
		if ( city == null || city.length() == 0) {
			System.out.println("q8");
			errors.add("City is required");
		}
		
		if ( state == null || state.length() == 0) {
			System.out.println("q8");
			errors.add("State is required");
		}
		
		if ( zip == null || zip.length() == 0) {
			System.out.println("q9");
			errors.add("Zip is required");
		}

		if(errors.size()>0) {
			return errors;
		}
		//check pass and confirm pass match?
//		if(!password.equals(confirmpassword)){
//			errors.add("Passwords do not match");
//		}
//		if(!action.equals("confirm")){
//			errors.add("Invalid Action");
//		}
		
		if(firstname.matches(".*[<>\"].*")){
			errors.add("First Name may not contain angle brackets or quotes");
		}
		
		if(lastname.matches(".*[<>\"].*")){
			errors.add("Last Name may not contain angle brackets or quotes");
		}
		
		if(username.matches(".*[<>\"].*")){
			errors.add("Username may not contain angle brackets or quotes");
		}
		
		if(addr_line1.matches(".*[<>\"].*")){
			errors.add("Address Line 1 may not contain angle brackets or quotes");
		}
		
		if(addr_line2.matches(".*[<>\"].*")){
			errors.add("Address Line 2 may not contain angle brackets or quotes");
		}
		
		if(city.matches(".*[<>\"].*")){
			errors.add("City may not contain angle brackets or quotes");
		}
		if(!city.matches("[a-zA-Z\\s]*")) {
			errors.add("City may not contain numbers or special characters");
		}
		
		if(state.matches(".*[<>\"].*")){
			errors.add("State may not contain angle brackets or quotes");
		}
		if(!state.matches("[a-zA-Z]*")) {
			errors.add("State may not contain anything without letters");
		}
		
		if(zip.matches(".*[<>\"].*")){
			errors.add("Zip may not contain angle brackets or quotes");
		}
		
		if(password.matches(".*\\s+.*")) {
			errors.add("Password can not contain any white space");
			return errors;
		}
		
//		if(confirmpassword.matches(".*\\s+.*")) {
//			errors.add("Confirm Password can not contain any white space");
//			return errors;
//		}

		return errors;
	}
	
}
