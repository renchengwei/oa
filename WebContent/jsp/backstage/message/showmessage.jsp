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
	$(document).ready(function(){
 		postcommit('MessageAction.do?method=getMessageById',{id:id},'',function(result){
			var message = result.data;
			$('#form1').form('load', message);
		});
	});
	function back() {
		window.location.href="<%=basePath%>jsp/backstage/message/messagelist.jsp";
	}
	
</script>
</head>
<body>
	<div class="easyui-panel" nohead="true" border="false">
		<div id="paneltitle" title="已发短信" class="easyui-panel" style="padding:10px 10px 10px 0px">
			<form id="form1">
            <table style="width:100%;">
				<tr>
					<td style="text-align:right;width:80px">收件人:</td>
					<td style="text-align:left">
						<input id="receiveusers" name="receiveusers" class="easyui-textbox" style="width:960px;" 
							data-options="required:false,readonly:true"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;width:80px;min-width: 40px;">内容:</td>
					<td style="text-align:left">
						<input id="content" name="content" class="easyui-textbox" style="width:960px;height: 350px;" multiline="true" 
							data-options="required:false,readonly:true"></input>
					</td>
				</tr>
			</table>
            </form>
		</div>

		<div style="text-align:center;padding:5px">
 			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-backBtn'" onclick="back()">返回</a>
		</div>
	</div>
</body>
</html>