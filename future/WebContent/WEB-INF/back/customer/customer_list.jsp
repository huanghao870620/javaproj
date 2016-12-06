<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="en">
<%@include file="../../../jsp/back/inc.jsp" %>
<script src="<%=contextPath %>/res/js/plugins/DateUtil.js" type="text/javascript"></script>
<script src="<%=contextPath %>/res/js/back/customer/customer_list.js" type="text/javascript"></script>
<meta charset="utf-8" />
<title>磊丹财经——后台客户管理</title>
<script type="text/javascript">
	var contextPath = '<%=contextPath %>';
</script>
</head>
<body>
<%@include file="../../../jsp/back/header.jsp" %>
<div class="contain">
	<%@include file="../../../jsp/back/menu/menu.jsp" %>
    <div class="contain_main">
    	<h1 class="map">
    		<a href="background_system.html">首页</a>&gt;
    		<span>客户管理</span>
    	</h1>
        <p class="warn">管理客户学员的搜索、筛选、跟单、马甲权限内容、信息查看，管理功能。</p>
        <div class="theme">
	  		<div class="easyui-panel" title="客户列表" style="height: 420px;" >  
		        <div class="easyui-layout" fit="true"  >  
		            <div id="easyui_toolbar" region="north" border="false"  
		                style="height: 32px; padding: 2px 5px; background: #fafafa;">  
		                <div style="float: left;">  
		                    <a href="<%=request.getContextPath() %>/back/customer/toAddCustomer.htm" class="easyui-linkbutton" plain="true" icon="icon-add">新增</a>  
		                </div>  
		                <div class="datagrid-btn-separator"></div>  
		                <div style="float: left;">  
		                    <a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-edit">编辑</a>  
		                </div>  
		                <div class="datagrid-btn-separator"></div>
		                <div style="float: left;">  
		                    <a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-remove">删除</a>  
		                </div>
		                <div class="datagrid-btn-separator"></div>
		                <div style="float: left;">  
		                    <a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-mini-edit">修改密码</a>
		                </div>
		                <div id="tb" style="float: right;margin-top: 4px;">  
                        	<input id="search" class="easyui-searchbox" searcher="doSearch" prompt="输入账号搜索"></input>   
		                </div>  
		            </div>  
		            <div region="center" border="false">
		                <table id="customerData"></table>
		            </div>
		        </div>   
		    </div>  
        </div>
    </div>
</div>
</body>
</html>