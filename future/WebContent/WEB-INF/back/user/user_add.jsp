<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="utf-8" />
<title>磊丹财经——后台客户管理</title>
<link href="<%=contextPath %>/res/back/css/customer.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/back/css/customer-add.css" type="text/css" rel="stylesheet" />
<%@include file="../../../jsp/back/inc.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/res/js/back/user/user_add.js"></script>
<script type="text/javascript" src="<%=contextPath %>/res/js/back/user/uploadImage.js"></script>
<script src="<%=contextPath %>/res/js/ajaxFileUpload.js" type="text/javascript"></script>
<script type="text/javascript">
	var contextPath = '<%=contextPath %>';
	var flag = ${flag};
</script>
</head>
<body>
<%@include file="../../../jsp/back/header.jsp" %>、
<div class="contain">
	<%@include file="../../../jsp/back/menu/menu.jsp" %>
    <div class="contain_main">
    	<h1 class="map">
    		<a href="background_system.html">首页</a>&gt;
    		<a href="<%=contextPath %>/back/user/toList.htm">用户管理</a>&gt;
    		<span>账号${operation }</span>
    	</h1>
        <p class="warn">正在进行用户帐号${operation }</p>
        <div class="theme">
        	<h2><p>用户帐号${operation }</p></h2>
       		<form id="addForm">
       			<input name="userDto.userId" value="${userDto.userId }" type="hidden" />
	            <ul class="the_main">
	            	<li>
	            		<p>账户</p>
	            		<c:if test="${flag==0 }">
	            			<input id="userName" name="userDto.userName" type="text" value="${userDto.userName }" />
	            			<span>账户只能有数字和字母组成</span>
	            		</c:if>
	            		<c:if test="${flag!=0 }">
	            			<input id="userName" name="userDto.userName" type="text" disabled="disabled" value="${userDto.userName }"/>
	            		</c:if>
	            	</li>
	            	<c:if test="${flag==0 }">
		                <li><p>密码</p><input id="password" name="userDto.password" type="password" value="${userDto.password }" /></li>
		                <li><p>确认密码</p><input id="dupPassword" name="userDto.dupPassword"  type="password" value="${userDto.password }" /></li>
	                </c:if>
	                <li><p>姓名</p><input id="name" name="userDto.name" type="text" value="${userDto.name }" /></li>
	                <li><p>Email</p><input id="email" name="userDto.email" type="email" value="${userDto.email }" /></li>
	                <li><p>选择角色</p>
	                 	<c:forEach items="${roles}" var="role">
	                 		<c:if test="${role.roleid!=5 }">
	                 			<input id="${role.roleid }" name="role" type="checkbox" value="${role.roleid }"
		                 			<c:forEach items="${userDto.roleList }" var="roleList">
		                 				<c:if test="${role.roleid == roleList.roleid }">
					                  		 checked="checked"
		                 				</c:if>
		                 			</c:forEach>
	                 			/>
		                  		<label for="${role.roleid }">${role.rolename }</label>
	                  		</c:if>
	                 	</c:forEach>
   				 	</li>
	            	
	            </ul>
	           
	            
	              <ul class="cho1">
	            		<li class="tit"><p>头像</p></li>
	                	<li class="con">
	                    	<p>
		                    	<span>
			                    	<c:if test="${userDto.CFileId!=null}">
			                    		<img src="<%=contextPath %>/download/file/show.htm?id=${userDto.CFileId}" width="45" height="40"/>
			                    	</c:if>
		                    	</span>
		                    	<i>(大小45*40)</i>
	                    	</p>
							<a href="javascript:;" class="file-up">选择文件
								<input id="file" type="file" name="file"/>
							</a>
						   <input  type="hidden" id="cFileId" name="userDto.CFileId" value="${userDto.CFileId}"></input>
	                    </li>
	            	</ul>
	            
	          
        	</form>
            <div class="butt">
            	<a class="finish" href="javascript:;">完成</a>
            	<a class="cancel" href="javascript:;">取消</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
