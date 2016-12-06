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
<%@include file="/jsp/back/inc.jsp"%>
<link href="<%=contextPath %>/res/back/css/lecturer.css" type="text/css" rel="stylesheet" />
</head>
<body>
<%@include file="/jsp/back/header.jsp"%>
<div class="contain">
    <%@include file="/jsp/back/menu/menu.jsp"%>
    <div class="contain_main">
    	<h1 class="map">
    		<a href="background_system.html">首页</a>&gt;
    		<span>讲师管理</span>
    	</h1>
        <p class="warn">讲师对页面内容进行添加、修改、更新、增删、设置。讲师必须按照格式添加内容。</p>
        <div class="theme">
        	<h2><p>讲师管理</p></h2>
            <ul class="title">
            	<li class="first">序号</li>
                <li class="six">操作建议</li>
                <li class="last"><p>操作选项</p></li>
            </ul>
            <!--S--查询结果-->
            <ul class="result">
            	<li class="first">1</li>
                <li class="six">此页面是：讲师对直播页面板块中的服务、信息的更新修改，老师可对板块/滚动进行主题化，专题化的搭配组合使用。</li>
                <li class="last">
                	<div class="button">
                		<input class="fourt" type="button" value="课件添加" onclick="location.href='<%=contextPath %>/back/courseware/toCoursewareTypePage.htm'" />
                		<input class="fourt" type="button" value="滚动广告" onclick=""/>
                		<input class="fourt" type="button" value="磊丹观点" onclick="location.href='<%=contextPath %>/back/viewpoint/toViewpointList.htm'"/>
                		<input class="fourt" type="button" value="交易内参" onclick="location.href='<%=contextPath %>/back/internal/toInternalTypePage.htm'" />
               		</div>
            	</li>
            </ul>
            <!--E--查询结果-->
        </div>
    </div>
</div>
</body>
</html>