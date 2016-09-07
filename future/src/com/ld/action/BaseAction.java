package com.ld.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

public class BaseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8599000384323843186L;
	
	
	public static final String DISPATCHER = "dispatcher";
	public static final String REDIRECT="redirect";
	
	
	private HttpServletResponse response;
	
	/**
	 * ����ajax��Ϣ
	 * @throws  
	 */
	public void sendAjaxMsg(String msg) {
		this.response = ServletActionContext.getResponse();
		this.response.setCharacterEncoding("UTF-8");
//		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html");
		try {
			this.response.getWriter().print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * תJSON����
	 * @param obj
	 */
	public void writeJson(Object obj){
		PrintWriter writer = null;
		try {
			JSONObject json = JSONObject.fromObject(obj);
			this.response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/json");
			writer = this.response.getWriter();
			writer.print(json);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			writer.close();
		}
	}
}
