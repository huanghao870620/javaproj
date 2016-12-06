package com.ld.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PrivateMessageReply implements Serializable {
	
	private static final long serialVersionUID = 3553541737609569973L;

	private BigDecimal id;// 回复消息ID

	private String content;// 回复内容

	private BigDecimal creatorId;// 回复人ID

	private Date createTime;// 回复时间

	private BigDecimal privateMessageId;// 关联私信ID

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

	public BigDecimal getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(BigDecimal creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getPrivateMessageId() {
		return privateMessageId;
	}

	public void setPrivateMessageId(BigDecimal privateMessageId) {
		this.privateMessageId = privateMessageId;
	}
}