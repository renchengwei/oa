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
	
	var type = '${param.type}';
	var pid = '${param.pid}';
	var id = '${param.id}';
	
	$(document).ready(function(){
		if(id == '') {//新建
			$('#type').val(type);
			$('#pid').val(pid);
			
			if(type == '1') {
				$('#paneltitle').panel({'title':'分公司信息'});
			}else {
				$('#paneltitle').panel({'title':'部门信息'});
			}
		}else {//修改
	 		postcommit('DepartmentAction.do?method=getDepartmentById',{id:id},'',function(result){
				var dep = result.data;
				$('#form1').form('load', dep);
			});
		}
	});
	
	function savedep() {
		var isValid = $('#form1').form('validate');
		if (!isValid){
			return;
		}
		postcommit('DepartmentAction.do?method=saveDepartment',$('#form1').serialize(),'',function(result){
			var dep = result.data;
			$('#form1').form('load', dep);
			alertInfo('保存成功');
			window.parent.reloadTree();
		});
	}
	
</script>
</head>
<body>
	<div class="easyui-panel" nohead="true" border="false">
		<div id="paneltitle" title="" class="easyui-panel" style="padding:10px 10px 10px 0px">
			<form id="form1">
            <table style="width:100%;">
				<tr>
					<td style="text-align:right;width:15%">分公司/部门名称:</td>
					<td style="width:85%; text-align:left" colspan="3">
						<input id="name" name="name" class="easyui-textbox" style="width:70%" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td style="text-align:right;width:15%">所属机构:</td> -->
<!-- 					<td style="width:85%; text-align:left" colspan="3"> -->
<!-- 						<input id="pname" name="pname" class="easyui-textbox" style="width:90%"></input> -->
<!-- 					</td> -->
<!-- 				</tr> -->
				
				<tr>
					<td style="text-align:right;">备注:</td>
					<td style="-align:left" colspan="3">
						<input id="notes" name="notes" style="width:70%;height:50px" class="easyui-textbox" multiline="true"
							data-options="required:false,validType:'length[0,250]'"></input>
					</td>
				</tr>
            </table>
            <input type="hidden" id="id" name="id">
            <input type="hidden" id="type" name="type">
            <input type="hidden" id="pid" name="pid">
            <input type="hidden" id="isdel" name="isdel">
            <input type="hidden" id="deltime" name="deltime">
            <input type="hidden" id="deluser" name="deluser">
            </form>
		</div>

		<div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-saveBtn'" onclick="savedep()">保存</a>
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-backBtn'" onclick="back()">返回</a> -->
		</div>
	</div>
</body>
</html>