package com.xa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.FbsDs;
import com.xa.mapper.FbsDsMapper;
import com.xa.service.FbsDsService;

@Service
@Transactional
public class FbsDsServiceImpl extends BaseServiceImpl<FbsDs, FbsDsMapper> implements FbsDsService<FbsDs> {

}
