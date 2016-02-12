package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author faisalshahnewaz
 *
 */

public class CustomerLogoutAction extends Action{

	@Override
	public String getName() {
		return "CustomerLogout.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		//invalidate the session and go to login page
    	HttpSession session = request.getSession(false);
        session.setAttribute("customer",null);
        session.invalidate();
		return "Index.jsp";
	}

}
