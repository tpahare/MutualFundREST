package com.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.genericdao.DuplicateKeyException;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import com.form.*;
import com.model.*;

//import edu.cmu.cs.webapp.todolist8.databean.User;
//import edu.cmu.cs.webapp.todolist8.formbean.LoginForm;

//import edu.cmu.cs.webapp.todolist8.formbean.LoginForm;

import com.databean.*;

/**
 * @author Xuesong Zhang (Andrew ID: xuesongz)
 */
public class EmployeeLoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	///
	private EmployeeDAO eDAO;
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
        	return "EmployeeLogin.jsp";
        }
		List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        try {
	    	LoginForm form = formBeanFactory.create(request);
	    	System.out.println(form.getUsername());
	        request.setAttribute("form",form);

	        // If no params were passed, return with no errors so that the form will be
	        // presented (we assume for the first time).
	        if (!form.isPresent()) {
	            return "EmployeeLogin.jsp";
	        }

	        // Any validation errors?
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "EmployeeLogin.jsp";
	        }

       		

	        // Look up the user
	        //User user = userDAO.read(form.getUserName());
	        EmployeeBean employee = eDAO.read(form.getUsername());
	        
	        if (employee == null) {
	            errors.add("Name not found");
	            return "EmployeeLogin.jsp";
	        }

	        // Check the password
	        if (!employee.getPassword().equals(form.getPassword())) {
	            errors.add("Incorrect password");
	            return "EmployeeLogin.jsp";
	        }
	
	        // Attach (this copy of) the user bean to the session
	        session.setAttribute("employee",employee);
	        
	        // If redirectTo is null, redirect to the "todolist" action
			return "EmployeeLoginSuccess.jsp";
			
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "EmployeeLogin.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "EmployeeLogin.jsp";
        }
	}
}
