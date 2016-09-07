<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>磊丹财经后台管理</title>
<link href="<%=request.getContextPath() %>/res/back/css/reset.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/res/back/css/loginlast.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/general/login.js"></script>

<script type="text/javascript">


var login;

 $(function(){
	 
	 window.login = new Login({
		 url : "<%=request.getContextPath()%>/back/login",
		 form : $('#loginForm')
	 });
	 
	 
	 
	  $('#checkCode').keydown(function(e){
     	 if(e.keyCode==13){
     		 window.login.submit();
     	 }
     });
	 
	 
 });

 
 function geneCode(){
			 $('#vercode').attr('src','<%=request.getContextPath()%>/general/crateimage/geneCode.htm?'+ Math.random());
 }

</script>

</head>

<body>
<s:actionerror />
<div class="login_last">

 <form action="<%=request.getContextPath() %>/back/login.htm" id="loginForm" method="post">
	<img class="logo" src="<%=request.getContextPath() %>/res/images/logo.png" />
    <img class="title" src="<%=request.getContextPath() %>/res/images/title.png" />
    <input name="user.userName"  class="user" type="text"onfocus="if(this.value==defaultValue) {this.value='';this.type='text'}" onblur="if(!value) {value=defaultValue; this.type='text';}"  value="用户名" />
    <input name="user.password" class="key" type="type" onfocus="if(this.value==defaultValue) {this.value='';this.type='password'}" onblur="if(!value) {value=defaultValue; this.type='text';}" value="密&nbsp;&nbsp;码" />
    
    <div class="yanzm">
    
    <input id="checkCode" name="checkCode" type="text" onfocus="if(this.value==defaultValue) {this.value='';}" onblur="if(!value) {value=defaultValue; this.type='text';}" value="验证码"  />
    
    <p class="code-number">
     	<img id="vercode" src="<%=request.getContextPath() %>/general/crateimage/geneCode.htm" onclick="this.src='<%=request.getContextPath()%>/back/geneCode.htm?'+ Math.random()" title="点击图片刷新验证码"/>
     	
	    <a href="javascript:void(0);">
	    	<img src="<%=request.getContextPath() %>/res/images/refresh.png" onclick="geneCode();" />
	    </a>
	    
    </p>
    
    </div>
    	<a class="login-button" href="javascript: window.login.submit();">登录</a>
    </form>
</div>
</body>
</html>
 --%>