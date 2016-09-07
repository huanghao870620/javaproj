package com.ld.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ld.mapper.CfileMapper;
import com.ld.model.Cfile;
import com.ld.service.CfileService;

@Service
@Transactional
public class CfileServiceImpl  extends BaseServiceImpl<Cfile, CfileMapper>
implements CfileService<Cfile> {

}
