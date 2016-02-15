package com.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import com.form.RequestCheckForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.CustomerDAO;
import com.model.Model;
import com.model.TrancDAO;
import com.view.Message;

public class RequestCheckAction extends Action {
	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);
	CustomerDAO customerDAO;
	TrancDAO tDAO;
	Message message = new Message();
	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	public RequestCheckAction(Model model) {
		this.customerDAO = model.getCustomerDAO();
		this.tDAO = model.getTrancDAO();
	}
	@Override
	public String getName() {
		return "requestCheck";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		//request.setAttribute("errors", errors);
		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		
		if(customer == null) {
			
			if(employee != null) {
				message.setMessage("I'm sorry you are not authorized to perform that action");
				return gson.toJson(message);
			}
			
			message.setMessage("You must log in prior to making this request");
			return gson.toJson(message);
		}
		
		try{
			RequestCheckForm form = formBeanFactory.create(request);
			request.setAttribute("form",form);
			
			/*if(!form.isPresent()){
				return "RequestCheck.jsp";
			}
			*/
			
			errors.addAll(form.getValidationErrors());
			
			//remove
			if(errors.size() > 0){
				message.setMessage("Errors");
				gson.toJson(message);
			}
			
//			try {
//				double tmp = Double.parseDouble(form.getAmount());
//			} catch (Exception e) {
//				errors.add("Amount should be a number");
//				return "RequestCheck.jsp";
//			}
			
			
			
		//	CustomerBean customer = (CustomerBean) session.getAttribute("customer");
			
//			Double amount = Double.parseDouble(form.getAmount());
//			DecimalFormat df = new DecimalFormat("0.00");
//			String tmpAmount = df.format(amount);
//			double amount1 = Double.parseDouble(tmpAmount);
//			long money = (long) (100 * amount1);
			
			
			//TransactionBean tBean = new TransactionBean();
			//tBean.setCid(customer.getCid());
			//tBean.setTransactiontype("request");
			
			Double requestMoney = Double.parseDouble(form.getCashValue());
			//TransactionBean[] tb = tDAO.match(MatchArg.equals("executedate", null));
			//CustomerBean c = customerDAO.read(customer.getCid());
			double cash = customer.getCash();
		/*	for (int i = 0; i < tb.length; i++) {
				if (tb[i].getTransactiontype().equals("buy") && tb[i].getCid() == customer.getCid()) {
					cash -= tb[i].getAmount();
				}
				if (tb[i].getTransactiontype().equals("request") && tb[i].getCid() == customer.getCid()) {
					cash -= tb[i].getAmount();
				}
			}*/
			if (cash - requestMoney < 0) {
				message.setMessage("I'm sorry, the amount requested is greater than the balance of your account");
				return gson.toJson(message);
			}
			
			//tBean.setAmount(requestMoney);
			//tBean.setExecutedate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
			//tDAO.create(tBean);
			customer.setCash(cash-requestMoney);
			customerDAO.update(customer);
			//System.out.print("look 5");
			message.setMessage("The withdrawal was successfully completed");
			return gson.toJson(message);
		}catch (RollbackException e) {
        	message.setMessage("Rollback Exception");
        	return gson.toJson(message);
        } catch (FormBeanException e) {
        	message.setMessage("Form Exception");
        	return gson.toJson(message);
        }

	}

}
