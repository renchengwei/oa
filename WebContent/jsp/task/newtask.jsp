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
			enable :true,
			chkStyle : 'radio',
			radioType : 'all'
		},
		callback: {
			onCheck: onCheck
		}
	};
	
	var id = '${param.id}';
	var flag = '${param.flag}';
	
	var editor = null;
	$(document).ready(function(){
		//下发
		if(flag == 'distribution') {
			postcommit('TaskAaction.do?method=distributionTask',{id:id},'',function(result){
				var data = result.data;
				$('#form1').form('load', data);
			});
		}
		
		initckeditor();
		initUserCombotree();
	});
	
	function onCheck() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		
		if(nodes != null && nodes.length > 0) {
			$('#transactor').val(nodes[0].id);
			$('#transactorname').val(nodes[0].name);
		}else {
			$('#transactor').val('');
			$('#transactorname').val('');
		}
	
		$('#transactorname').focus();
	}
	
	function initUserCombotree() {
		$.post("UserAction.do?method=getTreeDepUser",function(result){
			if(result.isSuccess) {
				var data = result.data;
				for(var i in data) {
					if(data[i].type != '3') {
						data[i].nocheck = true;
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
	
	function initckeditor() {
		editor = CKEDITOR.replace('content',{
			 toolbar:'Basic',
			 height:300,
			 width:960
		 }); 
	}
	
	function savetask() {
		editor.updateElement();
		var isValid = $('#form1').form('validate');
		if (!isValid){
			return;
		}
		var content = $('#content').val();
		if(content == '') {
			alertInfo("请填写任务内容!");
			return;
		}
		
		postcommit('TaskAaction.do?method=saveTask',$('#form1').serialize(),'',function(result){
			alertInfo('任务已下发!',function(){
				window.location.href="<%=basePath%>jsp/task/newtask.jsp";
			});
		});
	}
	
	//选择收件人
	function selectReceiveUsers() {
		$('#dd').dialog('open');  // open a window   
	}
	
</script>
</head>
<body>
	<div class="easyui-panel" nohead="true" border="false">
		<div id="paneltitle" title="新建任务" class="easyui-panel" style="padding:10px 10px 10px 0px">
			<form id="form1">
            <table style="width:100%;">
            	<tr>
					<td style="text-align:right;min-width:50px;">标题:</td>
					<td style="text-align:left" colspan="5">
						<input id="title" name="title" class="easyui-textbox" style="width:960px;" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">负责人:</td>
					<td style="text-align:left">
						<input id="transactorname" name="transactorname" class="easyui-validatebox" style="width:200px;"
							data-options="required:true" onclick="selectReceiveUsers();"></input>
					</td>
					<td style="text-align:right;">紧急程度:</td>
					<td style="text-align:left">
						<input id="level" class="easyui-combobox" name="level" style="width:200px" editable="false" panelHeight="auto"
    						data-options="required:true,valueField:'id',textField:'name',url:'DictionaryAction.do?method=getDictionaryByType&type=2'">
					</td>
					<td style="text-align:right;">截止时间:</td>
					<td style="text-align:left">
						<input id="endtime" name="endtime" style="width:200px;" class="easyui-datebox" required="required"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">内容:</td>
					<td style="text-align:left" colspan="5">
						<textarea id="content" name="content"></textarea>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;"></td>	
					<td style="text-align:left" colspan="5"	>
						<input id="ismsg" name="ismsg" type="checkbox" checked="checked" value="1">
						<label for="ismsg">短信通知</label>
					</td>
				</tr>
			</table>
            <input type="hidden" id="id" name="id">
            <input type="hidden" id="transactor" name="transactor">
            </form>
		</div>

		<div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-saveBtn'" onclick="savetask()">下发任务</a>
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-saveBtn'" onclick="saveinfor()">存草稿</a> -->
		</div>
	</div>
	<div id="dd" class="easyui-dialog" title="选择负责人" style="width:300px;height:450px;"  
        data-options="resizable:false,modal:true,closed: true,buttons:'#dlg-buttons'">  
        <ul id="treeDemo" class="ztree"></ul>
	</div>
	<div id="dlg-buttons" style="text-align: center;">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-closeBtn'"
        	onclick="javascript:$('#dd').dialog('close')">关闭</a>
    </div>
</body>
</html>