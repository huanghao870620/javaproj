package com.ld.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CourseWare implements Serializable{
	private static final long serialVersionUID = -5508677915185452372L;
//�μ�ID
	private BigDecimal courseWareId;
	public BigDecimal getCourseWareId() {
		return courseWareId;
	}

	public void setCourseWareId(BigDecimal courseWareId) {
		this.courseWareId = courseWareId;
	}

	public BigDecimal getCfileId() {
		return cfileId;
	}

	public void setCfileId(BigDecimal cfileId) {
		this.cfileId = cfileId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
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

	public String getCourseWareType() {
		return courseWareType;
	}

	public void setCourseWareType(String courseWareType) {
		this.courseWareType = courseWareType;
	}

	//ͼƬID
	private BigDecimal cfileId;

	//�γ�����
    private String courseName;

    //���ӵ�ַ
    private String linkAddress;


    //����ʱ��
    private Date createTime;

    //����ʱ��
    private Date updateTime;

    //�ڲ����� 1������ 2���߼�
    private String courseWareType;

  
}