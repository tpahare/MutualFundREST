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
import com.model.*;
import com.sun.xml.internal.ws.util.xml.CDATA;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.view.Message;
public class SellFundAction extends Action {
	TrancDAO tDAO;
	PositionDAO pDAO;
	FundDAO fDAO;
	FundPriceHistoryDAO fphDAO;
	CustomerDAO cDAO;
	Message message;
	Gson gson;
	public SellFundAction(Model model) {
		cDAO = model.getCustomerDAO();
		tDAO = model.getTrancDAO();
		pDAO = model.getPosDAO();
		fDAO = model.getFundDAO();
		fphDAO = model.getFundPriceHistoryDAO();
		message = new Message();
		gson = new GsonBuilder().disableHtmlEscaping().create();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "sellFund";
	}
	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if(customer == null) {
			if(employee != null) {
				message.setMessage("I'm sorry you are not authorized to perform that action");
				return gson.toJson(message);
			}
			message.setMessage("You must log in prior to making this request");
			return gson.toJson(message);
		}
		try {
			String fundSymbol = request.getParameter("fundSymbol");
			String numShares = request.getParameter("numShares");
			CustomerBean customerBean = cDAO.read(customer.getCid());
			long shares = Integer.parseInt(numShares);
			FundBean[] fundBeans = fDAO.match(MatchArg.equals("symbol", fundSymbol));
			PositionBean positionBean = pDAO.read(customerBean.getCid(),fundBeans[0].getFundid());
			if (shares > positionBean.getShares()) {
				message.setMessage("I'm sorry, you must first deposit sufficient funds in your account in order to make this purchase");
				return gson.toJson(message);
			}
			double price = fphDAO.getRecentPrice(fundBeans[0].getFundid());
			double amount = price * shares;
			customerBean.setCash(customerBean.getCash() + amount);
			cDAO.update(customerBean);
			positionBean.setShares(positionBean.getShares() - shares);
			pDAO.update(positionBean);
			message.setMessage("The sale was successfully completed");
			return gson.toJson(message);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		}
	}

}
