<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Form - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/icon.css">
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/base.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.easyui.min.js"></script>
	
	<script type="text/javascript">
	function submitForm(){
		
		var start=$("#limitStart").html(); 
		var end=$("#limitEnd").html();
		
		var s_t = new Date(start);
		var e_t = new Date(end);
		
		var v = $("#cronDate").datetimebox("getValue");
		var t = new Date(v);
		if(t.getTime() < s_t.getTime()){
		   $.messager.alert('警告','抢购时间不能早于专场开始时间!');
		   $('#aa0').accordion('select',"商品"+sp[1]);
		   return;
		}
		
		if(t.getTime()>e_t.getTime()){
			$.messager.alert('警告','抢购时间不能晚于专场结束时间!');
			$('#aa0').accordion('select',"商品"+sp[1]);
			return;
		}
		
		
		$('#ff').submit();
	}
	function clearForm(){
		$('#ff').form('clear');
	}
	
	$(function(){
		$('#dg_goodBigPic').datagrid({
			onDblClickRow: function(rowIndex,rowData){
				window.location.href = "<%=request.getContextPath()%>/dsGood/toEditDsGood.htm?id=" + rowData.id +"&&fbsId=${fbsId}" ;
			},
			 toolbar:[{ text:'增加',iconCls:'icon-add',handler:function(){  
				 window.location.href = "<%=request.getContextPath()%>/debrisSession/toAddDebrisSession.htm?fbsId=${session.id}";
             }  
			},
             {text:'删除', iconCls:'icon-remove', handler:function(){
            	 var row = $('#dg_goodBigPic').datagrid('getSelected');
            	  $.post("<%=request.getContextPath()%>/goods/delGoodPic.htm",{gfId:row.gfId},function(data){
            		    if(data.success){
            		    	$('#dg_goodBigPic').datagrid('reload');
            		    }
            	  });
            	 /*
            	 if (row) {
                     var rowIndex = $('#dg').datagrid('getRowIndex', row);
                     $('#dg').datagrid('deleteRow', rowIndex);  
            		 }	*/
            	 //$('#dg').datagrid('deleteRow', x); 
             }
             }
			 ]
		});
	});
	
	</script>
	
</head>
<body>
	
	<div id="p" class="easyui-panel" title="编辑抢购时间" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
		<div style="margin-bottom:20px">
				专场开始时间:<span id="limitStart">${startTimeStr }</span>
		</div>
		
		<div style="margin-bottom:20px">
				专场结束时间:<span id="limitEnd">${endTimeStr }</span>
		</div>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/debrisSession/editDebrisSession.htm">
			<div style="margin-bottom:20px">
			   <input type="hidden" name="id" value="${dSession.id }"/>
				<input type="hidden" name="fbsId" value="${fbsId }" />
				<input class="easyui-datetimebox" value="${cronTimeStr }" name="cronTime" id="cronDate" data-options="required:true,showSeconds:true,onSelect:function(date){
			}" label="抢购时间:" labelPosition="top" style="width:30%;"> 
			</div>
			
			 
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清除</a>
		</div>
	</div>
		
		
		
		<div style="margin:20px 0;"></div>
		<table id="dg_goodBigPic" title="此时间下的商品" style="width:30%;height:auto"
				data-options="rownumbers:true,singleSelect:true,pagination:false,url:'<%=request.getContextPath() %>/dsGood/getGoodsByDsSessionId.htm?dsId=${dSession.id}',method:'get'">
			<thead>
				<tr>
				<th data-options="field:'name',width:200,formatter:function(value,rec){
				   return '<a href=<%=request.getContextPath() %>/goods/toEditGood.htm?id='+rec.goodId+'>'+value+'</a>';
				}">商品名称</th>
				<th data-options="field:'price',width:100,formatter:function(value,rec){
				  return value+'元';
				}">价格</th>
				
				<th data-options="field:'inventory',width:50,formatter:function(value,rec){
				  return value;
				}">库存</th>
				</tr>
			</thead>
		</table>
		
		
		
		
		 
	</div>
	
	
</body>
</html>