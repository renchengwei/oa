<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commontaglib.jsp" %>
<style type="text/css">
	.ImageDivStyle {
		width:110px;
		height: 110px;
		border: 1px solid #CFCFCF;
		text-align: center;
		vertical-align: middle;
		display: table-cell;
	}
	.ImageStyle {
	    max-height:100px; 
	    max-width:100px;
	    border: 0px;
	    vertical-align: middle;
	    margin-left: auto;
	    margin-right: auto;
	}
</style>
<script type="text/javascript">
	
	$(document).ready(function(){
		var isSuccess = '${success}';
		if(isSuccess == 'true') {
			alertInfo('保存成功!');
		}else if(isSuccess == 'false'){
			var errormsg = '${errorMsg}';
			alertInfo(errormsg);
		}
		
 		postcommit('UserAction.do?method=getCurrentUser','','',function(result){
			var user = result.data;
			$('#form1').form('load', user);
			$('#imgPre').attr('src','<%=basePath%>userphoto/' + user.photo);
			$('#loginname').textbox('disable');
		});
	});
	
	/** 
	* 将本地图片 显示到浏览器上 
	*/ 
	function preImg(sourceId, targetId) { 
		if (typeof FileReader === 'undefined') {  
			alertInfo("您的浏览器不支持FileReader，请升级最新版本...");
			return;  
		}
		
		var reader = new FileReader();  
		reader.onload = function(e) {  
			var img = document.getElementById(targetId);  
			img.src = this.result;  
		}  
		var patn = /\.png$|\.bmp$|\.jpg$|\.jpeg$|\.gif$/i;
		var sourceObj = document.getElementById(sourceId);
		if(patn.test(sourceObj.files[0].name)) {
			reader.readAsDataURL(sourceObj.files[0]);
		}else {
			alertInfo("您选择的似乎不是图像文件。");
			$('#' + targetId).attr('src','');
			$('#' + sourceId).replaceWith('<input id="' + sourceId + '" name="' + sourceId + '" type="file" style="display: none;" onchange="preImg(this.id,\'imgPre\')" accept=".png,.emp,.jpg,.jpeg,.gif"/>');
		}
	}
		
	function saveuser() {
		var isValid = $('#form1').form('validate');
		if (!isValid){
			return;
		}
		$("#form1").submit();
	}
</script>
</head>
<body style="width:1024px;margin-left: auto;margin-right: auto;">
	<div class="easyui-panel" nohead="true" fit="true" style="border: 0px;">
		<div id="paneltitle" title="个人资料" class="easyui-panel" style="padding:10px 10px 10px 0px">
			<form id="form1" method="post" action="<%=basePath%>UserAction.do?method=updateUser" enctype="multipart/form-data">
            <table >
				<tr>
					<td style="text-align:right;width:150px">账号:</td>
					<td style="text-align:left;" >
						<input id="loginname" name="loginname" class="easyui-textbox" style="width:400px;" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
					<td style="text-align: left;padding-left: 50px;" rowspan="6" valign="top">
						<input id="photofile" name="photofile" type="file" accept=".png,.emp,.jpg,.jpeg,.gif" style="display: none;" onchange="preImg(this.id,'imgPre')">
						<div class="ImageDivStyle">
							<img id="imgPre" alt="请上传头像" src="" class="ImageStyle">
						</div>
						<div style="text-align: center;">
							<label for="photofile" style="cursor: pointer;color: green">上传头像(100*100)</label>
						</div>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">昵称:</td>
					<td style="text-align:left;width: auto;" colspan="2">
						<input id="nickname" name="nickname" class="easyui-textbox" style="width:400px" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right">真实姓名:</td>
					<td style="text-align:left;width: auto;" colspan="2">
						<input id="name" name="name" class="easyui-textbox" style="width:400px" 
							data-options="required:false,validType:'length[0,50]'"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right">性别:</td>
					<td style="text-align:left;width: auto;" colspan="2">
						<input id="sex1" type="radio"  name="sex" value="0" checked="checked"/>
                     	<label for="sex1">男</label>
                   	 	<input id="sex2" type="radio"  name="sex" value="1"/>
                   	 	<label for="sex2">女</label>
					</td>
				</tr>
				<tr>
					<td style="text-align:right">出生年月:</td>
					<td style="text-align:left" colspan="2">
						<input id="birthdate" name="birthdate" type="text" class="easyui-datebox" style="width:200px"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right">移动电话:</td>
					<td style="text-align:left" colspan="2">
						<input id="mobile" name="mobile" class="easyui-textbox" style="width:400px" 
							validType="mobilevalidate" data-options="required:false"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right">办公电话:</td>
					<td style="text-align:left" colspan="2">
						<input id="workphone" name="workphone" class="easyui-textbox" type="text" style="width:400px" 
							validType="telephonevalidate" data-options="required:false"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right">电子邮箱:</td>
					<td style="text-align:left" colspan="2">
						<input id="email" name="email" class="easyui-textbox" style="width:400px" 
							validType="emailvalidate" data-options="required:false"></input>
					</td>
				</tr>
								
			</table>
            <input type="hidden" id="id" name="id">
            </form>
		</div>
		<div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-saveBtn'" onclick="saveuser()">保存</a>
		</div>
	</div>
</body>
</html>