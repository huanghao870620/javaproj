<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>磊丹财经-在线视频直播室</title>
    <link rel="stylesheet" href="/res/front/css/index.css">
     <script type="text/javascript">
  
      var  closeJframe = function(){ 
    	  parent.document.getElementById("minutes-10-wrap").style.display = "none";
       }
       
       function addfavorite() 
{ 
    	   
   if (document.all) 
   { 
      window.external.addFavorite('http://192.168.10.203/login/toLogin.htm','磊丹财经-在线视频直播室'); 
   } 
   else if (window.sidebar) 
   { 
      window.sidebar.addPanel('磊丹财经-在线视频直播室', 'http://192.168.10.203/login/toLogin.htm', ""); 
   } 

}
    function show_Favorite(sURL,sTitle){   sURL =
    encodeURI(sURL);try{window.external.addFavorite(sURL,
    sTitle);}catch(e)
    {try{window.sidebar.addPanel(sTitle, sURL, "");}catch
    (e)
    {alert("加入收藏失败，请使用Ctrl+D进行添加,或手动在浏览器里进行设置.");}}

    }



</script> 
    
</head>
<body>
<div class="minutes-10">
    <div class="minutes-10-btn">
        <a href="javascript:void(0);" onclick="show_Favorite(window.location,document.title);" class="ml35"><img src="/res/images/10-minutes-btn01.png"></a>
        <a href="tencent://message/?uin=800015047&amp;Site=www.leidan18.com&amp;Menu=yes"><img src="/res/images/10-minutes-btn02.png"></a>
         <a onclick="closeJframe()"  href="javascript:;"><img src="/res/images/10-minutes-btn03.png"></a>
    </div>
</div>
</body>
</html>