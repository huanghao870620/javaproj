package com.ld.chat.web;
import com.ld.dto.TeacherViewDto;
import com.ld.live.BaseActor;

/**
 * ΢����Ϣ������
 * @author gaoran
 * @version 1.0
 * @updated 22-����-2016 13:58:44
 */
public class QuotationPushMessageActor extends BaseActor  implements Runnable {
	
	private UserDtoSessionBinding binding;
	private TeacherViewDto message;
	
	public QuotationPushMessageActor(UserDtoSessionBinding binding, TeacherViewDto message){
		 this.binding = binding;
		 this.message = message;
	}

	@Override
	public void run() {
		this.binding.sendTeacherViewMsg(message);
	}

}
