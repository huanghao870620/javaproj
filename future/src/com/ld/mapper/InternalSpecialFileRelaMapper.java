package com.ld.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ld.model.InternalSpecialFileRela;

public interface InternalSpecialFileRelaMapper extends BaseMapper<InternalSpecialFileRela>{
    
    public List<InternalSpecialFileRela> findListById(BigDecimal internalId);
    
    public InternalSpecialFileRela findByIdAndSequ(InternalSpecialFileRela internalSpecialFileRela);
}