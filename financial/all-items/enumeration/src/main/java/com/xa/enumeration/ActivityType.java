package com.xa.enumeration;
/**
 * 活动类型
 * @author burgess
 *
 */
public enum ActivityType {
     BRAND(1), /*品牌*/
     CLASSIFICATION(2), /*分类*/
     COUNTRY(3)  /*国家*/
     ;
	
	private Integer value;
	
	 ActivityType(Integer value) {
		 this.value=value;
	 }

	public Integer getValue() {
		return value;
	}
	
}
