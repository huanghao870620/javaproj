<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../jsp/back/inc.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>磊丹财经——后台管理员管理</title>
<link href="<%=request.getContextPath() %>/res/back/css/reset.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/res/back/css/system.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/res/back/css/ordroom.css" type="text/css" rel="stylesheet" />


<script type="text/javascript">
  $(function(){
		 var bypag = new byPaging({
   		  node : $('#dd'),
   		  url : '<%=request.getContextPath()%>/back/room/queryRooms.htm',
   		  title: "房间列表",
   		  columns : [  
   			            {field:'roomName', align:"center", title:"房间名称", width:100} ,  
   			            {field:'type', align:"center", title:"房间类型", width:100, formatter: function(val, rec){
   			            	
   			            	var str = '';
   			            	if(val == 1){
   			            		str += "普通房间";
   			            	}else if(val == 2){
   			            		str += "专题房间";
   			            	}else if(val == 3){
   			            		str += "实盘房间";
   			            	}
   			            	return str;
   			            }},
   			            {field:'id', align:'center', title:'操作', width:100, formatter: function(val,rec){
   			            	 console.log("val : " + val);
   			            	 console.log("rec : " + rec);
   			            	 return '<a href="<%=request.getContextPath()%>/back/room/toAdudit.htm">审核</a>';
   			            }}
   			        ]
   	  });
   	 
   	 bypag.start();
  })

</script>

</head>

<body>
<%@include file="../../../jsp/back/header.jsp" %>

<div class="contain">
	<%@include file="../../../jsp/back/menu/menu.jsp" %>
    <div class="contain_main">
    	<cc:showBreadCrumbs menuName="房间审核"/>
    <%--
        <p class="warn">后台管理员的增删、权限设置。增加管理员必须填写Email，以便遗忘密码时方便找回。</p>
     --%>
        
        <div id="dd"></div>
        
        <%--
        <div class="theme">
        	<h2><p>普通房间管理</p><!--<a href="#">编辑、管理员</a>--></h2>
            <ul class="title">
            	<li class="first">序号</li>
                <li class="sec">房间分类</li>
                <li class="thr">预览</li>
                <li class="four">进入级别</li>
                <li class="six">最大在线</li>
                <li class="seve">进入范围</li>
                <li class="last"><p>操作</p></li>
            </ul>
            <!--S--查询结果-->
            <ul class="result">
            	<li class="first">1</li>
                <li class="sec">普通房间</li>
                <li class="thr"><i>预览</i></li>
                <li class="four">普通</li>
                <li class="six">200人</li>
                <li class="seve">
                	<input value="允许相同房间级别学员进入" type="text" />
                </li>
                <li class="last"><div class="button"><input class="twot" type="button" value="审核" onclick="" /><input class="twot" type="button" value="设置" onclick="" /></div></li>
            </ul>    
            <!--E--查询结果-->
        </div>
        
        
        <!--专题房间-->
        <div class="theme">
        	<h2><p>专题房间管理</p><!--<a href="#">编辑、管理员</a>--></h2>
            <ul class="title">
            	<li class="first">序号</li>
                <li class="sec">房间分类</li>
                <li class="thr">预览</li>
                <li class="four">进入级别</li>
                <li class="six">最大在线</li>
                <li class="seve">进入范围</li>
                <li class="last"><p>操作</p></li>
            </ul>
            <!--S--查询结果-->
            <ul class="result">
            	<li class="first">1</li>
                <li class="sec">专题房间</li>
                <li class="thr"><i>预览</i></li>
                <li class="four">普通</li>
                <li class="six">200人</li>
                <li class="seve">
                	<input value="允许相同房间级别学员进入" type="text" />
                </li>
                <li class="last"><div class="button"><input class="twot" type="button" value="审核" onclick="" /><input class="twot" type="button" value="设置" onclick="" /></div></li>
            </ul>    
            <!--E--查询结果-->
        </div>
        
        <!--实盘房间-->
        <div class="theme">
        	<h2><p>实盘房间管理</p><!--<a href="#">编辑、管理员</a>--></h2>
            <ul class="title">
            	<li class="first">序号</li>
                <li class="sec">房间分类</li>
                <li class="thr">预览</li>
                <li class="four">进入级别</li>
                <li class="six">最大在线</li>
                <li class="seve">进入范围</li>
                <li class="last"><p>操作</p></li>
            </ul>
            <!--S--查询结果-->
            <ul class="result">
            	<li class="first">1</li>
                <li class="sec">实盘房间1</li>
                <li class="thr"><i>预览</i></li>
                <li class="four">普通</li>
                <li class="six">200人</li>
               <!-- <li class="seve">
                	<select name="">
                    	<option value="" selected>允许相同房间级别学员进入</option>
                        <option value="">允许高于和等于房间级别学员进入</option>
                    </select>
                </li>-->
                <li class="seve">
                	<input value="允许相同房间级别学员进入" type="text" />
                </li>
                <li class="last"><div class="button1"><input class="twot" type="button" value="审核" onclick="" /></div></li>
            </ul>    
            
             <ul class="result">
            	<li class="first">2</li>
                <li class="sec">实盘房间2</li>
                <li class="thr"><i>预览</i></li>
                <li class="four">普通</li>
                <li class="six">200人</li>
                <li class="seve">
                	<input value="允许相同房间级别学员进入" type="text" />
                </li>
                <li class="last"><div class="button1"><input class="twot" type="button" value="审核" onclick="" /></div></li>
            </ul>    
            
             <ul class="result">
            	<li class="first">3</li>
                <li class="sec">实盘房间3</li>
                <li class="thr"><i>预览</i></li>
                <li class="four">普通</li>
                <li class="six">200人</li>
                <li class="seve">
                	<input value="允许相同房间级别学员进入" type="text" />
                </li>
                <li class="last"><div class="button1"><input class="twot" type="button" value="审核" onclick="" /></div></li>
            </ul>    
            
             <ul class="result">
            	<li class="first">4</li>
                <li class="sec">实盘房间4</li>
                <li class="thr"><i>预览</i></li>
                <li class="four">普通</li>
                <li class="six">200人</li>
                <li class="seve">
                	<input value="允许相同房间级别学员进入" type="text" />
                </li>
                <li class="last"><div class="button1"><input class="twot" type="button" value="审核" onclick="" /></div></li>
            </ul>    
            
             <ul class="result">
            	<li class="first">5</li>
                <li class="sec">实盘房间5</li>
                <li class="thr"><i>预览</i></li>
                <li class="four">普通</li>
                <li class="six">200人</li>
                <li class="seve">
                	<input value="允许相同房间级别学员进入" type="text" />
                </li>
                <li class="last"><div class="button1"><input class="twot" type="button" value="审核" onclick="" /></div></li>
            </ul>    
            <!--E--查询结果-->
        </div>
        
         --%>
        
    </div>
    
</div>


</body>
</html>
