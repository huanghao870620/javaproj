package com.xa.enumeration;
/**
 * 第三方登录类型
 * @author burgess
 *
 */
public enum AccountType {
    WE_CHAT(1L), //微信
    FACE_BOOL(2L), //facebook
    TWITTER(3L) //推特
    ;
	private Long value;
	AccountType(Long value){
		this.value = value;
	}
	public Long getValue() {
		return value;
	}
	
}
