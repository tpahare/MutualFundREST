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
import com.google.gson.GsonBuilder;
import com.model.*;
import com.view.Message;
public class BuyFundAction extends Action {
	private FormBeanFactory<BuyFundForm> formBeanFactory = FormBeanFactory.getInstance(BuyFundForm.class);
	CustomerDAO cDAO;
	TrancDAO tDAO;
	FundDAO fDAO;
	FundPriceHistoryDAO fphDAO;
	PositionDAO pDAO;
	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	Message message = new Message();
	
	public BuyFundAction(Model model) {
		cDAO = model.getCustomerDAO();
		tDAO = model.getTrancDAO();
		fDAO = model.getFundDAO();
		fphDAO = model.getFundPriceHistoryDAO();
		pDAO = model.getPosDAO();
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

			String fundSymbol = request.getParameter("fundSymbol");
			String money = request.getParameter("cashValue");
			double inputMoney = Double.parseDouble(money);
			CustomerBean newCustomer = cDAO.read(customer.getCid());
			if (inputMoney > newCustomer.getCash()) {
				message.setMessage("I'm sorry, you must first deposit sufficient funds in your account in order to make this purchase");
				return gson.toJson(message);
			}
			FundBean[] fBeans = fDAO.match(MatchArg.equals("symbol", fundSymbol));
			FundPriceHistoryBean[] fundPriceHistoryBeans = fphDAO.match(MatchArg.equals("fundid", fBeans[0].getFundid()));
			int max = Integer.MIN_VALUE;
			for (FundPriceHistoryBean fBean : fundPriceHistoryBeans) {
				if (Integer.parseInt(fBean.getPricedate()) > max) {
					max = Integer.parseInt(fBean.getPricedate());
				}
			}
			FundPriceHistoryBean fBean = fphDAO.read(fBeans[0].getFundid(),new Integer(max).toString());
			if (inputMoney < fBean.getPrice()) {
				message.setMessage("I'm sorry, you must first deposit sufficient funds in your account in order to make this purchase");
				return gson.toJson(message);
			}
			long share = (long) (inputMoney / fBean.getPrice());
			PositionBean positionBean = pDAO.read(newCustomer.getCid(),fBeans[0].getFundid());
			positionBean.setShares(share);
			pDAO.update(positionBean);
			newCustomer.setCash(newCustomer.getCash() - share * fBean.getPrice());
			cDAO.update(newCustomer);
			message.setMessage("The purchase was successfully completed");
			return gson.toJson(message);
		} 
		catch (RollbackException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		} catch (NumberFormatException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		}
	}
}
