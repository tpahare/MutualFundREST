package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import com.databean.CustomerBean;
import com.databean.EmployeeBean;
import com.databean.FundBean;
import com.databean.FundPriceHistoryBean;
import com.databean.TransactionBean;
import com.databean.ViewCustomerAccountBean;
import com.databean.ViewTransactionBean;
import com.model.CustomerDAO;
import com.model.FundDAO;
import com.model.FundPriceHistoryDAO;
import com.model.Model;
import com.model.Model.*;
import com.model.TrancDAO;


public class ViewTransactionHistory extends Action{

	
	private CustomerDAO cDAO;
	private TrancDAO tDAO;
	private FundDAO fDAO;
	private FundPriceHistoryDAO fphDAO;
	
	public ViewTransactionHistory(Model model) {
		cDAO = model.getCustomerDAO();
		tDAO = model.getTrancDAO();
		fDAO = model.getFundDAO();
		fphDAO = model.getFundPriceHistoryDAO();
	}
	
	@Override
	public String getName() {
		
		return "ViewTransactionHistory.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);
		
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		
		if(employee == null) {
			errors.add("Please Login first");
			return "EmployeeLogin.do";
		}
		
		List<ViewTransactionBean> transactions = new ArrayList<ViewTransactionBean>();
		
		
		try {
			
			String customer = request.getParameter("viewtransactionhistorycid");
			if (customer == null || customer.length() == 0) {
				return "ViewCustomerAccount.do";
			}
//			if (request.getParameter("button") == null) {
//				return "ViewCustomerAccount.do";
//			}
//			if (!request.getParameter("button").equals("View")) {
//				errors.add("Invalid Button");
//				return "ViewCustomerAccount.do";
//			}
			TransactionBean[] transactionBeans = tDAO.match(MatchArg.equals("cid", Integer.parseInt(request.getParameter("viewtransactionhistorycid"))));
						
			
			for (int i = 0; i < transactionBeans.length; i++) {
				
				System.out.println("FundName:" + transactionBeans[i].getFundid());
				
				ViewTransactionBean viewTransaction = new ViewTransactionBean();
				
				System.out.println(transactionBeans[i].getFundid() != 0);
				
				if(transactionBeans[i].getFundid() != 0) {
					FundBean fund = fDAO.read(transactionBeans[i].getFundid());
					viewTransaction.setFundname(fund.getFundName());
				}
				
				
				//System.out.println("FundName:" + fund.getFundName());
				
				//FundPriceHistoryBean fphBean = fphDAO.read(transactionBeans[i].getFundid(),transactionBeans[i].getExecutedate());
				if (transactionBeans[i].getExecutedate() == null) {
					viewTransaction.setPrice(0);
				} else {
					if (transactionBeans[i].getTransactiontype().equals("buy") || transactionBeans[i].getTransactiontype().equals("sell")) {
					FundPriceHistoryBean fphBean = fphDAO.read(transactionBeans[i].getFundid(),transactionBeans[i].getExecutedate());
					viewTransaction.setPrice(((double)(fphBean.getPrice())) / 100);}
			
				}
				viewTransaction.setTransactiontype(transactionBeans[i].getTransactiontype());
				viewTransaction.setAmount(((double)(transactionBeans[i].getAmount())) / 100);
				viewTransaction.setExecutedate(transactionBeans[i].getExecutedate());
				viewTransaction.setShares(((double)(transactionBeans[i].getShares())) / 1000);
				transactions.add(viewTransaction);
			}
			
			request.setAttribute("transactions",transactions);
			
//			System.out.println(customerBeans[0].getUsername());
			
			return "ViewTransactionHistory.jsp";
			
			
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error.jsp";
		}
	}

	
}
