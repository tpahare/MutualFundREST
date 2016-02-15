/**
 * @author tpahare
 */
package com.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.genericdao.DuplicateKeyException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import com.form.*;
import com.google.gson.Gson;
import com.model.*;
import com.view.Menu;
import com.view.Message;

//import edu.cmu.cs.webapp.todolist8.databean.User;
//import edu.cmu.cs.webapp.todolist8.formbean.LoginForm;

//import edu.cmu.cs.webapp.todolist8.formbean.LoginForm;

import com.databean.*;

public class LoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	///
	private EmployeeDAO eDAO;
	private CustomerDAO cDAO;
	Gson gson = new Gson();
	Message message = new Message();
	Menu menu = new Menu();

	public LoginAction(Model model) {
		eDAO = model.getEmployeeDAO();
		cDAO = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "login";
	}

	@Override
	public String perform(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.getAttribute("employee") != null) {
			//session.invalidate();
		}
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
			LoginForm form = formBeanFactory.create(request);
			System.out.println(form.getUsername());
			request.setAttribute("form", form);

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			/*
			 * if (!form.isPresent()) { return "EmployeeLogin.jsp"; }
			 */

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				message.setMessage(errors.toString());
				return gson.toJson(message);
			}

			// Look up the user
			// User user = userDAO.read(form.getUserName());
			EmployeeBean[] employee = eDAO.match(MatchArg.and(MatchArg.equals("username",form.getUsername())),MatchArg.equals("password", form.getPassword()));

			if (employee.length == 0) {
				CustomerBean[] customer = cDAO.match(MatchArg.and(MatchArg.equals("username", form.getUsername()),
						MatchArg.equals("password", form.getPassword())));
				if (customer.length != 0) {
					session.setAttribute("customer", customer[0]);
					session.setAttribute("employee", null);
					message.setMessage("Welcome " + customer[0].getFirstname() + " " + customer[0].getLastname());
					return gson.toJson(message) + "\n" + gson.toJson(menu.customerMenu());
				}
					message.setMessage("The username/password combination that you entered is not correct");
					return gson.toJson(message);
				}

			message.setMessage("Welcome' " + employee[0].getFirstname() + " " + employee[0].getLastname());
			// Attach (this copy of) the employee bean to the session
			session.setAttribute("employee", employee[0]);
			session.setAttribute("customer", null);
		
			return gson.toJson(message)  + gson.toJson(menu.employeeMenu());

		} catch (RollbackException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		} catch (FormBeanException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		}
	}
}
