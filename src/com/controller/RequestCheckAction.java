package com.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.databean.CustomerBean;
import com.databean.TransactionBean;
import com.form.DepositeCheckForm;
import com.form.RequestCheckForm;
import com.model.CustomerDAO;
import com.model.Model;
import com.model.TrancDAO;

public class RequestCheckAction extends Action {
	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);
	CustomerDAO customerDAO;
	TrancDAO tDAO;
	public RequestCheckAction(Model model) {
		this.customerDAO = model.getCustomerDAO();
		this.tDAO = model.getTrancDAO();
	}
	@Override
	public String getName() {
		return "RequestCheck.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if(customer == null){
			errors.add("Please login first");
			return "CustomerLogin.do";
		}
		try{
			RequestCheckForm form = formBeanFactory.create(request);
			request.setAttribute("form",form);
			
			if(!form.isPresent()){
				return "RequestCheck.jsp";
			}
			
			
			errors.addAll(form.getValidationErrors());
			
			if(errors.size() > 0){
				return "RequestCheck.jsp";
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
			
			
			TransactionBean tBean = new TransactionBean();
			tBean.setCid(customer.getCid());
			tBean.setTransactiontype("request");
			
			long requestmoney = (long) (100 * Double.parseDouble(form.getAmount()));
			TransactionBean[] tb = tDAO.match(MatchArg.equals("executedate", null));
			CustomerBean c = customerDAO.read(customer.getCid());
			long cash = c.getCash();
			for (int i = 0; i < tb.length; i++) {
				if (tb[i].getTransactiontype().equals("buy") && tb[i].getCid() == customer.getCid()) {
					cash -= tb[i].getAmount();
				}
				if (tb[i].getTransactiontype().equals("request") && tb[i].getCid() == customer.getCid()) {
					cash -= tb[i].getAmount();
				}
			}
			if (cash - requestmoney < 0) {
				errors.add("Sorry you don't have enough money");
				return "RequestCheck.jsp";
			}
			
			tBean.setAmount(requestmoney);
			tDAO.create(tBean);
			
			System.out.print("look 5");
			
			return "RequestCheckSuccess.jsp";
		}catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
		
		
	}

}
