var bypag;
$(function(){
	var dateUtil = new DateUtil();
  	bypag = new byPaging({
	  	node : $('#userData'),
		url : contextPath+'/back/user/list.htm',
		deleteUrl : contextPath+'/back/user/deleteUser.htm',
		title: "用户列表",
		columns : [  
	        {field:'userName', align:"center", title:"用户名", width:100},  
	        {field:'email', align:"center", title:"邮箱", width:100},
            {field:'name', align:"center", title:"姓名", width:100},
            {field:'roleName', align:"center", title:"角色", width:100},
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
	
	// 删除用户
	$('a[icon=icon-remove]').on('click',function(){
		var rows = $('#userData').datagrid('getSelections');
		if(!rows||rows.length<=0){
			$.extend($.messager.defaults,{  ok:"好的" }); 
			$.messager.alert('提示','请选择要删除的用户!','info');
			return;
		}
		$.extend($.messager.defaults,{  ok:"确定",  cancel:"取消" });
		$.messager.confirm('操作提示','确定要删除所选用户吗？',function(r){
			if(r){
				bypag.init();
			}
		});
	});
	// 编辑用户信息
	$('a[icon=icon-edit]').on('click',function(){
		var rows = $('#userData').datagrid('getSelections');
		if(!rows||rows.length<=0){
			$.extend($.messager.defaults,{ok:"好的"}); 
			$.messager.alert('提示','请选择要编辑的用户!','info');
			return;
		}
		if(rows.length>1){
			$.extend($.messager.defaults,{  ok:"好的" }); 
			$.messager.alert('提示','一次只能编辑一条用户!','info');
			return;
		}
		var id = rows[0].userId;
		window.location.href= contextPath+'/back/user/toEditUser.htm?id='+id;
	});
	//修改密码
	$('a[icon=icon-mini-edit]').on('click',function(){
		var rows = $('#userData').datagrid('getSelections');
		if(!rows||rows.length<=0){
			$.extend($.messager.defaults,{ok:"好的"}); 
			$.messager.alert('提示','请选择要修改密码的用户!','info');
			return;
		}
		if(rows.length>1){
			$.extend($.messager.defaults,{ok:"好的"}); 
			$.messager.alert('提示','一次只能操作一个用户!','info');
			return;
		}
		var id = rows[0].userId;
		window.location.href= contextPath+'/back/user/toUpdatePass.htm?id='+id;
	});
})
function doSearch(value){
	bypag.url=contextPath+'/back/user/list.htm?userName='+value;
	bypag.start();
}