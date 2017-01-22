package com.xa.entity;

public class DeliveryAddress {
    private Long id;

    private String name;

    private String mobile;

    private Long cardIdBackFile;

    private Long cardIdFrontFile;

    private Long buyerId;

    private String idcard;

    private Long areaId;

    private String address;

    private Integer isDefault;
    
    private Integer used; // 是否使用
    
    

    public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getCardIdBackFile() {
        return cardIdBackFile;
    }

    public void setCardIdBackFile(Long cardIdBackFile) {
        this.cardIdBackFile = cardIdBackFile;
    }

    public Long getCardIdFrontFile() {
        return cardIdFrontFile;
    }

    public void setCardIdFrontFile(Long cardIdFrontFile) {
        this.cardIdFrontFile = cardIdFrontFile;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}