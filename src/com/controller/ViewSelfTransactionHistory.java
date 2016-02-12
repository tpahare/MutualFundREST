package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import com.databean.CustomerBean;
import com.databean.FundBean;
import com.databean.FundPriceHistoryBean;
import com.databean.TransactionBean;
import com.databean.ViewCustomerAccountBean;
import com.databean.ViewTransactionBean;
import com.model.CustomerDAO;
import com.model.FundDAO;
import com.model.FundPriceHistoryDAO;
import com.model.Model;
import com.model.TrancDAO;

public class ViewSelfTransactionHistory extends Action {

	private CustomerDAO cDAO;
	private TrancDAO tDAO;
	private FundDAO fDAO;
	private FundPriceHistoryDAO fphDAO;
	public ViewSelfTransactionHistory(Model model) {
		cDAO = model.getCustomerDAO();
		tDAO = model.getTrancDAO();
		fDAO = model.getFundDAO();
		fphDAO = model.getFundPriceHistoryDAO();
	}

	@Override
	public String getName() {

		return "ViewSelfTransactionHistory.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if (customer == null) {
			errors.add("Please login first");
			return "CustomerLogin.do";
		}

		List<ViewTransactionBean> transactions = new ArrayList<ViewTransactionBean>();

		try {

			TransactionBean[] transactionBeans = tDAO.match(
					MatchArg.equals("cid", customer.getCid()));

			for (int i = 0; i < transactionBeans.length; i++) {

				System.out.println("FundName:" + transactionBeans[i].getFundid());

				ViewTransactionBean viewTransaction = new ViewTransactionBean();

				System.out.println(transactionBeans[i].getFundid() != 0);

				if (transactionBeans[i].getFundid() != 0) {
					FundBean fund = fDAO.read(transactionBeans[i].getFundid());
					viewTransaction.setFundname(fund.getFundName());
				}

				// System.out.println("FundName:" + fund.getFundName());
				if (transactionBeans[i].getExecutedate() == null) {
					viewTransaction.setPrice(0);
				} else {
					if (transactionBeans[i].getTransactiontype().equals("buy") || transactionBeans[i].getTransactiontype().equals("sell")) {
					FundPriceHistoryBean fphBean = fphDAO.read(transactionBeans[i].getFundid(),transactionBeans[i].getExecutedate());
					viewTransaction.setPrice(fphBean.getPrice());}
			
				}
				viewTransaction.setTransactiontype(transactionBeans[i].getTransactiontype());
				viewTransaction.setAmount(transactionBeans[i].getAmount());
				viewTransaction.setExecutedate(transactionBeans[i].getExecutedate());
				viewTransaction.setShares(transactionBeans[i].getShares());
				transactions.add(viewTransaction);
			}

			request.setAttribute("transactions", transactions);

			return "ViewSelfTransactionHistory.jsp";

		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error.jsp";
		}
	}

}
