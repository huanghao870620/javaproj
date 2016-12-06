package com.ld.mapper;

import java.util.List;

import com.ld.model.CustomerLevelLimitRela;

public interface CustomerLevelLimitRelaMapper extends BaseMapper<CustomerLevelLimitRela>{
	void batchUpdateByCustomerLevelLimitRelaList(List<CustomerLevelLimitRela> list); // �������¼�¼
	List<CustomerLevelLimitRela> batchFindCustomerLevelLimitRela(); // ������ѯ��¼
}
