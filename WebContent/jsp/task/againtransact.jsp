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
<script type="text/javascript">
	
	var id = '${param.id}';
	var editor = null;
	$(document).ready(function(){
		$('#taskid').val(id);
		initckeditor();
	});
	
	function initckeditor() {
		editor = CKEDITOR.replace('content',{
			 toolbar:'Basic',
			 height:300,
			 width:960
		 }); 
	}
	
	function updateTaskAgainDo() {
		editor.updateElement();
		
		var content = $('#content').val();
		if(content == '') {
			alertInfo("请填写重办原因!");
			return;
		}
		
		postcommit('TaskAaction.do?method=updateTaskAgainDo',$('#form1').serialize(),'',function(result){
			alertInfo('任务已修改!',back);
		});
	}
	
	function back() {
		window.location.href="<%=basePath%>jsp/task/showsendtask.jsp?id=" + id;
	}
</script>
</head>
<body>
	<div class="easyui-panel" nohead="true" border="false">
		<div id="paneltitle" title="重新办理" class="easyui-panel" style="padding:10px 10px 10px 0px">
			<form id="form1">
            <table style="width:100%;">
            	<tr>
					<td style="text-align:right;width:50px;">原因:</td>
					<td style="text-align:left">
						<textarea id="content" name="content"></textarea>
					</td>
				</tr>
				
			</table>
			<input type="hidden" id="taskid" name="taskid"/>
            </form>
		</div>

		<div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-saveBtn'" onclick="updateTaskAgainDo()">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-backBtn'" onclick="back()">返回</a>
		</div>
	</div>
</body>
</html>