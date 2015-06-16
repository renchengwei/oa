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
	var editor = null;
	$(document).ready(function(){
		postcommit('EmailAction.do?method=getSendEmailById',{id:id},'',function(result){
			
			var email = result.data;
			
			if(email.emailtitle) {
				$('#emailtitle').html(email.emailtitle);
			}
			if(email.senduserbean) {
				$('#senduser').html(email.senduserbean.name);
			}
			
			if(email.sendtime) {
				$('#sendtime').html(email.sendtime);
			}
			if(email.receiveusernames) {
				$('#receiveusernames').html(email.receiveusernames);
			}
			
			if(email.content) {
				$('#emailcontent').html(email.content);
			}
			//处理附件
			if(email.files && email.files.length > 0) {
				initattachment(email.files);
			}
		});
	});

	//处理附件
	function initattachment(files) {
		$('#attachment').empty();
		$('#attachment').panel('setTitle','附件（' + files.length + '）');
 		$('#attachmentdiv').css('display','');
		for(var i in files) {
			var file = files[i];
			var div = '<div style="width:100%;padding:5px;"><span style="font-size:14px;">' + file.filename + 
						'</span><a href="javascript:void(0)" onclick="downloadfile(\'' + file.id +  
						'\')" style="padding-left: 5px;text-decoration: none;font-size: 14px;">下载</a></div>';
			$('#attachment').append(div);
		}
	}
	//下载附件
	function downloadfile(id) {
		window.open('<%=basePath%>EmailAction.do?method=downloadfile&id=' + id);
	}
	
	function back() {
		window.location.href="<%=basePath%>jsp/email/sendemaillist.jsp";
	}
	
	//转发email
	function transmitemail() {
		window.location.href="<%=basePath%>jsp/email/newemail.jsp?flag=transmit&id=" + id;
	}
	
	//删除邮件
	function deleteemail() {
 		$.messager.confirm('确认','邮件删除后不可恢复,您确认想要删除记录吗？',function(r){    
		    if (r){ 
		    	postcommit('EmailAction.do?method=deleteSendEmails',{ids:id},'',function(result){
		    		alertInfo("邮件已删除!",back);
				});
		    }    
		}); 
	}
</script>
</head>
<body style="margin-top: 1px;margin-left: 5px;margin-right: 7px;">
	<div class="easyui-panel" nohead="true" style="overflow-x:hidden;">
		<div style="height: 30px;padding: 5px;margin: 0px;background-color: #D4D4D4;">
			<a href="javascript:void(0)" class="easyui-linkbutton c2" style="padding-left: 5px;padding-right: 5px;margin-top: 0px;"
			 onclick="back();">返回列表</a>
			 
			 <a href="javascript:void(0)" class="easyui-linkbutton c2" style="padding-left: 5px;padding-right: 5px;margin-top: 0px;"
			 onclick="transmitemail();">转发邮件</a>
			 
			 <a href="javascript:void(0)" class="easyui-linkbutton c2" style="padding-left: 5px;padding-right: 5px;margin-top: 0px;"
			 onclick="deleteemail();">删除邮件</a>
			 
			 
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="back()" data-options="plain:false">返回</a> -->
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="transmitemail()" data-options="plain:false">转发</a> -->
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="deleteemail()" data-options="plain:false">删除</a> -->
<!--         	<a id="btn1" href="javascript:void(0)" class="easyui-linkbutton" style="display: none" onclick="toagaintransact()" data-options="plain:false">重新办理</a> -->
<!--         	<a id="btn2" href="javascript:void(0)" class="easyui-linkbutton" style="display: none" onclick="taskfinish()" data-options="plain:false">办理完结</a> -->
		</div>
 		<hr style="width:100%;padding: 0px;margin: 0px;"></hr>
		<div style="width:100%;background-color:#EBEBEB;padding: 10px;">
			<div id="emailtitle" style="font-size: 20px;"></div>
			<div>
				<font color="green">发件人：</font>
				<font id="senduser"></font>
			</div>
			<div>
				<font color="green">时&nbsp;&nbsp;&nbsp;&nbsp;间：</font>
				<font id="sendtime"></font>
			</div>
			<div>
				<font color="green">收件人：</font>
				<font id="receiveusernames"></font>
			</div>
		</div>
		<hr style="width:100%;padding: 0px;margin: 0px;"></hr>
		<div id="emailcontent" style="width:99%;padding: 10px;min-height: 100px;overflow: hidden;">
		</div>
		<div id="attachmentdiv" style="width: 100%;display: none;">
			<div id="attachment" title="附件" class="easyui-panel" style="padding:10px;width: 100%;">
			
			</div>
		</div>
		
	</div>
</body>
</html>