package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.databean.CustomerBean;
import com.databean.EmployeeBean;
import com.databean.TransactionBean;
import com.form.DepositeCheckForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.CustomerDAO;
import com.model.Model;
import com.model.TrancDAO;
import com.view.Menu;
import com.view.Message;

public class DepositCheckAction extends Action {
	private FormBeanFactory<DepositeCheckForm> formBeanFactory = FormBeanFactory.getInstance(DepositeCheckForm.class);
	CustomerDAO customerDAO;
	TrancDAO trancDAO;
	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	Message message = new Message();
	public DepositCheckAction(Model model) {
		this.customerDAO = model.getCustomerDAO();
		this.trancDAO = model.getTrancDAO();
	}
	@Override
	public String getName() {
		return "depositCheck";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		CustomerBean customer1 = (CustomerBean) session.getAttribute("customer");
		
		if(employee == null) {
			
			if(customer1 != null) {
				message.setMessage("I'm sorry you are not authorized to perform that action");
				System.out.println(message);
				return gson.toJson(message);
			}
			message.setMessage("You must log in prior to making this request");
			return gson.toJson(message);
		}
		

		try{
//			String customer = request.getParameter("username");
//			if (customer == null || customer.length() == 0) {
//				errors.add("I’m sorry, there was a problem depositing the money");
//				message.setMessage(errors.toString());
//				return gson.toJson(message);
				//return "ViewCustomerAccount.do";
//			}
//			if (request.getParameter("button") == null) {
//				return "ViewCustomerAccount.do";
//			}
//			if (!request.getParameter("button").equals("Deposit")) {
//				errors.add("Invalid Button");
//				return "ViewCustomerAccount.do";
//			}
			DepositeCheckForm form = formBeanFactory.create(request);
			request.setAttribute("form",form);
			
			String username = form.getUsername();
			if (username == null || username.length() == 0) {
				//errors.add("I’m sorry, there was a problem depositing the money");
				message.setMessage("I'm sorry, there was a problem depositing the money");
				return gson.toJson(message);
			}
	        request.setAttribute("username", username);
	        CustomerBean[] customerBean = customerDAO.match(MatchArg.equals("username", username));
	        if(customerBean.length==0){
	        	message.setMessage("Incorrect username");
	        	return gson.toJson(message);
	        }
	        int id = customerBean[0].getCid();
			
//			if(!form.isPresent()){
//				return "DepositCheck.jsp";
//			}
			
//			System.out.print("Username here:" + form.getUsername());
//			System.out.print("Amount here:" + form.getAmount());
//			System.out.print("Deposit Check look 1");
			
			
			errors.addAll(form.getValidationErrors());
			
			if(errors.size()!=0){
				message.setMessage(errors.toString());
				return gson.toJson(message);
				//return "DepositCheck.jsp";
			}
			
			
//			CustomerBean[] customer = customerDAO.match(MatchArg.equals("username", form.getUsername()));
//			if(customer.length == 0){
//				errors.add("Username does not exist");
//				return "DepositCheck.jsp";
//			}
			
			
			//System.out.println("hh");
			//SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
			CustomerBean customer = customerDAO.read(id);
			//TransactionBean tBean = new TransactionBean();
			//tBean.setCid(id);
			//tBean.setTransactiontype("deposit");
			//tBean.setExecutedate(format.format(new Date()));
			double depositmoney = Double.parseDouble(form.getCash());
			customer.setCash(customer.getCash()+depositmoney);
			//tBean.setAmount(depositmoney);
			
			//trancDAO.create(tBean);
			customerDAO.update(customer);
			//System.out.print("look 5");
			message.setMessage("The account has been successfully updated");
			return gson.toJson(message);
		}catch (RollbackException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
        } catch (FormBeanException e) {
        	message.setMessage(e.getMessage());
			return gson.toJson(message);
        }
		
		
	}

}
