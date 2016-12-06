package com.ld.model;

import java.io.Serializable;
import java.util.Date;

/**
 * �ڵ��۵�ʵ����
 * @author zeng.nian
 *
 */
public class Viewpoint implements Serializable{

	/**
	 * Ψһ��ʶ
	 */
	private static final long serialVersionUID = 206605732953008441L;
	
	private Long viewpointId;//�۵�ID
	private String title;//�۵����
	private String content;//�۵����
	private Date createTime;//����ʱ��
	private Date updateTime;//����ʱ��
	private String special;//ר��
	
	public Long getViewpointId() {
		return viewpointId;
	}
	public void setViewpointId(Long viewpointId) {
		this.viewpointId = viewpointId;
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
