<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>磊丹财经后台登录</title>
<link href="<%=request.getContextPath() %>/res/css/reset.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/res/css/admini.css" type="text/css" rel="stylesheet">
</head>     

<body>
<div class="box_bg"></div>
<div class="login">
	<div class="login-tit">
    	<h1>磊丹财经后台登录</h1>
    </div>
    <div class="login-main">
    	<label class="login_t">用户名：<input type="text"></label>
        <label class="login_t">密 &nbsp;&nbsp;码：<input type="password"></label>
        <label class="login_t"><span class="fir">验证码：<input class="yanz" type="text"></span>
        <a href="#">
        <img class="yanz_pic" src="<%=request.getContextPath() %>/res/images/yanz_s.png">
        <img class="refresh_s" src="<%=request.getContextPath() %>/res/images/refresh_s.png"></a></label>
        <p><input type="checkbox"><i>记住密码</i></p>
        <label class="btn-all"><a href="back.html" class="btn_dl">登录</a><a href="#" class="btn_qx">取消</a></label>
    </div>
</div>
</body>
</html>
