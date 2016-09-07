<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="utf-8">
<title>用户密码修改</title>
<%@include file="/jsp/back/inc.jsp" %>
<link href="<%=contextPath %>/res/back/css/password.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
	var contextPath = '<%=contextPath %>';
	$(function(){
		$('.finish').on('click',function(){
			var password = $('#password');
			if(!password.val()){
				alert('请输入新密码!');
				password.focus();
				return;
			}
			$.ajax({
				type:'POST',
				url:contextPath+'/back/user/updatePassword.htm',
				data:$('#passwordForm').serialize(),
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
					alert('修改密码失败,请稍后再试!');
				}
			});
		});
	})
</script>
</head>
<body>
<%@include file="/jsp/back/header.jsp" %>
<div class="contain">
	<%@include file="/jsp/back/menu/menu.jsp" %>
	<div class="contain_main">
    	<h1 class="map">
    		<a href="background_system.html">首页</a>&gt;
    		<a href="<%=contextPath %>/back/user/toList.htm">用户管理</a>&gt;
    		<span>修改密码</span>
    	</h1>
        <div class="theme">
        	<h2><p>修改密码</p></h2>
            <div class="newkey">
            	<form id="passwordForm">
            		<input name="userDto.userId" value="${userDto.userId }" type="hidden"/>
					<p>新密码</p><input id="password" name="userDto.password" type="password"/>
            	</form>
            </div>
            <span>
            	<a class="finish" href="javascript:;">完成</a>
            	<a class="cancel" href="<%=contextPath %>/back/user/toList.htm">取消</a>
            </span>
        </div>
    </div>
</div>
</body>
</html>