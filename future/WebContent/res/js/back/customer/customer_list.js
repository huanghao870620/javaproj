var bypag;
$(function(){
	var dateUtil = new DateUtil();
	bypag = new byPaging({
		node : $('#customerData'),
	  	url : contextPath+'/back/customer/findCustoemrList.htm',
	  	deleteUrl : contextPath+'/back/customer/deleteCustomer.htm',
	 	title: "客户列表",
	 	columns : [
			{field:'userName', align:"center", title:"帐号", width:100},  
			{field:'name', align:"center", title:"姓名", width:100},
			{field:'firmOfferAccount',align:"center",title:"实盘账号",width:100},
			{field:'levelName',align:"center",title:"会员等级",width:100},
			{field:'registTime', align:"center", title:"注册时间", width:100,formatter:function(val){
				return dateUtil.formatDate(val,'yyyy-MM-dd');
			}},
			{field:'lastLoginTime', align:"center", title:"最后登录时间", width:100,formatter:function(val){
				return dateUtil.formatDate(val,'yyyy-MM-dd hh:mm:ss');
			}}
	    ],
	    primaryKey: "userId"
	});
	bypag.start();
	
	// 删除客户
	$('a[icon=icon-remove]').on('click',function(){
		var rows = $('#customerData').datagrid('getSelections');
		if(!rows||rows.length<=0){
			$.extend($.messager.defaults,{  ok:"好的" }); 
			$.messager.alert('提示','请选择要删除的客户!','info');
			return;
		}
		$.extend($.messager.defaults,{  ok:"确定",  cancel:"取消" });
		$.messager.confirm('操作提示','确定要删除所选客户吗？',function(r){
			if(r){
				bypag.init();
			}
		});
	});
	// 编辑客户信息
	$('a[icon=icon-edit]').on('click',function(){
		var rows = $('#customerData').datagrid('getSelections');
		if(!rows||rows.length<=0){
			$.extend($.messager.defaults,{ok:"好的"}); 
			$.messager.alert('提示','请选择要编辑的客户!','info');
			return;
		}
		if(rows.length>1){
			$.extend($.messager.defaults,{  ok:"好的" }); 
			$.messager.alert('提示','一次只能编辑一个客户!','info');
			return;
		}
		var id = rows[0].userId;
		window.location.href= contextPath+'/back/customer/toEditCustomer.htm?id='+id;
	});
	//修改密码
	$('a[icon=icon-mini-edit]').on('click',function(){
		var rows = $('#customerData').datagrid('getSelections');
		if(!rows||rows.length<=0){
			$.extend($.messager.defaults,{ok:"好的"}); 
			$.messager.alert('提示','请选择要修改密码的客户!','info');
			return;
		}
		if(rows.length>1){
			$.extend($.messager.defaults,{ok:"好的"}); 
			$.messager.alert('提示','一次只能操作一个客户!','info');
			return;
		}
		var id = rows[0].userId;
		window.location.href= contextPath+'/back/customer/forwardUpdateCustomerPass.htm?id='+id;
	});
})

function doSearch(value){
	console.log('根据用户名搜索');
	bypag.url = contextPath+'/back/customer/findCustoemrList.htm?userName='+value
	bypag.start();
}