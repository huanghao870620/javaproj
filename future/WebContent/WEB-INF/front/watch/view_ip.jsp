<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/res/js/general/listener/listener.js"></script>
 <script type="text/javascript">
 
 var listener;
 
 $(function(){
	 
   window.listener = new Listener({
	    url : '<%=request.getContextPath()%>/front/getAllIp.htm' ,
	    node : $('#ips'),
	    deal : function(){
	   	 var liste = this;
		 $.post(this.url, {},  function(data){
			 var str = "";
			   data = JSON.parse(data);
			   for(var i=0; i<data.sessionids.length; i++){
				   str += data.sessionids[i] + "</br>";
			   }
			  liste.node.html(str);
		 });
	} 
	    	
	    	
   }).listen();
   
   new Listener({
	   url : '<%=request.getContextPath()%>/front/getAllMsg.htm',
	   node : $('#messages'),
	   deal : function(){
		   	 var liste = this;
			 $.post(this.url, {},  function(data){
				 var str = "";
				   data = JSON.parse(data);
				   for(var i=0; i<data.msgs.length; i++){
					   str += data.msgs[i] + "</br>";
				   }
				  liste.node.html(str);
			 });
		} 
   }).listen();
   
 });
 
 
 </script>

</head>
<body>

 <div id="ips">
 
 </div>
 
 <div id="messages">
 
 </div>

</body>
</html>