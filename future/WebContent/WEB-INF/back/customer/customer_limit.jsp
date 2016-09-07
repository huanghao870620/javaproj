<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="utf-8" />
<title>磊丹财经——后台客户管理</title>
<link href="<%=request.getContextPath() %>/res/back/css/reset.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/res/back/css\customer-add.css" type="text/css" rel="stylesheet" />
<%@include file="/jsp/back/inc.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/res/js/back/customer/customer_limit.js"></script>
<script type="text/javascript">
	var contextPath = '<%=contextPath %>';
	var limitArrString = ${limitArrString};
	
</script>
</head>
<body>
<%@include file="/jsp/back/header.jsp" %>
<div class="contain">
	<%@include file="/jsp/back/menu/menu.jsp" %>
    <div class="contain_main">
    	<h1 class="map"><a href="">客户管理</a>&gt;<a href="">服务权限</a></h1>
        <p class="warn">正在进行客户权限维护</p>
        <div class="theme">
        	<h2><p>服务权限表</p><!--<a href="#">添加、编辑、马甲权限</a>--></h2>
            <ul class="the_main">
                <li><p>会员等级</p>
                	<select name="memberlevel"  >
	    				<option value="0" selected>游客</option>
	    				<option value="1"  >注册会员</option>
	    				<option value="2"  >黄金会员</option>
	    				<option value="3"  >铂金会员</option>
                        <option value="4"  >钻石会员</option>
                        <option value="5"  >至尊会员</option>
					</select>
                </li>
                <li class="check"><p>服务权限表</p>
                <i><input type="checkbox" id="viewPoint" />磊丹观点</i>
                <i><input type="checkbox" id="inTransaction" />交易内参</i>
                <i><input type="checkbox" id="aROOM" />实盘房间</i>
                </li>
                 <li class="check"><p></p> 
                </li>
                  <li class="check"><p>基础课件权限表</p> 
                   <c:forEach items="${courseWareList}" var="courseWare">
	                   <c:if test="${courseWare.courseWareType == 1 }">
	                   <i><input onClick="courseWareChange(this)"  type="checkbox" id="${courseWare.courseWareId }" />${courseWare.courseName }</i>
	                   </c:if>
	                </c:forEach>
                   </li> 
                <li class="check"><p></p> 
                </li>
                  <li class="check"><p>高级课件权限表</p> 
                  <c:forEach items="${courseWareList}" var="courseWare">
	                   <c:if test="${courseWare.courseWareType == 2 }">
	                   <i><input onClick="courseWareChange(this)" type="checkbox" id="${courseWare.courseWareId }" />${courseWare.courseName }</i>
	                   </c:if>
	                </c:forEach>
                  </li> 
            </ul>
            <div class="butt">
            	<a class="finish" href="#">完成</a><a class="cancel" href="#" >取消</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
