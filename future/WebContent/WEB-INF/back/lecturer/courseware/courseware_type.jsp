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
<title>磊丹财经——后台管理员管理</title>
<link href="<%=contextPath %>/res/back/css/reset.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/system.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecturer.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecturer-choose.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecturer-courseware-type.css" type="text/css" rel="stylesheet" />
<%@include file="/jsp/back/inc.jsp"%>
</head>
<body>
<%@include file="/jsp/back/header.jsp"%>
<div class="contain">
<%@include file="/jsp/back/menu/menu.jsp"%>
    <div class="contain_main">
    	<h1 class="map">
    		<a href="background_system.html">首页</a>&gt;
    		<a href="<%=contextPath %>/back/viewpoint/toLecturer.htm">讲师管理</a>&gt;
    		<span>选择课件</span>
    	</h1>
        <p class="warn">讲师对页面内容进行添加、修改、更新、增删、设置。讲师必须按照格式添加内容。</p>
        <div class="course">
            <a class="cour011" href="<%= contextPath %>/back/courseware/toCoursewareListPage.htm?coursewareType=1"></a>
            <a class="cour021" href="<%= contextPath %>/back/courseware/toCoursewareListPage.htm?coursewareType=2"></a>
        </div>
    </div>
</div>
</body>
</html>