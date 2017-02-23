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
		//$('#ff').form('submit');
		$('#ff').submit();
	}
	function clearForm(){
		$('#ff').form('clear');
	}
	
	$(function(){
		
		$('#editBigPicDlg').dialog('close');
		
		
		$('#dg_goodBigPic').datagrid({
			onDblClickRow: function(rowIndex,rowData){
				window.location.href = "<%=request.getContextPath()%>/debrisSession/toEditDebrisSession.htm?id=" + rowData.id + "&&fbsId=${session.id }" ;
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
	
	<div id="p" class="easyui-panel" title="编辑专场" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/fastBuySession/editSession.htm">
			<div style="margin-bottom:20px">
			   <input type="hidden" name="id" value="${session.id }">
				<input class="easyui-textbox" name="name" value="${session.name }"  style="width:60%" data-options="label:'专场名称:',required:true">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="info" value="${session.info }" style="width:100%;height:60px" data-options="label:'品牌描述:',multiline:true">
			</div>
			
			
			<div style="margin-bottom:20px">
				<input class="easyui-numberspinner" name="discount" id="discount" value="${session.discount }" data-options="label:'折扣:',required:true,labelPosition:'top',spinAlign:'right'" style="width:40%;">
			</div>
			
			
			<div style="margin-bottom:20px">
				<input class="easyui-datetimebox" value="${startTimeStr }" name="startTime" id="limitStart" label="开始时间:" labelPosition="top" style="width:40%;">
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-datetimebox" value="${endTimeStr }" name="endTime" id="limitEnd" label="结束时间:" labelPosition="top" style="width:40%;">
			</div>
			 
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="轮播图" style="width:60%;height:auto;padding:10px;">
				      <div>
				       <input type="hidden" name="imgId"  value="${country.imgId }">
				     	<img id="imgPre" src="<%=request.getContextPath() %>/${file.uriPath }" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="imgAdvFile" id="aa" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgPre'));
				}, label:'轮播图:',required:true" >
			</div>
			
			 
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清除</a>
		</div>
	</div>
		
		
		<div style="margin:20px 0;"></div>
		<table id="dg_goodBigPic" title="专场抢购时间列表" style="width:30%;height:auto"
				data-options="rownumbers:true,singleSelect:true,pagination:false,url:'getTimeBySessionId.htm?id=${session.id}',method:'get'">
			<thead>
				<tr>
				<th data-options="field:'id',width:200">ID</th>
				<th data-options="field:'date',width:200">抢购时间</th>
				</tr>
			</thead>
		</table>
		
		
		<div id="editBigPicDlg" class="easyui-dialog" title="添加商品大图" data-options="iconCls:'icon-save'" style="width:400px;height:auto;padding:10px">
		
<form id="addBigPicForm" method="post" enctype="multipart/form-data" action="<%=request.getContextPath() %>/goods/addBigPic4Good.htm">
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p4" class="easyui-panel" title="" style="width:100%;height:auto;padding:10px;">
				      <div>
				         <input type="hidden" value="" name="bigPicFileId" />
				         <input type="hidden" value="${good.id}" name="goodId"> 
				     	<img id="imgBigPic" src="" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="bigPicFile" id="aa4" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgBigPic'));
				}, label:'选择文件:',required:true" >
			</div>
			
			 
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitAddPicForm()" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearAddPicForm()" style="width:80px">清除</a>
		</div>
	</div>
		 
	</div>
	
	
</body>
</html>