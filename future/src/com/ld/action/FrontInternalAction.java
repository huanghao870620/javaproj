package com.ld.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.dto.InternalDto;
import com.ld.model.Internal;
import com.ld.model.MessageBO;
import com.ld.service.InternalService;

@Namespace(value="/front/internal")
@ParentPackage(value="struts-front-login")
@InterceptorRefs(value={
		@InterceptorRef(value="frontLoginStack")
})
public class FrontInternalAction extends FrontBaseAction{

	private static final long serialVersionUID = -5078888785668807050L;
	
	@Autowired
	private InternalService<Internal> internalService;
	
	private InternalDto internalDto;
	
	public InternalDto getInternalDto() {
		return internalDto;
	}

	public void setInternalDto(InternalDto internalDto) {
		this.internalDto = internalDto;
	}

	private String internalType;
	
	public String getInternalType() {
		return internalType;
	}

	public void setInternalType(String internalType) {
		this.internalType = internalType;
	}


	/**
	 * 交易内参信息查询
	 * @return
	 */
	@Action(value="queryInternalList")
	public void queryInternalList(){
		System.out.println("internalType:"+internalType);
		this.internalService.queryInternalByAjax(internalType);
	}
	
	@Action(value="toJYNC",results={@Result(name=SUCCESS,location="/WEB-INF/front/internal/jync_internal.jsp")})
	public String toJSGD(){
		MessageBO messageBO = this.internalService.findById();
		internalDto = (InternalDto) messageBO.getObj();
		return SUCCESS;
	}

}
