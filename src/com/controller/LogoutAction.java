package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.view.Message;

/**
 * 
 * @author tpahare
 *
 */

public class LogoutAction extends Action{

	@Override
	public String getName() {
		return "logout";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		//invalidate the session and go to login page
    	Message message = new Message();
		Gson gson = new Gson();
    	HttpSession session = request.getSession(false);
        session.setAttribute("customer",null);
        session.setAttribute("employee", null);
        session.invalidate();
        message.setMessage("You've been logged out");
		return gson.toJson(message);
	}

}
