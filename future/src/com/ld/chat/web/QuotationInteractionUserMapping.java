//package com.ld.chat.web;
//
//import java.math.BigDecimal;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import com.ld.dto.UserDto;
//import com.ld.util.LoginUtil;
//
///**
// * 
// * @author huang.hao
// *
// */
//public class QuotationInteractionUserMapping extends BaseUserMapping{
//
//	private Map<String, UserDtoSessionBinding> binding_interaction = new ConcurrentHashMap<String, UserDtoSessionBinding>();
//
//	public Map<String, UserDtoSessionBinding> getBinding() {
//		return binding_interaction;
//	}
//	
//	
//	public void do0(){
//		UserDtoSessionBinding  isbOld = binding.get(sessionid);
//		if (null == isbOld) {
//			UserDto  cd = new UserDto();
////			cd.setAccount(customer.getAccount());
//			cd.setName(customer.getName());
//			cd.setNickName(customer.getNickName());
//			cd.setUserId(customer.getUserId());
//			cd.setLevelId(customer.getLevelId());
//			
//			if(httpSession.getAttribute(LoginUtil.FRONT_CUSTOMER_ROLEID)!=null)
//			{
//				cd.setRoleId((BigDecimal) httpSession.getAttribute(LoginUtil.FRONT_CUSTOMER_ROLEID));
//			}
//			else{
//				cd.setRoleId(BigDecimal.ZERO);
//			}
//			UserDtoSessionBinding isb = new UserDtoSessionBinding(session, cd);
//			binding.put(sessionid, isb);
//		}
//	}
//	
//}
