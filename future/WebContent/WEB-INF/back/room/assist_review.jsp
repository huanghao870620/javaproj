<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.net.InetAddress"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../jsp/back/inc.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>磊丹财经——审核助理</title>
<link href="<%=request.getContextPath() %>/res/back/css/check-room.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/res/js/general/listener/listener.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/res/js/back/room/assist_review.js"></script>

<script type="text/javascript">
var webSocket;
var server_ip = '${ip}';
 $(function(){
	 
	 new Listener({
		   url : '<%=request.getContextPath()%>/back/customer/getAllCustomer.htm',
		   node : $('#userid'),
		   deal : function(){
			   	 var liste = this;
				 $.post(this.url, {},  function(data){
					 var str = "";
					   data = JSON.parse(data);
					   for(var i=0; i<data.length; i++){
						   str += '<p><i></i><b>'+ data[i].name + '</b></p>';
					   }
					  liste.node.html(str);
				 },'text');
			} 
	   }).listen();
	 
	 
	 
	 
	 window.webSocket =  new WebSocket('ws://'+ server_ip + ':8080/'+ '<%=request.getContextPath()%>' + '/auditWebsocket');
	 
	 webSocket.onerror = function(event) {
         console.log(2);
       };  



       webSocket.onopen = function(event) {
       };  

       webSocket.onmessage = function(event) {
       	 var msg = event.data;
       	 var node = new str2Dom({
       		 content : msg,
       		 basepath : '<%=request.getContextPath()%>'
       			 }
       	 ).str2node();
       	 $('#msg_content').append(node);
       	 var msgContent = document.getElementById('msg_content');
    	 msgContent.scrollTop = msgContent.scrollHeight;
       };  
 });
 
 
 function auditMsg(msgid, node){
	  //console.log(node);
	 $.post('<%=request.getContextPath()%>/back/room/auditMessage.htm',{msgid: msgid, t: new Date },function(data){
		  data = JSON.parse(data);
		  if(data.success){
			  $(node).remove();
		  }
	 },'text');
 }
 
 function initTab(){
	 var oChoseBox = document.getElementById('chose-box');
	    var aP = oChoseBox.getElementsByTagName('p');
	    var oThemeBox = document.getElementById('theme-box');
	    var aDiv = oThemeBox.getElementsByClassName('theme_mr_b');
	    for(var i=0;i<aP.length;i++){
	        aP[i].index=i;
	        aP[i].onclick=function(){
	            for(var j=0;j<aP.length;j++){
	                aP[j].className='';
	                aDiv[j].style.display='none';
	            }
	            this.className='active';
	            aDiv[this.index].style.display='block';
	        }
	    }
 }

</script>

</head>

<body>
<%@include file="../../../jsp/back/header.jsp" %>
<div class="contain">
	<%@include file="../../../jsp/back/menu/menu.jsp" %>
    <div class="contain_main">
    	<h1 class="map"><a href="background_system.html">首页</a>&gt;<a href="background_system.html">用户管理</a>&gt;<a href="background_secret.html">普通管理员</a>&gt;<a href="background_check.html">文字审核助理</a></h1>
       <!-- <p class="warn">直播室文字审核助理添加，管理。</p>-->
        <div class="theme">
        	<h2><p>磊丹财经普通房间</p><a href="background_check.html">退出</a></h2>
            <div class="theme-main">
               <div class="theme_ml" id="chose-box">
                    <a>在线学员</a>
                    <div id="userid">
                    </div>
               </div>
               <div class="theme_mr">
               		<div class="theme_mr_t">
                    	<a>聊天室</a><p><b><input type="radio" name="radiobutton" value="radiobutton" checked> 发言审核</b><b> <input type="radio" name="radiobutton" value="radiobutton"> 发言不审核</b></p>
                    </div>
                    <!--聊天开始-->
                    <div id="theme-box">
                       <div class="theme_mr_b" id="msg_content" style="overflow-y:auto;overflow-x:hidden;height:650px;"></div>
                   	</div>
                    <!--聊天结束-->
               </div>
           </div>
        </div>
    </div>
</div>

<script type="text/javascript">
/*
    
    */
</script>

</body>
</html>
