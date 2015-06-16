<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ include file="/commontaglib.jsp" %>
<script type="text/javascript">

	var currentTreeNote;
	//全网单位树属性
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid",
				rootPId : 0
			}
		},
		view : {
			dblClickExpand : false
		},
		check : {
			enable : false
		},
		callback : {
			onClick : function(event, treeId, treeNode) {
				$('#workcenter').attr('src','jsp/backstage/user/userlist.jsp?id=' + treeNode.id);
			}
		}
	};
	
	$(document).ready(function() {
		reloadTree();
	});

	//刷新全网单位信息
	function reloadTree() {
		$.post("DepartmentAction.do?method=getAllDepartments", function(result) {
			if(result.isSuccess) {
				var deps = result.data;
				for(var i in deps) {
					if(deps[i].type == "0") {
						deps[i].icon = "<%=basePath%>icon/usercenterMenu.png";
					}else if(deps[i].type == "1"){
						deps[i].icon = "<%=basePath%>icon/usercenterMenu.png";
					}else {
						deps[i].icon = "<%=basePath%>icon/usercenterMenu.png";
					}
					
// 					if(systems[i].citycode == citycode) {
// 						systems[i].open=true;
// 					}
				}
				
				treeObj = $.fn.zTree.init($("#treeDemo"), setting, deps);
				var nodes = treeObj.getNodes();
				if(nodes.length > 0) {
					treeObj.expandNode(nodes[0], true, false, true);
				}
			}else {
				alertInfo(result.errorMsg);
			}
		}, 'json').error(function() {
			alertInfo("服务器异常!");
		});
	}
</script>
<style type="text/css">
div#rMenu {
	position: absolute;
	visibility: hidden;
	top: 0;
	background-color: #555;
	text-align: left;
	padding: 2px;
}

div#rMenu ul li {
	margin: 1px 0;
	padding: 0 5px;
	cursor: pointer;
	list-style: none outside none;
	background-color: #DFDFDF;
}
</style>
</head>

<body class="easyui-layout" style="width: 100%; height: 100%">
	<div region="west" split="false" style="width: 200px;border-left: 0px;border-top: 0px;" noheader="true">
		<ul id="treeDemo" class="ztree"></ul>
	</div>

	<div region="center" style="width: 100%; border: 0px" noheader="true">
		<iframe scrolling="auto" frameborder="0" id="workcenter" src=""
			style="width: 100%; height: 100%;border: 0px;"></iframe>
	</div>
	</body>
</HTML>