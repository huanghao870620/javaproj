package com.ld.dto;

import com.ld.model.Viewpoint;

public class ViewpointDto extends Viewpoint {

	/**
	 * Ψһ��ʶ
	 */
	private static final long serialVersionUID = -8709535045090273023L;
	
	private String week;//����
	
	private String special;//ר��
	
	private String fileId;//ͼƬID
	
	private String[] specialArray;//ר����������
	
	private String[] fileIdArray;//ר���ӦͼƬ��������

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
