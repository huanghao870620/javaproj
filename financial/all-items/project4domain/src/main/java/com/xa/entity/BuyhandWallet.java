package com.xa.entity;

public class BuyhandWallet {
    private Long id;

    private Long buyHandId;

    private Double balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyHandId() {
        return buyHandId;
    }

    public void setBuyHandId(Long buyHandId) {
        this.buyHandId = buyHandId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}