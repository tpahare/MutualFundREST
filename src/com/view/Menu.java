package com.view;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private String link;
	private String function;
	public Menu(){
		
	}
	public List<Menu> customerMenu(){
		List<Menu> customerMenu = new ArrayList<Menu>();
		customerMenu.add(new Menu("/portfolio", "View portfolio"));
		customerMenu.add(new Menu("/buyFund","Buy Fund"));
		customerMenu.add(new Menu("/logout","Logout from application"));
		return customerMenu;
	}
	public List<Menu> employeeMenu(){
		List<Menu> employeeMenu = new ArrayList<Menu>();
		employeeMenu.add(new Menu("/createCustomerAccount", "Create a customer Account"));
		employeeMenu.add(new Menu("/depositCheck","Deposit a Check"));
		employeeMenu.add(new Menu("/transitionDay","Do a transition Day"));
		employeeMenu.add(new Menu("/logout","Logout from application"));
		return employeeMenu;
	}
	public Menu(String link, String function){
		this.link = link;
		this.function = function;
	}
}
/*BASEURI}/createCustomerAccount
: {BASEURI}/depositCheck
: {BASEURI}/createFund
{BASEURI}/transitionDay
: {BASEURI}/logout*/
