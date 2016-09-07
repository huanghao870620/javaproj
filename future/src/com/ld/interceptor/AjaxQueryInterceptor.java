package com.ld.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.github.pagehelper.Page;
import com.ld.page.GeneratePage;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;

/**
 * 
 * @author huang.hao
 *
 */
@Aspect
@Component
public class AjaxQueryInterceptor {

	@Pointcut("execution(* com.ld.service.impl.*ServiceImpl.*ByAjax(..))")
	private void anyMethod(){}
	
	
	@Before("anyMethod()")
	public void doBefore(){
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> params = ctx.getParameters();
		GeneratePage gp = new GeneratePage(params);
		gp.generate();
	}
	
	
	/**
	 * 分页查询后执行
	 * @param rvt
	 */
	@AfterReturning(returning="rvt",value="anyMethod()")
	public void doAfter(Object rvt){
		Page<?> page = (Page<?>) rvt;
		ActionContext ctx = ActionContext.getContext();
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		Page<?> page = (Page<?>) ctx.get("resultPage");
		long total = page.getTotal();
		jsonMap.put("total", total);
		jsonMap.put("rows", page.getResult());
		JSONObject obj = JSONObject.fromObject(jsonMap);
//		json.accumulate("total", total);
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/json");
		PrintWriter writer = null;
		try {
			writer = resp.getWriter();
			writer.print(obj.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			writer.close();
		}
		
	}
	
	@After("anyMethod()")
	public void after(){
		System.out.println(11);
	}
}
