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
	
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid",
				rootPId : "0"
			}
		},
		view : {
			showLine: false,
			dblClickExpand : true
		},
		check : {
			enable : true
		},
		callback: {
			onCheck: onCheck
		}
	};
	

	var id = '${param.id}';
	$(document).ready(function(){
		if(id == '') {//新建
		}else {//修改
	 		postcommit('MessageAction.do?method=getMessageById',{id:id},'',function(result){
				var message = result.data;
				$('#form1').form('load', message);
			});
		}
		initUserCombotree();
	});
	
	function onCheck() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		
		var receiveusers = '';
		if(nodes != null && nodes.length > 0) {
			for(var i in nodes) {
				if(nodes[i].type == '3' && nodes[i].mobile != '') {
					receiveusers += nodes[i].mobile + "(" + nodes[i].name + ");";
				}
			}
		}
		$('#receiveusers').textbox('setText',receiveusers);
		$('#receiveusers').textbox('validate');
	}
	
	function initUserCombotree() {
		$.post("UserAction.do?method=getTreeDepUser",function(result){
			if(result.isSuccess) {
				var data = result.data;
				var receiveusers = $('#receiveusers').val();
				
				//判断是否选中
				for(var i in data) {
					if(data[i].type == '3' && (receiveusers.indexOf(data[i].mobile)) != 0) {
						data[i].checked=true;
					}
					if(data[i].type != '3') {
						data[i].icon = '<%=basePath%>icon/dep.png';
						data[i].isParent = true;
					}else {
						data[i].icon = '<%=basePath%>icon/user1.png';
					}
				}
 				treeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
 				treeObj.expandAll(true);
			}else {
				alertInfo(result.errorMsg);
			}
	    },'json');
	} 
	
	function selectuser() {
		$('#dd').dialog('open');  // open a window
	}
	
	function savemessage(status) {
		$('#status').val(status);
		
		var isValid = $('#form1').form('validate');
		if (!isValid){
			return;
		}

		postcommit('MessageAction.do?method=saveMessage',$('#form1').serialize(),'',function(result){
			var message = result.data;
			$('#form1').form('load', message);
			if(status == '0') {
				alertInfo('信息已保存',back);
			}else {
				alertInfo('信息已发送',back);
			}
			
		});
	}
	
	function back() {
		window.location.href="<%=basePath%>jsp/backstage/message/messagelist.jsp";
	}
	
</script>
</head>
<body>
	<div class="easyui-panel" nohead="true" border="false">
		<div id="paneltitle" title="新短信" class="easyui-panel" style="padding:10px 10px 10px 10px">
			<form id="form1">
            <table style="width:100%;">
				<tr>
					<td style="text-align:right;width:80px;min-width: 50px;">收件人:</td>
					<td style="text-align:left">
						<input id="receiveusers" name="receiveusers" class="easyui-textbox" style="width:95%;" 
							data-options="required:true"></input>
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectuser()" style="height: 20px;width: 20px;">+</a>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">内容:</td>
					<td style="text-align:left">
						<input id="content" name="content" class="easyui-textbox" style="width:95%;height: 350px;" multiline="true" 
							data-options="required:true"></input>
					</td>
				</tr>
			</table>
            <input type="hidden" id="id" name="id">
            <input type="hidden" id="status" name="status">
            <input type="hidden" id="sendtime" name="sendtime">
            <input type="hidden" id="senduser" name="senduser">
            <input type="hidden" id="isdel" name="isdel">
            <input type="hidden" id="deltime" name="deltime">
            <input type="hidden" id="deluser" name="deluser">
            </form>
		</div>

		<div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-saveBtn'" onclick="savemessage(1)">发送</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-zcBtn'" onclick="savemessage(0)">存草稿</a>
 			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-backBtn'" onclick="back()">返回</a>
		</div>
	</div>
	<div id="dd" class="easyui-dialog" title="选择收件人" style="width:300px;height:450px;"  
        data-options="resizable:false,modal:true,closed: true">  
        <ul id="treeDemo" class="ztree"></ul>
	</div>
</body>
</html>