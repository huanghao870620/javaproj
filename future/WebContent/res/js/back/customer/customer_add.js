$(function(){
	//限制账户只能输入字母和数字
	$('#userName').keydown(function(event){
		var keyCode = (navigator.appname=="Netscape")?event.which:window.event.keyCode;
		if ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90)){
			return true
		}
		// 小数字键盘
		if (keyCode >= 96 && keyCode <= 105){
			return true
		}
		// Backspace, del, 左右方向键
		if (keyCode == 8 || keyCode == 46 || keyCode == 37 || keyCode == 39){
			return true
		}
		return false;
	});
	//完成按钮
	$('a.finish').on('click',function(){
		//账户
		var userName = $('#userName');
		//姓名
		var name = $('#name');
		//实盘账号
		var firmOffer = $('#firmOffer');
		//客户等级
		var levelId = $('#levelId');
		
		if(flag == 0){
			if(!userName.val()){
				alert('请填写账户!');
				userName.focus();
				return;
			}
			if(userName.val()){
				var reg = /^[a-zA-Z][0-9]+$/;
				if(!reg.test(userName.val())){
					alert('填写的账户不合规范，请重新填写!');
					userName.focus();
					return;
				}
			}
		}
		if(!name.val()){
			alert('请填写姓名!');
			name.focus();
			return;
		}
		if(!firmOffer.val()){
			alert('请填写实盘账户!');
			firmOffer.focus();
			return;
		}
		if(!levelId.val()){
			alert('请选择会员等级!');
			levelId.focus();
			return;
		}
		if(flag == 0){
			url = '/back/customer/addCustomer.htm';
		}else{
			url = '/back/customer/updateCustomer.htm';
		}
		
		//提交表单
		$.ajax({
			type:'POST',
			url:contextPath+url,
			data:$('#customerForm').serialize(),
			cache:false,
			async:false,
			dataType:'json',
			success:function(data){
				if(data.success==-1){
					window.location.href=contextPath+"/back/customer/toCustomerList.htm";
				}else{
					alert(data.message);
				}
			},
			error:function(data){
				if(flag == 0){
					alert('添加客户失败,请稍后再试!');
				}else{
					alert('编辑客户失败,请稍后再试!');
				}
			}
		});
	})
	
	//取消按钮
	$('a.cancel').on('click',function(){
		window.location.href=contextPath+"/back/customer/toCustomerList.htm";
	});
	
})