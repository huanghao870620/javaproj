var bypag;
var internalType;
$(function(){
	var dateUtil = new DateUtil();
	//获取内参类型
	internalType = $('#internalTypeHidden').val();
	// 初始化加载磊丹观点数据
	bypag = new byPaging({
		node : $('#internalData'),
		url : contextPath+'/back/internal/queryInternalList.htm?internalType='+internalType+'&rows=5',
		deleteUrl:contextPath+'/back/internal/deleteInternal.htm',
		title : "交易内参列表",
		columns :
		[{
			field : 'week',
			align : "center",
			title : "序号",
			width : 100
		}, {
			field : 'title',
			align : "center",
			title : "标题",
			width : 100
		}, {
			field : 'createTime',
			align : 'center',
			title : '时间',
			width : 100,
			formatter:function(val){
				return dateUtil.formatDate(val,'yyyy-MM-dd hh:mm:ss');
			}
		},{
			field : 'special',
			align : 'center',
			title : '专题名称',
			width : 100
		}],
		primaryKey:"internalId",
		showPageList:false,
		pageSize:5
	});
	bypag.start();
	
	// 删除交易内参
	$('a[icon=icon-remove]').on('click',function(){
		var rows = $('#internalData').datagrid('getSelections');
		if(!rows||rows.length<=0){
			$.extend($.messager.defaults,{  ok:"好的" }); 
			$.messager.alert('提示','请选择要删除的交易内参!','info');
			return;
		}
		$.extend($.messager.defaults,{  ok:"确定",  cancel:"取消" });
		$.messager.confirm('操作提示','确定要删除所选交易内参吗？',function(r){
			if(r){
				bypag.init();
			}
		});
	});
	// 编辑磊丹观点
	$('a[icon=icon-edit]').on('click',function(){
		var rows = $('#internalData').datagrid('getSelections');
		if(!rows||rows.length<=0){
			$.extend($.messager.defaults,{ok:"好的"}); 
			$.messager.alert('提示','请选择要编辑的交易内参!','info');
			return;
		}
		if(rows.length>1){
			$.extend($.messager.defaults,{  ok:"好的" }); 
			$.messager.alert('提示','一次只能编辑一条交易内参!','info');
			return;
		}
		var id = rows[0].internalId;
		window.location.href= contextPath+'/back/internal/toEditInternal.htm?internalType='+internalType+'&internalId='+id;
	});
});

//根据标题搜索
function doSearch(value){
	bypag.url = contextPath+'/back/internal/queryInternalList.htm?internalType='+internalType+'&rows=5&title='+value;
	bypag.start();
}