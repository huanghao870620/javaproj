$(function(){
	//上传图片
	$('input[type=file]').live('change',function(){
		var $this = $(this);
		if($this.val().length>0){
			var id = $(this).attr('id');
			$.ajaxFileUpload({
				url:contextPath+'/back/user/uploadUserHeadPortrait.htm',
				secureuri:false,
				type:'POST',
				fileElementId:id,
				dataType:'json',
				success:function(data){
					if(data.success == -1){
						var htmlStr = '<img src="'+contextPath+'/download/file/show.htm?id='+data.obj+'" width="45" height="40"/>';
						$('#'+id).parent().prev().find('span').html(htmlStr);
						$('#'+id).parent().next('input:hidden').val(data.obj);
					}else{
						alert(data.message);
					}
				},
				error:function(data){
					console.log(data);
					alert('上传文件异常');
				}
			})
		}
	});
	
})