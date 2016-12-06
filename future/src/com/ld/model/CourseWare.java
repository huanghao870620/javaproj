package com.ld.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CourseWare implements Serializable{
	private static final long serialVersionUID = -5508677915185452372L;
//课件ID
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

	//图片ID
	private BigDecimal cfileId;

	//课程名称
    private String courseName;

    //链接地址
    private String linkAddress;


    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //内参类型 1：基础 2：高级
    private String courseWareType;

  
}