package com.xa.entity;

public class CustomerOrder {
    private Long id;

    private Long orderId;

    private Long buyHandId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBuyHandId() {
        return buyHandId;
    }

    public void setBuyHandId(Long buyHandId) {
        this.buyHandId = buyHandId;
    }
}