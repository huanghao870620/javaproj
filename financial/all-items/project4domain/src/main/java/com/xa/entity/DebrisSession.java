package com.xa.entity;

import java.util.Date;

public class DebrisSession {
    private Long id;

    private Date cronTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCronTime() {
        return cronTime;
    }

    public void setCronTime(Date cronTime) {
        this.cronTime = cronTime;
    }
}