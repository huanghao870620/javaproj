package com.xa.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xa.util.Constants;
import com.xa.util.Msg;

import net.sf.json.JSONObject;
/**
 * 
 * @author burgess
 *
 */
@Component
public class SpringMVCInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		String sign = request.getParameter("sign");
		if(StringUtils.isEmpty(sign)){
			this.sendAjaxMsg(new JSONObject().accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString(), response);
			return false;
		}
		return true;
	}
	
	
	public void sendAjaxMsg(String msg, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		PrintWriter write = response.getWriter();
		write.print(msg);
		write.flush();
	}

}
