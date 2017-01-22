package com.xa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.BuyHand;
import com.xa.entity.CustomerOrder;
import com.xa.entity.File;
import com.xa.mapper.AccountAssociatedMapper;
import com.xa.mapper.AccountTransactionRecordsMapper;
import com.xa.mapper.AccountTypeMapper;
import com.xa.mapper.BankCardMapper;
import com.xa.mapper.BankCardTypeMapper;
import com.xa.mapper.BuyHandMapper;
import com.xa.mapper.BuyhandWalletMapper;
import com.xa.mapper.ConsumptionTypeMapper;
import com.xa.mapper.CustomerOrderMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.mapper.MenuMapper;
import com.xa.mapper.MobileVercodeLogMapper;
import com.xa.mapper.OrdersMapper;
import com.xa.mapper.UserMapper;
import com.xa.service.InitContextService;
import com.xa.service.impl.BaseServiceImpl;

@Service
@Transactional
public class InitContextServiceImpl extends BaseServiceImpl<BuyHand, BuyHandMapper>
		implements InitContextService<BuyHand> {
	
	
	@Autowired
	private AccountAssociatedMapper accountAssociatedMapper;
	
	@Autowired
	private AccountTransactionRecordsMapper accountTransactionRecordsMapper;
	
	@Autowired
	private AccountTypeMapper accountTypeMapper;
	
	@Autowired
	private BankCardMapper bankCardMapper;
	
	@Autowired
	private BankCardTypeMapper bankCardTypeMapper;
	
	@Autowired
	private ConsumptionTypeMapper consumptionTypeMapper;
	
	@Autowired
	private BuyHandMapper buyHandMapper;
	
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
//	@Autowired
//	private GoodsOrderReleaseMapper goodsOrderReleaseMapper;
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private MobileVercodeLogMapper mobileVercodeLogMapper;
	
	@Autowired
	private OrdersMapper ordersMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private BuyhandWalletMapper buyhandWalletMapper;
	
	/**
	 * 客户和订单关联
	 */
	@Autowired
	private CustomerOrderMapper customerOrderMapper;

	/**
	 * 初始化上下文
	 */
	public void initContext(){
		
	}
	
	
	/**
	 * 添加客户
	 */
	private void addCustomer(){
		BuyHand customer = new BuyHand();
		File admNotice = new File();
	}
	
	
	/**
	 * 添加客户和订单关系
	 */
	public void addCustomerOrder(){
		CustomerOrder co = new CustomerOrder();
		co.setBuyHandId(1L);
		co.setOrderId(1L);
		this.customerOrderMapper.insert(co);
	}
	
	
}
