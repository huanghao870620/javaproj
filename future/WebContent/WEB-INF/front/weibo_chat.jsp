<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.net.InetAddress"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head lang="en">
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>磊丹财经-进入房间</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/front/css/tabswitch.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/front/css/index.css">
    <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/res/js/base.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/front/front_index/weibo_chat.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/front/lrtk.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/front/scroll.js"></script>
    
    <script type="text/javascript">
      var ip='${ip}';
      var server_ip = '<%=InetAddress.getLocalHost().getHostAddress()%>'; 
      var webSocket ;
      var  roleId_current ='${roleId}';
      var  customerId_current = '${customer.id}';
      var  privateChat =false;
      var  teacherUserId;
     // privateChat:privateChat
        $(function(){
        	
            $('.myscroll').myScroll({
                speed: 50, //数值越大，速度越慢
                rowHeight: 47 //li的高度
            });
            //  微博群聊websocket 
            window.webSocket =  new WebSocket('ws://'+ server_ip + ':8080/weibosocket');  
            webSocket.onerror = function(event) {
              console.log(2);
            };  
            webSocket.onopen = function(event) {
            };  
            webSocket.onmessage = function(event) {  
            	 var data = event.data;
            	 data= $.parseJSON(data);  
            	 var msg = data.msg;
            	 var customerName =data.customerName;
            	 var  createTime = data.createTime;
            	 var   roleId_msg = data.roleId;
            	 var   customerId_msg =data.customerId;
            	 var node = new chatDom({
            		 content : msg,
            		 basepath : '<%=basePath%>',
            		 customerName:customerName,
            		 createTime : createTime,
            		 roleId_msg:roleId_msg,
            		 roleId_current:roleId_current,
            		 customerId_msg:customerId_msg,
            		 customerId_current:customerId_current
            		
            			 }
            	 ).str2node();
            	 $('#msg_content').append(node);
            };  
            
            //  私人聊天websocket
            window.privatewebSocket =  new WebSocket('ws://'+ server_ip + ':8080/privatesocket');  
            privatewebSocket.onerror = function(event) {
              console.log(2);
            };  
            privatewebSocket.onopen = function(event) {
            };  
            privatewebSocket.onmessage = function(event) {  
            	
            	privateChat = true;
            	document.getElementById("msg_content").setAttribute("style", "display:none");
            	document.getElementById("private_msg_content").setAttribute("style", "display:block");
            	
            	
           	 var data = event.data;
        	 data= $.parseJSON(data);  
        	 var msg = data.msg;
        	 var customerName =data.customerName;
        	 var  createTime = data.createTime;
        	 var node = new chatPrivateDom({
        		 content : msg,
        		 basepath : '<%=basePath%>',
        		 customerName:customerName,
        		 createTime : createTime,
        			 }
        	 ).str2node();
            $('#private_msg_content').append(node);
            };  
            
            $('#writeMsg').keydown(function(e){
            	 if(e.keyCode==13){
            	          send(privateChat);
            	 }
            });
        });
        function send(privateChat_var){
        	 var msg = $('#writeMsg').val();
        	  if(msg == ""){
        		  alert("不能发送空消息!");
        		  return ;
        	  }
        	  if(!privateChat_var)
              {
        	   var param = {ip : window.ip, msg : msg};
        	   var toStr = JSON.stringify(param)
        	   window.webSocket.send(toStr);
              }
        	  else{
        		  var param = {ip : window.ip, msg : msg,teacherUserId:teacherUserId};
        	       	 var toStr = JSON.stringify(param)
        	       	 window.privatewebSocket.send(toStr);
        	  }
        	  
        	  $('#writeMsg').val("");
        }
        
        
        function  enterPrivateChat(teacherUserId_var){
        	
        	teacherUserId = teacherUserId_var;
        	//alert(teacherUserId )
        	privateChat = true;
        	document.getElementById("msg_content").setAttribute("style", "display:none");
        	document.getElementById("private_msg_content").setAttribute("style", "display:block");
        	  
        }
        
        function  enterGroupChat(){
        	
        	document.getElementById("msg_content").setAttribute("style", "display:block");
        	document.getElementById("private_msg_content").setAttribute("style", "display:none");
        	privateChat = false;
        }
        
        
    </script>
</head>
<body>
<img src="<%=request.getContextPath() %>/res/images/backbg.jpg" class="bg-yx-pic">
<div class="wrap">
<!-- <div >
<ul >
<li tabindex="0"   title="群聊" style="width: 91px;" id="tabindex_group">群聊
</li>
<li tabindex="0"  title="单聊" style="width: 91px;" id="tabindex_privatechat">单聊
</li>
</ul>
</div> -->
<div class="menu">
        <ul>
            <li><a href="javascript:enterGroupChat();">群聊</a></li>
            <li><a href="javascript:enterPrivateChat(teacherUserId);">单聊1</a></li>
            <li><a href="javascript:void(0);">单聊2</a></li>
            <li><a href="javascript:void(0);">单聊3</a></li>
        </ul>
    </div>
    <div class="wrap_fluid">
        <div class="middle clearfix">
            <div class="unit-right fl">
                <div class="chart-box fl">
                    <div class="chart">
                        <ul class="info" id="msg_content" style='display: block;'>
                           
                        </ul>
                        
                        <ul class="info" id="private_msg_content"  style='display: none;'>
                           
                        </ul>
                        
                        
                    </div>
                    <div class="message">
                        <div class="send-message">
                            <p class="message-box clearfix">
                                <a href="#" class="face"><i class="face-icon"></i>表情</a>
                                <a href="#" class="clear clear-a"><i class="clear-icon"></i>清屏</a>
                            </p>
                            <form method="post" action="#" name="#">
                                <textarea id="writeMsg"></textarea>
                                <a href="javascript: window.send(privateChat)" class="send-btn"></a>
                                <a href="#" class="small-send-btn">发送</a>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="bottom-text ">
        <p class="bottom-text-p">分析师所发表言论只代表个人观点，仅供参考，投资有风险，入市需谨慎。为保证浏览效果，建议使用最高屏幕分辨率。</p>
    </div>
</div>
</body>
</html>