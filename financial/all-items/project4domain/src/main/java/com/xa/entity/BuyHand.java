package com.xa.entity;

import java.util.Date;

public class BuyHand {
    private Long id;

    private String mobile;

    private String password;

    private Long admNotice;

    private Long passport;

    private Long sidPhoto;

    private Long lifePhoto;

    private Long headPortrait;

    private String nickname;

    private String gender;

    private String signature;

    private String qrCodeLinks;

    private String email;

    private String idNumber;

    private String passportNo;

    private String inputId;
    
    private String payPassword;
    
    private Integer intensity;

    private Date addTime;
    
    
    

    public Integer getIntensity() {
		return intensity;
	}

	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAdmNotice() {
        return admNotice;
    }

    public void setAdmNotice(Long admNotice) {
        this.admNotice = admNotice;
    }

    public Long getPassport() {
        return passport;
    }

    public void setPassport(Long passport) {
        this.passport = passport;
    }

    public Long getSidPhoto() {
        return sidPhoto;
    }

    public void setSidPhoto(Long sidPhoto) {
        this.sidPhoto = sidPhoto;
    }

    public Long getLifePhoto() {
        return lifePhoto;
    }

    public void setLifePhoto(Long lifePhoto) {
        this.lifePhoto = lifePhoto;
    }

    public Long getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(Long headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getQrCodeLinks() {
        return qrCodeLinks;
    }

    public void setQrCodeLinks(String qrCodeLinks) {
        this.qrCodeLinks = qrCodeLinks;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
    
    
}