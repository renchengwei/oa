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
		postcommit('TaskAaction.do?method=getTaskById',{id:id,flag:'receive'},'',function(result){
			
			var task = result.data;
			
			if(task.status == '1') {
				$('#btn1').css('display','');
				$('#btn2').css('display','');
				$('#btn3').css('display','');
			}
			
			if(task.issueuserbean) {
				$('#issueuser').html(task.issueuserbean.name);
			}
			if(task.levelname) {
				$('#levelname').html(task.levelname);
			}
			if(task.issuetime) {
				$('#issuetime').html(task.issuetime);
			}
			if(task.endtime) {
				$('#endtime').html(task.endtime);
			}
			if(task.transactorbean) {
				$('#transactor').html(task.transactorbean.name);
			}
			if(task.title) {
				$('#tasktitle').html(task.title);
			}
			if(task.content) {
				$('#taskcontent').html(task.content);
			}
			if(task.progresslist && task.progresslist.length > 0) {
				var progresslist = task.progresslist;
				for(var i in progresslist) {
					addProgress(progresslist[i]);
				}
			}
			
		});
	});

	//添加任务进度
	function addProgress(progress) {
		var div = '<div id=' + progress.id + ' style="overflow-x:hidden;margin-top:10px;border-top: 0px;">';
		div += '<hr style="width:100%;padding: 0px;margin: 0px;"></hr>';
		div += '<div style="width:100%;background-color:#EBEBEB;padding: 5px;">工作进度：' + progress.writeusername + '&nbsp;&nbsp;&nbsp;' + progress.writetime + '</div>';
		div += '<div style="width:100%;padding: 5px;border: 0px;">' + progress.content + '</div>';
		div += '</di>';
		$('body').append(div)
		$('#' + progress.id).panel({   
			noheader:true
		});  
	}
	
	function back() {
		window.location.href="<%=basePath%>jsp/task/receivetasklist.jsp";
	}
	//下发任务
	function distributiontask() {
		window.location.href="<%=basePath%>jsp/task/newtask.jsp?flag=distribution&id=" + id;
	}
	
	function addTaskProgress() {
		window.location.href="<%=basePath%>jsp/task/newtaskprogress.jsp?id=" + id;
	}
	
	function taskOK() {
		postcommit('TaskAaction.do?method=updateTaskOK',{id:id},'',function(result){
			alertInfo('任务已修改!',back);
		});
	}
	
</script>
</head>
<body style="margin-top: 1px;margin-left: 5px;margin-right: 7px;">
	<div class="easyui-panel" nohead="true" style="overflow-x:hidden;">
	
		<div style="height: 30px;padding: 5px;margin: 0px;background-color: #D4D4D4;">
			<a href="javascript:void(0)" class="easyui-linkbutton c2" style="padding-left: 5px;padding-right: 5px;margin-top: 0px;"
			 onclick="back();">返回列表</a>
			 
			<a id="btn1" href="javascript:void(0)" class="easyui-linkbutton c2" style="padding-left: 5px;padding-right: 5px;margin-top: 0px;display: none;"
			 onclick="addTaskProgress();">添加进度</a>
			 
			<a id="btn2" href="javascript:void(0)" class="easyui-linkbutton c2" style="padding-left: 5px;padding-right: 5px;margin-top: 0px;display: none;"
			 onclick="taskOK();">办理完成</a>
			 
			<a id="btn3" href="javascript:void(0)" class="easyui-linkbutton c2" style="padding-left: 5px;padding-right: 5px;margin-top: 0px;display: none;"
			 onclick="distributiontask();">下发任务</a>
		
		</div>
<!-- 		<div style="width:100%;height:30px;background-color:#D4D4D4;padding-left: 5px;"> -->
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="back()" data-options="plain:false">返回</a> -->
<!--         	<a id="btn1" href="javascript:void(0)" class="easyui-linkbutton" style="display: none" onclick="addTaskProgress()" data-options="plain:false">添加工作进度</a> -->
<!--         	<a id="btn2" href="javascript:void(0)" class="easyui-linkbutton" style="display: none" onclick="taskOK()" data-options="plain:false">任务办理完成</a> -->
<!-- 			<a id="btn3" href="javascript:void(0)" class="easyui-linkbutton" style="display: none" onclick="distributiontask()" data-options="plain:false">下发任务</a> -->
<!-- 		</div> -->
 		<hr style="width:100%;padding: 0px;margin: 0px;"></hr>
		<div style="width:100%;background-color:#EBEBEB;text-align: center;padding: 5px;">
				<span id="tasktitle" style="font-size: 20px;"></span>
			<div style="width:100%;text-align: left;padding-top: 15px;">
				下发人：<span id="issueuser" style="padding-right: 10px;"></span>
				下发时间：<span id="issuetime" style="padding-right: 10px;"></span>
				紧急程度：<span id="levelname" style="padding-right: 10px;"></span>
				截止时间：<span id="endtime" style="padding-right: 10px;"></span>
				负责人：<span id="transactor" style="padding-right: 10px;"></span>
			</div>
		</div>
		<div id="taskcontent" style="width:99%;padding: 5px;overflow: hidden;">

		</div>
	</div>
<!-- 	<div class="easyui-panel" nohead="true" style="overflow-x:hidden;margin-top:10px;border-top: 0px;	"> -->
<!-- 		<hr style="width:100%;padding: 0px;margin: 0px;"></hr> -->
<!-- 		<div style="width:100%;background-color:#EBEBEB;padding: 5px;"> -->
<!-- 			工作进度：任成威	2010-39-34 -->
<!-- 		</div> -->
<!-- 		<div style="width:100%;padding: 5px;border: 0px;"> -->
<!-- 			干的不错 -->
<!-- 		</div> -->
<!-- 	</div> -->
</body>
</html>