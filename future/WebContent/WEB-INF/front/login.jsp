<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="utf-8" />
<title>磊丹财经直播室登录</title>
<link href="<%=contextPath %>/res/front/css/reset.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/front/css/loginlast.css" type="text/css" rel="stylesheet" />
<link href="<%=contextPath %>/res/front/css/loginfirst.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/res/js/general/login.js"></script>
<script type="text/javascript">
     var contextPath = '<%=contextPath %>';
</script>
</head>
<body>
<div class="login_last">
	<form id="login">
		<img class="logo" src="<%=request.getContextPath() %>/res/images/logo.png" />
		<img class="title" src="<%=request.getContextPath() %>/res/images/title_first.png" />
		<input name="userDto.userName" class="user" type="text" placeholder="用户名" />
		<input name="userDto.password" class="key" type="password"  placeholder="密&nbsp;&nbsp;码" />
		<div class="yanzm">
			<input id="checkCode" name="userDto.checkCode" type="text" placeholder="验证码"  />
			<p>
				<img id="geneCode" src="<%=request.getContextPath() %>/general/crateimage/geneCode.htm"/>
				<a class="freshCode" href="javascript:;">
					<img id="geneCode" src="<%=request.getContextPath() %>/res/images/refresh.png" />
				</a>
			</p>
		</div>
	</form>
	<a class="login-button" href="javascript:;">登录</a>
	<p class="quest">如果账户相关问题，请点击在线客服</p>
	<div class="qqkf">
		<a class="qleft" href="#">
			<img src="<%=request.getContextPath() %>/res/images/qqkf.png" />
		</a>
		<a class="qright" href="#">
	 		<img src="<%=request.getContextPath() %>/res/images/qqkf.png" />
	 	</a>
	</div>
</div>
</body>
</html>
