<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commontaglib.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath %>uploadify/uploadify.css">
<script type="text/javascript" src="<%=basePath %>ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=basePath %>uploadify/jquery.uploadify.min.js"></script>
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
    var flag = '${param.flag}';
    var receiveusers = '';
	var editor = null;
	$(document).ready(function(){
		//转发
		if(flag == 'transmit'){
			postcommit('EmailAction.do?method=transmitEmail',{id:id},'',function(result){
				var data = result.data;
				initUserCombotree();
				$('#form1').form('load', data);
				var attachments = data.files;
				if(attachments != null && attachments.length > 0) {
					initattachment(attachments);
				}
				
			});
		}else if(flag == 'update'){//修改
			postcommit('EmailAction.do?method=getSendEmailById',{id:id},'',function(result){
				var data = result.data;
				receiveusers = data.receiveuserid;
				initUserCombotree();
				$('#form1').form('load', data);
				var attachments = data.files;
				if(attachments != null && attachments.length > 0) {
					initattachment(attachments);
				}
			});
		}else if(flag == 'reply') {//回复
			postcommit('EmailAction.do?method=replyReceiveEmail',{id:id},'',function(result){
				var data = result.data;
				receiveusers = data.receiveuserid;
				initUserCombotree();
				$('#form1').form('load', data);
				var attachments = data.files;
				if(attachments != null && attachments.length > 0) {
					initattachment(attachments);
				}
			});
		}else {
			initUserCombotree();
		}
		initckeditor();
		initfile();
	});
	//初始化附件信息
	function initattachment(attachments) {
		for(var i in attachments) {
			var file = attachments[i];
			var htmlstr = "<div id='"+ file.id + "' style='margin:5px 0px 0px 5px'>" +
			"<input type='hidden' name='fileid' value='" + file.id + "'/>" + 
			file.filename + "&nbsp&nbsp" +  
			"<a style='text-decoration: none;' href='javascript:delattachment(\"" + file.id + "\")'>删除</a>" +
			"</div>";
			$('#attachmentDiv').append(htmlstr);
		}
	}
	
	function initfile() {
	//延时防止客户端缓存文件，造成uploadify.js不更新，而引起的“喔唷，崩溃啦”
	    setTimeout(function(){
	    	$("#file_upload").uploadify({
		        swf         : '<%=basePath%>uploadify/uploadify.swf',
		        uploader    : '<%=basePath%>EmailAction.do?method=uploadFile',
		        multi		: false,
		        buttonText	: '添加附件',
		        fileObjName	: 'file',
		        fileSizeLimit	: '100MB',
		        width		:	50,
		        height		:	15,
		        preventCaching	: true,
		        progressData	: 'percentage',
		        onUploadSuccess : function(file, data, response) {
		        	var result = $.parseJSON(data); 
		        	if(result.isSuccess) {
		        		var fileinfo = result.data;
		    			var htmlstr = "<div id='"+ fileinfo.id + "' style='margin:5px 0px 0px 5px'>" +
		    			"<input type='hidden' name='fileid' value='" + fileinfo.id + "'/>" + 
		    			fileinfo.filename + "&nbsp&nbsp" +  
		    			"<a style='text-decoration: none;' href='javascript:delattachment(\"" + fileinfo.id + "\")'>删除</a>" +
		    			"</div>";
		    			$('#attachmentDiv').append(htmlstr);
		        	}else {
		        		alertInfo(result.errorMsg); 
		        	}
		        },
		        onUploadError	: function(file , errorCode, errorMsg , errorString) {
		        	alertInfo(errorMsg);
		        },
		        onFallback	: function() {
		        	alertInfo("flash不兼容!");
		        }
		    });
	    },10);
	}
	
	function delattachment(id) {
		postcommit('EmailAction.do?method=deleteEmailFile',{id:id},'',function(result){
			$("#"+id).remove();			
		});
	}
	
	function onCheck() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		
		var receiveuserid = '';
		var receiveusers = '';
		if(nodes != null && nodes.length > 0) {
			for(var i in nodes) {
				if(nodes[i].type == '3') {
					receiveuserid += nodes[i].id + ";";
					receiveusers += nodes[i].name + ";";
				}
			}
		}
		$('#receiveuserid').val(receiveuserid);
		$('#receiveusernames').val(receiveusers);
		$('#receiveusernames').focus();
	}
	
	function initUserCombotree() {
		var ids = [];
		if(receiveusers != '') {
			ids = receiveusers.split(';');
		}
		
		$.post("UserAction.do?method=getTreeDepUser",function(result){
			if(result.isSuccess) {
				var data = result.data;
				var receiveusers = '';
				var receiveuserid = '';
				//判断是否选中
				for(var i in data) {
					if(data[i].type != '3') {
						data[i].icon = '<%=basePath%>icon/dep.png';
						data[i].isParent = true;
					}else {
						data[i].icon = '<%=basePath%>icon/user1.png';
					}
					for(var j in ids) {
						if(data[i].id == ids[j]) {
							data[i].checked=true;
							receiveusers += data[i].name + ";";
							receiveuserid += data[i].id + ";";
						}
					}
				}
				$('#receiveuserid').val(receiveuserid);
				$('#receiveusernames').val(receiveusers);
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
	
	function saveemail(status) {
		$('#status').val(status);
		editor.updateElement();
		var isValid = $('#form1').form('validate');
		if (!isValid){
			return;
		}
		var content = $('#content').val();
		if(content == '') {
			alertInfo("请填写邮件正文!");
			return;
		}
		
		postcommit('EmailAction.do?method=saveEmail',$('#form1').serialize(),'正在发送中...',function(result){
			var email = result.data;
			window.location.href="<%=basePath%>jsp/email/sendsuccess.jsp?id=" + email.id + '&status=' + email.status;
		});
	}
	//选择收件人
	function selectReceiveUsers() {
		$('#dd').dialog('open');  // open a window   
	}
	
	function back() {
		history.back();
	}
	
</script>
</head>
<body>
	<div class="easyui-panel" nohead="true" border="false">
		<div id="paneltitle" title="写邮件" class="easyui-panel" style="padding:10px 10px 10px 0px">
			<form id="form1">
            <table style="width:100%;">
				<tr>
					<td style="text-align:right;min-width:50px;">收件人:</td>
					<td style="text-align:left">
					<input id="receiveusernames" name="receiveusernames" class="easyui-validatebox" style="width:960px;"
							data-options="required:true" onclick="selectReceiveUsers();"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">标题:</td>
					<td style="text-align:left">
						<input id="title" name="title" class="easyui-textbox" style="width:960px;" 
							data-options="required:true,validType:'length[0,50]'"></input>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;"></td>
					<td style="text-align:left">
						<input id="file_upload" name="file_upload" type="file">
					</td>
				</tr>
				<tr>
					<td style="text-align:right;"></td>
					<td style="text-align:left">
						<div id="attachmentDiv" style="background-color: #F5F5F5;width:960px;">
                		</div>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;">正文:</td>
					<td style="text-align:left">
						<textarea id="content" name="content"></textarea>
					</td>
				</tr>
				<tr>
					<td style="text-align:right;"></td>
					<td style="text-align:left">
						<input id="ismsg" name="ismsg" type="checkbox" checked="checked" value="1">
						<label for="ismsg">短信通知</label>
					</td>
				</tr>
			</table>
            <input type="hidden" id="id" name="id">
            <input type="hidden" id="receiveuserid" name="receiveuserid">
            <input type="hidden" id="isdel" name="isdel">
            <input type="hidden" id="deltime" name="deltime">
            <input type="hidden" id="deluser" name="deluser">
            <input type="hidden" id="status" name="status">
            </form>
		</div>

		<div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-saveBtn'" onclick="saveemail(1)">发送</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-zcBtn'" onclick="saveemail(0)">存草稿</a>
		</div>
	</div>
	<div id="dd" class="easyui-dialog" title="选择收件人" style="width:300px;height:450px;"  
        data-options="resizable:false,modal:true,closed: true,buttons:'#dlg-buttons'">  
        <ul id="treeDemo" class="ztree"></ul>
	</div>
	 <div id="dlg-buttons" style="text-align: center;">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-closeBtn'"
        	onclick="javascript:$('#dd').dialog('close')">关闭</a>
    </div>
</body>
</html>