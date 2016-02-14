package com.controller;
/**
 * @author faisalshahnewaz.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.databean.CustomerBean;
import com.databean.EmployeeBean;
import com.databean.FundPriceHistoryBean;
import com.databean.ViewCustomerAccountBean;
import com.form.ViewCustomerAccountSearchForm;
import com.model.CustomerDAO;
import com.model.FundPriceHistoryDAO;
import com.model.Model;

public class ViewCustomerAccountSearchAction extends Action{

	private FormBeanFactory<ViewCustomerAccountSearchForm> formBeanFactory = FormBeanFactory.getInstance(ViewCustomerAccountSearchForm.class);
	private CustomerDAO cDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	
	public ViewCustomerAccountSearchAction(Model model) {
		cDAO = model.getCustomerDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}

	@Override
	public String getName() {
		return "ViewCustomerAccountSearch.do";
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
        
		/*
		 * 1.get the list of customers from CustomerDAO
		 * 2. create a list of View customer account to hold the objects of view customer acc.bean.
		 * 3. load data from different table to each view cus acc bean.
		 * 4. add the beans to the list.
		 * 5. pass the bean list to jsp.
		 * 6. return the jsp name.
		*/
		CustomerBean[] customerBeans;
		List<ViewCustomerAccountBean> customerList = new ArrayList<ViewCustomerAccountBean>();
		
		try {
			
			//show transaction date
			String date = fundPriceHistoryDAO.getMaxDate();
			request.setAttribute("Date", date);
			
			//get the form variable username from jsp request
			ViewCustomerAccountSearchForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);
			
	        //check if any validation error
	        errors.addAll(form.getValidationErrors());
	        if(errors.size()>0) {
	        	return "ViewCustomerAccount.jsp";
	        }
	       	
	        customerBeans = cDAO.getCustomerListbySearch(form.getUsername());
			request.setAttribute("customerList",customerList);
			
			//check if there is any customer?
			if (customerBeans.length>0) {
				for(CustomerBean customerBean: customerBeans) {
					
					ViewCustomerAccountBean viewCusBean = new ViewCustomerAccountBean();
					
					//load data to bean
					viewCusBean.setCid(customerBean.getCid());
					viewCusBean.setUsername(customerBean.getUsername());
					viewCusBean.setFirstname(customerBean.getFirstname());
					viewCusBean.setLastname(customerBean.getLastname());
					viewCusBean.setAddrline1(customerBean.getAddr_line1());
					viewCusBean.setAddrline2(customerBean.getAddr_line2());
					viewCusBean.setCity(customerBean.getCity());
					viewCusBean.setState(customerBean.getState());
					viewCusBean.setZip(customerBean.getZip());
					viewCusBean.setCash(customerBean.getCash());
					
					//other data load
					
					System.out.println("User Name:" + customerBean.getUsername());				
					
					//add the bean to the List
					customerList.add(viewCusBean);
				}				
				
				System.out.println(customerBeans[0].getUsername());
			} else {
				errors.add("No customer named " + form.getUsername() + " found");
			}
			
			
			
			return "ViewCustomerAccount.jsp";
			
		} catch (RollbackException e) {
			errors.add(e.getMessage());
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
		} catch (ParseException e) {
			errors.add(e.getMessage());
		} 
		
		return "error.jsp";
	}
}
