<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
	String contextPath = request.getContextPath();
%>  
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/res/front/css/level2.css">
<title>交易内参--专题格式</title>
</head>
<body>
<div class="vip-wrap">
	<div class="viewpot-top viewpot-top-jync">
	    <div class="viewpot-top-con clearfix">
	        <h1 class="fl ">VIP专属内参</h1>
	        <span class="calendar-box fl"><i class="calendar-icon"></i><fmt:formatDate value="${internalDto.createTime }" pattern="yyyy-MM-dd"/></span>
	        <span class="vip-jync-logotex  fr"></span>
	    </div>
	</div>
	<div class="viewpot-con">
		<c:forEach var="internal" varStatus="status" items="${internalDto.specialArray }">
			<c:if test="${internal!=null }">
			    <div class="viewpot-list" id="xhyd${status.index }">
			        <h2 class="jync-bg-position"><i class="vip-icon01"></i>${internal }</h2>
					<div class="viewpot-con-item01">
					    <div class="viewpot-img-box">
					    <c:if test="${internalDto.fileIdArray[status.index]!=null }">
						    <img src="<%=contextPath %>/download/file/show.htm?id=${internalDto.fileIdArray[status.index] }">
					    </c:if>
					    </div>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
    <!--右侧导航开始-->
    <ul class="vip-nav-fix jync-nav">
    	<c:forEach var="internal" varStatus="status" items="${internalDto.specialArray }">
			<li>
			    <a  href="#xhyd${status.index }">${internal }</a>
			</li>
		</c:forEach>
		<li class="go_top">
		    <a href="#">TOP<i class="go_top_icon"></i></a>
		</li>
		<li>
		    <a href="#"><i class="feedback-icon"></i><br>需求反馈</a>
		</li>
    </ul>
</div>
<!--右侧导航结束-->
<script type="text/javascript" src="<%=contextPath %>/res/js/front/jquery-1.11.3.js"></script>
<script type="text/javascript" src="<%=contextPath %>/res/js/front/level2.js"></script>
</body>
</html>