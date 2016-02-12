
package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;




import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.databean.EmployeeBean;
import com.form.CreateEmployeeForm;
import com.model.EmployeeDAO;
import com.model.Model;



/*
 * Processes the parameters from the form in register.jsp.
 * If successful:
 *   (1) creates a new User bean
 *   (2) sets the "user" session attribute to the new User bean
 *   (3) redirects to view to manage.do.
 */
public class CreateEmployeeAction extends Action {
	private FormBeanFactory<CreateEmployeeForm> formBeanFactory = FormBeanFactory.getInstance(CreateEmployeeForm.class);

	private EmployeeDAO employeeDAO;
	
	public CreateEmployeeAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() { return "createEmployee.do"; }

    public String perform(HttpServletRequest request) {
        
    	HttpSession session = request.getSession();
    	
    	List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		
		if(employee == null) {
			errors.add("Please Login first");
			return "EmployeeLogin.do";
		}
		
        try {
        	CreateEmployeeForm form = formBeanFactory.create(request);
	        //request.setAttribute("userList",employeeDAO.getEmployees());
	        request.setAttribute("form",form);
	        
	        System.out.print("look1");
	
	        // If no params were passed, return with no errors so that the form will be
	        // presented (we assume for the first time).
	        if (!form.isPresent()) {
	            return "CreateEmployee.jsp";
	        }
	        
	        System.out.print("look2");
	
	        // Any validation errors?
	        errors.addAll(form.getValidationErrors());
	        
	        System.out.print(errors);
	        
	        if (errors.size() != 0) {
	            return "CreateEmployee.jsp";
	        }
	         EmployeeBean[] checkUser = employeeDAO.match(MatchArg.equals("username", form.getUsername()));
	        // Create the user bean
	         
	         System.out.print("look3");
	         
	         if(checkUser.length > 0){
	        	 errors.add("User with email: " + form.getUsername() + " already exists");
	        	 return "CreateEmployee.jsp";
	         }
	        EmployeeBean employeeBean = new EmployeeBean();
	        employeeBean.setUsername((form.getUsername()));
	        employeeBean.setFirstname(form.getFirstname());
	        employeeBean.setLastname(form.getLastname());
	        employeeBean.setPassword(form.getPassword());
        	employeeDAO.create(employeeBean);
        
			// Attach (this copy of) the user bean to the session
	        
	        session.setAttribute("employee",employeeBean);
	        
			return "CreateEmployeeSuccess.jsp"; 
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
