package com.ld.dto;

import java.math.BigDecimal;

import com.ld.model.CourseWare;

public class CourseWareDto extends CourseWare {

	private static final long serialVersionUID = 1480512033866511090L;

	//–Ú∫≈
	private String week;
	

	//Õº∆¨ID
	private BigDecimal cfileId;
	//¡¥Ω”µÿ÷∑
	private String	linkAddress;
	
	private String	courseName;


	

	public BigDecimal getCfileId() {
		return cfileId;
	}

	public void setCfileId(BigDecimal cfileId) {
		this.cfileId = cfileId;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	
}
