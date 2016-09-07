package com.ld.model;

import java.math.BigDecimal;

public class UserRole {
    private BigDecimal roleid;

    private BigDecimal userid;

    public BigDecimal getRoleid() {
        return roleid;
    }

    public void setRoleid(BigDecimal roleid) {
        this.roleid = roleid;
    }

    public BigDecimal getUserid() {
        return userid;
    }

    public void setUserid(BigDecimal userid) {
        this.userid = userid;
    }
}