package com.ld.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ld.live.Message;

public class PrivateMessage extends Message implements Serializable {

	private static final long serialVersionUID = -5400658459096308960L;

	private BigDecimal id;// ˽��ID

	private String content;// ˽������

	private Date createTime;// ˽�Ŵ���ʱ��

	private BigDecimal creatorId;// ˽�Ŵ�����ID

	private BigDecimal viewId;// ˽�Ź����۵�ID
	
	private List<PrivateMessageReply> replyList;//˽�Żظ�����

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(BigDecimal creatorId) {
		this.creatorId = creatorId;
	}

	public BigDecimal getViewId() {
		return viewId;
	}

	public void setViewId(BigDecimal viewId) {
		this.viewId = viewId;
	}

	public List<PrivateMessageReply> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<PrivateMessageReply> replyList) {
		this.replyList = replyList;
	}
}