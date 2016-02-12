package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.view.Message;

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
    	Message message = new Message();
		Gson gson = new Gson();
    	HttpSession session = request.getSession(false);
        session.setAttribute("customer",null);
        session.invalidate();
        message.setMessage("Logged Out Successfully");
		return gson.toJson(message);
	}

}
