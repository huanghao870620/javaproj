package com.ld.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.dto.ViewpointDto;
import com.ld.model.MessageBO;
import com.ld.model.Viewpoint;
import com.ld.service.ViewpointService;

@Namespace(value="/front/viewpoint")
@ParentPackage(value="struts-front-login")
@InterceptorRefs(value={
		@InterceptorRef(value="frontLoginStack")
})
public class FrontViewpointAction extends FrontBaseAction {

	private static final long serialVersionUID = 1249103722676378571L;
	
	@Autowired
	private ViewpointService<Viewpoint> viewpointService;
	
	private ViewpointDto viewpointDto;
	
	public ViewpointDto getViewpointDto() {
		return viewpointDto;
	}

	public void setViewpointDto(ViewpointDto viewpointDto) {
		this.viewpointDto = viewpointDto;
	}

	/**
	 * 观点列表查询
	 */
	@Action(value="queryViewpointList")
	public void queryViewpointList(){
		this.viewpointService.queryViewpointByAjax();
	}

	@Action(value="toJSGD",results={@Result(name=SUCCESS,location="/WEB-INF/front/viewpoint/jsgd_viewpoint.jsp")})
	public String toJSGD(){
		MessageBO messageBO = this.viewpointService.findViewpointById();
		this.setViewpointDto((ViewpointDto) messageBO.getObj());
		return SUCCESS;
	}
}
