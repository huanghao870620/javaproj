package com.ld.entity;

public class Room {
    private Long id;

    private Integer type;

    private String name;

    private Long enterLevel;

    private Long intoScope;

    private Boolean allowVisitorsToEnter;

    private Boolean allowVisitorsToSpeak;

    private Long allowWords;

    private Long qrCodeImage;

    private Long skin;

    private String riskWarning;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEnterLevel() {
        return enterLevel;
    }

    public void setEnterLevel(Long enterLevel) {
        this.enterLevel = enterLevel;
    }

    public Long getIntoScope() {
        return intoScope;
    }

    public void setIntoScope(Long intoScope) {
        this.intoScope = intoScope;
    }

    public Boolean getAllowVisitorsToEnter() {
        return allowVisitorsToEnter;
    }

    public void setAllowVisitorsToEnter(Boolean allowVisitorsToEnter) {
        this.allowVisitorsToEnter = allowVisitorsToEnter;
    }

    public Boolean getAllowVisitorsToSpeak() {
        return allowVisitorsToSpeak;
    }

    public void setAllowVisitorsToSpeak(Boolean allowVisitorsToSpeak) {
        this.allowVisitorsToSpeak = allowVisitorsToSpeak;
    }

    public Long getAllowWords() {
        return allowWords;
    }

    public void setAllowWords(Long allowWords) {
        this.allowWords = allowWords;
    }

    public Long getQrCodeImage() {
        return qrCodeImage;
    }

    public void setQrCodeImage(Long qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }

    public Long getSkin() {
        return skin;
    }

    public void setSkin(Long skin) {
        this.skin = skin;
    }

    public String getRiskWarning() {
        return riskWarning;
    }

    public void setRiskWarning(String riskWarning) {
        this.riskWarning = riskWarning;
    }
}