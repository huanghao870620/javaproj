package com.ld.action;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.dto.CourseWareDto;
import com.ld.model.CourseWare;

import com.ld.model.MessageBO;
import com.ld.service.CourseWareService;


/**
 * createTime:20160-07-08
 * @author zeng.nian
 *
 */
@Namespace(value="/back/courseware")
@ParentPackage(value="struts-login")
@InterceptorRefs(value={
		@InterceptorRef(value="loginStack")
})
public class CourseWareAction extends BackBaseAction{

	private static final long serialVersionUID = 7083811319602216903L;
	
	@Autowired
	private CourseWareService<CourseWare> courseWareService;
	
	//�μ����� 1������ 2���߼�
	private String coursewareType;
	
	//�μ����� 1������ 2���߼�
	private String coursewareTypeValue;
	
	

	public String getCoursewareTypeValue() {
		return coursewareTypeValue;
	}

	public void setCoursewareTypeValue(String coursewareTypeValue) {
		this.coursewareTypeValue = coursewareTypeValue;
	}

	public String getCoursewareType() {
		return coursewareType;
	}

	public void setCoursewareType(String coursewareType) {
		this.coursewareType = coursewareType;
	}

	
	//ʵ�����
	private CourseWareDto courseWareDto;
	


	public CourseWareDto getCourseWareDto() {
		return courseWareDto;
	}

	public void setCourseWareDto(CourseWareDto courseWareDto) {
		this.courseWareDto = courseWareDto;
	}


	//��ӿμ�����ӡ��༭��ʶ 0������ 1���߼�
	private int flag = 0;
	
	//��ӡ��༭
	private String flagStr;
	
	public String getFlagStr() {
		return flagStr;
	}

	public void setFlagStr(String flagStr) {
		this.flagStr = flagStr;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * ��ת���׿μ�����ҳ��
	 * @return
	 */
	@Action(value="toCoursewareTypePage",results={@Result(name=SUCCESS,location="/WEB-INF/back/lecturer/courseware/courseware_type.jsp")})
	public String toCourseWareTypePage(){
		return SUCCESS;
	}
	
	/**
	 * ��ת���׿μ��б�ҳ��
	 * @return
	 */
	@Action(value="toCoursewareListPage",results={@Result(name=SUCCESS,location="/WEB-INF/back/lecturer/courseware/courseware_list.jsp")})
	public String toCourseWareListPage(){
		this.setParams(coursewareType);
		return SUCCESS;
	}
	
	/**
	 * ���׿μ���Ϣ��ѯ
	 * @return
	 */
	@Action(value="queryCoursewareList")
	public void queryCourseWareList(){
		this.courseWareService.queryCourseWareByAjax(coursewareType);
	}
	
	/**
	 * ��ת���μ����ҳ��
	 * @return
	 */
	@Action(value="toAddCourseWarePage",results={@Result(name=SUCCESS,location="/WEB-INF/back/lecturer/courseware/courseware_add.jsp")})
	public String toAddCourseWarePage(){
		this.setParams(coursewareType);
		this.setFlag(0);
		this.setFlagStr("添加");
		return SUCCESS;
	}
	
	/**
	 * ����ID��ѯ�μ���Ϣ
	 * @return
	 */
	@Action(value="toEditCourseWare",results={@Result(name=SUCCESS,location="/WEB-INF/back/lecturer/courseware/courseware_add.jsp")})
	public String queryCourseWareById(){
		MessageBO messageBO = this.courseWareService.findById();
		courseWareDto = (CourseWareDto) messageBO.getObj();
		this.setParams(coursewareType);
		this.setFlag(1);
		this.setFlagStr("编辑");
		return SUCCESS;
	}
	
	/**
	 * ��ӿμ���Ϣ
	 * @return
	 */
	@Action(value="addCourseWare")
	public void addCourseWare(){
		MessageBO messageBO = this.courseWareService.addCourseWare(courseWareDto);
		writeJson(messageBO);
	}
	
	/**
	 * ���¿μ���Ϣ
	 * @return
	 */
	@Action(value="updateCourseWare")
	public void updateCourseWare(){
		MessageBO messageBO = this.courseWareService.updateCourseWare(courseWareDto);
		writeJson(messageBO);
	}
	
	/**
	 * ɾ���μ���Ϣ
	 */
	@Action(value="deleteCourseware")
	public void deleteCourseWare(){
		MessageBO messageBO = this.courseWareService.deleteCourseWare();
		writeJson(messageBO);
	}
	
	/**
	 * ���ý��׿μ�����
	 * ����-ԭ��
	 * @param internalType
	 */
	private void setParams(String courseWareType){
		if(StringUtils.equals("1", courseWareType)){
			this.setCoursewareTypeValue("基础");
		}else{
			this.setCoursewareTypeValue("高级");
		}
	}
}
