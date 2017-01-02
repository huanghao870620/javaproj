package com.xa.entity;

public class OrderRecv {
    private Long id;

    private Long buyhandId;

    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyhandId() {
        return buyhandId;
    }

    public void setBuyhandId(Long buyhandId) {
        this.buyhandId = buyhandId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}