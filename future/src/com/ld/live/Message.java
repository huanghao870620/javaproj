package com.ld.live;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;

import com.ld.freemarker.ProcessFtl;
import com.ld.mapper.UserMapper;
import com.ld.model.AbstractUser;
import com.ld.model.User;

/**
 * ��Ϣ
 * 
 * @author huang.hao
 * @version 1.0
 * @updated 11-����-2016 13:58:43
 */
public class Message {

	public Message() {
	}
	
	Map<String, Object> root = new HashMap<String,Object>();

	private String msg; // ��Ϣ

	private Date inputTime; // ����ʱ��

	private BigDecimal id; // ��Ϣid

	private boolean pass = false; // ����Ƿ�ͨ��

	private AbstractUser author; // ����

	private BigDecimal userId;

	private String customerName;

	private String customerAccount;

	private BigDecimal roomId;

	private String roomName;
	
	private String basePath; // ��Ŀ��·��
	
	private String sessionid; // websocket session id


	private BigDecimal levelId;


	private Boolean isDelete;// �Ƿ�ɾ��ù۵�

	private BigDecimal cfileId;// ͼ��ID
	
	
	private BigDecimal customerId;
	
	
	private BigDecimal roleid;
	
	
	
	

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public BigDecimal getRoleid() {
		return roleid;
	}

	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setAuthor(AbstractUser author) {
		this.author = author;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public Message(String msg, AbstractUser author) {
		this.msg = msg;
		this.author = author;
		this.setInputTime(new Date());
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public String getMsg() {
		return msg;
	}

	public AbstractUser getAuthor() {
		return author;
	}

	public BigDecimal getCfileId() {
		return cfileId;
	}

	public void setCfileId(BigDecimal cfileId) {
		this.cfileId = cfileId;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}


	/**
	 * ��ɫ���Ͳ��ǿ�
	 * 
	 * @return
	 */
//	public boolean roleIdIsNotNull() {
//		return this.roleid != null;
//	}


	public BigDecimal getLevelId() {
		return levelId;
	}

	public void setLevelId(BigDecimal levelId) {
		this.levelId = levelId;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	public BigDecimal getRoomId() {
		return roomId;
	}

	public void setRoomId(BigDecimal roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	

	/**
	 * ��Ϣ�ǲ��ǽ�ʦ����
	 * 
	 * @return
	 */
//	public boolean isLectureSendMsg() {
//		return this.roleid.toString().equals(Dictionary.TEACHER_TYPE_ROLE);
//	}

	
	
	/**
	 * ת��Ϊ�Ѿ���˵���Ϣ�ַ�
	 * @return
	 */
	public String intoAlreadyReviewMessageStr(WebApplicationContext ctx){
		if(null != ctx){
			UserMapper userMapper = (UserMapper)ctx.getBean("userMapper");
			BigDecimal userid = this.getCustomerId();
			User user = userMapper.findById(userid);
			this.setAuthor(user);
		}
		 root.put("message", this);
		 ProcessFtl pf = new ProcessFtl(root, "msg/commonMsg.ftl", "");
		 return pf.process(); 
	}
	
	/**
	 * ת��Ϊ��Ҫ��˵���Ϣ�ַ�
	 * @return
	 */
	public String intoNeedReviewMessageStr(){
		root.put("message", this);
		ProcessFtl pf = new ProcessFtl(root, "msg/auditMsg.ftl", this.getBasePath());
		return pf.process();
	}


}
