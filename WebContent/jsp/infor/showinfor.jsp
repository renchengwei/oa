<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commontaglib.jsp" %>
<style type="text/css">
	.panel-body {
		line-height: 1.6;
	}
</style>
<script type="text/javascript">
	
	var id = '${param.id}';
	$(document).ready(function(){
		var bodyHeight = document.documentElement.clientHeight;
		$('#aa').css('min-height',bodyHeight-10);
		
 		postcommit('InformationAction.do?method=getInforById',{id:id},'',function(result){
			var infor = result.data;
			$('#title').text(infor.title);
			$('#notes').append(infor.issueuserBean.name);
			$('#notes').append(infor.issuetime);
			$('#content').append(infor.content);
		});
		
		/* $('.panel easyui-fluid').css({""}); */
		
		
		//initckeditor();
	});
	
	function back() {
		window.location.href="<%=basePath%>jsp/infor/inforlist.jsp";
	}
	
</script>
</head>
<body style="text-align: center;width:1020px;margin-left: auto;margin-right: auto;margin-top: 0px;height: 100%;">
	<div id="aa" class="easyui-panel">
		<div style="height: 30px;padding: 5px;margin: 0px;background-color: #D4D4D4;">
			<a href="javascript:void(0)" class="easyui-linkbutton c2" style="padding-left: 5px;padding-right: 5px;margin-top: 0px;"
			 onclick="back();">返回列表</a>
		</div>
		<div>
			<h1 id="title" style="text-align: center;margin-bottom: 0px;"></h1>
			<div id="notes" style="width:98%;text-align: right;padding-top: 0px;">
			</div>
			<hr style="width:99%"/>
		</div>
		<div id="content" style="padding: 0px;width: 960px;margin-left: auto;margin-right: auto;overflow: hidden;">
		</div>
	</div>
</body>
</html>