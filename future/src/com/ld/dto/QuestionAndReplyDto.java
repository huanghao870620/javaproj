package com.ld.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ld.model.PrivateMessage;
import com.ld.model.User;

public class QuestionAndReplyDto implements Serializable {

	private static final long serialVersionUID = 4480784922475315880L;

	private BigDecimal viewId;// �۵�ID
	private String viewContent;// �۵�����
	private BigDecimal viewPicId;// �۵�����ͼƬID
	private BigDecimal adviceId;// �۵㽨��ID 1������ 2����������
	private BigDecimal mineralId;// ������� 1������ 2��ԭ��
	private Date viewCreateTime;// �۵㴴��ʱ��
	private BigDecimal viewCreatorId;// �۵㴴����ID
	private String viewCreatorName;// �۵㴴��������
	private BigDecimal viewCreatorHeadId;// �۵㴴����ͷ��
	private List<PrivateMessage> questionList;// �۵����ʼ���
	private boolean isCustomer;//��ǰ˽���û��Ƿ�Ϊ�ͻ�
	private User user;
	private int replyCount;//��ʦ�ظ�����
	
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
