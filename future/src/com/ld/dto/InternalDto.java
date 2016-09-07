package com.ld.dto;

import com.ld.model.Internal;

public class InternalDto extends Internal {

	private static final long serialVersionUID = 1480512033866511090L;

	//ÐòºÅ
	private String week;
	
	//Í¼Æ¬
	private String fileId;
	
	private String[] fileIdArray;
	
	private String[] specialArray;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String[] getFileIdArray() {
		return fileIdArray;
	}

	public void setFileIdArray(String[] fileIdArray) {
		this.fileIdArray = fileIdArray;
	}

	public String[] getSpecialArray() {
		return specialArray;
	}

	public void setSpecialArray(String[] specialArray) {
		this.specialArray = specialArray;
	}
}
