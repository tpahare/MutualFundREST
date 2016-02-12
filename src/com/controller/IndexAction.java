package com.controller;

import javax.servlet.http.HttpServletRequest;

import com.model.Model;

public class IndexAction extends Action {
	public IndexAction(Model model) {
		
	}
	public String getName() {
		return "Index.do";
	}
	public String perform(HttpServletRequest request) {
		return "Index.jsp";
	}
}
