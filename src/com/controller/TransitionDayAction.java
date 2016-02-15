package com.controller;

import java.util.*;
import java.math.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.genericdao.DuplicateKeyException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import java.text.*;
import java.time.chrono.MinguoChronology;

import com.databean.*;
import com.form.*;
import com.model.*;
//import com.sun.xml.internal.ws.api.message.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.view.Message;
public class TransitionDayAction extends Action {
	CustomerDAO cDAO;
	TrancDAO tDAO;
	FundDAO fDAO;
	FundPriceHistoryDAO fphDAO;
	PositionDAO pDAO;
	Gson gson;
	Message message;
	public TransitionDayAction(Model model) {
		cDAO = model.getCustomerDAO();
		tDAO = model.getTrancDAO();
		fDAO = model.getFundDAO();
		fphDAO = model.getFundPriceHistoryDAO();
		pDAO = model.getPosDAO();
		gson = new GsonBuilder().disableHtmlEscaping().create();
		message = new Message();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "TransitionDay";
	}

	@Override
	public synchronized String perform(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if(employee == null) {
			
			if(customer != null) {
				message.setMessage("I'm sorry you are not authorized to perform that action");
				return gson.toJson(message);
			}
			message.setMessage("You must log in prior to making this request");
			return gson.toJson(message);
		}
		try {
			FundBean[] fundBeans = fDAO.match();
			for (FundBean fundBean : fundBeans) {
				double price = fphDAO.getRecentPrice(fundBean.getFundid());
				double min = price - price * 0.1;
				double max = price + price * 0.1;
				int date = Integer.parseInt(fphDAO.getMaxDate(fundBean.getFundid()));
				date++;
				FundPriceHistoryBean fundPriceHistoryBean = new FundPriceHistoryBean();
				fundPriceHistoryBean.setFundid(fundBean.getFundid());
				fundPriceHistoryBean.setPricedate(new Integer(date).toString());
				Random random = new Random();
				double randomPrice = min + (max - min) * random.nextDouble();
				fundPriceHistoryBean.setPrice(randomPrice);
				fphDAO.create(fundPriceHistoryBean);
			}
			message.setMessage("The fund prices have been recalculated");
			return gson.toJson(message);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		}
	}
}
