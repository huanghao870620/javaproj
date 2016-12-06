package com.ld.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Customer extends AbstractUser implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 9190721181095763317L;

	private BigDecimal id;

    private String account;

    private String password;

    private String name;

    private String nickName;

    private String firmOfferAccount;

    private BigDecimal levelId;

    private String email;

    private String phone;

    private String address;

    private Date registrationTime;
    
    private Date updateTime;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFirmOfferAccount() {
        return firmOfferAccount;
    }

    public void setFirmOfferAccount(String firmOfferAccount) {
        this.firmOfferAccount = firmOfferAccount;
    }

    public BigDecimal getLevelId() {
        return levelId;
    }

    public void setLevelId(BigDecimal levelId) {
        this.levelId = levelId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public BigDecimal getAccountId() {
		return this.id;
	}

	@Override
	public BigDecimal getCFileId() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}