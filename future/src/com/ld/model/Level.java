package com.ld.model;

import java.math.BigDecimal;

public class Level {
    private BigDecimal id;

    private String levelName;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}