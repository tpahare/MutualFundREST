/**
 * @author tpahare
 */
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
import com.view.Menu;
import com.view.Message;

//import edu.cmu.cs.webapp.todolist8.databean.User;
//import edu.cmu.cs.webapp.todolist8.formbean.LoginForm;

//import edu.cmu.cs.webapp.todolist8.formbean.LoginForm;

import com.databean.*;

public class LoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);

	private CustomerDAO cDAO;
	Gson gson = new Gson(); // first make a gson object
	Message message = new Message(); // make an object of message
	Menu menu = new Menu();
	public LoginAction(Model model) {
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
		System.out.println("in customer login action perform");
		HttpSession session = request.getSession();

		/*
		 * Wherever we were earlier returning jsp pages or errors. Now we put
		 * those in the Message.setMessage(), put it in gson.toJson and return
		 * that
		 */
		if (session.getAttribute("customer") != null) {
			CustomerBean customer = (CustomerBean) session.getAttribute("customer");
			message.setMessage("Customer is already logged in as " + customer.getUsername());
			return gson.toJson(message);
		}
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
			/*
			 * try { ServletFileUpload upload = new ServletFileUpload();
			 * Iterator<FileItem> iterator = (Iterator<FileItem>)
			 * upload.getItemIterator(request); if (!iterator.hasNext()) {
			 * errors.add("Your can not input a file"); return
			 * "CustomerLogin.jsp"; } } catch (Exception e) {
			 * 
			 * }
			 */
			LoginForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			// if (!form.isPresent()) {
			// return "Form not present";
			// }

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				message.setMessage(errors.toString());
				return gson.toJson(message);
			}

			// Look up the user
			// User user = userDAO.read(form.getUserName());
			CustomerBean[] customer = cDAO.match(MatchArg.equals("username", form.getUsername()));

			if (customer.length == 0) {
				message.setMessage("The username/password combination is incorrect");
				return gson.toJson(message);
			}

			// Check the password
			if (!customer[0].getPassword().equals(form.getPassword())) {
				message.setMessage("The username/password combination is incorrect");
				return gson.toJson(message);
			}

			// Attach (this copy of) the user bean to the session
			session.setAttribute("customer", customer[0]);

			// If redirectTo is null, redirect to the "todolist" action
			message.setMessage("Welcome " + customer[0].getFirstname() + " " + customer[0].getLastname());
			
			return gson.toJson(message) + "\n" + gson.toJson(menu.customerMenu());
		} catch (RollbackException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		} catch (FormBeanException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		}
	}
}
