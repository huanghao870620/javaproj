<%@page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Custom DataGrid Pager - jQuery EasyUI Demo</title>
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/icon.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/base.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.easyui.min.js"></script>
	
	
	<script type="text/javascript">
	
	
	$(function(){
		var t = $('#tt');
		t.tree({
			url: '<%=request.getContextPath()%>/classification/getClassifi.htm',
			method: 'get',
			animate: true,
			onContextMenu: function(e,node){
				e.preventDefault();
				$(this).tree('select',node.target);
				$('#mm').menu('show',{
					left: e.pageX,
					top: e.pageY
				});
			},
			onDblClick: function(node) {
				window.location.href="<%=request.getContextPath()%>/classification/toEditClass.htm?classid="+node.id;
			 //   $(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);  
			  //  node.state = node.state === 'closed' ? 'open' : 'closed';  
			}  
		});
	});
	
	
	function append(){
		var t = $('#tt');
		var node = t.tree('getSelected');
		
		
		$('#pid').val(node.id);
		$('#dlg').dialog('open');
		
		/*
		t.tree('append', {
			parent: (node?node.target:null),
			data: [{
				text: 'new item1'
			},{
				text: 'new item2'
			}]
		});
		*/
		
	}
	
	function removeit(){
		var node = $('#tt').tree('getSelected');
		$.post("<%=request.getContextPath()%>/classification/deleteClass.htm",{id:node.id},function(data){
			   if(data.success){
				$('#tt').tree('remove', node.target);
			   }
		});
		
	}
	function collapse(){
		var node = $('#tt').tree('getSelected');
		$('#tt').tree('collapse',node.target);
	}
	function expand(){
		var node = $('#tt').tree('getSelected');
		$('#tt').tree('expand',node.target);
	}
	
	
	  $(function(){
		  $('#dlg').dialog('close')
	  });
	  
	  
	  function submitForm(){
			//$('#ff').form('submit');
			//location.reload();
			$('#ff').submit();
		}
		function clearForm(){
			$('#ff').form('clear');
		}
	  
	</script>
	
</head>
<body>
  <h2>分类列表</h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" style="padding:5px">
		<ul id="tt" class="easyui-tree"></ul>
	</div>
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="append()" data-options="iconCls:'icon-add'">Append</div>
		<div onclick="removeit()" data-options="iconCls:'icon-remove'">Remove</div>
		<div class="menu-sep"></div>
		<div onclick="expand()">Expand</div>
		<div onclick="collapse()">Collapse</div>
	</div>
	
	<div id="dlg" class="easyui-dialog" title="添加分类" data-options="iconCls:'icon-save'" style="width:600px;height:400px;padding:10px">
		<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/classification/addClass.htm">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="name" style="width:60%" data-options="label:'分类名称:',required:true">
			</div>
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="分类图片" style="width:60%;height:auto;padding:10px;">
				      <div>
				     	<img id="imgPre" src="" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<input type="hidden" name="pid" id="pid"  value=""> 
			
			
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="imgFile" id="aa" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgPre'));
				}, label:'分类图片:',required:true" >
			</div>
			
			
			 
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清除</a>
		</div>
	</div>
		
	</div>
	
  
</body>
</html>