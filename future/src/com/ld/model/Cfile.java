package com.ld.model;

import java.math.BigDecimal;
import java.util.Date;

public class Cfile {
    private BigDecimal id;

    private String originalName;

    private String name;

    private String urlCode;

    private String type;

    private BigDecimal filesize;

    private Date createTime;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlCode() {
        return urlCode;
    }

    public void setUrlCode(String urlCode) {
        this.urlCode = urlCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getFilesize() {
        return filesize;
    }

    public void setFilesize(BigDecimal filesize) {
        this.filesize = filesize;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}