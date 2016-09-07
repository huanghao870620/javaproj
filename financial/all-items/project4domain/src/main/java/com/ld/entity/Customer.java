package com.ld.entity;

import java.util.Date;

public class Customer {
    private Long id;

    private String account;

    private String name;

    private String firmOfferAccount;

    private Long mgid;

    private Date registrationTime;

    private Date lastLoginTime;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirmOfferAccount() {
        return firmOfferAccount;
    }

    public void setFirmOfferAccount(String firmOfferAccount) {
        this.firmOfferAccount = firmOfferAccount;
    }

    public Long getMgid() {
        return mgid;
    }

    public void setMgid(Long mgid) {
        this.mgid = mgid;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}