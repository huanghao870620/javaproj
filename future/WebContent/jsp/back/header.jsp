<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div class="header">
	<ul class="header_left">
    	<li class="widlogo"><img src="<%=request.getContextPath() %>/res/images/logo_white.png" /></li>
    </ul>
    <a href="<%=request.getContextPath() %>/index/frontIndex.htm" class="exit-btn">返回</a>
    <a href="<%=request.getContextPath() %>/login/logout.htm" class="exit-btn">注销</a>
    <span class="header_right">Hi,${sessionScope.frontLoginUser.name }</span>
</div>
