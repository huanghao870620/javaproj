package com.xa.entity;

import java.util.Date;

public class Orders {
    private Long id;

    private Float sumPrice;

    private String orderNo;

    private Long buyerId;

    private Integer state;
    
    private Integer src; // 订单来源   1-秒杀专场    

    private Integer receWay; // 收货方式  1-自提  2-地址
    
    private Long addressId;
    
    private Date generateTime;
    

    public Integer getSrc() {
		return src;
	}

	public void setSrc(Integer src) {
		this.src = src;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Integer getReceWay() {
		return receWay;
	}

	public void setReceWay(Integer receWay) {
		this.receWay = receWay;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Float sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(Date generateTime) {
        this.generateTime = generateTime;
    }
}