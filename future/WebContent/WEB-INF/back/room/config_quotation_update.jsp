<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../jsp/back/inc.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>磊丹财经——后台管理员管理</title>
<link href="<%=request.getContextPath() %>/res/back/css/reset.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/res/back/css/system.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath() %>/res/back/css/config_quotation.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
 
function toSubmit(){
	 $("#configForm").submit();
}
</script>
</head>

<body>
<%@include file="../../../jsp/back/header.jsp" %>

<div class="contain">
	<%@include file="../../../jsp/back/menu/menu.jsp" %>
    <div class="contain_main">
    	<cc:showBreadCrumbs menuName="实盘房间"/>
    <%--
        <p class="warn">后台管理员的增删、权限设置。增加管理员必须填写Email，以便遗忘密码时方便找回。</p>
     --%>
	<form   id="configForm" action="<%=contextPath %>/back/room/updateQuotationConfig.htm" method="post" enctype="multipart/form-data" style="height: 100%">
		<div style="height: 800px; width: 100%; overflow-y: auto;">
		<input type="hidden" name="rcd.id" value="${rcd.id}"/>
		<input   name="rcd.type"  value="0"  type="hidden"/>
		<table class="ttab" height="100" width="70%" border="0" cellpadding="0" cellspacing="1"
			align="center">
			<tr>
				<td height="30"
					 colspan="2">
					<div align="center">
					<font  size="3" ><b>编辑实盘房间信息</b></font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="30" width="20%">
					<div align="right" class="STYLE1">内容：</div></td>
				<td>
					<div align="left" class="STYLE1" style="padding-left: 10px;">
						<textarea rows="8" cols="70" name="rcd.name"  id="content">${rcd.name}</textarea>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">
					<div align="right" class="STYLE1">上传图片：</div>
				</td>
				<td>
					<div align="left" class="STYLE1" style="padding-left: 10px;">
						<div align="left" class="STYLE1" style="padding-top: 5px;">
						<div style="height: 10px;"></div>
						<input type="file" name="file" title="继续添加图片..."/>
						</div>
						 <input type="hidden" id="cate3" value="0"/>
						<!-- 其他图片展示区域 -->
						<div id="divCate3" style="display: block;">
						图片：<br/>
						<c:if test="${rcd.cfileId!=null}">
						<img  width="100px" height="80px"src="<%=contextPath %>/download/file/show.htm?id=${rcd.cfileId}" />
						</c:if>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="padding: 10px">
					<div align="center">
			 			<input type="button" value="　保　存　" class="input_btn_style1" onclick="toSubmit();"/>　　　　
			 			<input id="backBt" type="button" value="　返　回　" class="input_btn_style1" onclick="javascript:window.location.href='javascript:history.go(-1)'"/>
		 			</div>
				</td>
			</tr>
		</table></div>

</form>
</div>
    </div>
</body>
</html>
