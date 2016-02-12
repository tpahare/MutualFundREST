/**
 * @author tpahare
 */
package com.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.genericdao.DuplicateKeyException;
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

public class EmployeeLoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	///
	private EmployeeDAO eDAO;
	Gson gson = new Gson();
	Message message = new Message();
	public EmployeeLoginAction(Model model) {
		eDAO = model.getEmployeeDAO();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "EmployeeLogin.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.getAttribute("employee") != null) {
			EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
			message.setMessage("Employee is already logged in as " + employee.getUsername());
			return gson.toJson(message);
        }
		List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        try {
	    	LoginForm form = formBeanFactory.create(request);
	    	System.out.println(form.getUsername());
	        request.setAttribute("form",form);

	        // If no params were passed, return with no errors so that the form will be
	        // presented (we assume for the first time).
	       /* if (!form.isPresent()) {
	            return "EmployeeLogin.jsp";
	        }*/

	        // Any validation errors?
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	        	message.setMessage(errors.toString());
	            return gson.toJson(message);
	        }

       		

	        // Look up the user
	        //User user = userDAO.read(form.getUserName());
	        EmployeeBean employee = eDAO.read(form.getUsername());
	        
	        if (employee == null) {
	        	message.setMessage("Employee username not found");
	            return gson.toJson(message);
	        }

	        // Check the password
	        if (!employee.getPassword().equals(form.getPassword())) {
	        	message.setMessage("Incorrect password");
	            return gson.toJson(message);
	        }
	
	        // Attach (this copy of) the user bean to the session
	        session.setAttribute("employee",employee);
	        
	        // If redirectTo is null, redirect to the "todolist" action
	    	message.setMessage("Logged in successfully");
        	return gson.toJson(message);
			
        }  catch (RollbackException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		} catch (FormBeanException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		}
	}
}
