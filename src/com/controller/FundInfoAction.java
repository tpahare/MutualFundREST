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

public class FundInfoAction extends Action {
	FundDAO fDAO;
	PositionDAO pDAO;
	FundPriceHistoryDAO fphDAO;
	public FundInfoAction(Model model) {
		fDAO = model.getFundDAO();
		pDAO = model.getPosDAO();
		fphDAO = model.getFundPriceHistoryDAO();
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
		List<FundInfoBean> fundInfo = new ArrayList<FundInfoBean>();
		request.setAttribute("fundInfo", fundInfo);
		HttpSession session = request.getSession();
		
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if(customer == null){
			errors.add("Please login First");
			return "CustomerLogin.do";
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
				long recentPrice = fphDAO.getRecentPrice(pb[i].getFundid());
				fundInfo.add(new FundInfoBean(fb.getFundid(), fb.getTicker(), fb.getFundName(), pb[i].getShares(), recentPrice * pb[i].getShares()));
			}
			return "FundInfo.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (ParseException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
	
}
