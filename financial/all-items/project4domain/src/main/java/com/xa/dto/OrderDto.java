package com.xa.dto;

import java.util.Date;

public class OrderDto {

	private Long buyerId;
	private String orderNo;
	private Integer receWay;
	private Integer state;
	private Float sumPrice;
	private Long addressId;
	private String mobile;
	private Long orderId;
	private Date generateTime;
	
	
	private String area;
	
	
	
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Date getGenerateTime() {
		return generateTime;
	}
	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getReceWay() {
		return receWay;
	}
	public void setReceWay(Integer receWay) {
		this.receWay = receWay;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Float getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(Float sumPrice) {
		this.sumPrice = sumPrice;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	
}
