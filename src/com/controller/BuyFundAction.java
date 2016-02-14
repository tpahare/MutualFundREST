package com.controller;
import java.util.*;
import java.math.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.DuplicateKeyException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import java.text.*;

import com.databean.*;
import com.form.*;
import com.google.gson.Gson;
import com.model.*;
import com.view.Message;
public class BuyFundAction extends Action {
	private FormBeanFactory<BuyFundForm> formBeanFactory = FormBeanFactory.getInstance(BuyFundForm.class);
	CustomerDAO cDAO;
	TrancDAO tDAO;
	FundDAO fDAO;
	FundPriceHistoryDAO fphDAO;
	
	Gson gson = new Gson();
	Message message = new Message();
	
	public BuyFundAction(Model model) {
		cDAO = model.getCustomerDAO();
		tDAO = model.getTrancDAO();
		fDAO = model.getFundDAO();
		fphDAO = model.getFundPriceHistoryDAO();
	}
	@Override
	public String getName() {
		return "buyFund";
	}

	@Override
	public String perform(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if(customer == null){
//			errors.add("Please login first");
//			return "CustomerLogin.do";
			message.setMessage("You must log in prior to making this request");
			return gson.toJson(message);
		}
		
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		if(employee == null){
			message.setMessage("I'm sorry you are not authorized to preform that action");
			return gson.toJson(message);
		}
		try {
//			BuyFundForm form = formBeanFactory.create(request);
//			request.setAttribute("form", form);
//			FundBean[] fundList = fDAO.getFundList();
//			request.setAttribute("fundList", fundList);
//			if (!form.isPresent()) {
//				return "BuyFund.jsp";
//			}
//			errors.addAll(form.getValidationErrors());
//			if (errors.size() > 0) {
//				return "BuyFund.jsp";
//			}
//			String fundsymbol = form.getFundsymbol();
			String fundSymbol = request.getParameter("fundSymbol");
			//CustomerBean c = (CustomerBean) session.getAttribute("customer");
			CustomerBean c = cDAO.read(customer.getCid());
			FundBean[] fb = fDAO.match(MatchArg.equals("symbol", fundSymbol));
//			if (fb.length == 0) {
//				errors.add("This fund does not exist");
//				return "BuyFund.jsp";
//			}
//			try {
//				double tmp = Double.parseDouble(form.getMoney());
//			} catch (Exception e) {
//				errors.add("Dollar Amount should be a number");
//				return "BuyFund.jsp";
//			}
			TransactionBean transaction = new TransactionBean();
			//parse the money
			BigDecimal bg = new BigDecimal(request.getParameter("cashValue"));
//			if (bg.doubleValue() <= 0) {
//				errors.add("Dollar Amount can not be negative");
//				return "BuyFund.jsp";
//			}
//			if (bg.scale() > 2) {
//				errors.add("Dollar Amount should have at most two decimal places");
//				return "BuyFund.jsp";
//			}
//			if (bg.doubleValue() < 1) {
//				errors.add("Dollar amount should not be less than 1 dollar");
//				return "BuyFund.jsp";
//			}
			Double amount = Double.parseDouble(request.getParameter("cashValue"));
			DecimalFormat df = new DecimalFormat("0.00");
			String tmpAmount = df.format(amount);
			double amount1 = Double.parseDouble(tmpAmount);
			long money = (long) (amount1);
			TransactionBean[] tb = tDAO.match(MatchArg.equals("executedate", null));
			long cash = c.getCash();
			for (int i = 0; i < tb.length; i++) {
				if ((tb[i].getTransactiontype().equals("buy") || tb[i].getTransactiontype().equals("request")) && tb[i].getCid() == c.getCid()) {
					cash -= tb[i].getAmount();
				}
			}
			if (cash - money < 0) {
				message.setMessage("I’m sorry, you must first deposit sufficient funds in your account in order to make this purchase");
				return gson.toJson(message);
//				errors.add("Sorry you don't have enough money");
//				return "BuyFund.jsp";
			}
			transaction.setAmount(money);
			transaction.setCid(c.getCid());
			transaction.setFundid(fb[0].getFundid());
			transaction.setTransactiontype("buy");
			tDAO.create(transaction);
			
			message.setMessage("The purchase was successfully completed");
			return gson.toJson(message);
		} 
		
//		catch (FormBeanException e) {
//			errors.add(e.getMessage());
//			return "error.jsp";
//		} 
		
		catch (RollbackException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		} catch (NumberFormatException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		}
	}
}
