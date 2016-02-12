package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EmployeeLogoutAction extends Action{

	@Override
	public String getName() {
		return "EmployeeLogout.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		//invalidate the session and go to login page
    	HttpSession session = request.getSession(false);
        session.setAttribute("employee",null);
        session.invalidate();
		return "Index.jsp";
	}

}
