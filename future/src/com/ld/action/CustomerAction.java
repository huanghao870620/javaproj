package com.ld.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.dto.UserDto;
import com.ld.model.Level;
import com.ld.model.MessageBO;
import com.ld.model.User;
import com.ld.service.CustomerService;
import com.ld.service.LevelService;
import com.ld.service.UserService;

@Namespace(value = "/back/customer")
@ParentPackage(value="struts-login")
@InterceptorRefs(value={
		@InterceptorRef(value="loginStack")
})
public class CustomerAction extends BackBaseAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private LevelService<Level> levelService;

	@Autowired
	private CustomerService<User> customerService;
	
	@Autowired
	private UserService<User> userService;
	
	private UserDto customer;
	
	//�������༭��ʶ 0������ 1���༭
	private int flag;
	
	//�������༭����
	private String operation;

	public UserDto getCustomer() {
		return customer;
	}

	public void setCustomer(UserDto customer) {
		this.customer = customer;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * ��ת����ӿͻ�ҳ��
	 * 
	 * @return
	 */
	@Action(value = "toAddCustomer", results = {
			@Result(name = SUCCESS, location = "/WEB-INF/back/customer/customer_add.jsp")
	})
	public String toAddCustomer() {
		//��ȡ�ͻ��ȼ�
		this.levelService.getAllLevel();
		this.setFlag(0);
		this.setOperation("添加");
		return SUCCESS;
	}

	/**
	 * ��ӿͻ�
	 * @return
	 */
	@Action(value = "addCustomer")
	public void insert() {
		MessageBO mesageBO = this.customerService.addCustomer(customer);
		writeJson(mesageBO);
	}

	/**
	 * ��ת���ͻ��б�ҳ��
	 * 
	 * @return
	 */
	@Action(value = "toCustomerList", results = {
			@Result(name = SUCCESS, location = "/WEB-INF/back/customer/customer_list.jsp")
	})
	public String forwardCustomerList() {
		return SUCCESS;
	}
	
	/**
	 * ��ȡ�ͻ��б�
	 */
	@Action(value="findCustoemrList")
	public void findCustomerList(){
		this.customerService.findCustomerByAjax();
	}
	
	/**
	 * ��ת�����¿ͻ�����ҳ��
	 * @return
	 */
	@Action(value="forwardUpdateCustomerPass", results={
			@Result(name=SUCCESS, location="/WEB-INF/back/customer/update_password.jsp", type="dispatcher")
	})
	public String forwardUpdateCustomerPass(){
		MessageBO messageBO = this.userService.findUserById();
		this.setCustomer((UserDto) messageBO.getObj());
		return SUCCESS;
	}
	
	/**
	 * �޸�����
	 */
	@Action(value="updatePassword")
	public void updatePassword(){
		MessageBO messageBO = this.userService.updateUserPass(customer);
		writeJson(messageBO);
	}
	
	/**
	 * ��ת���ͻ��༭ҳ��
	 * @return
	 */
	@Action(value="toEditCustomer",results={
			@Result(name=SUCCESS,location="/WEB-INF/back/customer/customer_add.jsp")
	})
	public String toEditCustomer(){
		MessageBO messageBO = this.customerService.findCustomerById();
		this.setCustomer((UserDto) messageBO.getObj());
		//��ȡ�ͻ��ȼ�
		this.levelService.getAllLevel();
		this.setFlag(1);
		this.setOperation("编辑");
		return SUCCESS;
	}
	
	/**
	 * ���¿ͻ���Ϣ
	 * @return
	 */
	@Action(value="updateCustomer", results={
			@Result(name=SUCCESS, location="/WEB-INF/back/customer/update_password.jsp")
	})
	public void updateCustomer(){
		MessageBO messageBO = this.customerService.updateCustomer(this.customer);
		writeJson(messageBO);
	}
	
	/**
	 * ɾ���ͻ�
	 * @return
	 */
	@Action(value="deleteCustomer")
	public void delete(){
		MessageBO messageBO=this.customerService.delete();
		writeJson(messageBO);
	}
	
	/**
	 * ��ȡ����ǰ���û�(�첽��ʽ)
	 */
	@Action(value="getAllCustomer")
	public void getAllCustomer(){
		this.sendAjaxMsg(this.customerService.getAllCustomer());
	}
}
