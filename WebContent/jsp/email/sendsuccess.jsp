<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commontaglib.jsp" %>
<script type="text/javascript">
	
	var emailid = '${param.id}';
	var status = '${param.status}';
	$(document).ready(function(){
		//草稿
		if(status == '0') {
			$('#divtip').html('您的邮件已保存');
			$('#divmsg').html('此邮件保存成功，并已保存到“草稿箱”文件夹。');
			
		}else{//发送
			$('#divtip').html('您的邮件已发送');
			$('#divmsg').html('此邮件发送成功，并已保存到“已发送”文件夹。');
		}
	});
	
	function shouemail() {
		if(status == '0') {
			window.location.href="<%=basePath%>jsp/email/newemail.jsp?flag=update&id=" + emailid;
		}else {
			window.location.href="<%=basePath%>jsp/email/showsendemail.jsp?id=" + emailid;
		}
	}
	
	function tonewemail() {
		window.location.href="<%=basePath%>jsp/email/newemail.jsp";
	}
</script>
</head>
<body>
	<div class="easyui-panel" nohead="true" fit="ture" style="padding: 10px;">
		<div id="divtip" style="font-size: 20px;color: green;font-weight: bold;padding-top: 5px;"></div>
		<div id="divmsg" style="font-size: 14px;color: blue;padding-top: 5px;"></div></div>
		<div style="padding-top: 5px;">
			<a href="javascript:void(0)" onclick="shouemail()">查看此邮件</a>
			<a href="javascript:void(0)" onclick="tonewemail()">在写一封</a>
		</div>
</body>
</html>