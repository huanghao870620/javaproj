package com.xa.mapper;

import java.util.List;
import com.xa.entity.AccountAssociated;

public interface AccountAssociatedMapper extends BaseMapper<AccountAssociated>{
	
	List<AccountAssociated> selectAccountAssociatedByUnionId(String unionId);
	
	/**
	 * 解除绑定
	 * @param unionId
	 */
	void dissociated(String unionId);
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	List<AccountAssociated> selectAssociatedByBuyHandIdAndUnionId(AccountAssociated param);
}