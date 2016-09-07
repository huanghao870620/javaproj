$(function(){
	var login = new Login({
		url : contextPath+'/login/login.htm',
		form : $('#login')
	});
	//登录
	$('.login-button').on('click',function(){
		login.login();
	});
	
	//
	$('.login-btn').on('click', function(){
		login.login();
	});
	
	//回车登录
	$('#checkCode').keydown(function(e){
		if(e.keyCode==13){
			login.login();
		}
	});
	//刷新验证码
	$('.freshCode').on('click',function(){
		freshCode();
	});
});
  
Login = function(o){
	this.url = o.url;
	this.form = o.form;
}

/**
 * 登录
 */
Login.prototype.login = function(){
	var userName = $('input[name="userDto.userName"]');
	var password = $('input[name="userDto.password"]');
	var checkCode = $('input[name="userDto.checkCode"]');
	if(!userName.val()){
		alert('请输入用户名!');
		userName.focus();
		return;
	}
	if(!password.val()){
		alert('请输入密码!');
		password.focus();
		return;
	}
	if(!checkCode.val()){
		alert('请输入验证码!');
		checkCode.focus();
		return;
	}
	$.post(this.url, this.form.serialize(), function(data){
		if(data.success==-1){
			window.parent.location.href=contextPath+"/index/frontIndex.htm";
		}else{
			alert(data.message);
			freshCode();
		}
	},'json');
}

//刷新验证码
function freshCode(){
	$('#geneCode').attr('src',contextPath+'/general/crateimage/geneCode.htm?' + Math.random());
}


