package com.ld.model;

import java.math.BigDecimal;
import java.util.Date;

public class ViewpointCFile {
    private BigDecimal viewpointId;//�۵�ID

    private BigDecimal cfileId;//�ļ�ID

    private BigDecimal fileSort;//�ļ�˳����������Ӧ
    
    private Date createTime;//����ʱ��
    
    private Date updateTime;//�޸�ʱ��

	public BigDecimal getViewpointId() {
		return viewpointId;
	}

	public void setViewpointId(BigDecimal viewpointId) {
		this.viewpointId = viewpointId;
	}

	public BigDecimal getCfileId() {
		return cfileId;
	}

	public void setCfileId(BigDecimal cfileId) {
		this.cfileId = cfileId;
	}

	public BigDecimal getFileSort() {
		return fileSort;
	}

	public void setFileSort(BigDecimal fileSort) {
		this.fileSort = fileSort;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}