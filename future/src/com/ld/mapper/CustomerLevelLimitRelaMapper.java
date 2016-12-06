package com.ld.mapper;

import java.util.List;

import com.ld.model.CustomerLevelLimitRela;

public interface CustomerLevelLimitRelaMapper extends BaseMapper<CustomerLevelLimitRela>{
	void batchUpdateByCustomerLevelLimitRelaList(List<CustomerLevelLimitRela> list); // 批量更新记录
	List<CustomerLevelLimitRela> batchFindCustomerLevelLimitRela(); // 批量查询记录
}
