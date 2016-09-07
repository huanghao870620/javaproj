<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
%>	
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<title>磊丹财经——讲师管理</title>
<link href="<%=contextPath %>/res/back/css/lecturer-view.css" type="text/css" rel="stylesheet" />
<%@include file="/jsp/back/inc.jsp"%>
<script src="<%=contextPath %>/res/js/plugins/DateUtil.js" type="text/javascript"></script>
<script src="<%=contextPath %>/res/js/back/viewpoint/viewpoint.js" type="text/javascript"></script>
<script type="text/javascript">
	var contextPath = '<%=contextPath %>';
</script>
</head>
<body>
	<%@include file="/jsp/back/header.jsp"%>
	<div class="contain">
		<%@include file="/jsp/back/menu/menu.jsp"%>
		<div class="contain_main">
			<h1 class="map">
				<a href="background_system.html">首页</a>&gt;
				<a href="<%=contextPath %>/back/viewpoint/toLecturer.htm">讲师管理</a>&gt;
				<span>磊丹观点</span>
			</h1>
			<p class="warn">正在进行磊丹观点上传、更新。(一页为一星期)</p>
			<div class="theme">
				<div class="easyui-panel" title="磊丹观点"
					iconCls="icon-save" style="width: auto; height: 420px;"
					collapsible="false" minimizable="false" maximizable="false"
					closable="false" border="false">
					<div class="easyui-layout" fit="true">
						<div id="easyui_toolbar" region="north" border="false"
							style="height:33px;padding: 2px 5px; background: #fafafa;">
							<div style="float: left;">
								<a href="<%=contextPath %>/back/viewpoint/toAddViewpoint.htm"
									class="easyui-linkbutton" plain="true" icon="icon-add">新增</a>
							</div>
							<div class="datagrid-btn-separator"></div>
							<div style="float: left;">
								<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" icon="icon-edit">编辑</a>
							</div>
							<div class="datagrid-btn-separator"></div>
							<div style="float: left;">
								<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" icon="icon-remove">删除</a>
							</div>
							<div id="tb" style="float: right;margin-top:4px;">  
	                        	<input id="search" class="easyui-searchbox" searcher="doSearch" prompt="输入标题搜索"></input>   
		                	</div>
						</div>
						<div region="center" border="false">
							<table id="viewpointData"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>