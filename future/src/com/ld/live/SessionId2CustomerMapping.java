package com.ld.live;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

import javax.websocket.Session;

import org.springframework.web.context.WebApplicationContext;

import com.ld.chat.web.BaseUserMapping;
import com.ld.model.AbstractUser;
import com.ld.model.User;

/**
 * ǰ̨�û�ӳ��
 * @author huang.hao
 *
 */
public class SessionId2CustomerMapping extends BaseUserMapping<CustomerSessionBinding>{

	
	/**
	 * ����ǰ���û�����Ϣ
	 */
	private Collection<CustomerSessionBinding> userSessionBindingCollection = null;
	
	/**
	 * �û��󶨹��캯��
	 */
	public SessionId2CustomerMapping(){
		this.userSessionBindingCollection = this.binding.values();
	}

	
	/**
	 * ��ȡ����sessionid
	 * @return
	 */
	public Set<String> getAllSessionId(){
		return this.binding.keySet();
	}
	

	
	
	/**
	 * �ַ���Ϣ
	 */
	public void distMsg(Message message){
		GroupPushMessage gpm = new GroupPushMessage(this.getAllBinding(), message);
		gpm.groupSendMessage();
	}
	
	public void distMsg(Message message, WebApplicationContext ctx){
		GroupPushMessage gpm = new GroupPushMessage(this.getAllBinding(), message);
		gpm.groupSendMessage(ctx);
	}
	
	
	
	
	
	/**
	 *  ���ǰ̨�û���
	 */
	public void addCustomerBinding(String sessionid, Session session,  User e){
		if (null == this.findBySessionId(sessionid)) {
			CustomerSessionBinding isb =  new CustomerSessionBinding(session, e);
			this.addCustomerSessionBinding(sessionid, isb);
		}
	}
	
	
	
	/**
	 * ɾ��ǰ̨�û���
	 * @param sessionId
	 */
	public void deleteBySessionId(String sessionId){
		  this.binding.remove(sessionId);
	}
	
	
	/**
	 * ���sessionid ��ȡ�û�����Ϣ
	 * @param sessionId
	 * @return
	 */
	public CustomerSessionBinding findBySessionId(String sessionId){
		  return this.binding.get(sessionId);
	}
	
	
	/**
	 * ����û�����Ϣ
	 * @param sessionId
	 * @param binding
	 */
	public void addCustomerSessionBinding(String sessionId, CustomerSessionBinding binding){
		   this.binding.put(sessionId, binding);
	}
	
	
	/**
	 * ���sid��ȡ���û�
	 * @param sid
	 * @return
	 */
	public AbstractUser getCustomerBySessionId(String sid){
		CustomerSessionBinding csb = this.findBySessionId(sid);
		return csb.getEntity();
	}
	
	
	/**
	 * ���customer id ��ȡ����Ϣ
	 * @param customerId
	 * @return
	 */
	public CustomerSessionBinding findUserSessionBindingByCustomerId(BigDecimal customerId){
		CustomerSessionBinding bindingArr[] = new CustomerSessionBinding[this.userSessionBindingCollection.size()];
		this.userSessionBindingCollection.toArray(bindingArr);
		for(int i=0; i<bindingArr.length; i++){
			if(bindingArr[i].getEntity().getUserId().compareTo(customerId) == 0){
				  return bindingArr[i];
			}
		}
		 return null;
	}
	
}
