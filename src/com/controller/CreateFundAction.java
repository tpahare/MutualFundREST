package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.databean.EmployeeBean;
import com.databean.FundBean;
import com.form.CreateFundForm;
import com.model.FundDAO;
import com.model.Model;

public class CreateFundAction extends Action {
	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory.getInstance(CreateFundForm.class);
	FundDAO fundDAO;
	public CreateFundAction(Model model) {
		this.fundDAO = model.getFundDAO();
	}
	@Override
	public String getName() {
		return "CreateFund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		
		HttpSession session = request.getSession();
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		
		if(employee == null) {
			errors.add("Please Login first");
			return "EmployeeLogin.do";
		}
		
		try{
			CreateFundForm form = formBeanFactory.create(request);
			request.setAttribute("form",form);
			
			if(!form.isPresent()){
				return "CreateFund.jsp";
			}
			
			errors.addAll(form.getValidationErrors());
			
			if(errors.size()!=0){
				return "CreateFund.jsp";
			}
			
			FundBean[] fund = fundDAO.match(MatchArg.equals("fundName", form.getFundName()));
			if(fund.length != 0){
				errors.add("Fund by the name \""+ form.getFundName() +"\" already exists");
				return "CreateFund.jsp";
			}

			fund = fundDAO.match(MatchArg.equals("ticker", form.getTicker()));
			if(fund.length != 0){
				errors.add("Ticker already exists for the fund \"" + fund[0].getFundName() + "\"");
				return "CreateFund.jsp";
			}
			
			
			FundBean newFund = new FundBean();
			
			newFund.setTicker(form.getTicker());
			newFund.setFundName(form.getFundName());
			fundDAO.create(newFund);
			return "CreateFundSuccess.jsp";
		}catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
		
		
	}

}
