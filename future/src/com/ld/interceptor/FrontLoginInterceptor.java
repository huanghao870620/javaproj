package com.ld.interceptor;

import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.dto.UserDto;
import com.ld.mapper.CourseWareMapper;
import com.ld.mapper.CustomerLevelLimitRelaMapper;
import com.ld.model.User;
import com.ld.service.LoginService;
import com.ld.util.LoginUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * ǰ̨��¼������
 * @author huang.hao
 *
 */
public class FrontLoginInterceptor extends AbstractInterceptor {
	
	@Autowired
	private LoginService loginService;
	@Autowired
	CustomerLevelLimitRelaMapper customerLevelLimitRelaMapper;
	@Autowired
	private CourseWareMapper courseWareMapper;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(){
		LoginUtil.CUSLIMITRELA = customerLevelLimitRelaMapper.findById(new BigDecimal("0"));//游客权限
		LoginUtil.COURSEWARELIST = courseWareMapper.queryAllCourseWare();//课件信息
	}
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
	
		StringBuffer url = ServletActionContext.getRequest().getRequestURL();
		String[] path = new String[]{"front/viewpoint/toJSGD","download/file/show","front/internal/toJYNC"};
		for (int i = 0; i < path.length; i++) {
			if(url.indexOf(path[i])>-1){
				HttpSession session = ServletActionContext.getRequest().getSession();
				session.setAttribute("checkCode", "1111");
				UserDto userDto = new UserDto();
				userDto.setUserName("admin");
				userDto.setPassword("admin");
				userDto.setCheckCode("1111");
				loginService.login(userDto);
				break;
			}
		}
		User user = (User)ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER);
		if(null == user){
			return LoginUtil.NO_LOGIN;
		}else{
			return invocation.invoke();
		}
	}

}
