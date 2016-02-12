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
import com.form.ChangeEmployeePasswordForm;
import com.model.EmployeeDAO;
import com.model.Model;

public class ChangeEmployeePasswordAction extends Action{

	private FormBeanFactory<ChangeEmployeePasswordForm> formBeanFactory = FormBeanFactory.getInstance(ChangeEmployeePasswordForm.class);
	private EmployeeDAO employeeDAO;
	
	public ChangeEmployeePasswordAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
	}
	
	
	@Override
	public String getName() {
		return "ChangeEmployeePassword.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>(); 
		request.setAttribute("errors", errors);
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		
		if(employee == null) {
			errors.add("Please Login first");
			return "EmployeeLogin.do";
		}
		
		try {
			//load the form params to a form bean
			ChangeEmployeePasswordForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			//if no param redirect to change pass jsp (1st time)
			if(!form.isPresent()) {
				return "ChangeEmployeePassword.jsp";
			}
			//check validation errors
			errors.addAll(form.getValidationErrors());
			if(errors.size()>0) {
				return "ChangeEmployeePassword.jsp";
			}
						
			//check old password field matches?
			if(!employeeDAO.read(employee.getUsername()).getPassword().equals(form.getOldPassword())) {
				errors.add("Old password does not match");
				return "ChangeEmployeePassword.jsp";
			}
			
			//if no error then,
			employeeDAO.changePassword(employee.getUsername(), form.getNewPassword());
			
			
//			return "EmployeeLogin.jsp";
			return "ChangeEmployeePwdSuccess.jsp";
			
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "ChangeEmployeePassword.jsp";
		} catch (RollbackException e1) {
			errors.add(e1.toString());
			return "ChangeEmployeePassword.jsp";
		}
		
	}

	
}
