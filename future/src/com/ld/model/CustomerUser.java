package com.ld.model;

import java.math.BigDecimal;

public class CustomerUser {
    private BigDecimal customerId;

    private BigDecimal userId;

    public BigDecimal getCustomerId() {
        return customerId;
    }

    public void setCustomerId(BigDecimal customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }
}