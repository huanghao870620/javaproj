package com.ld.action;

import java.io.File;

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

@Namespace(value="/back/viewpoint")
@ParentPackage(value="struts-login")
@InterceptorRefs(value={
		@InterceptorRef(value="loginStack")
})
public class ViewpointAction extends BackBaseAction {
	/**
	 * Ψһ��ʶ
	 */
	private static final long serialVersionUID = 8237447996402550565L;
	
	@Autowired
	private ViewpointService<Viewpoint> viewpointService;
	
	//�ڵ��۵�ʵ�����Dto
	private ViewpointDto viewpointDto;
	
	//�ڵ��۵㣬��ӡ��༭��ʶ 0����� 1���༭
	private int flag = 0;
	
	//��ӡ��༭
	private String flagStr;
	
	private File file; //�ϴ��ļ�����
	
	private String fileFileName;//�ϴ��ļ���
	

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public ViewpointDto getViewpointDto() {
		return viewpointDto;
	}

	public void setViewpointDto(ViewpointDto viewpointDto) {
		this.viewpointDto = viewpointDto;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public String getFlagStr() {
		return flagStr;
	}

	public void setFlagStr(String flagStr) {
		this.flagStr = flagStr;
	}

	/**
	 * ��ת����ʦ����ҳ��
	 * @return
	 */
	@Action(value="toLecturer",results={@Result(name=SUCCESS,location="/WEB-INF/back/lecturer/lecturer.jsp")})
	public String toLecturer(){
		return SUCCESS;
	}

	/**
	 * ��ת���ڵ��۵��б�ҳ��
	 * @return
	 */
	@Action(value="toViewpointList",results={@Result(name=SUCCESS,location="/WEB-INF/back/lecturer/viewpoint/viewpoint.jsp")})
	public String toViewpointList(){
		return SUCCESS;
	}
	
	/**
	 * ��ת������ڵ��۵�ҳ��
	 * @return
	 */
	@Action(value="toAddViewpoint",results={@Result(name=SUCCESS,location="/WEB-INF/back/lecturer/viewpoint/viewpoint_add.jsp")})
	public String toAddViewpoint(){
		this.setFlagStr("添加");
		return SUCCESS;
	}
	
	/**
	 * ����ڵ��۵�
	 * @return
	 * @throws Exception 
	 */
	@Action(value="addViewpoint")
	public void addViewpoint(){
		MessageBO messageBO = this.viewpointService.addViewpoint(viewpointDto);
		writeJson(messageBO);
	}
	
	/**
	 * ɾ���ڵ��۵�
	 * @return
	 */
	@Action(value="delViewpoint")
	public void delViewpoint(){
		MessageBO messageBO = this.viewpointService.delViewpoint();
		writeJson(messageBO);
	}
	
	/**
	 * �༭�ڵ��۵�
	 * @return
	 */
	@Action(value="updateViewpoint")
	public void updateViewpoint(){
		MessageBO messageBO = this.viewpointService.updateViewpoint(viewpointDto);
		writeJson(messageBO);
	}
	
	/**
	 * ����Id��ѯ�ڵ��۵�
	 * @return
	 */
	@Action(value="toEditViewpoint",results={@Result(name=SUCCESS,location="/WEB-INF/back/lecturer/viewpoint/viewpoint_add.jsp")})
	public String findViewpointById(){
		MessageBO messageBO = this.viewpointService.findViewpointById();
		this.setViewpointDto((ViewpointDto)messageBO.getObj());
		this.setFlag(1);
		this.setFlagStr("编辑");
		return SUCCESS;
	}
	
	/**
	 * �۵��б��ѯ
	 */
	@Action(value="queryViewpointList")
	public void queryViewpointList(){
		this.viewpointService.queryViewpointByAjax();
	}
	
	/**
	 * �ϴ�ר��ͼƬ
	 */
	@Action(value="uploadViewpointPic")
	public void uploadViewpointPic(){
		if(file != null){
			if(file.isFile()){
				MessageBO result = this.viewpointService.uploadViewpointPic(file,fileFileName);
				writeJson(result);
			}			
		}
	}
}
