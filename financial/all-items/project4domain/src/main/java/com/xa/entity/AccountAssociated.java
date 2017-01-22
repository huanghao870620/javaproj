package com.xa.entity;

public class AccountAssociated {
    private Long id;

    private String unionId;

    private Long accountTypeId;

    private Long buyhandId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public Long getBuyhandId() {
        return buyhandId;
    }

    public void setBuyhandId(Long buyhandId) {
        this.buyhandId = buyhandId;
    }
}