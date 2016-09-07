package com.ld.dto;

import com.ld.model.Viewpoint;

public class ViewpointDto extends Viewpoint {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = -8709535045090273023L;
	
	private String week;//星期
	
	private String special;//专题
	
	private String fileId;//图片ID
	
	private String[] specialArray;//专题数据数组
	
	private String[] fileIdArray;//专题对应图片数据数组

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String[] getSpecialArray() {
		return specialArray;
	}

	public void setSpecialArray(String[] specialArray) {
		this.specialArray = specialArray;
	}

	public String[] getFileIdArray() {
		return fileIdArray;
	}

	public void setFileIdArray(String[] fileIdArray) {
		this.fileIdArray = fileIdArray;
	}
}
