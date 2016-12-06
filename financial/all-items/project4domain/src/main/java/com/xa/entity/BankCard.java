package com.xa.entity;

public class BankCard {
	private Long id;

	private Long buyHandId;
	
	private Long bankTypeId;
	
	private String no;
	
	private String holderName;
	
	private String mobile;
	
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Long getBuyHandId() {
		return buyHandId;
	}

	public void setBuyHandId(Long buyHandId) {
		this.buyHandId = buyHandId;
	}

	public Long getBankTypeId() {
		return bankTypeId;
	}

	public void setBankTypeId(Long bankTypeId) {
		this.bankTypeId = bankTypeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}