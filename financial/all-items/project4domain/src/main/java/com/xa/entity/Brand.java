package com.xa.entity;

public class Brand {
    private Long id;

    private String name;

    private Long imgId;
    
    private Long detailPic;

    private Integer isRecommended;

    private Long uploadTypeId;
    
    private String info;
    

    public Long getDetailPic() {
		return detailPic;
	}

	public void setDetailPic(Long detailPic) {
		this.detailPic = detailPic;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    public Integer getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(Integer isRecommended) {
        this.isRecommended = isRecommended;
    }

    public Long getUploadTypeId() {
        return uploadTypeId;
    }

    public void setUploadTypeId(Long uploadTypeId) {
        this.uploadTypeId = uploadTypeId;
    }
}