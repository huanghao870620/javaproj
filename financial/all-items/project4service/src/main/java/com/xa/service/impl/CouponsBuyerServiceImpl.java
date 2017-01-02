package com.xa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.CouponsBuyer;
import com.xa.mapper.CouponsBuyerMapper;
import com.xa.service.CouponsBuyerService;

@Service
@Transactional
public class CouponsBuyerServiceImpl extends BaseServiceImpl<CouponsBuyer, CouponsBuyerMapper> implements CouponsBuyerService<CouponsBuyer> {

}
