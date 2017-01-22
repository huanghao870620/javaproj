package com.xa.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	public void sendAjaxMsg(String msg) throws IOException {
		this.response.setCharacterEncoding("UTF-8");
		this.response.setContentType("text/json");
		PrintWriter write = this.response.getWriter();
		write.print(msg);
		write.flush();
	}
	
	public void sendHtml(String msg) throws IOException {
		this.response.setCharacterEncoding("UTF-8");
		this.response.setContentType("text/html");
		PrintWriter write = this.response.getWriter();
		write.print(msg);
		write.flush();
	}
}
