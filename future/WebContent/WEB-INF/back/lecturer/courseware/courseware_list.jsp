<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
%>    
<!DOCTYPE html>
<html>
<head lang="en">
<title>磊丹财经——讲师管理</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<link href="<%=contextPath %>/res/back/css/reset.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/system.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecturer-view.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecturer-internal.css" type="text/css" rel="stylesheet" />
<%@include file="/jsp/back/inc.jsp"%>
<script type="text/javascript" src="<%=contextPath %>/res/js/plugins/DateUtil.js"></script>
<script type="text/javascript" src="<%=contextPath %>/res/js/back/courseware/courseware_list.js"></script>
<script type="text/javascript">
	var contextPath = '<%=contextPath %>';
</script>
</head>
<body>
	<input type="hidden" name="coursewareType" id="coursewareTypeHidden" value="${coursewareType }" />
	<%@include file="/jsp/back/header.jsp"%>
	<div class="contain">
		<%@include file="/jsp/back/menu/menu.jsp"%>
		<div class="contain_main">
			<h1 class="map">
				<a href="background_system.html">首页</a>&gt;
				<a href="<%=contextPath %>/back/viewpoint/toLecturer.htm">讲师管理</a>&gt;
				<a href="<%=contextPath %>/back/courseware/toCoursewareTypePage.htm">选择课件</a>&gt;
				<span>${coursewareTypeValue }课件</span>
			</h1>
			<p class="warn">正在进行课件上传、更新。</p>
			<div class="theme">
				<div class="easyui-panel" title="${coursewareTypeValue }课件"
					iconCls="icon-save" style="width: auto; height: 420px;"
					collapsible="false" minimizable="false" maximizable="false"
					closable="false" border="false">
					<div class="easyui-layout" fit="true">
						<div id="easyui_toolbar" region="north" border="false"
							style="height:33px;padding: 2px 5px; background: #fafafa;">
							<div style="float: left;">
								<a href="<%=contextPath %>/back/courseware/toAddCourseWarePage.htm?coursewareType=${coursewareType}" class="easyui-linkbutton" plain="true" icon="icon-add">新增</a>
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
	                         	<input id="search" class="easyui-searchbox" searcher="doSearch" prompt="输入课程名称搜索"></input>   
		                	</div>
						</div>
						<div region="center" border="false">
							<table id="coursewareData"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>