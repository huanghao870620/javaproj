package com.ld.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ld.mapper.CustomerLevelLimitRelaMapper;
import com.ld.mapper.UserMapper;
import com.ld.model.CustomerLevelLimitRela;
import com.ld.model.MessageBO;
import com.ld.model.User;
import com.ld.service.CustomerLimitService;
import com.ld.util.LoginUtil;
import com.ld.util.Logs;
import com.opensymphony.xwork2.ActionContext;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class CustomerLimitServiceImpl extends BaseServiceImpl<User, UserMapper> implements CustomerLimitService<User> {

	@Autowired
	CustomerLevelLimitRelaMapper customerLevelLimitRelaMapper;

	/**
	 * 获取权限列表
	 */
	public void getAllCustomerLimit() {
		try {
			List<CustomerLevelLimitRela> list = this.customerLevelLimitRelaMapper.batchFindCustomerLevelLimitRela();
			if (list != null && list.size() > 0) {
				JSONArray jsonarray = JSONArray.fromObject(list);
				ActionContext ctx = ActionContext.getContext();
				// String jsonString =
				// "{'memberlevel':[{'level':'0','viewPoint':'Y','inTransaction':'Y','documentary':'Y','vs':'Y'},{'level':'1','viewPoint':'Y','inTransaction':'Y','documentary':'Y','vs':'Y'},{'level':'2','viewPoint':'Y','inTransaction':'Y','documentary':'Y','vs':'Y'},{'level':'3','viewPoint':'Y','inTransaction':'Y','documentary':'Y','vs':'Y'}]}";
				ctx.put("limitArrString", jsonarray.toString());
				if(!LoginUtil.COURSEWARELIST.equals( ctx.getApplication().get("courseWareList")) ){//课件信息
					ctx.getApplication().put("courseWareList", LoginUtil.COURSEWARELIST);
				}
			} else {
				Logs.getLogger().error("CustomerServiceImpl.getAllCustomer error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("CustomerServiceImpl.getAllCustomer error", e);
		}

	}

	/**
	 * 更新记录
	 */
	public MessageBO updateCustomerLimit() {
		MessageBO messageBO = null;
		try {
			ActionContext ctx = ActionContext.getContext();
			Map<String, Object> params = ctx.getParameters();
			String[] ids = (String[]) params.get("customerLimitStrings");
			JSONArray jsonArray = JSONArray.fromObject(ids[0]);
			if (jsonArray.isEmpty()) {
				messageBO = new MessageBO("0", "更新客户权限失败!");
				return messageBO;
			} else {
				List<CustomerLevelLimitRela> list = new ArrayList<CustomerLevelLimitRela>();
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsons = (JSONObject) jsonArray.get(i);
					CustomerLevelLimitRela cr = (CustomerLevelLimitRela) JSONObject.toBean(jsons,
							CustomerLevelLimitRela.class);
					list.add(cr);
				}

				this.customerLevelLimitRelaMapper.batchUpdateByCustomerLevelLimitRelaList(list);
				LoginUtil.CUSLIMITRELA = this.customerLevelLimitRelaMapper.findById(new BigDecimal("0"));//游客权限
				messageBO = new MessageBO("-1", "更新客户权限成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("CustomerServiceImpl.findCustomerById error", e);
			messageBO = new MessageBO("0", "更新客户权限失败!");
		}
		return messageBO;
	}
}
