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
		$('#dg').datagrid();
	});
	
	</script>
	
</head>
<body>
	
	<div id="p" class="easyui-panel" title="订单详情" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
	<div style="margin-bottom:20px">
				订单号:${orderDto.orderNo }
			</div>
			
			<div style="margin-bottom:20px">
				收货人手机号:${address.mobile }
			</div>
			
			<div style="margin-bottom:20px">
			   收货人姓名:${address.name }
			</div>
			
			<div style="margin-bottom:20px">
			所在区域: ${orderDto.area }
			</div>
			
			<div style="margin-bottom:20px">
			收货人地址:${address.address }
			</div>
			
			<div style="margin-bottom:20px">
			 收货人证件号: ${address.idcard }
			</div>
			
			<div style="margin-bottom:20px">
				订单总价:${orderDto.sumPrice }元
			</div>
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="收货人身份证正面照" style="width:60%;height:auto;padding:10px;">
				      <div>
				         <input type="hidden" value="${cardIdFrontFile.id }" name="thumbFileId" />
				     	 <img id="imgPre" src="<%=request.getContextPath() %>/${cardIdFrontFile.uriPath}" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="收货人身份证反面照" style="width:60%;height:auto;padding:10px;">
				      <div>
				         <input type="hidden" value="${cardIdBackFile.id }" name="thumbFileId" />
				     	 <img id="imgPre" src="<%=request.getContextPath() %>/${cardIdBackFile.uriPath}" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
	
	<div style="margin:20px 0;"></div>
	<table id="dg" title="" style="width:60%;height:auto"
			data-options="rownumbers:true,singleSelect:true,pagination:false,url:'getGoodsForOrderId.htm?orderId=${orderDto.orderId }',method:'get'">
		<thead>
			<tr>
			<%--
				<th data-options="field:'goodId',width:40">ID</th>
				<th data-options="field:'thumbFilePath',width:100,height:20,formatter:function(value,rec){
					  return '<img src=<%=request.getContextPath() %>/'+value+'></img>';
					}">商品缩略图</th>
			 --%>
				<th data-options="field:'goodName',width:400,formatter:function(value,rec){
				    return '<a href=<%=request.getContextPath() %>/goods/toEditGood.htm?id='+rec.goodId+'>'+value+'</a>';
				}">商品名称</th>
				<th data-options="field:'price',width:200,formatter:function(value,rec){
				  return value+'元';
				}">单价</th>
				
				<th data-options="field:'count',width:40">商品数量</th>
				
				<%--
				<th data-options="field:'goodName',width:100">价格</th>
				 --%>
			</tr>
		</thead>
	</table>
	
	
		
	</div>
	
	
</body>
</html>