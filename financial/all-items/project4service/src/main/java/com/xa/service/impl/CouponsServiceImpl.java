package com.xa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Coupons;
import com.xa.mapper.CouponsMapper;
import com.xa.service.CouponsService;

@Service
@Transactional
public class CouponsServiceImpl extends BaseServiceImpl<Coupons, CouponsMapper> implements CouponsService<Coupons> {

}
