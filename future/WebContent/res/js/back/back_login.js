

/**
 * 
 */
Login = function(o){
	this.url = o.url;
	this.form = o.form;
}


/**
 * 登录
 */
Login.prototype.login = function(){
	$.post(this.url, {}, function(data){
		
	});
}

/**
 * 提交
 */
Login.prototype.submit = function(){
	 this.form.submit();
}