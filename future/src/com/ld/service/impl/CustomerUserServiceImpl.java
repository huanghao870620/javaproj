//package com.ld.service.impl;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.ld.dto.CustomerUserDto;
//import com.ld.mapper.UserMapper;
//import com.ld.model.CustomerUser;
//import com.ld.service.CustomerUserService;
//
//@Service
//@Transactional
//public class CustomerUserServiceImpl  extends BaseServiceImpl<CustomerUser, UserMapper>
//implements CustomerUserService<CustomerUser> {
//
////	@Autowired
////	private CustomerUserMapper customerUserMapper;
//	/**
//	 * ��Ӷ�Ӧ��ʦ���͵� �ͻ�����  ��Ӧ���м��
//	 */
//	@Override
//	public void addCustomerUser(CustomerUserDto dto) {
//		// TODO Auto-generated method stub
//		CustomerUser cu = new CustomerUser();
//		cu.setCustomerId(dto.getCustomerId());
//		cu.setUserId(dto.getUserId());
////		customerUserMapper.insert(cu);
//	}
//
//}
