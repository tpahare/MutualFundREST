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
import com.view.Menu;
import com.view.Message;

public class FundInfoAction extends Action {
	
	FundDAO fDAO;
	PositionDAO pDAO;
	FundPriceHistoryDAO fphDAO;
	CustomerDAO cDAO;
	
	Gson gson = new Gson();
	Message message = new Message();
	Menu menu = new Menu();
	
	public FundInfoAction(Model model) {
		fDAO = model.getFundDAO();
		pDAO = model.getPosDAO();
		fphDAO = model.getFundPriceHistoryDAO();
		cDAO = model.getCustomerDAO();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "viewPortfolio";
	}

	@Override
	public String perform(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		List<FundInfoBean1> fundInfo = new ArrayList<FundInfoBean1>();
		request.setAttribute("fundInfo", fundInfo);
		HttpSession session = request.getSession();
		
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		
		if(customer == null) {
			
			if(employee != null) {
				message.setMessage("I am sorry you are not authorized to perform that action");
				return gson.toJson(message);
			}
			
//			errors.add("Please Login first");
//			return "EmployeeLogin.do";
			message.setMessage("You must log in prior to make that request");
			return gson.toJson(message);
		}
		
		/*if (request.getParameter("action") == null) {
			return "ViewAccount.jsp";
		}
		if (!request.getParameter("action").equals("View")) {
			errors.add("Invalid Button");
			return "ViewAccount.jsp";
		}*/
		
		try {
			PositionBean[] pb = pDAO.match(MatchArg.equals("customerid", customer.getCid()));
			for (int i = 0; i < pb.length; i++) {
				FundBean fb = fDAO.read(pb[i].getFundid());
				double recentPrice = fphDAO.getRecentPrice(pb[i].getFundid());
//				fundInfo.add(new FundInfoBean(fb.getFundid(), fb.getSymbol(), fb.getName(), pb[i].getShares(), recentPrice * pb[i].getShares()));
				fundInfo.add(new FundInfoBean1(fb.getName(), String.valueOf(pb[i].getShares()), String.valueOf(recentPrice)));
			}
			
			CustomerBean customerBeanNow = cDAO.read(customer.getCid());
			String cash = String.valueOf(customerBeanNow.getCash());
			
			if(fundInfo.size()==0) {
				message.setMessage("You do not have any funds at this time");
				return gson.toJson(message);
			}
			
			return gson.toJson(fundInfo) + " " + gson.toJson(cash);
		} catch (RollbackException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		} catch (ParseException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		}
	}
	
}
