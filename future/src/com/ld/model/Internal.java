package com.ld.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Internal implements Serializable{
	private static final long serialVersionUID = -5508677915185452372L;

	//内参ID
	private BigDecimal internalId;

	//标题
    private String title;

    //内容简介
    private String content;

    //专题
    private String special;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //内参类型 1：金属 2：原油
    private String internalType;

    public BigDecimal getInternalId() {
		return internalId;
	}

	public void setInternalId(BigDecimal internalId) {
		this.internalId = internalId;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getInternalType() {
        return internalType;
    }

    public void setInternalType(String internalType) {
        this.internalType = internalType;
    }
}