package com.xa.entity;

import java.util.Date;

public class RealNameAuth {
    private Long id;

    private String name;

    private String cartNo;

    private Long frontCartPhoto;

    private Long backCartPhoto;

    private Long buyerId;

    private Date addTime;

    private Date updateTime;

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

    public String getCartNo() {
        return cartNo;
    }

    public void setCartNo(String cartNo) {
        this.cartNo = cartNo;
    }

    public Long getFrontCartPhoto() {
        return frontCartPhoto;
    }

    public void setFrontCartPhoto(Long frontCartPhoto) {
        this.frontCartPhoto = frontCartPhoto;
    }

    public Long getBackCartPhoto() {
        return backCartPhoto;
    }

    public void setBackCartPhoto(Long backCartPhoto) {
        this.backCartPhoto = backCartPhoto;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}