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
import com.databean.FundBean;
import com.databean.FundPriceHistoryBean;
import com.form.ResearchFundSearchForm;
import com.model.FundDAO;
import com.model.FundPriceHistoryDAO;
import com.model.Model;
import com.databean.ResearchBean;

public class ResearchFundAction extends Action {

	private FormBeanFactory<ResearchFundSearchForm> formBeanFactory = FormBeanFactory
			.getInstance(ResearchFundSearchForm.class);
	FundDAO fundDAO;
	FundPriceHistoryDAO fundPriceHistoryDAO;

	public ResearchFundAction(Model model) {
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}

	@Override
	public String getName() {
		return "ResearchFund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if(customer == null){
			errors.add("Please login first");
			return "CustomerLogin.do";
		}
		try {
			// get the form variable username from jsp request
			ResearchFundSearchForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			String fundId = request.getParameter("fundId");
			if (fundId == null) {

				FundBean[] fundList;
				// check search or the whole list
				// System.out.println(form.getAction());
				if (form.getAction() != null) {
					if (form.getAction().equals("ShowAll")) {
						fundList = fundDAO.getFundList();
						request.setAttribute("fundList", fundList);

					} else if (form.getAction().equals("SearchFundName")) {
						// check if any validation error
						errors.addAll(form.getValidationErrors());
						if (errors.size() > 0) {
							System.out.println("err");
							return "ResearchFund.jsp";
						}
						fundList = fundDAO.getFundListBySearch(form.getFundname());
						request.setAttribute("fundList", fundList);

						// check if any user exists after search, add error if
						// none
						if (fundList.length == 0) {
							errors.add("No fund with name " + form.getFundname() + " exists");
						}
					}
				} else {
					fundList = fundDAO.match();
					request.setAttribute("fundList", fundList);
					// return "ResearchFund.jsp";
				}

				return "ResearchFund.jsp";
			}

			int fundid = Integer.parseInt(fundId);

			FundPriceHistoryBean[] fundHistory = fundPriceHistoryDAO.match(MatchArg.equals("fundid", fundid));
			if (fundHistory.length == 0) {
				errors.add("Fund doesn't have a history");
				return "ResearchFund.jsp";
			}
			List<ResearchBean> fundBeans = new ArrayList<ResearchBean>();
			for (FundPriceHistoryBean fund : fundHistory) {
				//fund.setPrice(fund.getPrice() / 100);
				//fundBeans.add(new ResearchBean(fund.getPricedate(), ((double) (fund.getPrice()) / 100)));
				ResearchBean fundBean = new ResearchBean();
				fundBean.setDate(fund.getPricedate());
				fundBean.setPrice(((double) (fund.getPrice()) / 100));
				fundBeans.add(fundBean);
			}
			FundBean fund = fundDAO.read(fundid);
			request.setAttribute("fundName", fund.getFundName());
			request.setAttribute("fundBeans", fundBeans);
			request.setAttribute("fundHistory", fundHistory);
			request.setAttribute("tickerName", fund.getTicker());
			return "ResearchFund.jsp";
		} catch (RollbackException e) {
			errors.add("Exception");
			return "ResearchFund.jsp";
		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			errors.add(e.getMessage());
			return "ResearchFund.jsp";
		}
	}

}
