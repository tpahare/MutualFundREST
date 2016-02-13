package com.controller;

/**
 * @author faisalshahnewaz.
 */

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import com.databean.CustomerBean;
import com.databean.EmployeeBean;
import com.form.CreateCustomerForm;
import com.google.gson.Gson;
import com.model.CustomerDAO;
import com.model.Model;
import com.view.Menu;
import com.view.Message;

public class CreateCustomerAction extends Action{

	private FormBeanFactory<CreateCustomerForm> formBeanFactory = FormBeanFactory.getInstance(CreateCustomerForm.class);
	private CustomerDAO cDAO;
	private Gson gson = new Gson();
	private Message message = new Message();
	
	//constructor
	public CreateCustomerAction(Model model) {
		cDAO = model.getCustomerDAO();
	}
	
	@Override
	public String getName() {
		
		return "createCustomerAccount";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);	
		
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		
		if(employee == null) {
//			errors.add("Please Login first");
//			return "EmployeeLogin.do";
			message.setMessage("You must log in prior to make that request");
			return gson.toJson(message);
		}
		
		try {		
			//create a form instance and load data from req.
			CreateCustomerForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			
			//check if there is any data? if none then its 1st time, then load the .jsp page.
//			if(!form.isPresent()) {
//				return "CreateCustomer.jsp";
//			}
			
			
			// Check all form validation errors
	        errors.addAll(form.getValidationErrors());
	        
		        //if any error, redirect to the .jsp page with errors.
		        if(errors.size()>0) {
		        	
//		        	return "CreateCustomer.jsp";
		        	message.setMessage("I am sorry there was problem creating the account");
		        	return gson.toJson(message);
		        }
	        
		        //else check, if there is any existing user in database.
		        CustomerBean[] customerBean = cDAO.match(MatchArg.equals("username", form.getUsername()));
		        if(customerBean.length>0) {
//		        	errors.add("Username already exists");
//		        	return "CreateCustomer.jsp";
		        	message.setMessage("I am sorry there was problem creating the account 1");
		        	return gson.toJson(message);
		        }
		        
			//Create customer- create bean, load data in bean and create in database.
		    CustomerBean cBean = new CustomerBean();
		    
		    cBean.setUsername(form.getUsername());
		    cBean.setFirstname(form.getFirstname());
		    cBean.setLastname(form.getLastname());
		    cBean.setPassword(form.getPassword());
		    cBean.setAddrline1(form.getAddrline1());
		    cBean.setAddrline2(form.getAddrline2());
		    cBean.setCity(form.getCity());
		    cBean.setState(form.getState());
		    cBean.setZip(form.getZip());
		    
		    cDAO.create(cBean);
		    
		    //attach the bean with session
		    session.setAttribute("customer", cBean);
		    
//			return "CreateCustomerSuccessfully.jsp";
		    message.setMessage("The account has been successfully created");
		    return gson.toJson(message);
			
		} catch (FormBeanException e) {
//			errors.add(e.getMessage());
//			return "error.jsp";
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		} catch (RollbackException e) {
//			errors.add(e.getMessage());
//			return "error.jsp";
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		}	
	}
}
