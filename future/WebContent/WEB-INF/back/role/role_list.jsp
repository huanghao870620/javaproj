<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../jsp/back/inc.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/js/plugins/jquery-easyui-1.4.5/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/js/plugins/jquery-easyui-1.4.5/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/js/plugins/jquery-easyui-1.4.5/demo/demo.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/plugins/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/res/js/back/customer/customer.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>磊丹财经——后台客户管理</title>

<script type="text/javascript">
      
      $(function () {  
    	  
    	 var bypag = new byPaging({
    		  node : $('#dd'),
    		  url : '<%=request.getContextPath()%>/back/role/list.htm',
//     		  title: "角色列表",
    		  columns : [  
    			            {field:'rolename', align:"center", title:"角色", width:100} //,  
    			         //   {field:'nickName', align:"center", title:"昵称", width:100},
    			          //  {field:'id', align:'center', title:'操作', width:100, formatter: function(val,rec){
    			           // 	 return '<a href="">修改密码</a>';
    			           // }}
    			        ]
    	  });
    	 
    	 bypag.start();
    	  
        
      });  
    
      
</script>


</head>

<body>
<%@include file="../../../jsp/back/header.jsp" %>
<div class="contain">
	<%@include file="../../../jsp/back/menu/menu.jsp" %>
    <div class="contain_main">
     	<cc:showBreadCrumbs menuName="角色管理"/>
        <p class="warn">管理客户学员的搜索、筛选、跟单、马甲权限内容、信息查看，管理功能。</p>
        <div class="theme">

		<div>

                     
</div>

  <div class="easyui-panel" title="角色列表"  
        icon="icon-save" style="width: auto; height: 420px;">  
        <div class="easyui-layout" fit="true"  >  
<!--             <div  id="easyui_toolbar" region="north" border="false"   -->
<!--                 style="border-bottom: 1px solid #ddd; height: 32px; padding: 2px 5px; background: #fafafa;">   -->
<!--                 <div style="float: left;">   -->
<%--                     <a href="<%=request.getContextPath() %>/back/customer/toAddCustomer.htm" class="easyui-linkbutton" plain="true" icon="icon-add">新增</a>   --%>
<!--                 </div>   -->
  
<!--                 <div class="datagrid-btn-separator"></div>   -->
  
<!--                 <div style="float: left;">   -->
<!--                     <a href="#" class="easyui-linkbutton" plain="true" icon="icon-save">编辑</a>   -->
<!--                 </div>   -->
  
<!--                 <div class="datagrid-btn-separator"></div>   -->
  
<!--                 <div style="float: left;">   -->
<!--                     <a href="#" class="easyui-linkbutton" plain="true"   -->
<!--                         icon="icon-remove">删除</a>   -->
<!--                 </div>   -->
  
<!--                 <div id="tb" style="float: right;">   -->
<!--                          <input id="ss" class="easyui-searchbox"   -->
<!--                        prompt="搜索条件"   -->
<!--                         style="width: 130px; vertical-align: middle;"></input>    -->
<!--                 </div>   -->
  
<!--             </div>   -->
            <div region="center" border="false">  
                <table id="dd"></table>  
            </div>
        </div>   
  
    </div>  
  
            
        </div>
    </div>
</div>


 
</body>
</html>
