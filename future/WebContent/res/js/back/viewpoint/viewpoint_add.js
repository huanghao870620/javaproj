$(function(){
	$('.finish').on('click',function(){
		var title = $('#title');
		var content = $('#content');
		var specialArr = $('input[id^=special]');
		var fileIdArr = $('input:hidden[id^=fileId]');
		if(!title.val()||title.val().length<=0){
			alert('请填写标题');
			title.focus();
			return;
		}
		if(title.val().length>25){
			alert('标题最多只能填写25个字符');
			title.focus();
			return;
		}
		if(!content.val()||content.val().length<=0){
			alert('请填写内容简述');
			content.focus();
			return;
		}
		if(content.val().length>100){
			alert('内容简述最多只能填写100个字符');
			content.focus();
			return;
		}
		//检测对应专题是否上传图片
		if(specialArr&&specialArr.length>0){
			for (var i = 0; i < specialArr.length; i++) {
				if(specialArr[i].value){
					if(fileIdArr[i].value){
						continue;
					}else{
						alert('请为对应的专题上传图片!');
						return;
					}
				}else{
					if(fileIdArr[i].value){
						alert('请填写对应的专题名称!');
						$('#special'+(i+1)).focus();
						return;
					}else{
						continue;
					}
				}
			}
		}
		//表单对象
		var $viewpointForm = $('#viewpointForm');
		var url = $viewpointForm.attr('action');
		$.post(url,$viewpointForm.serialize(),function(data){
			if(!data){
				alert('操作失败,请稍后再试!');
			}else{
				if(data.success==0){
					alert(data.message);
				}else{
					window.location.href=contextPath+"/back/viewpoint/toViewpointList.htm";
				}
			}
		},'json');
	});
	
	//上传图片
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
					alert('上传文件异常');
				}
			})
		}
	});
})
