package com.ld.action;

import java.io.File;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.model.MessageBO;
import com.ld.model.Viewpoint;
import com.ld.service.ViewpointService;
import com.ld.util.LoginUtil;
import com.opensymphony.xwork2.ActionContext;

@Namespace(value="/index")
@ParentPackage(value="struts-front-login")
@InterceptorRefs(value={
		@InterceptorRef(value="frontLoginStack")
})
public class FrontIndexAction extends FrontBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2187617231707596097L;
	
	@Autowired
	private ViewpointService<Viewpoint> viewpointService;
	
	private File file; //�ϴ��ļ�����
	
	private String fileFileName;//�ϴ��ļ���
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	@Action(value = "frontIndex", results = {
			@Result(name = "success", location = "/WEB-INF/front/index.jsp", type = "dispatcher") })
	public String index(){
		String ip = ServletActionContext.getRequest().getRemoteAddr();
		ActionContext ctx = ActionContext.getContext();
		if(!LoginUtil.COURSEWARELIST.equals( ctx.getApplication().get("courseWareList")) ){//课件信息
			ctx.getApplication().put("courseWareList", LoginUtil.COURSEWARELIST);
		}
		ctx.put("ip", ip);
		return SUCCESS;
	}
	
	@Action(value="uploadPic")
	public void uploadPic(){
		if(file != null){
			if(file.isFile()){
				MessageBO result = this.viewpointService.uploadViewpointPic(file,fileFileName);
				writeJson(result);
			}			
		}
	}
	
	@Action(value="toTestChat",results={@Result(name="success",location="/WEB-INF/front/testChat.html")})
	public String toTestChat(){
		return SUCCESS;
	}

}
