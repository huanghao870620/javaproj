package com.ld.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ld.menu.AppendBread;
import com.ld.menu.RecursiveMenu;
import com.ld.service.MenuService;
import com.ld.util.StringUtil;

/**
 * 面包屑 自定义标签
 * @author hao.huang
 *
 */
public class BreadCrumbTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8716925167290024106L;

	private WebApplicationContext wac = null;

	private MenuService<?> menuService;
	private HttpServletRequest request;
	private String menuName;
	
	

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public BreadCrumbTag() {
	}

	@Override
	public int doStartTag() throws JspException {
		this.wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.pageContext.getServletContext());
		this.menuService = (MenuService<?>) this.wac.getBean("menuServiceImpl");
		try {
			JspWriter out = this.pageContext.getOut();
			if (StringUtil.isBlank(this.menuName)) {
				out.println("No UserInfo Found...");
				return SKIP_BODY;
			}
			
			RecursiveMenu rm = new RecursiveMenu(this.menuService, this.menuName);
			
			if(this.pageContext.getRequest() instanceof HttpServletRequest){
				this.request = (HttpServletRequest)this.pageContext.getRequest();
			}
			AppendBread ab = new AppendBread(rm.getBreads(), this.request.getContextPath());
			out.println(ab.getBufStr().toString());			
		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

}
