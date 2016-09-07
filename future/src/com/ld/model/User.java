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

	private BigDecimal userId;//用户ID

    private String userName;//用户名

    private String password;//密码

    private String name;//姓名

    private String firmOfferAccount;//实盘账号

    private Date registTime;//注册时间

    private Date lastLoginTime;//最后登录时间

    private String nickName;//昵称

    private BigDecimal levelId;//等级ID

    private String email;//邮箱

    private String phone;//手机号码

    private String address;//地址

    private Date updateTime;//更新时间

    private BigDecimal CFileId;//文件ID
    
    private String roleName;//角色名字
    
    
    private List<Role> roles; // 角色
    
    
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
	 * 是不是自己
	 * @param userId
	 * @return
	 */
	public boolean isSelf(BigDecimal userId){
		 return this.userId.equals(userId);
	}
	
	
}