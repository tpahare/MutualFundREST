package com.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import com.form.*;
import com.google.gson.Gson;
import com.model.*;
import com.view.Message;

//import edu.cmu.cs.webapp.todolist8.databean.User;
//import edu.cmu.cs.webapp.todolist8.formbean.LoginForm;

//import edu.cmu.cs.webapp.todolist8.formbean.LoginForm;

import com.databean.*;

public class CustomerLoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	
	private CustomerDAO cDAO;
	Gson gson = new Gson();
	Message message = new Message();
	public  CustomerLoginAction(Model model) {
		cDAO = model.getCustomerDAO();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "CustomerLogin.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		// TODO Auto-generated method stub
		System.out.println("in perform");
		HttpSession session = request.getSession();
		if (session.getAttribute("customer") != null) {
			return "CustomerLoginSuccess.jsp";
        }
		List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        try {
        	/*try {
        	ServletFileUpload upload = new ServletFileUpload();
        	Iterator<FileItem> iterator = (Iterator<FileItem>) upload.getItemIterator(request);
        		if (!iterator.hasNext()) {
        			errors.add("Your can not input a file");
        			return "CustomerLogin.jsp";
        		}
        	} catch (Exception e) {
        		
        	}*/
	    	LoginForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);

	        // If no params were passed, return with no errors so that the form will be
	        // presented (we assume for the first time).
//	        if (!form.isPresent()) {
//	            return "Form not present";
//	        }

	        // Any validation errors?
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "Errors in form";
	        }

       		

	        // Look up the user
	        //User user = userDAO.read(form.getUserName());
	        CustomerBean[] customer = cDAO.match(MatchArg.equals("username", form.getUsername()));
	        
	        if (customer.length == 0) {
	            errors.add("Customer not found");
	            return "customer not found";
	        }

	        // Check the password
	        if (!customer[0].getPassword().equals(form.getPassword())) {
	            errors.add("Incorrect password");
	            return "incorrect password";
	        }
	
	        // Attach (this copy of) the user bean to the session
	        session.setAttribute("customer",customer[0]);
	        
	        // If redirectTo is null, redirect to the "todolist" action
        	message.setMessage("Logged in successfully");
        	return gson.toJson(message);
	      } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
	}
}

