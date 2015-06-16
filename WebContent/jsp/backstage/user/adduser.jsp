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
	
	var id = '${param.id}';
	var depid = '${param.depid}';
	
	$(document).ready(function(){
		if(id == '') {//新建
			$('#pid').combobox('setValue',depid);
		}else {//修改
	 		postcommit('UserAction.do?method=getUserById',{id:id},'',function(result){
				var user = result.data;
				$('#form1').form('load', user);
				$('#loginname').textbox('disable');
			});
		}
	});
	
	function saveuser() {
		var isValid = $('#form1').form('validate');
		if (!isValid){
			return;
		}
		postcommit('UserAction.do?method=saveUser',$('#form1').serialize(),'',function(result){
			var user = result.data;
			$('#form1').form('load', user);
			$('#loginname').textbox('disable');
			alertInfo('保存成功',function() {back();});
		});
	}
	
	function back() {
		history.back();
	}
	
</script>
</head>
<body>
	<div class="easyui-panel" nohead="true" border="false">
		<div id="paneltitle" title="用户信息" class="easyui-panel" style="padding:10px 10px 10px 0px">
			<form id="form1">
            <table style="width:100%;">
				<tr>
					<td style="text-align:right;width:15%">账号:</td>
					<td style="width:85%; text-align:left">
						<input id="loginname" name="loginname" class="easyui-textbox" style="width:60%" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">昵称:</td>
					<td style="text-align:left">
						<input id="nickname" name="nickname" class="easyui-textbox" style="width:60%" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">角色:</td>
					<td style="text-align:left">
						<input id="roleid" class="easyui-combobox" name="roleid" style="width:60%" editable="false" panelHeight="auto"
    						data-options="required:true,valueField:'id',textField:'name',url:'UserAction.do?method=getRoles',loadFilter:errorFilter">
					</td>
				</tr>
				
				<tr>
					<td style="text-align:right;width:15%">密码:</td>
					<td style="width:85%; text-align:left">
						<input id="password" name="password" class="easyui-textbox" type="password" style="width:60%" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
				</tr>
				
				<tr>
					<td style="text-align:right" colspan="2">
						<hr width="100%">
					</td>				
				</tr>
				<tr>
					<td style="text-align:right">真实姓名:</td>
					<td style="text-align:left">
						<input id="name" name="name" class="easyui-textbox" style="width:60%" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right">性别:</td>
					<td style="text-align:left">
						<input id="sex1" type="radio"  name="sex" value="0" checked="checked"/>
                     	<label for="sex1">男</label>
                   	 	<input id="sex2" type="radio"  name="sex" value="1"/>
                   	 	<label for="sex2">女</label>
					</td>
				</tr>
				<tr>
					<td style="text-align:right">出生年月:</td>
					<td style="text-align:left">
						<input id="birthdate" name="birthdate" type="text" class="easyui-datebox" style="width:200px"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right">是否禁用:</td>
					<td style="text-align:left">
						<input id="available1" type="radio"  name="available" value="0" checked="checked"/>
                   	 	<label for="available1">否</label>
						<input id="available2" type="radio"  name="available" value="1"/>
                     	<label for="available2">是</label>
                   	 	
					</td>
				</tr>
				<tr>
					<td style="text-align:right">入职时间:</td>
					<td style="text-align:left">
						<input id="hiredate" name="hiredate" type="text" class="easyui-datebox" style="width:200px;"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right">所属部门:</td>
					<td style="text-align:left">
						<input id="pid" class="easyui-combobox" name="pid" style="width:60%" editable="false" panelHeight="auto"
    						data-options="valueField:'id',textField:'name',url:'DepartmentAction.do?method=getAllDepartments',loadFilter:errorFilter">
					</td>
				</tr>
				<tr>
					<td style="text-align:right">职务:</td>
					<td style="text-align:left">
						<input id="duty" class="easyui-combobox" name="duty" style="width:60%" editable="false" panelHeight="auto"
    						data-options="required:true,valueField:'id',textField:'name',url:'DictionaryAction.do?method=getDictionaryByType&type=1'">
					</td>
				</tr>
				<tr>
					<td style="text-align:right">移动电话:</td>
					<td style="text-align:left">
						<input id="mobile" name="mobile" class="easyui-textbox" style="width:60%" 
							validType="mobilevalidate" data-options="required:false"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right">办公电话:</td>
					<td style="text-align:left">
						<input id="workphone" name="workphone" class="easyui-textbox" type="text" style="width:60%" 
							validType="telephonevalidate" data-options="required:false"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right">电子邮箱:</td>
					<td style="text-align:left">
						<input id="email" name="email" class="easyui-textbox" style="width:60%" 
							validType="emailvalidate" data-options="required:false"></input>
					</td>
				</tr>
								
			</table>
            <input type="hidden" id="id" name="id">
            <input type="hidden" id="pid" name="pid">
            <input type="hidden" id="mark" name="mark">
            <input type="hidden" id="isdel" name="isdel">
            <input type="hidden" id="deltime" name="deltime">
            <input type="hidden" id="deluser" name="deluser">
            </form>
		</div>

		<div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-saveBtn'" onclick="saveuser()">保存</a>
 			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-backBtn'" onclick="back()">返回</a>
		</div>
	</div>
</body>
</html>