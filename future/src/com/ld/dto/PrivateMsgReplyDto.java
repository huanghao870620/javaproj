package com.ld.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.ld.live.Message;

public class PrivateMsgReplyDto extends Message implements Serializable, Cloneable {

	private static final long serialVersionUID = 678971514567437467L;
	/* 改版讲师回复开始 */
	private String replyName;// 回复人姓名
	private String replyTime;// 回复时间
	private BigDecimal replyHeadId;// 回复人头像ID
	private String replyContent;// 回复内容
	private BigDecimal replyViewId;// 回复所属观点ID
	private BigDecimal replierId;// 回复人ID
	private BigDecimal teacherId;// 讲师ID
	/* 改版讲师回复结束 */
	/* 改版客户提问开始 */
	private BigDecimal teacherIconId;// 讲师头像ID
	private String teacherName;// 讲师姓名
	private String viewContent;// 观点内容
	private String viewCreateTime;// 观点创建时间
	private BigDecimal viewCfile;// 观点关联的图片ID
	private BigDecimal adviceId;// 操作建议ID 1：策略 2：操作建议
	private BigDecimal mineralId;// 现货ID 1：原油 2：金属
	private String quesContent;// 提问内容
	private String quesCreateTime;// 提问创建时间
	private String quesCreatorName;// 提问人姓名
	private BigDecimal quesCreatorLevelId;// 提问人等级，客户等级
	private BigDecimal quesCreatorHeadId;// 提问人头像ID（非客户）
	private BigDecimal questionType;// 提问类型，1：首次问题 2：回复讲师
	private BigDecimal viewId;// 观点ID
	private boolean isCustomer;//是否为客户 
	private BigDecimal quesCreatorId;//私信发送者ID
	private BigDecimal privateMessageId;//私信ID
	/* 改版客户提问结束 */

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

	public void setIsCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public BigDecimal getQuesCreatorId() {
		return quesCreatorId;
	}

	public void setQuesCreatorId(BigDecimal quesCreatorId) {
		this.quesCreatorId = quesCreatorId;
	}

	public BigDecimal getPrivateMessageId() {
		return privateMessageId;
	}

	public void setPrivateMessageId(BigDecimal privateMessageId) {
		this.privateMessageId = privateMessageId;
	}

}
