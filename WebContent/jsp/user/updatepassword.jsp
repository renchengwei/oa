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
	
	$(document).ready(function(){

	});
	
	function updatepassword() {
		var isValid = $('#form1').form('validate');
		if (!isValid){
			return;
		}
		
		if($('#password').val() != $('#password1').val()) {
			alertInfo('密码输入不一致!');
			return;
		}
		
		postcommit('UserAction.do?method=updatePassword',$('#form1').serialize(),'',function(result){
			alertInfo('密码已修改，请重新登录!',function() {
				window.location.href="LoginAction.do?method=logout";
			});
		});
	}
</script>
</head>
<body style="width:1024px;margin-left: auto;margin-right: auto;">
	<div class="easyui-panel" nohead="true" fit="true" style="border: 0px;">
		<div id="paneltitle" title="修改密码" class="easyui-panel" style="padding:10px 10px 10px 0px">
			<form id="form1" action="">
            <table >
				<tr>
					<td style="text-align:right;width: 150px">原密码:</td>
					<td style="text-align:left;">
						<input id="oldpassword" name="oldpassword" class="easyui-textbox" type="password" style="width:400px" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">新密码:</td>
					<td style="text-align:left;">
						<input id="password" name="password" class="easyui-textbox" type="password" style="width:400px" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">确认密码:</td>
					<td style="text-align:left;">
						<input id="password1" name="password1" class="easyui-textbox" type="password" style="width:400px" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
				</tr>
			</table>
            </form>
		</div>
		<div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-saveBtn'" onclick="updatepassword()">提交</a>
		</div>
	</div>
</body>
</html>