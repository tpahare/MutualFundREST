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
import com.google.gson.Gson;
import com.model.FundDAO;
import com.model.Model;
import com.view.Message;

public class CreateFundAction extends Action {
	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory.getInstance(CreateFundForm.class);
	FundDAO fundDAO;

	Gson gson = new Gson();
	Message message = new Message();

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

		if (employee == null) {
			message.setMessage("Please login first");
			return gson.toJson(message);
		}

		try {
			CreateFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			if (!form.isPresent()) {
				return "CreateFund.jsp";
			}

			errors.addAll(form.getValidationErrors());

			if (errors.size() != 0) {
				message.setMessage(errors.toString());
				return gson.toJson(message);
			}

			FundBean[] fund = fundDAO.match(MatchArg.equals("fundName", form.getFundName()));
			if (fund.length != 0) {
				message.setMessage("Fund by the name \"" + form.getFundName() + "\" already exists");
				return gson.toJson(message);
			}

			fund = fundDAO.match(MatchArg.equals("ticker", form.getTicker()));
			if (fund.length != 0) {
				message.setMessage("Ticker already exists for the fund \"" + fund[0].getFundName() + "\"");
				return gson.toJson(message);
			}

			FundBean newFund = new FundBean();

			newFund.setTicker(form.getTicker());
			newFund.setFundName(form.getFundName());
			fundDAO.create(newFund);
			message.setMessage("Fund " + newFund.getFundName() + "(" + newFund.getTicker() + ") created successfully");
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
