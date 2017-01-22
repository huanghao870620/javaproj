package com.xa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.ActivityAssociated;
import com.xa.mapper.ActivityAssociatedMapper;
import com.xa.service.ActivityAssociatedService;

@Service
@Transactional
public class ActivityAssociatedServiceImpl extends BaseServiceImpl<ActivityAssociated, ActivityAssociatedMapper>
		implements ActivityAssociatedService<ActivityAssociated> {

}
