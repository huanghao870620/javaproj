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
<link href="<%=contextPath %>/res/back/css/lecture-choose02.css" type="text/css"	 rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecture-choose02_add.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecturer-view_add.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecturer-internal_add.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecturer-internal_add-metal.css" type="text/css" rel="stylesheet" />
<%@include file="/jsp/back/inc.jsp" %>
<script type="text/javascript">
	var contextPath = '<%=contextPath %>';
</script>
<script src="<%=contextPath %>/res/js/ajaxFileUpload.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=contextPath %>/res/js/back/courseware/courseware_add.js"></script>
</head>
<body>
<%@include file="/jsp/back/header.jsp" %>
<div class="contain">
<%@include file="/jsp/back/menu/menu.jsp" %>
    <div class="contain_main" style="width: 86.5%;overflow-y:auto;overflow-x:hidden;">
    	<h1 class="map">
    		<a href="background_system.html">首页</a>&gt;
    		<a href="<%=contextPath %>/back/viewpoint/toLecturer.htm">讲师管理</a>&gt;
    		<a href="<%=contextPath %>/back/courseware/toCoursewareTypePage.htm">课件添加</a>&gt;
    		<a href="<%=contextPath %>/back/courseware/toCoursewareListPage.htm?coursewareType=${coursewareType }">${coursewareTypeValue }课件</a>&gt;
    		<span>${flagStr }${coursewareTypeValue }课件</span>
    	</h1>
        <p class="warn">正在${flagStr }${coursewareTypeValue }课件</p>
        <div class="theme">
        	<c:if test="${flag==0 }">
        		<form id="courseWareForm" action="<%=contextPath %>/back/courseware/addCourseWare.htm">
        	</c:if>
        	<c:if test="${flag==1 }">
        		<form id="courseWareForm" action="<%=contextPath %>/back/courseware/updateCourseWare.htm">
        		<input type="hidden" name="courseWareDto.courseWareId" value="${courseWareDto.courseWareId }" />
        	</c:if>
        		<input type="hidden" name="courseWareDto.courseWareTypeValue" value="${courseWareTypeValue }"/>
        		<input type="hidden" id="hiddenCourseWareType" name="courseWareDto.courseWareType" value="${coursewareType }"/>
	        	<h2><p>${flagStr }${coursewareTypeValue }课件</p></h2>
	            <div class="choose_con">
	                <ul class="cho1">
	            		<li class="tit"><p>${coursewareTypeValue }课件图片</p></li>
	                	<li class="con">
	                    	<p>
	                    		<span>
	                    			<c:if test="${courseWareDto.cfileId!=null}">
		                    			<img src="<%=contextPath %>/download/file/show.htm?id=${courseWareDto.cfileId}" width="33" height="33"/>
	                    			</c:if>
	                    		</span>
	                    		<i>(大小33*33)</i>
	                    	</p>
	                        <a href="javascript:;" class="file-up">选择图片
								<input id="file1" type="file" name="file"/>
							</a>
	                        <input id="fileId1" type="hidden" name="courseWareDto.cfileId" value="${courseWareDto.cfileId}"></input>
	                    </li>
	            	</ul>
	            	<ul class="cho2">
	            		<li class="tit"><p>课程名称</p></li>
	                	<li class="con"><b><input class="view" id="courseName" maxlength="25" name="courseWareDto.courseName" type="text" value="${courseWareDto.courseName }" /></b></li>
	            	</ul>
	                <ul class="cho3">
	            		<li class="tit"><p>链接地址</p></li>
	                	<li class="con"><input id="linkAddress" maxlength="100" name="courseWareDto.linkAddress"  type="text" value="${courseWareDto.linkAddress }" /></input></li>
	            	</ul>
	        	</div>
        	</form>
            <div class="butt">
            	<a class="finish" href="javascript:void(0);">完成</a>
            	<a class="cancel" href="<%= contextPath %>/back/courseware/toCoursewareListPage.htm?coursewareType=${coursewareType}">取消</a>
            </div>
   		</div>
	</div>
</body>
</html>
<div style="display:none">
	<script type="text/javascript">
	var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
	document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F6f798e51a1cd93937ee8293eece39b1a' type='text/javascript'%3E%3C/script%3E"));
	</script>
	<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_5718743'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s9.cnzz.com/stat.php%3Fid%3D5718743%26show%3Dpic2' type='text/javascript'%3E%3C/script%3E"));</script>
</div>