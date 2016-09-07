package com.ld.entity;

public class Plate {
    private Long id;

    private String name;

    private String location;

    private String contentType;

    private Long logo;

    private String link;

    private Integer clickEvent;

    private Long uploadFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getClickEvent() {
        return clickEvent;
    }

    public void setClickEvent(Integer clickEvent) {
        this.clickEvent = clickEvent;
    }

    public Long getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(Long uploadFile) {
        this.uploadFile = uploadFile;
    }
}