package com.ld.model;

import java.math.BigDecimal;

public class AdviceType {
	private BigDecimal id;

    private String adviceName;

    public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getAdviceName() {
        return adviceName;
    }

    public void setAdviceName(String adviceName) {
        this.adviceName = adviceName;
    }
}