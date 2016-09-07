package com.ld.model;

import java.math.BigDecimal;
import java.util.Date;

public class ViewpointSpecialFileRela {
    private BigDecimal viewpointId;

    private BigDecimal cfileId;

    private BigDecimal specialFileSequ;

    private String special;

    private Date createTime;

    private Date updateTime;

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

	public BigDecimal getSpecialFileSequ() {
		return specialFileSequ;
	}

	public void setSpecialFileSequ(BigDecimal specialFileSequ) {
		this.specialFileSequ = specialFileSequ;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
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