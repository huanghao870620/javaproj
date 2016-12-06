package com.ld.action;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ld.common.Dictionary;
import com.ld.model.User;
import com.ld.util.LoginUtil;
import com.opensymphony.xwork2.ActionContext;

@Namespace(value="/front")
@ParentPackage(value="struts-front-login")
@InterceptorRefs(value={
		@InterceptorRef(value="frontLoginStack")
})
public class WeiboChatAction extends FrontBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2187617231707596097L;
	
	@Action(value = "weiboChat", results = {
			@Result(name = "success", location = "/WEB-INF/front/weibo_chat.jsp", type = "dispatcher") })
	public String index(){
		String ip = ServletActionContext.getRequest().getRemoteAddr();
		ActionContext ctx = ActionContext.getContext();
		ctx.put("ip", ip);
		User customer = (User)ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER);
		ctx.put("customer", customer);
		// ��ȡ��ǰ��¼�û��Ƿ� ��������̨�û��������ȡ��Ӧ�Ĺ����û�������ɫ, �����ɫ����Ϊ��ͨ
		if(ctx.getSession().get(LoginUtil.FRONT_CUSTOMER_ROLEID)!=null)
		{
			BigDecimal roleId = (BigDecimal) ctx.getSession().get(LoginUtil.FRONT_CUSTOMER_ROLEID);
			if(roleId.toString().equals(Dictionary.TEACHER_TYPE_ROLE))
			{
			ctx.put("roleId", ctx.getSession().get(LoginUtil.FRONT_CUSTOMER_ROLEID));
			}
			else{
				ctx.put("roleId", BigDecimal.ZERO);
			}
		}
		else{
			ctx.put("roleId", BigDecimal.ZERO);
		}
		return SUCCESS;
	}
	
	@ResponseBody
	@RequestMapping("privateChat")
	public String privateChat(@RequestParam("privateChat") String teacherUserId,
			HttpServletRequest request){
		//String res= "0";
		// ��ȡ��ǰ��¼�û��Ƿ� ��������̨�û��������ȡ��Ӧ�Ĺ����û�������ɫ, �����ɫ����Ϊ��ͨ
//		if(!StringUtils.isEmpty(teacherUserId))
//		{
//			// ��ȡ������������д���� teacherUserId
//			ctx.put("teacherUserId", teacherUserId);
//			res = "1";
//		}
//		return res;
		return  null;
	}
	
	/*@Action(value = "privateChat", results = {
			@Result(name = "success", location = "/WEB-INF/front/private_chat.jsp", type = "dispatcher") })
	public String  privateChat(){
		String ip = ServletActionContext.getRequest().getRemoteAddr();
		ActionContext ctx = ActionContext.getContext();
		ctx.put("ip", ip);
		// ��ȡ��ǰ��¼�û��Ƿ� ��������̨�û��������ȡ��Ӧ�Ĺ����û�������ɫ, �����ɫ����Ϊ��ͨ
		if(!StringUtils.isEmpty(ServletActionContext.getRequest().getParameter("teacherUserId")))
		{
			// ��ȡ������������д���� teacherUserId
			String  teacherUserId = ServletActionContext.getRequest().getParameter("teacherUserId");
			ctx.put("teacherUserId", teacherUserId);
		}
		
		return SUCCESS;
	}
	*/
	
	
	
	
	
	
	

}
