package com.xa.entity;

public class Mail {
    private Long id;

    private Long buyHandId;

    private String title;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyHandId() {
        return buyHandId;
    }

    public void setBuyHandId(Long buyHandId) {
        this.buyHandId = buyHandId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}