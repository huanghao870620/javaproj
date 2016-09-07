package com.ld.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author huang.hao
 *
 */
public class User extends AbstractUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3838416573126129471L;

	private BigDecimal userId;//�û�ID

    private String userName;//�û���

    private String password;//����

    private String name;//����

    private String firmOfferAccount;//ʵ���˺�

    private Date registTime;//ע��ʱ��

    private Date lastLoginTime;//����¼ʱ��

    private String nickName;//�ǳ�

    private BigDecimal levelId;//�ȼ�ID

    private String email;//����

    private String phone;//�ֻ�����

    private String address;//��ַ

    private Date updateTime;//����ʱ��

    private BigDecimal CFileId;//�ļ�ID
    
    private String roleName;//��ɫ����
    
    
    private List<Role> roles; // ��ɫ
    
    
    public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public BigDecimal getCFileId() {
		return CFileId;
	}

	public void setCFileId(BigDecimal cFileId) {
		CFileId = cFileId;
	}

	public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirmOfferAccount() {
        return firmOfferAccount;
    }

    public void setFirmOfferAccount(String firmOfferAccount) {
        this.firmOfferAccount = firmOfferAccount;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BigDecimal getLevelId() {
		return levelId;
	}

	public void setLevelId(BigDecimal levelId) {
		this.levelId = levelId;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	@Override
	public String getAccount() {
		return this.userName;
	}

	@Override
	public BigDecimal getAccountId() {
		return this.userId;
	}
	
	
	/**
	 * �ǲ����Լ�
	 * @param userId
	 * @return
	 */
	public boolean isSelf(BigDecimal userId){
		 return this.userId.equals(userId);
	}
	
	
}