package com.ld.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ld.model.PrivateMessage;
import com.ld.model.User;

public class QuestionAndReplyDto implements Serializable {

	private static final long serialVersionUID = 4480784922475315880L;

	private BigDecimal viewId;// 观点ID
	private String viewContent;// 观点内容
	private BigDecimal viewPicId;// 观点内容图片ID
	private BigDecimal adviceId;// 观点建议ID 1：策略 2：操作建议
	private BigDecimal mineralId;// 矿产分类 1：金属 2：原油
	private Date viewCreateTime;// 观点创建时间
	private BigDecimal viewCreatorId;// 观点创建人ID
	private String viewCreatorName;// 观点创建人姓名
	private BigDecimal viewCreatorHeadId;// 观点创建人头像
	private List<PrivateMessage> questionList;// 观点提问集合
	private boolean isCustomer;//当前私信用户是否为客户
	private User user;
	private int replyCount;//讲师回复数量
	
	public BigDecimal getViewId() {
		return viewId;
	}
	public void setViewId(BigDecimal viewId) {
		this.viewId = viewId;
	}
	public String getViewContent() {
		return viewContent;
	}
	public void setViewContent(String viewContent) {
		this.viewContent = viewContent;
	}
	public BigDecimal getViewPicId() {
		return viewPicId;
	}
	public void setViewPicId(BigDecimal viewPicId) {
		this.viewPicId = viewPicId;
	}
	public BigDecimal getAdviceId() {
		return adviceId;
	}
	public void setAdviceId(BigDecimal adviceId) {
		this.adviceId = adviceId;
	}
	public BigDecimal getMineralId() {
		return mineralId;
	}
	public void setMineralId(BigDecimal mineralId) {
		this.mineralId = mineralId;
	}
	public Date getViewCreateTime() {
		return viewCreateTime;
	}
	public void setViewCreateTime(Date viewCreateTime) {
		this.viewCreateTime = viewCreateTime;
	}
	public BigDecimal getViewCreatorId() {
		return viewCreatorId;
	}
	public void setViewCreatorId(BigDecimal viewCreatorId) {
		this.viewCreatorId = viewCreatorId;
	}
	public String getViewCreatorName() {
		return viewCreatorName;
	}
	public void setViewCreatorName(String viewCreatorName) {
		this.viewCreatorName = viewCreatorName;
	}
	public BigDecimal getViewCreatorHeadId() {
		return viewCreatorHeadId;
	}
	public void setViewCreatorHeadId(BigDecimal viewCreatorHeadId) {
		this.viewCreatorHeadId = viewCreatorHeadId;
	}
	public List<PrivateMessage> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<PrivateMessage> questionList) {
		this.questionList = questionList;
	}
	public boolean getIsCustomer() {
		return isCustomer;
	}
	public void setCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	
}
