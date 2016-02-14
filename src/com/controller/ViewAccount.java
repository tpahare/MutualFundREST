package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import com.databean.CustomerBean;
import com.databean.ViewCustomerAccountBean;
import com.model.CustomerDAO;
import com.model.FundPriceHistoryDAO;
import com.model.Model;


public class ViewAccount extends Action{

	
	private CustomerDAO cDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	
	public ViewAccount(Model model) {
		cDAO = model.getCustomerDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}
	
	@Override
	public String getName() {
		
		return "ViewAccount.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);
		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if(customer == null){
			errors.add("Please login first");
			return "CustomerLogin.do";
		}		
		
		
		try {
			
			CustomerBean customerBean = (CustomerBean) session.getAttribute("customer");
			CustomerBean customerBeanNow = cDAO.read(customerBean.getCid());
				
				ViewCustomerAccountBean viewCusBean = new ViewCustomerAccountBean();
				
				//load data to bean
				viewCusBean.setCid(customerBeanNow.getCid());
				viewCusBean.setUsername(customerBeanNow.getUsername());
				viewCusBean.setFirstname(customerBeanNow.getFirstname());
				viewCusBean.setLastname(customerBeanNow.getLastname());
				viewCusBean.setAddrline1(customerBeanNow.getAddr_line1());
				viewCusBean.setAddrline2(customerBeanNow.getAddr_line2());
				viewCusBean.setCity(customerBeanNow.getCity());
				viewCusBean.setState(customerBeanNow.getState());
				viewCusBean.setZip(customerBeanNow.getZip());
				viewCusBean.setCash(customerBeanNow.getCash());
				
				//other data load
				
				System.out.println("User Name:" + customerBeanNow.getUsername());				
				
				//add the bean to the List
				
				
			String date = fundPriceHistoryDAO.getMaxDate();
			request.setAttribute("customer",viewCusBean);
			request.setAttribute("Date", date);
			return "ViewAccount.jsp";
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "error.jsp";
	}

	
}
