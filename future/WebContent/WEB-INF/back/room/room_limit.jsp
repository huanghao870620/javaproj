<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>磊丹财经——普通房间管理</title>
<link href="<%=request.getContextPath() %>/res/back/css/reset.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/res/back/css/system.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/res/back/css/lecture-choose02.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/res/back/css/lecture-choose02_add.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/res/back/css/ordroom_limit.css" type="text/css" rel="stylesheet" />
</head>

<body>
<div class="header">
	<ul class="header_left">
    	<li class="widlogo"><img src="images/logo_white.png" /></li>
        <li><a class="abg" href="background_system.html">首页</a></li>
        <li><a href="#">刷新</a></li>
    </ul>
    <span class="header_right">Hi,管理员</span>
</div>
<div class="contain">
	<ul class="subnav">
    	<li><a href="background_system.html">管理员</a></li>
        <li><a href="#">用户管理</a></li>
        <li><a href="background_customer.html" >客户管理</a></li>
        <li><a href="background_lecturer.html">讲师管理</a></li>
        <li><a href="background_secret.html">助理秘书</a></li>
        <li><a href="#">客服</a></li>    
        <span class="cur">普通房间</span>
        <li><a href="#">实盘房间</a></li>
        <li><a href="#">专题房间</a></li>
    </ul>
    <div class="contain_main">
    	<h1 class="map"><a href="background_system.html">首页</a>&gt;<a href="background_ordroom.html">普通房间</a>&gt;<a href="background_ordroom_limit.html">房间内容权限设置</a></h1>
        <p class="warn">服务权限表是根据会员等级下的服务进行选取的。</p>
        <div class="theme">
        	<h2><p>房间内容权限设置</p><!--<a href="#">高级课件添加</a>--></h2>
            <div class="choose_con">
            	<ul class="cho2">
            		<li class="tit"><p>进入级别</p></li>
                	<li class="con">
                    	<select>
                        	<option value="" selected="selected">游客</option>
                            <option value="">普通</option>
                            <option value="">白银</option>
                            <option value="">黄金</option>
                            <option value="">铂金</option>
                            <option value="">VIP</option>
                            <option value="">钻石</option>
                            <option value="">至尊</option>
                        </select>
                    </li>
            	</ul>
            	<%--
            	<ul class="cho3">
            		<li class="tit"><p>进入范围</p></li>
                	<li class="con">
                    	<select>
                        	<option value="" selected="selected">允许相同房间级别学员进入</option>
                        	<option value="">允许高于和等于房间级别学员进入</option>
                        </select>
                    </li>
            	</ul>
            	 --%>
            	<%--
                <ul class="cho2">
            		<li class="tit"><p>允许游客进入</p></li>
                	<li class="con">
                    	<select>
                        	<option value="" selected="selected">允许</option>
                            <option value="">不允许</option>
                        </select>
                    </li>
            	</ul>
            	 --%>
                <ul class="cho2">
            		<li class="tit"><p>允许游客发言</p></li>
                	<li class="con">
                    	<select>
                        	<option value="" selected="selected">允许</option>
                            <option value="">不允许</option>   
                        </select>
                    </li>
            	</ul>
               
            	<%--
                <ul class="cho1">
            		<li class="tit"><p>右下角二维码</p></li>
                	<li class="con">
                    	<p><span></span><i>(大小86*86)</i></p>
                        <a href="#">选择图片</a>
                    </li>
            	</ul>
            	 --%>
               <ul class="cho2">
            		<li class="tit"><p>房间</p></li>
                	<li class="con">
                    	<select>
                        	<option value="" selected="selected">房间一</option>
                            <option value="">房间二</option> 
                            <option value="">房间三</option> 
                            <option value="">房间四</option>   
                        </select>
                    </li>
                 </ul>
                 <%--
                 <ul class="cho2">
            		<li class="tit"><p>允许游客发言</p></li>
                	<li class="con"><input class="fay" type="text" value="分析师所发表言论只代表个人观点，仅供参考，投资有风险，入市需谨慎。为保证浏览效果，建议使用最高屏幕分辨率。" /></li>
            	</ul>
                  --%>
        	</div>
            <div class="butt">
            	<a class="finish" href="#">完成</a><a class="cancel" href="background_ordroom.html">取消</a>
            </div>
    </div>
</div>
</body>
</html>
