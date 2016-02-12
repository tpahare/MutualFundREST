package com.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.databean.CustomerBean;
import com.databean.EmployeeBean;
import com.form.ChangeCustomerPasswordForm;
///import com.form.LoginForm;
import com.model.CustomerDAO;
import com.model.Model;

public class ChangeCustomerPasswordAction extends Action{
	private FormBeanFactory<ChangeCustomerPasswordForm> formBeanFactory = FormBeanFactory.getInstance(ChangeCustomerPasswordForm.class);
	private CustomerDAO cDAO;
	
	public ChangeCustomerPasswordAction(Model model) {
		// TODO Auto-generated constructor stub
		cDAO = model.getCustomerDAO();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ChangeCustomerPassword.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		
		
		//HttpSession session = request.getSession();
	
		/*if (session.getAttribute("employee") != null) {
        	return "ChangeCustomerPassword.jsp";
        }*/
		List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		if(employee == null){
			errors.add("Please login first");
			return "EmployeeLogin.do";
		}
		
        try {
        	String customer = request.getParameter("resetpwdusername");
        	if (customer == null || customer.length() == 0) {
        		return "ViewCustomerAccount.do";
        	}
//        	if (request.getParameter("button") == null) {
//    			return "ViewCustomerAccount.do";
//    		}
//    		if (!request.getParameter("button").equals("Reset")) {
//    			errors.add("Invalid Button");
//    			return "ViewCustomerAccount.do";
//    		}
	    	ChangeCustomerPasswordForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);
	        
	        String resetpwdusername = request.getParameter("resetpwdusername");
	        request.setAttribute("resetpwdusername", resetpwdusername);
	        
	        System.out.println("Change Pwd in Action username 1: " + resetpwdusername);

	        // If no params were passed, return with no errors so that the form will be
	        // presented (we assume for the first time).
	        if (!form.isPresent()) {
	            return "ChangeCustomerPassword.jsp";
	        }
	        
	        System.out.println("Change Pwd: STEP 1");

	        // Any validation errors?
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "ChangeCustomerPassword.jsp";
	        }

	        
       		System.out.println("Change Pwd: STEP 2");

//	        // Look up the user
//	        //User user = userDAO.read(form.getUserName());
//	        CustomerBean[] customer = cDAO.match(MatchArg.equals("username",form.getUsername()));
//	        
//	        if (customer.length == 0) {
//	            errors.add("Name not found");
//	            return "ChangeCustomerPassword.jsp";
//	        }
//
//	        // Check the password
//	        if (!customer[0].getPassword().equals(form.getOldPassword())) {
//	            errors.add("Incorrect password");
//	            return "ChangeCustomerPassword.jsp";
//	        }
	
	        // Attach (this copy of) the user bean to the session
       		System.out.println("Change Pwd in Action username 2: " + resetpwdusername);
       		
	        cDAO.changePassword(resetpwdusername, form.getNewPassword());
	     //   message = "Password Changed successfully";
	  //      request.setAttribute("message", message);
	        // If redirectTo is null, redirect to the "todolist" action
	        System.out.println("Change Pwd: STEP 3");
	        
			return "ChangeCusPwdSuccess.jsp";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
	}
}
