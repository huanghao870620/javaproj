package com.ld.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ld.live.Message;

public class TeacherView extends Message{
    private BigDecimal id;

    private String viewContent;

    private String adviceId;

	private String mineralId;

    private BigDecimal  teacherId;//当前讲师客户id
    
    private String teacherName;
    
	
    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	private Date createTime;

    
    public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public BigDecimal getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(BigDecimal teacherId) {
		this.teacherId = teacherId;
	}
    
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getViewContent() {
        return viewContent;
    }

    public void setViewContent(String viewContent) {
        this.viewContent = viewContent;
    }

	public String getAdviceId() {
		return adviceId;
	}

	public void setAdviceId(String adviceId) {
		this.adviceId = adviceId;
	}

	public String getMineralId() {
		return mineralId;
	}

	public void setMineralId(String mineralId) {
		this.mineralId = mineralId;
	}

}