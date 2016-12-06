package com.ld.dto;

import java.math.BigDecimal;

import com.ld.model.TeacherView;

public class TeacherViewDto extends TeacherView {
	/**
	 * 封装前端登录客户dto, 用于区分登录用户所属角色为讲师还是普通客户
	 */
	private BigDecimal cfileId;
	private Boolean isDelete;// 是否删除该观点
	private String compareDate;
	private String mineral;// 数据类型
	private String advice;// 重要性
	private String[] mineralArr;
	private String[] adviceArr;
	private BigDecimal teacherIconId;
	private boolean isReplied;//是否回复过
	
	public TeacherViewDto() {
	}

	public String getCompareDate() {
		return compareDate;
	}

	public void setCompareDate(String compareDate) {
		this.compareDate = compareDate;
	}

	public Boolean isDelete() {
		return isDelete;
	}

	public void setDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public BigDecimal getCfileId() {
		return cfileId;
	}

	public void setCfileId(BigDecimal cfileId) {
		this.cfileId = cfileId;
	}

	public String getMineral() {
		return mineral;
	}

	public void setMineral(String mineral) {
		this.mineral = mineral;
		this.mineralArr = mineral.replaceAll("\\s*", "").split(",");
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
		this.adviceArr = advice.replaceAll("\\s*", "").split(",");
	}

	public String[] getMineralArr() {
		return mineralArr;
	}

	public void setMineralArr(String[] mineralArr) {
		this.mineralArr = mineralArr;
	}

	public String[] getAdviceArr() {
		return adviceArr;
	}

	public void setAdviceArr(String[] adviceArr) {
		this.adviceArr = adviceArr;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public BigDecimal getTeacherIconId() {
		return teacherIconId;
	}

	public void setTeacherIconId(BigDecimal teacherIconId) {
		this.teacherIconId = teacherIconId;
	}

	public boolean getIsReplied() {
		return isReplied;
	}

	public void setReplied(boolean isReplied) {
		this.isReplied = isReplied;
	}
	
}
