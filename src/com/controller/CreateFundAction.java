/**
 * @author tpahare
 */
package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.databean.CustomerBean;
import com.databean.EmployeeBean;
import com.databean.FundBean;
import com.databean.FundPriceHistoryBean;
import com.form.CreateFundForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.FundDAO;
import com.model.FundPriceHistoryDAO;
import com.model.Model;
import com.view.Message;

public class CreateFundAction extends Action {
	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory.getInstance(CreateFundForm.class);
	FundDAO fundDAO;
	FundPriceHistoryDAO fphDAO;
	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	Message message = new Message();

	public CreateFundAction(Model model) {
		this.fundDAO = model.getFundDAO();
		this.fphDAO = model.getFundPriceHistoryDAO();
	}

	@Override
	public String getName() {
		return "createFund";
	}

	@Override
	public String perform(HttpServletRequest request) {

		HttpSession session = request.getSession();

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		/*EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");

		if (employee == null) {
			message.setMessage("You must log in prior to making this request");
			return gson.toJson(message);
		}*/
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if(employee == null) {
			if(customer != null) {
				message.setMessage("I'm sorry you are not authorized to perform that action");
				return gson.toJson(message);
			}
			message.setMessage("You must log in prior to making this request");
			return gson.toJson(message);
		}
		try {
			CreateFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			errors.addAll(form.getValidationErrors());

			if (errors.size() != 0) {
				message.setMessage(errors.toString());
				return gson.toJson(message);
			}

			FundBean[] fund = fundDAO.match(MatchArg.equals("name", form.getName()));
			if (fund.length != 0) {
				message.setMessage("I'm sorry, there was a problem creating the fund");
				return gson.toJson(message);
			}

			fund = fundDAO.match(MatchArg.equals("symbol", form.getSymbol()));
			if (fund.length != 0) {
				message.setMessage("I'm sorry, there was a problem creating the fund");
				return gson.toJson(message);
			}

			FundBean newFund = new FundBean();

			newFund.setSymbol(form.getSymbol());
			newFund.setName(form.getName());
			fundDAO.create(newFund);
			FundBean[] fund2 = fundDAO.match(MatchArg.equals("name", form.getName()));
			FundPriceHistoryBean fphBean = new FundPriceHistoryBean();

			fphBean.setFundid(fund2[0].getFundid());
			fphBean.setPrice(Double.parseDouble(request.getParameter("initial_value")));
			
			//PriceDate
			if (fphDAO.match().length == 0) {
				fphBean.setPricedate("1");
				fphDAO.create(fphBean);
			} else {
				FundPriceHistoryBean[] fundPriceHistoryBeans = fphDAO.match();
				int max = Integer.MIN_VALUE;
				for (FundPriceHistoryBean fBean : fundPriceHistoryBeans) {
					if (Integer.parseInt(fBean.getPricedate()) > max) {
						max = Integer.parseInt(fBean.getPricedate());
					}
				}
				fphBean.setPricedate(Integer.toString(max));
				fphDAO.create(fphBean);
			}
			
//			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//			Date date = new Date();
//			fphBean.setPricedate(dateFormat.format(date));
//			fphDAO.create(fphBean);
			message.setMessage("The fund has been successfully created");
			return gson.toJson(message);
		} catch (RollbackException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		} catch (FormBeanException e) {
			message.setMessage(e.getMessage());
			return gson.toJson(message);
		}

	}

}
