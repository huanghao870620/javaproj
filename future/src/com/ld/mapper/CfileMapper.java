package com.ld.mapper;

import java.util.List;

import com.ld.model.Cfile;

public interface CfileMapper extends BaseMapper<Cfile>{
	List<Cfile> batchFindByCfileIdList(List<String> id); // ������ѯ��¼
	 void batchRemoveCfileById(List<String> id); // ����ɾ����¼
}