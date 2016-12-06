<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>5秒钟禁言弹窗</title>
    <link rel="stylesheet" href="/res/front/css/index.css">
    <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/front/front_index/index_chat.js"></script>
    
   <script type="text/javascript">
   var contextPath = '<%=contextPath %>';
   var timeOut;
       function startCountDown(){
    	   window.clearTimeout(timeOut);
    	  jump();
           
        }; 
        function jump() { 	
        	timeOut=  window.setTimeout(function(){ 
            	$("#numberCount").html( $("#numberCount").html()-1); 
                if($("#numberCount").html() > 0) { 
                    jump(); 
                } else { 
                	$("#wait").val("ok"); 
                 parent.document.getElementById("online-10-wrap").style.display = "none";
                } 
            }, 1000); 
        } 
        
</script> 
    
</head>
<body >


<div class="online-10">
    <div class="online-tit">
        <p>游客的发言时间间隔为8秒，还有<span  id="numberCount" >8</span>秒！</p>
        <p>游客请联系在线客服，领取马甲，享受VIP服务，发言不受限制!</p>
         <input style="display:none"  type="text" name="wait" id="wait" value="ok" />
    </div>
    <div class="online-qq">
        <a href="tencent://message/?uin=800015047&amp;Site=www.leidan18.com&amp;Menu=yes" class="ml15"><img src="/res/images/online-qq.png"></a>
        <a href="tencent://message/?uin=800015047&amp;Site=www.leidan18.com&amp;Menu=yes"><img src="/res/images/online-qq.png"></a>
        <a href="tencent://message/?uin=800015047&amp;Site=www.leidan18.com&amp;Menu=yes"><img src="/res/images/online-qq.png"></a>
        <a href="tencent://message/?uin=800015047&amp;Site=www.leidan18.com&amp;Menu=yes"><img src="/res/images/online-qq.png"></a>
        <a href="tencent://message/?uin=800015047&amp;Site=www.leidan18.com&amp;Menu=yes"><img src="/res/images/online-qq.png"></a>
    </div>
</div>
</body>
</html>