<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<title>磊丹财经——后台管理员管理</title>
<link href="<%=contextPath %>/res/back/css/lecture-choose02.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecture-choose02_add.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecturer-view_add.css" type="text/css" rel="stylesheet" />
<%@include file="/jsp/back/inc.jsp" %>
<script type="text/javascript">
	var contextPath = '<%=contextPath %>';
</script>
<script src="<%=contextPath %>/res/js/ajaxFileUpload.js" type="text/javascript"></script>
<script src="<%=contextPath %>/res/js/back/viewpoint/viewpoint_add.js" type="text/javascript"></script>
</head>
<body>
<%@include file="/jsp/back/header.jsp" %>
<div class="contain">
<%@include file="/jsp/back/menu/menu.jsp" %>
	<div class="contain_main" style="width: 86.5%;overflow-y:auto;overflow-x:hidden;">
		<h1 class="map">
			<a href="background_system.html">首页</a>&gt;
			<a href="<%=contextPath %>/back/viewpoint/toLecturer.htm">讲师管理</a>&gt;
			<a href="<%=contextPath %>/back/viewpoint/toViewpointList.htm">磊丹观点</a>&gt;
			<span>${flagStr }磊丹观点</span>
		</h1>
        <p class="warn">正在进行磊丹观点上传、更新。</p>
        <div class="theme">
        	<h2><p>${flagStr }磊丹观点</p></h2>
        	<c:if test="${flag==1 }">
        		<form id="viewpointForm" action="<%=contextPath %>/back/viewpoint/updateViewpoint.htm">
        		<input name="viewpointDto.viewpointId" type="hidden" value="${viewpointDto.viewpointId }" />
        	</c:if>
        	<c:if test="${flag!=1 }">
        		<form id="viewpointForm" action="<%=contextPath %>/back/viewpoint/addViewpoint.htm">
        	</c:if>
	            <div class="choose_con">
	            	<ul class="cho2">
	            		<li class="tit"><p>标题</p></li>
	                	<li class="con"><b><input id="title" maxlength="25" name="viewpointDto.title" class="view" type="text" value="${viewpointDto.title }"/></b><span>标题长度最多25字</span></li>
	            	</ul>
	                <ul class="cho3">
	            		<li class="tit"><p>内容简介</p></li>
	                	<li class="con"><textarea id="content" maxlength="100" name="viewpointDto.content">${viewpointDto.content }</textarea><p>内容简介长度最多100字</p></li>
	            	</ul>
	                <ul class="cho3">
	            		<li class="tit"><p>品种专题（名称）</p></li>
	                	<li class="con">
	                		<i>
	                			<b><em>①</em><input id="special1" name="viewpointDto.special" type="text" value="${viewpointDto.specialArray[0] }"/></b>
	                			<b><em>②</em><input id="special2" name="viewpointDto.special" type="text" value="${viewpointDto.specialArray[1] }"/></b>
	                			<b><em>③</em><input id="special3" name="viewpointDto.special" type="text" value="${viewpointDto.specialArray[2] }"/></b>
	                		</i>
	                	</li>
	            	</ul>
	                <ul class="cho1">
	            		<li class="tit"><p>图片①</p></li>
	                	<li class="con">
	                    	<p>
		                    	<span>
			                    	<c:if test="${viewpointDto.fileIdArray[0]!=null}">
			                    		<img src="<%=contextPath %>/download/file/show.htm?id=${viewpointDto.fileIdArray[0]}" width="240" height="184"/>
			                    	</c:if>
		                    	</span>
		                    	<i>(大小945*高度不限)</i>
	                    	</p>
							<a href="javascript:;" class="file-up">选择文件
								<input id="file" type="file" name="file"/>
							</a>
						    <input id="fileId1" type="hidden" name="viewpointDto.fileId" value="${viewpointDto.fileIdArray[0]}"></input>
	                    </li>
	            	</ul>
	                <ul class="cho1">
	            		<li class="tit"><p>图片②</p></li>
	                	<li class="con">
	                    	<p>
		                    	<span>
		                    		<c:if test="${viewpointDto.fileIdArray[1]!=null}">
		                    			<img src="<%=contextPath %>/download/file/show.htm?id=${viewpointDto.fileIdArray[1]}" width="240" height="184"/>
		                    		</c:if>
		                    	</span>
		                    	<i>(大小945*高度不限)</i>
	                    	</p>
						    <a href="javascript:;" class="file-up">
						    	选择文件<input id="file1" type="file" name="file"/>
							</a>
	                        <input id="fileId2" type="hidden" name="viewpointDto.fileId" value="${viewpointDto.fileIdArray[1]}"></input>
	                    </li>
	            	</ul>
	                <ul class="cho1">
	            		<li class="tit"><p>图片③</p></li>
	                	<li class="con">
	                    	<p>
		                    	<span>
		                    		<c:if test="${viewpointDto.fileIdArray[2]!=null}">
		                    			<img src="<%=contextPath %>/download/file/show.htm?id=${viewpointDto.fileIdArray[2]}" width="240" height="184"/>
		                    		</c:if>
		                    	</span>
		                    	<i>(大小945*高度不限)</i>
	                    	</p>
							<a href="javascript:;" class="file-up">
								选择文件<input id="file2" type="file" name="file"/>
							</a>
	                        <input id="fileId3" type="hidden" name="viewpointDto.fileId" value="${viewpointDto.fileIdArray[2]}"></input>
	                    </li>
	            	</ul>
	        	</div>
        	</form>
            <div class="butt">
            	<a class="finish" href="javascript: void(0);">完成</a>
            	<a class="cancel" href="<%=contextPath %>/back/viewpoint/toViewpointList.htm">取消</a>
            </div>
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