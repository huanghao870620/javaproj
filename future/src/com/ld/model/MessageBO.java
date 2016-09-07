package com.ld.model;

public class MessageBO {
	
	private String success;//�ɹ�ʧ�ܱ�ʶ -1���ɹ� 0��ʧ��
	private String message;//��Ϣ��
	private Object obj;//���ؽ����
	
	public MessageBO() {}
	
	public MessageBO(String success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public MessageBO(String success, String message,Object obj) {
		this.success = success;
		this.message = message;
		this.obj = obj;
	}

	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
