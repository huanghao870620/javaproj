package com.ld.action;

import org.apache.commons.lang.StringUtils;
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

/**
 * createTime:20160-07-08
 * @author zeng.nian
 *
 */
@Namespace(value="/back/internal")
@ParentPackage(value="struts-login")
@InterceptorRefs(value={
		@InterceptorRef(value="loginStack")
})
public class InternalAction extends BackBaseAction{

	private static final long serialVersionUID = 7083811319602216903L;
	
	@Autowired
	private InternalService<Internal> internalService;
	
	//�ڲ����� 1������ 2��ԭ��
	private String internalType;
	
	//�����ڲ���������
	private String internalTypeStr;
	
	//ʵ�����
	private InternalDto internalDto;
	
	//�����ڲΣ���ӡ��༭��ʶ 0����� 1���༭
	private int flag = 0;
	
	//��ӡ��༭
	private String flagStr;
	
	public String getFlagStr() {
		return flagStr;
	}

	public void setFlagStr(String flagStr) {
		this.flagStr = flagStr;
	}

	public String getInternalType() {
		return internalType;
	}

	public void setInternalType(String internalType) {
		this.internalType = internalType;
	}

	public String getInternalTypeStr() {
		return internalTypeStr;
	}

	public void setInternalTypeStr(String internalTypeStr) {
		this.internalTypeStr = internalTypeStr;
	}

	public InternalDto getInternalDto() {
		return internalDto;
	}

	public void setInternalDto(InternalDto internalDto) {
		this.internalDto = internalDto;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * ��ת�����ڲ�����ҳ��
	 * @return
	 */
	@Action(value="toInternalTypePage",results={@Result(name=SUCCESS,location="/WEB-INF/back/lecturer/internal/internal_type.jsp")})
	public String toInternalTypePage(){
		return SUCCESS;
	}
	
	/**
	 * ��ת�����ڲ��б�ҳ��
	 * @return
	 */
	@Action(value="toInternalListPage",results={@Result(name=SUCCESS,location="/WEB-INF/back/lecturer/internal/internal_list.jsp")})
	public String toInternalListPage(){
		this.setParams(internalType);
		return SUCCESS;
	}
	
	/**
	 * �����ڲ���Ϣ��ѯ
	 * @return
	 */
	@Action(value="queryInternalList")
	public void queryInternalList(){
		this.internalService.queryInternalByAjax(internalType);
	}
	
	/**
	 * ��ת���ڲ����ҳ��
	 * @return
	 */
	@Action(value="toAddInternalPage",results={@Result(name=SUCCESS,location="/WEB-INF/back/lecturer/internal/internal_add.jsp")})
	public String toAddInternalPage(){
		this.setParams(internalType);
		this.setFlag(0);
		this.setFlagStr("添加");
		return SUCCESS;
	}
	
	/**
	 * ����ID��ѯ�ڲ���Ϣ
	 * @return
	 */
	@Action(value="toEditInternal",results={@Result(name=SUCCESS,location="/WEB-INF/back/lecturer/internal/internal_add.jsp")})
	public String queryInternalById(){
		MessageBO messageBO = this.internalService.findById();
		internalDto = (InternalDto) messageBO.getObj();
		this.setParams(internalType);
		this.setFlag(1);
		this.setFlagStr("编辑");
		return SUCCESS;
	}
	
	/**
	 * ����ڲ���Ϣ
	 * @return
	 */
	@Action(value="addInternal")
	public void addInternal(){
		MessageBO messageBO = this.internalService.addInternal(internalDto);
		writeJson(messageBO);
	}
	
	/**
	 * �����ڲ���Ϣ
	 * @return
	 */
	@Action(value="updateInternal")
	public void updateInternal(){
		MessageBO messageBO = this.internalService.updateInternal(internalDto);
		writeJson(messageBO);
	}
	
	/**
	 * ɾ���ڲ���Ϣ
	 */
	@Action(value="deleteInternal")
	public void deleteInternal(){
		MessageBO messageBO = this.internalService.deleteInternal();
		writeJson(messageBO);
	}
	
	/**
	 * ���ý����ڲ�����
	 * ����-ԭ��
	 * @param internalType
	 */
	private void setParams(String internalType){
		if(StringUtils.equals("1", internalType)){
			this.setInternalTypeStr("金属");
		}else{
			this.setInternalTypeStr("原油");
		}
	}
}
