 var people;
$(function(){
     people =limitArrString	;
  //   alert(JSON.stringify(people));
	 function  checkChange(){
		 if(people[$('select[name=memberlevel]').val()].viewPoint=="Y"){
			  $('#viewPoint').attr("checked", true);
		  }else{
			  $('#viewPoint').attr("checked", false);
		  }; 
		  if(people[$('select[name=memberlevel]').val()].inTransaction=="Y"){
			  $('#inTransaction').attr("checked", true);
		  }else{
			  $('#inTransaction').attr("checked", false);
		  }; 
		  if(people[$('select[name=memberlevel]').val()].aROOM=="Y"){
			  $('#aROOM').attr("checked", true);
		  }else{
			  $('#aROOM').attr("checked", false);
		  }; 
		  //动态加载所有课件
		var  defaultCourseWare =people[$('select[name=memberlevel]').val()].allCourseWareLevel;
		var courseWareSingle = eval('('+ defaultCourseWare +')');
			$.each(courseWareSingle,function(name,value) {
				  if(courseWareSingle[name]=='Y'){
					  $('#'+name).attr("checked", true);
				  }else{
					  $('#'+name).attr("checked", false);
				  }; 
				}
			);
	}
	
	checkChange();
	  
	$('select[name=memberlevel]').change(function(){

		checkChange();
	})
	
	$('#viewPoint').on('click',function(){
	
		  if($('#viewPoint').is(':checked')) {
			  people[$('select[name=memberlevel]').val()].viewPoint ="Y"; 
			}else{
				 people[$('select[name=memberlevel]').val()].viewPoint ="N"; 
			}
	})
	
	$('#inTransaction').on('click',function(){
	
		  if($('#inTransaction').is(':checked')) {
			  people[$('select[name=memberlevel]').val()].inTransaction ="Y"; 
			}else{
				 people[$('select[name=memberlevel]').val()].inTransaction ="N"; 
			}
	})
	$('#aROOM').on('click',function(){
	
		  if($('#aROOM').is(':checked')) {
			  people[$('select[name=memberlevel]').val()].aROOM ="Y"; 
			}else{
				 people[$('select[name=memberlevel]').val()].aROOM ="N"; 
			}
	})


	//完成按钮
	$('a.finish').on('click',function(){
			var url = '/back/customer/toUpdateCustomerLimit.htm';
			$.post(contextPath+url,{customerLimitStrings :JSON.stringify(people)},function(data){
				if(!data){
					alert('操作失败,请稍后再试!');
				}else{
					alert(data.message);
				}
			});
	
	})
	
	//取消按钮
	$('a.cancel').on('click',function(){
		window.location.href=contextPath+"/back/customer/toCustomerLimit.htm";
	});
	
})
//click事件
function courseWareChange(objA){
	var courseWare = eval('('+ people[$('select[name=memberlevel]').val()].allCourseWareLevel +')');
	     var id=objA.id;
		if($('#'+id).is(':checked')) {
			courseWare[id] ="Y"; 
			people[$('select[name=memberlevel]').val()].allCourseWareLevel =JSON.stringify(courseWare); 
		}else{
			courseWare[id] ="N"; 
			people[$('select[name=memberlevel]').val()].allCourseWareLevel =JSON.stringify(courseWare); 
		}
		
}