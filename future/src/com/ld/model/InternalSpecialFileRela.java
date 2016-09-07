package com.ld.model;

import java.math.BigDecimal;
import java.util.Date;

public class InternalSpecialFileRela {
    private BigDecimal internalId;

    private String special;
    
    private String cfileId;
    
    private BigDecimal specialFileSequ;

    private Date createTime;

    private Date updateTime;


	public BigDecimal getInternalId() {
		return internalId;
	}

	public void setInternalId(BigDecimal internalId) {
		this.internalId = internalId;
	}

	public String getCfileId() {
        return cfileId;
    }

    public void setCfileId(String cfileId) {
        this.cfileId = cfileId;
    }

	public BigDecimal getSpecialFileSequ() {
		return specialFileSequ;
	}

	public void setSpecialFileSequ(BigDecimal specialFileSequ) {
		this.specialFileSequ = specialFileSequ;
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

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}
}