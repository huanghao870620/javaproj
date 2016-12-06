package com.ld.dto;

import java.math.BigDecimal;

import com.ld.model.PrivateMessage;

/**
 * ����ͻ��Խ�ʦ�۵� ���ʵ�չʾ����
 * 
 * @author gao.ran 2016 07 08
 */

public class TeacherViewMessageDto extends PrivateMessage {

	private static final long serialVersionUID = -7923998578474646945L;
	
	private String replyName;// �ظ�������
	private String replyTime;// �ظ�ʱ��
	private BigDecimal replyHeadId;// �ظ���ͷ��ID
	private String replyContent;// �ظ�����
	private BigDecimal replyViewId;// �ظ������۵�ID
	private BigDecimal replierId;// �ظ���ID
	private BigDecimal teacherId;//��ʦID
	/* �İ潲ʦ�ظ����� */

	private BigDecimal teacherIconId;// ��ʦͷ��ID
	private String teacherName;// ��ʦ����
	private String viewContent;// �۵�����
	private String viewCreateTime;// �۵㴴��ʱ��
	private BigDecimal viewCfile;// �۵������ͼƬID
	private BigDecimal adviceId;// ��������ID 1������ 2����������
	private BigDecimal mineralId;// �ֻ�ID 1��ԭ�� 2������
	private String quesContent;// ��������
	private String quesCreateTime;// ���ʴ���ʱ��
	private String quesCreatorName;// ����������
	private BigDecimal quesCreatorLevelId;// �����˵ȼ����ͻ��ȼ�
	private BigDecimal quesCreatorHeadId;// ������ͷ��ID���ǿͻ���
	private BigDecimal questionType;// �������ͣ�1���״����� 2���ظ���ʦ
	private BigDecimal viewId;//�۵�ID
	private boolean isCustomer;//�������Ƿ��ǿͻ�

	public TeacherViewMessageDto() {
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public BigDecimal getReplyHeadId() {
		return replyHeadId;
	}

	public void setReplyHeadId(BigDecimal replyHeadId) {
		this.replyHeadId = replyHeadId;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public BigDecimal getReplyViewId() {
		return replyViewId;
	}

	public void setReplyViewId(BigDecimal replyViewId) {
		this.replyViewId = replyViewId;
	}

	public BigDecimal getReplierId() {
		return replierId;
	}

	public void setReplierId(BigDecimal replierId) {
		this.replierId = replierId;
	}

	public BigDecimal getTeacherIconId() {
		return teacherIconId;
	}

	public void setTeacherIconId(BigDecimal teacherIconId) {
		this.teacherIconId = teacherIconId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getViewContent() {
		return viewContent;
	}

	public void setViewContent(String viewContent) {
		this.viewContent = viewContent;
	}

	public String getViewCreateTime() {
		return viewCreateTime;
	}

	public void setViewCreateTime(String viewCreateTime) {
		this.viewCreateTime = viewCreateTime;
	}

	public BigDecimal getViewCfile() {
		return viewCfile;
	}

	public void setViewCfile(BigDecimal viewCfile) {
		this.viewCfile = viewCfile;
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

	public String getQuesContent() {
		return quesContent;
	}

	public void setQuesContent(String quesContent) {
		this.quesContent = quesContent;
	}

	public String getQuesCreateTime() {
		return quesCreateTime;
	}

	public void setQuesCreateTime(String quesCreateTime) {
		this.quesCreateTime = quesCreateTime;
	}

	public String getQuesCreatorName() {
		return quesCreatorName;
	}

	public void setQuesCreatorName(String quesCreatorName) {
		this.quesCreatorName = quesCreatorName;
	}

	public BigDecimal getQuesCreatorLevelId() {
		return quesCreatorLevelId;
	}

	public void setQuesCreatorLevelId(BigDecimal quesCreatorLevelId) {
		this.quesCreatorLevelId = quesCreatorLevelId;
	}

	public BigDecimal getQuesCreatorHeadId() {
		return quesCreatorHeadId;
	}

	public void setQuesCreatorHeadId(BigDecimal quesCreatorHeadId) {
		this.quesCreatorHeadId = quesCreatorHeadId;
	}

	public BigDecimal getQuestionType() {
		return questionType;
	}

	public void setQuestionType(BigDecimal questionType) {
		this.questionType = questionType;
	}

	public BigDecimal getViewId() {
		return viewId;
	}

	public void setViewId(BigDecimal viewId) {
		this.viewId = viewId;
	}

	public BigDecimal getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(BigDecimal teacherId) {
		this.teacherId = teacherId;
	}

	public boolean getIsCustomer() {
		return isCustomer;
	}

	public void setCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}
	
}
