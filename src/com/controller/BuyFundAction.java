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
public class BuyFundAction extends Action {
	private FormBeanFactory<BuyFundForm> formBeanFactory = FormBeanFactory.getInstance(BuyFundForm.class);
	CustomerDAO cDAO;
	TrancDAO tDAO;
	FundDAO fDAO;
	FundPriceHistoryDAO fphDAO;
	public BuyFundAction(Model model) {
		cDAO = model.getCustomerDAO();
		tDAO = model.getTrancDAO();
		fDAO = model.getFundDAO();
		fphDAO = model.getFundPriceHistoryDAO();
	}
	@Override
	public String getName() {
		return "BuyFund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if(customer == null){
			errors.add("Please login first");
			return "CustomerLogin.do";
		}
		try {
			BuyFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			FundBean[] fundList = fDAO.getFundList();
			request.setAttribute("fundList", fundList);
			if (!form.isPresent()) {
				return "BuyFund.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0) {
				return "BuyFund.jsp";
			}
			String fundsymbol = form.getFundsymbol();
			//CustomerBean c = (CustomerBean) session.getAttribute("customer");
			CustomerBean c = cDAO.read(customer.getCid());
			FundBean[] fb = fDAO.match(MatchArg.equals("ticker", fundsymbol));
			if (fb.length == 0) {
				errors.add("This fund does not exist");
				return "BuyFund.jsp";
			}
			try {
				double tmp = Double.parseDouble(form.getMoney());
			} catch (Exception e) {
				errors.add("Dollar Amount should be a number");
				return "BuyFund.jsp";
			}
			TransactionBean transaction = new TransactionBean();
			//parse the money
			BigDecimal bg = new BigDecimal(form.getMoney());
			if (bg.doubleValue() <= 0) {
				errors.add("Dollar Amount can not be negative");
				return "BuyFund.jsp";
			}
			if (bg.scale() > 2) {
				errors.add("Dollar Amount should have at most two decimal places");
				return "BuyFund.jsp";
			}
			if (bg.doubleValue() < 1) {
				errors.add("Dollar amount should not be less than 1 dollar");
				return "BuyFund.jsp";
			}
			Double amount = Double.parseDouble(form.getMoney());
			DecimalFormat df = new DecimalFormat("0.00");
			String tmpAmount = df.format(amount);
			double amount1 = Double.parseDouble(tmpAmount);
			long money = (long) (100 * amount1);
			TransactionBean[] tb = tDAO.match(MatchArg.equals("executedate", null));
			long cash = c.getCash();
			for (int i = 0; i < tb.length; i++) {
				if ((tb[i].getTransactiontype().equals("buy") || tb[i].getTransactiontype().equals("request")) && tb[i].getCid() == c.getCid()) {
					cash -= tb[i].getAmount();
				}
			}
			if (cash - money < 0) {
				errors.add("Sorry you don't have enough money");
				return "BuyFund.jsp";
			}
			transaction.setAmount(money);
			transaction.setCid(c.getCid());
			transaction.setFundid(fb[0].getFundid());
			transaction.setTransactiontype("buy");
			tDAO.create(transaction);
			
			return "BuyFundSuccessfully.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (NumberFormatException e) {
			errors.add("Your input should be a number");
			return "BuyFund.jsp";
		}
	}
}
