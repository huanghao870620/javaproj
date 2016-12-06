package com.ld.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 磊丹观点实体类
 * @author zeng.nian
 *
 */
public class Viewpoint implements Serializable{

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = 206605732953008441L;
	
	private Long viewpointId;//观点ID
	private String title;//观点标题
	private String content;//观点简述
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	private String special;//专题
	
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
