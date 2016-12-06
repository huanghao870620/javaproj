<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="utf-8" />
<title>磊丹财经——后台客户管理</title>
<%@include file="/jsp/back/inc.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/res/js/plugins/DateUtil.js"></script>
<script type="text/javascript" src="<%=contextPath %>/res/js/back/user/user_list.js"></script>
<script type="text/javascript">
	var contextPath = '<%=contextPath %>'; 
</script>
</head>
<body>
<%@include file="/jsp/back/header.jsp" %>
<div class="contain">
	<%@include file="/jsp/back/menu/menu.jsp" %>
	<div class="contain_main">
		<h1 class="map">
			<a href="background_system.html">首页</a>&gt;
			<span>用户管理</span>
		</h1>
		<p class="warn">用户的增删、权限设置。增加管理员必须填写Email，以便遗忘密码时方便找回。</p>
		<div class="theme">
		  	<div class="easyui-panel" title="用户列表" style="width: auto; height: 420px;">  
		        <div class="easyui-layout" fit="true"  >  
		            <div id="easyui_toolbar" region="north" border="false"  
		                style="height: 32px; padding: 2px 5px; background: #fafafa;">  
		                <div style="float: left;">  
		                    <a href="<%=request.getContextPath() %>/back/user/toAddUser.htm" class="easyui-linkbutton" plain="true" icon="icon-add">新增</a>  
		                </div>  
		                <div class="datagrid-btn-separator"></div>  
		                <div style="float: left;">  
		                    <a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-edit">编辑</a>  
		                </div>  
		                <div class="datagrid-btn-separator"></div>  
		                <div style="float: left;">  
		                    <a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-remove">删除</a>  
		                </div>
		                <div style="float: left;">  
		                    <a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-mini-edit">修改密码</a>  
		                </div>
		                <div id="tb" style="float: right;margin-top:4px;">  
	                         <input id="search" class="easyui-searchbox" searcher="doSearch" prompt="输入用户名搜索"></input>   
		                </div>  
		            </div>  
		            <div region="center" border="false">  
		                <table id="userData"></table>  
		            </div>
		        </div>   
		    </div>  
	    </div>
	</div>
</div>
</body>
</html>