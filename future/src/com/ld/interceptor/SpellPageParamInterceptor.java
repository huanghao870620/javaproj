package com.ld.interceptor;

import java.util.Map;

import com.ld.page.GeneratePage;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * @author huang.hao
 *
 */
public class SpellPageParamInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -371988461141838993L;

	/**
	 * 
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		Map<String, Object> ctxParam = ctx.getParameters();
		GeneratePage gp = new GeneratePage(ctxParam);
		gp.generate();
		return invocation.invoke();
	}

}
