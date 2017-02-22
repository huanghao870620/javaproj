package com.xa.entity;

import java.util.Date;

public class FastBuySession {
    private Long id;

    private Date startTime;

    private Date endTime;

    private String name;

    private String info;
    
    private Float discount;

    private Date addTime;
    
    
    private Long imgAdvId; //专场轮播图
    
    
    
    

    public Long getImgAdvId() {
		return imgAdvId;
	}

	public void setImgAdvId(Long imgAdvId) {
		this.imgAdvId = imgAdvId;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}