<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commontaglib.jsp" %>
<script type="text/javascript" src="<%=basePath %>ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=basePath %>ckfinder/ckfinder.js"></script>
<script type="text/javascript">
	
	var id = '${param.id}';
	var editor = null;
	$(document).ready(function(){
		if(id == '') {//新建
		}else {//修改
	 		postcommit('InformationAction.do?method=getInforById',{id:id},'',function(result){
				var infor = result.data;
				$('#form1').form('load', infor);
			});
		}
		initckeditor();
	});
	
	function initckeditor() {
		editor = CKEDITOR.replace('content',{
			 toolbar:'Basic',
			 height:350,
			 width:960
		 }); 
	}
	
	function saveinfor() {
		editor.updateElement();
		
		var isValid = $('#form1').form('validate');
		if (!isValid){
			return;
		}
		var content = $('#content').val();
		if(content == '') {
			alertInfo("请填写发布内容!");
			return;
		}
		
		postcommit('InformationAction.do?method=saveInfor',$('#form1').serialize(),'',function(result){
			var infor = result.data;
			$('#form1').form('load', infor);
			alertInfo('保存成功',back);
		});
	}
	
	function back() {
		history.back();
	}
	
</script>
</head>
<body>
	<div class="easyui-panel" nohead="true" border="false">
		<div id="paneltitle" title="通知公告" class="easyui-panel" style="padding:10px 10px 10px 0px">
			<form id="form1">
            <table style="width:100%;">
				<tr>
					<td style="text-align:right;width:80px;min-width: 60px;">标题:</td>
					<td style="text-align:left" colspan="3">
						<input id="title" name="title" class="easyui-textbox" style="width:960px;" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">发布范围:</td>
					<td style="text-align:left">
						<input id="depids" class="easyui-combobox" name="depids" style="width:300px;" editable="false" panelHeight="auto"
    						data-options="valueField:'id',textField:'name',required:true,multiple:true,
     							url:'DepartmentAction.do?method=getAllDepartments',loadFilter:errorFilter"> 
					</td>
					<td style="text-align:right;">截止时间:</td>
					<td style="text-align:left">
						<input id="endtime" name="endtime" type="text" class="easyui-datebox" style="width:300px"
							data-options="required:true"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">内容:</td>
					<td style="text-align:left" colspan="3">
						<textarea id="content" name="content"></textarea>
					</td>
				</tr>
			</table>
            <input type="hidden" id="id" name="id">
            <input type="hidden" id="isdel" name="isdel">
            <input type="hidden" id="deltime" name="deltime">
            <input type="hidden" id="deluser" name="deluser">
            </form>
		</div>
		<div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-saveBtn'" onclick="saveinfor()">保存</a>
 			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-backBtn'" onclick="back()">返回</a>
		</div>
	</div>
</body>
</html>