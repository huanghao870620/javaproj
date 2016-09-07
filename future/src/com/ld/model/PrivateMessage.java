package com.ld.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ld.live.Message;

public class PrivateMessage extends Message implements Serializable {

	private static final long serialVersionUID = -5400658459096308960L;

	private BigDecimal id;// 私信ID

	private String content;// 私信内容

	private Date createTime;// 私信创建时间

	private BigDecimal creatorId;// 私信创建人ID

	private BigDecimal viewId;// 私信关联观点ID
	
	private List<PrivateMessageReply> replyList;//私信回复集合

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