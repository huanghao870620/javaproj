package com.ld.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ld.dto.UserDto;
import com.ld.live.CustomerSessionBinding;
import com.ld.live.UserManager;
import com.ld.mapper.UserMapper;
import com.ld.mapper.UserRoleMapper;
import com.ld.model.MessageBO;
import com.ld.model.User;
import com.ld.model.UserRole;
import com.ld.service.CustomerService;
import com.ld.util.EncryptionTool;
import com.ld.util.Logs;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<User, UserMapper> implements CustomerService<User> {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	UserRoleMapper userRoleMapper;
	
	/**
	 *  获取客户列表
	 * @return
	 */
	public List<UserDto> findCustomerByAjax() {
		List<UserDto> list= null;
		try {
			UserDto userDto = new UserDto();
			ActionContext ac = ActionContext.getContext();
			Map<String,Object> map = ac.getParameters();
			String[] param = (String[]) map.get("userName");
			if(ArrayUtils.isNotEmpty(param)&&StringUtils.isNotBlank(param[0])){
				userDto.setUserName(param[0]);
			}
			list = this.userMapper.findCustomerByPaging(userDto);
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("CustomerServiceImpl.findCustomerByAjax error",e);
		}
		return list;
	}
	
	/**
	 *获取所有前台用户
	 */
	public String getAllCustomer(){
		JSONArray array = new JSONArray();
		try {
			Collection<CustomerSessionBinding> colle = UserManager.getUserManager().getScm().getAllBinding();
			for (CustomerSessionBinding usb : colle) {
				User user = usb.getEntity();
				if(user != null){
					JSONObject obj = new JSONObject();
					obj.accumulate("name", user.getAccount());
					array.add(obj);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("CustomerServiceImpl.getAllCustomer error",e);
		}
		return array.toString();
	}
	
	/**
	 *  新增客户
	 */
	public MessageBO addCustomer(UserDto userDto) {
		MessageBO messageBO = null;
		try {
			boolean bo = validateCustomerInfo(userDto,"0");
			if(bo){
				//验证用户名是否存在
				User userResult = this.userMapper.findUser(userDto);
				if(userResult!=null){
					messageBO = new MessageBO("0","新增客户失败,该用户名已存在!");
					return messageBO;
				}
				User user = new User();
				user.setUserName(userDto.getUserName());
				user.setPassword(EncryptionTool.addSaltEncrypt(userDto.getUserName(), userDto.getUserName()));
				user.setName(userDto.getName());
				user.setFirmOfferAccount(userDto.getFirmOfferAccount());
				user.setLevelId(userDto.getLevelId());
				user.setRegistTime(new Date());
				user.setUpdateTime(new Date());
				this.userMapper.insert(user);
				//新增客户角色关联
				UserRole ur = new UserRole();
				ur.setRoleid(new BigDecimal(5));
				ur.setUserid(user.getUserId());
				this.userRoleMapper.insert(ur);
				
				messageBO = new MessageBO("-1","新增客户成功!");
			}else{
				Logs.getLogger().info("UserServiceImpl.addCustomer 参数验证失败!");
				messageBO = new MessageBO("0","新增客户失败,请填写完整的客户信息!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("UserServiceImpl.addCustomer error",e);
			messageBO = new MessageBO("0","新增客户失败!");
		}
		return messageBO;
	}
	
	/**
	 * ɾ��
	 */
	public MessageBO delete(){
		MessageBO messageBO = null;
		try {
			ActionContext ctx = ActionContext.getContext();
			Map<String, Object> params = ctx.getParameters();
			String[] ids = (String[]) params.get("id");
			if(ArrayUtils.isEmpty(ids)){
				messageBO = new MessageBO("0","删除客户失败,请稍后再试!");
				return messageBO;
			}else{
				String idString = new String(ids[0]);
				String[] idList =idString.split(",");
				for(int k=0 ; k<idList.length ; k++){
					BigDecimal id = new BigDecimal(idList[k]);
			//	BigDecimal id = new BigDecimal(ids[0]);
				//删除用户
				super.delete(id);
				//删除用户角色关联关系
				this.userRoleMapper.delete(id);
			}
				messageBO = new MessageBO("-1","删除客户成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("CustomerServiceImpl.delete error",e);
			messageBO = new MessageBO("0","删除客户失败!");
		}
		return messageBO;
	}
	
	
	/**
	 * 根据ID获取用户信息
	 */
	public MessageBO findCustomerById() {
		MessageBO messageBO = null;
		try {
			ActionContext ctx = ActionContext.getContext();
			Map<String, Object> params = ctx.getParameters();
			String[] ids = (String[]) params.get("id");
			if(ArrayUtils.isEmpty(ids)){
				messageBO = new MessageBO("0","获取客户信息失败!");
				return messageBO;
			}else{
				User user = this.userMapper.findById(new BigDecimal(ids[0]));
				UserDto userDto = new UserDto();
				userDto.setUserId(user.getUserId());
				userDto.setUserName(user.getUserName());
				userDto.setName(user.getName());
				userDto.setFirmOfferAccount(user.getFirmOfferAccount());
				userDto.setLevelId(user.getLevelId());
				messageBO = new MessageBO("-1","获取客户信息成功!",userDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("CustomerServiceImpl.findCustomerById error",e);
			messageBO = new MessageBO("0","获取客户信息失败!");
		}
		return messageBO;
	}
	
	/**
	 * 更新记录
	 */
	public MessageBO updateCustomer(UserDto userDto){
		MessageBO messageBO = null;
		try {
			boolean bo = validateCustomerInfo(userDto,"1");
			if(bo){
				//更新用户基本信息
				User user = this.userMapper.findById(userDto.getUserId());
				user.setName(userDto.getName());
				user.setFirmOfferAccount(userDto.getFirmOfferAccount());
				user.setLevelId(userDto.getLevelId());
				user.setUpdateTime(new Date());
				this.userMapper.updateByPrimaryKey(user);
				messageBO = new MessageBO("-1","更新客户信息成功!");
			}else{
				Logs.getLogger().info("UserServiceImpl.updateUser error 更新客户信息失败，参数不合法!");
				messageBO = new MessageBO("0","更新客户信息失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("CustomerServiceImpl.updateCustomer error",e);
			messageBO = new MessageBO("0","更新客户信息失败!");
		}
		return messageBO;
	}
	
	/**
	 * 验证客户表单信息
	 * @param userDto
	 * @return
	 */
	private boolean validateCustomerInfo(UserDto userDto,String flag){
		if(userDto==null){
			return false;
		}
		if("0".equals(flag)){
			if(StringUtils.isBlank(userDto.getUserName())){
				return false;
			}
		}
		if(StringUtils.isBlank(userDto.getName())){
			return false;
		}
		if(StringUtils.isBlank(userDto.getFirmOfferAccount())){
			return false;
		}
		if(userDto.getLevelId()==null){
			return false;
		}
		return true;
	}
}
