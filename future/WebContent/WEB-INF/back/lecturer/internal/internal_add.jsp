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
<link href="<%=contextPath %>/res/back/css/lecture-choose02.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecture-choose02_add.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecturer-view_add.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecturer-internal_add.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/lecturer-internal_add-metal.css" type="text/css" rel="stylesheet" />
<%@include file="/jsp/back/inc.jsp" %>
<script type="text/javascript">
	var contextPath = '<%=contextPath %>';
</script>
<script src="<%=contextPath %>/res/js/ajaxFileUpload.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=contextPath %>/res/js/back/internal/internal_add.js"></script>
</head>
<body>
<%@include file="/jsp/back/header.jsp" %>
<div class="contain">
<%@include file="/jsp/back/menu/menu.jsp" %>
    <div class="contain_main" style="width: 86.5%;overflow-y:auto;overflow-x:hidden;">
    	<h1 class="map">
    		<a href="background_system.html">首页</a>&gt;
    		<a href="<%=contextPath %>/back/viewpoint/toLecturer.htm">讲师管理</a>&gt;
    		<a href="<%=contextPath %>/back/internal/toInternalTypePage.htm">选择内参（品种）</a>&gt;
    		<a href="<%=contextPath %>/back/internal/toInternalListPage.htm?internalType=${internalType }">交易内参（${internalTypeStr }）</a>&gt;
    		<span>${flagStr }${internalTypeStr }内参</span>
    	</h1>
        <p class="warn">正在${flagStr }${internalTypeStr }内参</p>
        <div class="theme">
        	<c:if test="${flag==0 }">
        		<form id="internalForm" action="<%=contextPath %>/back/internal/addInternal.htm">
        	</c:if>
        	<c:if test="${flag==1 }">
        		<form id="internalForm" action="<%=contextPath %>/back/internal/updateInternal.htm">
        		<input type="hidden" name="internalDto.internalId" value="${internalDto.internalId }" />
        	</c:if>
        		<input type="hidden" name="internalDto.internalType" value="${internalType }"/>
        		<input type="hidden" id="hiddenInternalType" name="internalType" value="${internalType }"/>
	        	<h2><p>${flagStr }${internalTypeStr }内参</p></h2>
	            <div class="choose_con">
	            	<ul class="cho2">
	            		<li class="tit"><p>标题</p></li>
	                	<li class="con"><b><input class="view" id="title" maxlength="25" name="internalDto.title" type="text" value="${internalDto.title }" /></b><span>标题长度最多25字</span></li>
	            	</ul>
	                <ul class="cho3">
	            		<li class="tit"><p>内容简介</p></li>
	                	<li class="con"><textarea id="content" maxlength="100" name="internalDto.content" />${internalDto.content }</textarea><p>内容简介长度最多100字</p></li>
	            	</ul>
	                <ul class="cho3">
	            		<li class="tit"><p>专题名称</p></li>
	                	<li class="con">
	                		<i>
	                			<b><em>①</em><input id="special1" name="internalDto.special" type="text" value="${internalDto.specialArray[0] }" /></b>
	                			<b><em>②</em><input id="special2" name="internalDto.special" type="text" value="${internalDto.specialArray[1] }" /></b>
	                			<b><em>③</em><input id="special3" name="internalDto.special" type="text" value="${internalDto.specialArray[2] }" /></b>
	                			<b><em>④</em><input id="special4" name="internalDto.special" type="text" value="${internalDto.specialArray[3] }" /></b>
	                			<b><em>⑤</em><input id="special5" name="internalDto.special" type="text" value="${internalDto.specialArray[4] }" /></b>
	                			<b><em>⑥</em><input id="special6" name="internalDto.special" type="text" value="${internalDto.specialArray[5] }" /></b>
	                		</i>
	                	</li>
	            	</ul>
	                <ul class="cho1">
	            		<li class="tit"><p>图片①</p></li>
	                	<li class="con">
	                    	<p>
	                    		<span>
	                    			<c:if test="${internalDto.fileIdArray[0]!=null }">
		                    			<img src="<%=contextPath %>/download/file/show.htm?id=${internalDto.fileIdArray[0] }" width="240" height="184"/>
	                    			</c:if>
	                    		</span>
	                    		<i>(大小945*高度不限)</i>
	                    	</p>
	                        <a href="javascript:;" class="file-up">选择文件
								<input id="file1" type="file" name="file"/>
							</a>
	                        <input id="fileId1" type="hidden" name="internalDto.fileId" value="${internalDto.fileIdArray[0] }"></input>
	                    </li>
	            	</ul>
	                <ul class="cho1">
	            		<li class="tit"><p>图片②</p></li>
	                	<li class="con">
	                    	<p>
	                    		<span>
	                    			<c:if test="${internalDto.fileIdArray[1]!=null }">
		                    			<img src="<%=contextPath %>/download/file/show.htm?id=${internalDto.fileIdArray[1] }" width="240" height="184"/>
	                    			</c:if>
	                    		</span>
	                    		<i>(大小945*高度不限)</i>
	                    	</p>
	                        <a href="javascript:;" class="file-up">选择文件
								<input id="file2" type="file" name="file"/>
							</a>
	                        <input id="fileId2" type="hidden" name="internalDto.fileId" value="${internalDto.fileIdArray[1] }"></input>
	                    </li>
	            	</ul>
	                <ul class="cho1">
	            		<li class="tit"><p>图片③</p></li>
	                	<li class="con">
	                    	<p>
	                    		<span>
	                    			<c:if test="${internalDto.fileIdArray[2]!=null }">
		                    			<img src="<%=contextPath %>/download/file/show.htm?id=${internalDto.fileIdArray[2] }" width="240" height="184"/>
	                    			</c:if>
	                    		</span>
	                    		<i>(大小945*高度不限)</i>
	                    	</p>
	                        <a href="javascript:;" class="file-up">选择文件
								<input id="file3" type="file" name="file"/>
							</a>
	                        <input id="fileId3" type="hidden" name="internalDto.fileId" value="${internalDto.fileIdArray[2] }"></input>
	                    </li>
	            	</ul>
	                <ul class="cho1">
	            		<li class="tit"><p>图片④</p></li>
	                	<li class="con">
	                    	<p>
	                    		<span>
	                    			<c:if test="${internalDto.fileIdArray[3]!=null }">
		                    			<img src="<%=contextPath %>/download/file/show.htm?id=${internalDto.fileIdArray[3] }" width="240" height="184"/>
	                    			</c:if>
	                    		</span>
	                    		<i>(大小945*高度不限)</i>
	                    	</p>
	                        <a href="javascript:;" class="file-up">选择文件
								<input id="file4" type="file" name="file"/>
							</a>
	                        <input id="fileId4" type="hidden" name="internalDto.fileId" value="${internalDto.fileIdArray[3] }"></input>
	                    </li>
	            	</ul>
	                <ul class="cho1">
	            		<li class="tit"><p>图片⑤</p></li>
	                	<li class="con">
	                    	<p>
	                    		<span>
	                    			<c:if test="${internalDto.fileIdArray[4]!=null }">
		                    			<img src="<%=contextPath %>/download/file/show.htm?id=${internalDto.fileIdArray[4] }" width="240" height="184"/>
	                    			</c:if>
	                    		</span>
	                    		<i>(大小945*高度不限)</i>
	                    	</p>
	                        <a href="javascript:;" class="file-up">选择文件
								<input id="file5" type="file" name="file"/>
							</a>
	                        <input id="fileId5" type="hidden" name="internalDto.fileId" value="${internalDto.fileIdArray[4] }"></input>
	                    </li>
	            	</ul>
	                <ul class="cho1">
	            		<li class="tit"><p>图片⑥</p></li>
	                	<li class="con">
	                    	<p>
	                    		<span>
	                    			<c:if test="${internalDto.fileIdArray[5]!=null }">
		                    			<img src="<%=contextPath %>/download/file/show.htm?id=${internalDto.fileIdArray[5] }" width="240" height="184"/>
	                    			</c:if>
	                    		</span>
	                    		<i>(大小945*高度不限)</i>
	                    	</p>
	                        <a href="javascript:;" class="file-up">选择文件
								<input id="file6" type="file" name="file"/>
							</a>
	                        <input id="fileId6" type="hidden" name="internalDto.fileId" value="${internalDto.fileIdArray[5] }"></input>
	                    </li>
	            	</ul>
	        	</div>
        	</form>
            <div class="butt">
            	<a class="finish" href="javascript:void(0);">完成</a>
            	<a class="cancel" href="<%= contextPath %>/back/internal/toInternalListPage.htm?internalType=${internalType}">取消</a>
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