package com.xa.entity;

import java.util.Date;

public class Activity {
    private Long id;

    private String name;

    private String info;

    private Float sill;

    private Float price;

    private Integer activityType;

    private Long count;
    
    private Long imgAdvId;

    private Date addTime;

    private Date upTime;
    
    private Integer shelves; //是否上架
    

    public Integer getShelves() {
		return shelves;
	}

	public void setShelves(Integer shelves) {
		this.shelves = shelves;
	}

	public Long getImgAdvId() {
		return imgAdvId;
	}

	public void setImgAdvId(Long imgAdvId) {
		this.imgAdvId = imgAdvId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getSill() {
        return sill;
    }

    public void setSill(Float sill) {
        this.sill = sill;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }
}