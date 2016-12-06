    <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@page import="java.net.InetAddress"%>
            <%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>磊丹财经直播室登录</title>
    <!--<link rel="stylesheet" type="text/css" href="D:/project/future/WebContent/res/front/css/index.css">-->
    <link rel="stylesheet" type="text/css" href="/res/front/css/index.css">
    <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/res/js/general/login.js"></script>
   <script type="text/javascript">
     var contextPath = '<%=contextPath %>';
</script>
</head>
<body>
<!--<div class="bg login-wrap">-->
    <!--<div class="user-login position-middle">-->
        <!--<div class="login-con position-con">-->
        <div class="login-wrap" style="width: 334px; height: 422px;">
            <div class="clearfix login-top">
                <h1 class="fl">用户登陆</h1>
                <p class="fr">如果还没有账号<a  href="tencent://message/?uin=800015047&amp;Site=www.leidan18.com&amp;Menu=yes">联系在线客服</a></p>
            </div>
            <form method="post" action="#" name="#" id="login">
                <div class="input_box margin-top32 clearfix">
                    <span class="text-icon fl user-icon"></span>
                    <input class="tex fl" type="text" placeholder="请输入注册账号" name="userDto.userName">
                </div>
                <div class="input_box margin-top20 clearfix">
                    <span class="text-icon fl password-icon"></span>
                    <input class="tex fl" type="password" placeholder="请输入账号密码" name="userDto.password">
                </div>
                <div class="input_box margin-top20 codes-box clearfix">
                    <input class="tex fl" type="text" id="checkCode" name="userDto.checkCode" placeholder="请输入验证码">
                    <p class=" fl">
                        <!-- <img src="<%=request.getContextPath() %>/res/images/yanzm.jpg"/>
                        <a href="javascript:;">
                            <img src="<%=request.getContextPath() %>/res/images/refresh.png" />
                        </a> -->
                        <img id="geneCode" src="/general/crateimage/geneCode.htm"/>
                        <a class="freshCode" href="javascript:;">
                            <img id="geneCode" src="/res/images/refresh.png" />
                        </a>
                    </p>
                </div>
                <a href="#" class="login-btn margin-top17">立即登录</a>
            </form>
            <div class="clearfix password-forgot">
                <p class="fl"><i class="security"></i></p>
            </div>
            <div class="kf">
                <p class="margin-bottom14">请点击在线客户，免费注册账号</p>
                <a  href="tencent://message/?uin=800015047&amp;Site=www.leidan18.com&amp;Menu=yes" class="kf-btn">在线客服</a>
                <a href="tencent://message/?uin=800015047&amp;Site=www.leidan18.com&amp;Menu=yes" class="kf-btn">在线客服</a>
            </div>
        <!--</div>-->
    <!--</div>-->
<!--</div>-->
        </div>

</body>
</html>