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
<link href="<%=request.getContextPath() %>/res/back/css/customer-add.css" type="text/css" rel="stylesheet" />
<%@include file="/jsp/back/inc.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/res/js/back/customer/customer_add.js"></script>
<script type="text/javascript">
	var contextPath = '<%=contextPath %>';
	var flag = ${flag};
</script>
</head>
<body>
<%@include file="/jsp/back/header.jsp" %>
<div class="contain">
	<%@include file="/jsp/back/menu/menu.jsp" %>
	<div class="contain_main">
    	<h1 class="map">
    		<a href="background_system.html">首页</a>&gt;
    		<a href="<%=contextPath %>/back/customer/list.htm">客户管理</a>&gt;
    		<span>客户${operation }</span>
    	</h1>
        <p class="warn">正在进行客户帐号${operation }</p>
        <s:actionerror />
        <form id="customerForm">
        	<input name="customer.userId" value="${customer.userId }" type="hidden" />
	        <div class="theme">
	        	<h2><p>客户帐号${operation }</p></h2>
	            <ul class="the_main">
	            	<li>
	            		<p>账户</p>
	            		<c:if test="${flag==0 }">
		            		<input id="userName" name="customer.userName" type="text" value="" />
		            		<span>账户只能有数字和字母组成，并由字母开头，例：A1011</span>
	            		</c:if>
	            		<c:if test="${flag!=0 }">
	            			<input id="userName" name="customer.userName" type="text" disabled="disabled" value="${customer.userName }" />
	            		</c:if>
	            	</li>
	                <li><p>姓名</p><input id="name" name="customer.name" type="text" value="${customer.name }" /></li>
	                <li><p>实盘账户</p><input id="firmOffer" name="customer.firmOfferAccount" type="text" value="${customer.firmOfferAccount }" /></li>
	                <li><p>会员等级</p>
	                	<select id="levelId" name="customer.levelId">
	                	   <c:forEach var="level" items="${levels}">
	                	   		<c:if test="${level.id == customer.levelId }">
		                	       <option selected="selected" value="${level.id}">${level.levelName }</option>
	                	   		</c:if>
	                	   		<c:if test="${level.id != customer.levelId }">
	                	   			<option value="${level.id}">${level.levelName }</option>
	                	   		</c:if>
	                	   </c:forEach>
						</select>
	                </li>
	            </ul>
	            <div class="butt">
	            	<a class="finish" href="javascript:;">完成</a>
	            	<a class="cancel" href="javascript:;">取消</a>
	            </div>
	        </div>
        </form>
    </div>
</div>
</body>
</html>
