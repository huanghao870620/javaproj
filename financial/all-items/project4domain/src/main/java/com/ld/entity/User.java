package com.ld.entity;

import java.util.Date;

public class User {
	private Long id; // �û�ID

	private String account; // �˻�

    private Long headPortrait; // ͷ��ID

	private String name; // ����

	private String email; // ����
	
    private String password; //����

	private Long roleid; // ��ɫID

	private Date registeredtime; // ע��ʱ��

    private Date registeredTime;

    private Date lastLoginTime;// ����¼ʱ��

    private Date updateTime; //����ʱ��


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}


    public Long getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(Long headPortrait) {
        this.headPortrait = headPortrait;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public Date getRegisteredtime() {
		return registeredtime;
	}

    public Date getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(Date registeredTime) {
        this.registeredTime = registeredTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}