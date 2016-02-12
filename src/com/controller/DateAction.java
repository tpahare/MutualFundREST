package com.controller;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.databean.EmployeeBean;
import com.databean.FundBean;
import com.databean.FundPriceHistoryBean;
import com.form.DateForm;
import com.model.FundDAO;
import com.model.FundPriceHistoryDAO;
import com.model.Model;

public class DateAction extends Action {
	private FormBeanFactory<DateForm> formBeanFactory = FormBeanFactory.getInstance(DateForm.class);
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	private FundDAO fundDAO;

	public DateAction(Model model) {
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
		fundDAO = model.getFundDAO();
	}

	@Override
	public String getName() {
		return "Date.do";
	}

	@Override
	public synchronized String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session == null){
			return "Index.jsp";
		}
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		
		if(employee == null) {
			errors.add("Please Login first");
			return "EmployeeLogin.do";
		}
		
		try{
			DateForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if(!form.isPresent()){
				return "TransitionDay.jsp";
			}
			
			errors.addAll(form.getValidationErrors());
			
			if(errors.size() > 0){
				return "TransitionDay.jsp";
			}
		
			//System.out.println("DATE: "+ form.getPricedate());
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			
			String date = fundPriceHistoryDAO.getMaxDate();
			Date transactionDate = sdf1.parse(form.getPricedate());
			if (date != null) {
				Date maxdate = sdf.parse(date);
				if(transactionDate.compareTo(maxdate) <= 0){
					errors.add("Transition day for this date has already occured");
					return "TransitionDay.jsp";
				}
			}
				session.setAttribute("date", sdf.format(transactionDate));
			
			FundBean[] fundBeans = fundDAO.match();
			/*FundPriceHistoryBean[] fphBeans = fundPriceHistoryDAO.match();
			boolean flag = false;
			for (int i = 0; i < fphBeans.length; i++) {
				if (fphBeans[i].getPrice() == -1) {
					flag = true;
				}
			}*/
			/*if (date != null && transactionDate.compareTo(sdf.parse(date)) == 0 && !flag) {
				errors.add("Transition day for this date has already occured");
				return "TransitionDay.jsp";
			}
			if (date != null && transactionDate.compareTo(sdf.parse(date)) > 0 && flag) {
				errors.add("Previous transition day has not finished, please wait!");
				return "TransitionDay.jsp";
			}*/

			for (int i = 0; i < fundBeans.length; i++) {
				FundPriceHistoryBean fphBean = new FundPriceHistoryBean();
				fphBean.setFundid(fundBeans[i].getFundid());
				fphBean.setPricedate(sdf.format(transactionDate));
				fphBean.setPrice((long) (-1));
				fundPriceHistoryDAO.create(fphBean);
			}
			request.setAttribute("fundBeans", fundBeans);
			
			return "TransitionDayInput.jsp";
		}catch (FormBeanException e) {
			errors.add(e.toString());
			return "TransitionDay.jsp";
		} catch (RollbackException e1) {
			errors.add(e1.toString());
			return "TransitionDay.jsp";
		} catch (ParseException e) {
			errors.add("Please enter date as - yyyy-MM-dd");
			return "TransitionDay.jsp";
		}

	}

}