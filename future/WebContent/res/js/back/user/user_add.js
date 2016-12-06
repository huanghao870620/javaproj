$(function(){
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
	$('a.finish').on('click',function(){
		//账户
		var userName = $('#userName');
		//姓名
		var name = $('#name');
		//密码
		var password = $('#password');
		//确认密码
		var dupPassword = $('#dupPassword');
		//角色
		var role = $('input[name=role]:checked');
		//邮箱
		var email = $('#email');
		
		if(flag == 0){
			if(!userName.val()){
				alert('请填写账户!');
				userName.focus();
				return;
			}
			if(userName.val()){
				var reg = /^[A-Za-z0-9]+$/;
				if(!reg.test(userName.val())){
					alert('填写的账户不合规范，请重新填写!');
					userName.focus();
					return;
				}
			}
			if(!password.val()){
				alert('请填写密码!');
				password.focus();
				return;
			}
			if(!dupPassword.val()){
				alert('请填写确认密码!');
				dupPassword.focus();
				return;
			}
			if(password.val()!=dupPassword.val()){
				alert('密码和确认密码不一致,请重新填写!');
				$('#password').val('');
				$('#dupPassword').val('');
				password.focus();
				return;
			}
		}
		if(!name.val()){
			alert('请填写姓名!');
			name.focus();
			return;
		}
		if(role.length<=0){
			alert('请选择角色!');
			return;
		}
		if(!email.val()){
			alert('请填写Email!');
			email.focus();
			return;
		}
		if(email.val()){
			var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
			if(!reg.test(email.val())){
				 alert("您输入的邮箱有误,请重新核对后再输入!");
				 email.focus();
				 return;
			}
		}
		var url;
		if(flag == 0){
			url = '/back/user/addUser.htm';
		}else{
			url = '/back/user/updateUser.htm';
		}
		//提交表单
		$.ajax({
			type:'POST',
			url:contextPath+url,
			data:$('#addForm').serialize(),
			cache:false,
			async:false,
			dataType:'json',
			success:function(data){
				if(data.success==-1){
					window.location.href=contextPath+"/back/user/toList.htm";
				}else{
					alert(data.message);
				}
			},
			error:function(data){
				if(flag == 0){
					alert('添加用户失败,请稍后再试!');
				}else{
					alert('编辑用户失败,请稍后再试!');
				}
			}
		});
	})
	
	//取消
	$('a.cancel').on('click',function(){
		window.location.href=contextPath+"/back/user/toList.htm";
	});
	
})