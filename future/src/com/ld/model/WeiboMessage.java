package com.ld.model;

import java.math.BigDecimal;

import com.ld.live.Message;
/**
 * ��նԾ� ����΢���� ��Ϣʵ��
 * @author gao.ran
 *
 */
public class WeiboMessage extends Message{
	
	public WeiboMessage(){}
	
    public WeiboMessage(String msg, AbstractUser author) {
		super(msg, author);
	}
    
    private boolean senderIsTeacher;
    
    private BigDecimal roleId;
    

	public BigDecimal getRoleId() {
		return roleId;
	}

	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}

	public boolean isSenderIsTeacher() {
		return senderIsTeacher;
	}

	public void setSenderIsTeacher(boolean senderIsTeacher) {
		this.senderIsTeacher = senderIsTeacher;
	}
    
}