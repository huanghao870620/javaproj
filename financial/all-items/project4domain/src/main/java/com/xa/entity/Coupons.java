package com.xa.entity;

import java.util.Date;

public class Coupons {
    private Long id;

    private Float price;

    private Float sill;

    private Integer state;

    private Date limitStart;

    private Date limitEnd;

    private Long classId;

    private Long brandId;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getSill() {
        return sill;
    }

    public void setSill(Float sill) {
        this.sill = sill;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Date limitStart) {
        this.limitStart = limitStart;
    }

    public Date getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Date limitEnd) {
        this.limitEnd = limitEnd;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}