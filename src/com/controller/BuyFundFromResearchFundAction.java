package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.form.BuyFundFromResearchFundForm;
import com.model.CustomerDAO;
import com.model.FundDAO;
import com.model.Model;
import com.model.TrancDAO;

public class BuyFundFromResearchFundAction extends Action{

	private FormBeanFactory<BuyFundFromResearchFundForm> formBeanFactory = FormBeanFactory.getInstance(BuyFundFromResearchFundForm.class);
	private CustomerDAO cDAO;
	private TrancDAO tDAO;
	private FundDAO fDAO;
	
	public BuyFundFromResearchFundAction(Model model) {
		cDAO = model.getCustomerDAO();
		tDAO = model.getTrancDAO();
		fDAO = model.getFundDAO();
	}

	@Override
	public String getName() {
		return "BuyFundFromResearchFund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		
		
		try {
			//get the form
			BuyFundFromResearchFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			String fundName = (String) request.getAttribute("fundName");
			String fundHistory = (String) request.getAttribute("fundHistory");
			String tickerName = (String) request.getAttribute("tickerName");
			
			
			System.out.println("fund name:"+fundName);
			
			//validation error check
			//errors.addAll(form.getValidationErrors());
			
			if (errors.size() > 0) {
				return "ResearchFund.jsp";
			}
			
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
		}
		
		return null;
	}
}
