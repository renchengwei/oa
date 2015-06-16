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
<script type="text/javascript" src="<%=basePath %>ckfinder/ckfinder.js"></script>

<script type="text/javascript" src="<%=basePath %>uploadify/jquery.uploadify.js"></script>
<script type="text/javascript">
	
// 	var data = '${param.data}';
	var editor = null;
	$(document).ready(function(){
// 		if(data == '') {//新建
// 			data = $.parseJSON(data);
// 			$('#form1').form('load', data);
// 		}
		initckeditor();
	//	initfile();
	});
	
// 	function initfile() {
// 		$("#file_upload").uploadify({
<%-- 	        swf         : '<%=basePath%>uploadify/uploadify.swf', --%>
<%-- 	        uploader    : '<%=basePath%>EmailAction.do?method=uploadFile', --%>
// 	        multi		: false,
// 	        buttonText	: '添加附件',
// 	        fileObjName	: 'file',
// 	        fileSizeLimit	: '16MB',
// 	        width		:	50,
// 	        height		:	15,
// 	        preventCaching	: true,
// 	        progressData	: 'percentage',
// 	        onUploadSuccess : function(file, data, response) {
// 	        	var result = $.parseJSON(data); 
// 	        	if(result.isSuccess) {
// 	        		var fileinfo = result.data;
// 	    			var htmlstr = "<div id='"+ fileinfo.id + "' style='margin:5px 0px 0px 5px'>" +
// 	    			"<input type='hidden' name='fileid' value='" + fileinfo.id + "'/>" + 
// 	    			fileinfo.filename + "&nbsp&nbsp" +  
// 	    			"<a style='text-decoration: none;' href='javascript:delattachment(\"" + fileinfo.id + "\")'>删除</a>" +
// 	    			"</div>";
// 	    			$('#attachmentDiv').append(htmlstr);
// 	        	}else {
// 	        		alertInfo(result.errorMsg); 
// 	        	}
// 	        },
// 	        onUploadError	: function(file , errorCode, errorMsg , errorString) {
// 	        	alertInfo(errorMsg);
// 	        },
// 	        onFallback	: function() {
// 	        	alertInfo("flash不兼容!");
// 	        }
// 	    });
// 	}
	
// 	function delattachment(id) {
// 		alert(id);		
// 	}
	
	function initckeditor() {
		editor = CKEDITOR.replace('content',{
			 toolbar:'Basic',
			 height:350,
			 width:824
		 }); 
		CKFinder.setupCKEditor(editor, '<%=basePath %>ckfinder/');
	}
	
	function savepost() {
		editor.updateElement();
		var isValid = $('#form1').form('validate');
		if (!isValid){
			return;
		}
		var content = $('#content').val();
		if(content == '') {
			alertInfo("请填写正文!");
			return;
		}
		
		postcommit('PostAction.do?method=savePost',$('#form1').serialize(),'正在发布...',function(result){
			var post = result.data;
			$('#form1').form('load', post);
			alertInfo("帖子已发布!",back);
		});
	}
	
	function back() {
		window.location.href="<%=basePath%>jsp/post/postlist.jsp";
	}
	
</script>
</head>
<body style="width:1024px;margin-left: auto;margin-right: auto;text-align: center;">
  	<div id="paneltitle" title="发布帖子" class="easyui-panel" style="padding:0px;margin:0px;text-align: center;">  
  		<div style="width:970px;padding-top:10px;margin-left: auto;margin-right: auto;">
			<form id="form1" style="margin: 0px;padding: 0px;">
           	<table style="width:100%;margin: 0xp;padding: 0px">
			<tr>
				<td style="text-align:left;width:155px;">
				
					<input id="type" class="easyui-combobox" name="type" style="width:160px" editable="false" panelHeight="auto" required="false"
    						data-options="valueField:'id',textField:'name',url:'DictionaryAction.do?method=getDictionaryByType&type=3',
    								loadFilter:function(result){ 
 									if(result) { 
 										return $.merge( [{name:'选择主题分类',id:'',selected:true}], result); 
 									} 
 								}"> 
						</input>
				</td>
				<td style="text-align:left">
					<input id="title" name="title" class="easyui-textbox" style="width:500px;" prompt="帖子主题"
						data-options="required:true,validType:'length[0,50]'"></input>
				</td>
			</tr>
			<tr>
				<td style="text-align:left" colspan="2">
					<textarea id="content" name="content"></textarea>
				</td>
			</tr>
		</table>
           <input type="hidden" id="id" name="id">
           </form>
		</div>
		<div style="text-align:center;padding-top: 5px;padding-bottom: 10px;">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-saveBtn'" onclick="savepost()">发布帖子</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-saveBtn'" onclick="back()">返回列表</a>
	</div>
	</div>
</body>
</html>