package com.ld.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Internal implements Serializable{
	private static final long serialVersionUID = -5508677915185452372L;

	//�ڲ�ID
	private BigDecimal internalId;

	//����
    private String title;

    //���ݼ��
    private String content;

    //ר��
    private String special;

    //����ʱ��
    private Date createTime;

    //����ʱ��
    private Date updateTime;

    //�ڲ����� 1������ 2��ԭ��
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