package com.ld.model;

import java.math.BigDecimal;

public class Mineral {
	private BigDecimal id;

    private String name;


    public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}