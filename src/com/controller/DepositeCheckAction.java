package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.databean.CustomerBean;
import com.databean.EmployeeBean;
import com.databean.TransactionBean;
import com.form.DepositeCheckForm;
import com.model.CustomerDAO;
import com.model.Model;
import com.model.TrancDAO;

public class DepositeCheckAction extends Action {
	private FormBeanFactory<DepositeCheckForm> formBeanFactory = FormBeanFactory.getInstance(DepositeCheckForm.class);
	CustomerDAO customerDAO;
	TrancDAO trancDAO;
	public DepositeCheckAction(Model model) {
		this.customerDAO = model.getCustomerDAO();
		this.trancDAO = model.getTrancDAO();
	}
	@Override
	public String getName() {
		return "DepositCheck.do";
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
			String customer = request.getParameter("depositcheckcid");
			if (customer == null || customer.length() == 0) {
				return "ViewCustomerAccount.do";
			}
//			if (request.getParameter("button") == null) {
//				return "ViewCustomerAccount.do";
//			}
//			if (!request.getParameter("button").equals("Deposit")) {
//				errors.add("Invalid Button");
//				return "ViewCustomerAccount.do";
//			}
			DepositeCheckForm form = formBeanFactory.create(request);
			request.setAttribute("form",form);
			
			int depositcheckcid = Integer.parseInt(request.getParameter("depositcheckcid"));
	        request.setAttribute("depositcheckcid", depositcheckcid);
			
			if(!form.isPresent()){
				return "DepositCheck.jsp";
			}
			
//			System.out.print("Username here:" + form.getUsername());
//			System.out.print("Amount here:" + form.getAmount());
			System.out.print("Deposit Check look 1");
			
			
			errors.addAll(form.getValidationErrors());
			
			if(errors.size()!=0){
				return "DepositCheck.jsp";
			}
			
			
//			CustomerBean[] customer = customerDAO.match(MatchArg.equals("username", form.getUsername()));
//			if(customer.length == 0){
//				errors.add("Username does not exist");
//				return "DepositCheck.jsp";
//			}
			
			
			//System.out.println("hh");
			
			TransactionBean tBean = new TransactionBean();
			tBean.setCid(depositcheckcid);
			tBean.setTransactiontype("deposit");
			
			long depositmoney = (long) (100 * Double.parseDouble(form.getAmount()));
			
			tBean.setAmount(depositmoney);
			
			trancDAO.create(tBean);
			
			System.out.print("look 5");
			
			return "DepositCheckSuccess.jsp";
		}catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
		
		
	}

}
