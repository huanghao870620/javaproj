package com.xa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.AllocCoupons;
import com.xa.mapper.AllocCouponsMapper;
import com.xa.service.AllocCouponsService;
import com.xa.service.impl.BaseServiceImpl;

@Service
@Transactional
public class AllocCouponsServiceImpl extends BaseServiceImpl<AllocCoupons, AllocCouponsMapper>
		implements AllocCouponsService<AllocCoupons> {

}
