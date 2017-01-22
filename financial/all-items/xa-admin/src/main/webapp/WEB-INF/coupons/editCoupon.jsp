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
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/base.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.min.js"></script>
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
		
		$('#brand').combobox('select','${coupons.brandId}');
		$('#countryId').combobox('select','${coupons.countryId}');
		$('#allocTypeId').combobox('select','${coupons.allocTypeId}');
		
		$('#limitStart').datetimebox('setValue', '${lStart}');	// set datetimebox value
		$('#limitEnd').datetimebox('setValue', '${lEnd}');	// set datetimebox value
	//	var v = $('#dt').datetimebox('getValue');		// get datetimebox value
	//	alert(v);
		
		
		$('#tt').tree({
			url:'<%=request.getContextPath() %>/classification/getClassifi.htm',
			checkbox:true,
			method:'get',
			cascadeCheck: false,
			animate:true,
			onBeforeCheck:function(node, checked){
				 $('#classid').val(node.id);
			},
			
			 onSelect: function (node) {
					var cknodes = $('#tt').tree("getChecked");
			        for (var i = 0; i < cknodes.length; i++) {
			          if (cknodes[i].id != node.id) {
			            $('#tt').tree("uncheck", cknodes[i].target);
			          }
			        }
			        if (node.checked) {
			          $('#tt').tree('uncheck', node.target);
			 
			        } else {
			          $('#tt').tree('check', node.target);
			 
			        }
				 
	         },
			onLoadSuccess : function(node, data) {
				var node2= $('#tt').tree('find','${coupons.classId}');
		           $('#tt').tree('check',node2.target);
				
				
				 $(this).find('span.tree-checkbox').unbind().click(function () {
			          $('#tt').tree('select', $(this).parent());
			          return false;
			        });
				
			}
		});
	});
	
	</script>
	
</head>
<body>
	
	<div id="p" class="easyui-panel" title="编辑优惠券" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/coupons/editCoupons.htm">
			<div style="margin-bottom:20px">
			    <input type="hidden" name="id" value="${coupons.id }" />
				<input class="easyui-textbox" name="name" value="${coupons.name }" style="width:60%" data-options="label:'名称:',required:true">
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="price" value="${coupons.price }" style="width:60%" data-options="label:'金额:',required:true">
			</div>
			 
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="sill" value="${coupons.sill }" style="width:60%" data-options="label:'门槛:',required:true">
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-datetimebox" id="limitStart" name="limitStart" value="${coupons.limitStart }" label="开始时间:" labelPosition="top" style="width:40%;">
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-datetimebox" id="limitEnd" name="limitEnd" value="${coupons.limitEnd }" label="结束时间:" labelPosition="top" style="width:40%;">
			</div>
			
			<div style="margin-bottom:20px">
				<select class="easyui-combobox" id="brand" name="brandId"   label="品牌:"  labelPosition="top" style="width:40%;">
					<c:forEach items="${brands}" var="b">
						<option value="${b.id }">${b.name }</option>
					</c:forEach>
				</select>
			</div>
			
			<div style="margin-bottom:20px">
				<select class="easyui-combobox" id="countryId" name="countryId"   label="国家:"  labelPosition="top" style="width:40%;">
					<c:forEach items="${countrys}" var="c">
						<option value="${c.id }">${c.name }</option>
					</c:forEach>
				</select>
			</div>
			
			<div style="margin-bottom:20px">
				<select class="easyui-combobox" name="allocTypeId" id="allocTypeId"  label="分配类型:"  labelPosition="top" style="width:40%;">
					<c:forEach items="${allocTypes}" var="at">
						<option value="${at.id }">${at.name }</option>
					</c:forEach>
				</select>
			</div>
			
			
			<input type="hidden" id="classid" name="classId" value="${coupons.classId }"/>
			
			<div style="margin-bottom:20px">
				分类:
				<div class="easyui-panel" style="padding:5px">
					<ul id="tt" class="easyui-tree" ></ul>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="note" value="${coupons.note }" style="width:100%;height:60px" data-options="label:'备注:',multiline:true">
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