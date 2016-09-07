package com.ld.interceptor;

import java.util.Map;

import com.ld.model.User;
import com.ld.util.LoginUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 
 * @author huang.hao
 *
 */
public class BackLoginInterceptor extends  AbstractInterceptor {
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8520956501703822932L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		Map<String, Object> session = ctx.getSession();
		User user = (User)session.get(LoginUtil.FRONT_LOGIN_USER);
		if(null == user){
			return LoginUtil.NO_LOGIN;
		}else{
			return invocation.invoke();
		}
	}

	

}
