$(function(){
	$('.finish').on('click',function(){
		var courseName = $('#courseName');
		var linkAddress = $('#linkAddress');
		var fileId1 = $('#fileId1');
		if(!courseName.val()){
			alert('请填写课程名称');
			title.focus();
			return;
		}
		if(!linkAddress.val()){
			alert('请填写连接地址');
			content.focus();
			return;
		}
		if(!fileId1.val()){
			alert('请上传图片');
			content.focus();
			return;
		}
		
		//表单对象
		var $viewpointForm = $('#courseWareForm');
		var url = $viewpointForm.attr('action');
		$.post(url,$viewpointForm.serialize(),function(data){
			if(!data){
				alert('操作失败,请稍后再试!');
			}else{
				if(data.success==0){
					alert(data.message);
				}else{
					var internalType = $('#hiddenCourseWareType').val();
					window.location.href=contextPath+"/back/courseware/toCoursewareListPage.htm?coursewareType="+internalType;
				}
			}
		},'json');
	});
	//上传专题图片
	$('input[type=file]').live('change',function(){
		var $this = $(this);
		if($this.val().length>0){
			var id = $(this).attr('id');
			$.ajaxFileUpload({
				url:contextPath+'/back/viewpoint/uploadViewpointPic.htm',
				secureuri:false,
				type:'POST',
				fileElementId:id,
				dataType:'json',
				success:function(data){
					if(data.success == -1){
						var htmlStr = '<img src="'+contextPath+'/download/file/show.htm?id='+data.obj+'" width="240" height="184"/>';
						$('#'+id).parent().prev().find('span').html(htmlStr);
						$('#'+id).parent().next('input:hidden').val(data.obj);
					}else{
						alert(data.message);
					}
				},
				error:function(data){
					console.log(data);
					alert('上传图片失败');
				}
			})
		}
	});
})