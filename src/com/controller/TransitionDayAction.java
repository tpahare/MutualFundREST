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

import com.databean.*;
import com.form.*;
import com.model.*;

public class TransitionDayAction extends Action {
	CustomerDAO cDAO;
	TrancDAO tDAO;
	FundDAO fDAO;
	FundPriceHistoryDAO fphDAO;
	PositionDAO pDAO;
	public TransitionDayAction(Model model) {
		cDAO = model.getCustomerDAO();
		tDAO = model.getTrancDAO();
		fDAO = model.getFundDAO();
		fphDAO = model.getFundPriceHistoryDAO();
		pDAO = model.getPosDAO();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "TransitionDay.do";
	}

	@Override
	public synchronized String perform(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		
		if(employee == null) {
			errors.add("Please Login first");
			return "EmployeeLogin.do";
		}
		try {
			String mdate = fphDAO.getMaxDate();
			request.setAttribute("lastDate",mdate);
			FundBean[] fundBeans = fDAO.match();
			request.setAttribute("fundBeans", fundBeans);
			String action = request.getParameter("action");
			if (action == null) {
				request.setAttribute("fundBeans", fundBeans);
				return "TransitionDayInput.jsp";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			
			//String mdate = fphDAO.getMaxDate();
			String date = (String) request.getParameter("pricedate");
			if (date == null || date.length() == 0) {
				errors.add("Transition date can not be empty");
				request.setAttribute("fundBeans", fundBeans);
				return "TransitionDayInput.jsp";
			}
			Date transactionDate = sdf1.parse(date);
			//request.setAttribute("lastDate",mdate);
			if (mdate != null) {
				Date maxdate = sdf.parse(mdate);
				if(transactionDate.compareTo(maxdate) <= 0){
					errors.add("Transition day for this date has already occured");
					request.setAttribute("fundBeans", fundBeans);
					return "TransitionDayInput.jsp";
				}
			}
			date = sdf.format(transactionDate);
			TransactionBean[] tb = tDAO.match(MatchArg.equals("executedate", null));
			Map map = request.getParameterMap();
			String[] fids = (String[]) map.get("fundid");
			String[] prices = (String[]) map.get("price");
			if (fids == null || fids.length == 0) {
				errors.add("You do not have any fund");
				return "TransitionDayInput.jsp";
			}
			for (int i = 0; i < prices.length; i++) {
				checkValidation(prices[i], errors);
				if (errors.size() > 0) {
					request.setAttribute("fundBeans", fundBeans);
					return "TransitionDayInput.jsp";
				}
			}
			
			Map<Integer, Double> mapPrice = new HashMap<Integer, Double>();
			int n = fids.length;
			for (int i = 0; i < n; i++) {
				addFundHistory(Integer.parseInt(fids[i]), date, (long) (100 * Double.parseDouble(prices[i])), fphDAO);
				mapPrice.put(Integer.parseInt(fids[i]), Double.parseDouble(prices[i]));
			}
			for (int i = 0; i < tb.length; i++) {
				CustomerBean customer = cDAO.read(tb[i].getCid());
				operation(customer, tb[i], pDAO, cDAO, tDAO, mapPrice, tb[i].getTransactiontype());
				tb[i].setExecutedate(date);
				tDAO.update(tb[i]);
			}
			return "TransitionDaySuccess.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (ParseException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (NumberFormatException e) {
			errors.add("Price should be a number");
			return "TransitionDayInput.jsp";
		}
	}
	private void addFundHistory(int fundId, String priceDate, long price, FundPriceHistoryDAO fphDAO) throws FormBeanException, RollbackException{
		FundPriceHistoryBean bean = new FundPriceHistoryBean();
		bean.setFundid(fundId);
		bean.setPrice(price);
		bean.setPricedate(priceDate);
		fphDAO.create(bean);
	}
	private void operation(CustomerBean customer, TransactionBean transaction, PositionDAO pDAO, CustomerDAO cDAO, TrancDAO tDAO, Map<Integer, Double> map, String type) throws FormBeanException, RollbackException {
		switch (type) {
		case "buy" :
			opbuy(customer, transaction, pDAO, cDAO, tDAO, map);
			break;
		case "sell" :
			opsell(customer, transaction, pDAO, cDAO, tDAO, map);
			break;
		case "request" :
			oprequest(customer, transaction, pDAO, cDAO, tDAO, map);
			break;
		case "deposit" :
			opdeposit(customer, transaction, pDAO, cDAO, tDAO, map);
			break;
		default :
			
		}
	}
	private void opbuy(CustomerBean customer, TransactionBean transaction, PositionDAO pDAO, CustomerDAO cDAO, TrancDAO tDAO, Map<Integer, Double> map) throws FormBeanException, RollbackException {
		/*double share = (((double) transaction.getAmount()) / 100) / map.get(transaction.getFundid());
		if (share < 0.001) {
			return;
		}*/
		long cash = customer.getCash() - transaction.getAmount();
		customer.setCash(cash);
		cDAO.update(customer);
		PositionBean position = new PositionBean();
		position.setCustomerid(customer.getCid());
		position.setFundid(transaction.getFundid());
		double share = (((double) transaction.getAmount()) / 100) / map.get(transaction.getFundid());
		DecimalFormat df = new DecimalFormat("0.000");
		share = Double.valueOf(df.format(share));
		transaction.setShares((long)(share * 1000));
		tDAO.update(transaction);
		position.setShares((long) (share * 1000));
		PositionBean p = pDAO.read(customer.getCid(),transaction.getFundid());
		if (p == null) {
			pDAO.create(position);
		} else {
			position.setShares(p.getShares() + position.getShares());
			pDAO.update(position);
		}
	}
	private void opsell(CustomerBean customer, TransactionBean transaction, PositionDAO pDAO, CustomerDAO cDAO, TrancDAO tDAO, Map<Integer, Double> map) throws FormBeanException, RollbackException {
		/*double share = ((double) transaction.getShares()) / 1000;
		double price = share * map.get(transaction.getFundid());
		if (price < 0.01) {
			return;
		}*/
		PositionBean pos = pDAO.read(customer.getCid(),transaction.getFundid());
		pos.setShares(pos.getShares() - transaction.getShares());
		pDAO.update(pos);
		double share = ((double) (transaction.getShares())) / 1000;
		double price = share * map.get(transaction.getFundid());
		DecimalFormat df = new DecimalFormat("0.00");
		double money = Double.valueOf(df.format(price));
		long cash = ((long) (money * 100));
		customer.setCash(customer.getCash() + cash);
		cDAO.update(customer);
		transaction.setAmount(cash);
		tDAO.update(transaction);
	}
	private void opdeposit(CustomerBean customer, TransactionBean transaction, PositionDAO pDAO, CustomerDAO cDAO, TrancDAO tDAO, Map<Integer, Double> map) throws FormBeanException, RollbackException {
		long cash = transaction.getAmount();
		customer.setCash(customer.getCash() + cash);
		cDAO.update(customer);
	}
	private void oprequest(CustomerBean customer, TransactionBean transaction, PositionDAO pDAO, CustomerDAO cDAO, TrancDAO tDAO, Map<Integer, Double> map) throws FormBeanException, RollbackException {
		long cash = transaction.getAmount();
		customer.setCash(customer.getCash() - cash);
		cDAO.update(customer);
	}
	private void checkValidation(String s, List<String> errors) {
		if (s == null || s.trim().length() == 0) {
			errors.add("Price cannot be empty");
			return;
		}
		double price = 0.0;
		try {
			price = Double.parseDouble(s);
		} catch (Exception e) {
			errors.add("Price should be a number");
			return;
		}
		BigDecimal bg = new BigDecimal(s);
		if (bg.doubleValue() <= 0) {
			errors.add("Price cannot be less than or equal to zero");
			return;
		}
		if (bg.scale() > 2) {
			errors.add("Price should have at most two decimal places");
			return;
		}
		if (bg.doubleValue() >= 1000) {
			errors.add("Price should be less than one thousand");
			return;
		}
	}
}
