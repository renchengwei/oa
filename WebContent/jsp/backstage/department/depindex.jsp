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
			onRightClick : OnRightClick,
			onClick : function(event, treeId, treeNode) {
				$('#workcenter').attr('src','jsp/backstage/department/adddep.jsp?id=' + treeNode.id);
			}
		}
	};
	
	$(document).ready(function() {
		reloadTree();
		bindTabMenuEvent();
	});

	//显示右键菜单
	function OnRightClick(event, treeId, treeNode) {
		currentTreeNote = treeNode;
		if(currentTreeNote.type=='0') {
			$('#depmenu2').menu('show', {
				left : event.clientX,
				top : event.clientY
			});
		}else if(currentTreeNote.type=='1') {
			$('#depmenu1').menu('show', {
				left : event.clientX,
				top : event.clientY
			});
		}else {
			$('#depmenu3').menu('show', {
				left : event.clientX,
				top : event.clientY
			});
		}
	}

	//树形右键事件
	function bindTabMenuEvent() {

		//添加分公司
		$('.addorg').click(function() {
			$('#workcenter').attr('src','jsp/backstage/department/adddep.jsp?type=1&pid=' + currentTreeNote.id);

		});

		//添加部门
		$('.adddep').click(function() {
			$('#workcenter').attr('src','jsp/backstage/department/adddep.jsp?type=2&pid=' + currentTreeNote.id);
		});

		//删除分公司或部门
		$('.delete').click(function() {
			$.messager.confirm('提示', '删除分公司/部门会删除所有与之相关的分公司/部门和人员，确认删除?', function(r){
                if (r){
                	var id = currentTreeNote.id;
                	postcommit("DepartmentAction.do?method=deleteDepartment&id=" + id,'','',function(result) {
               			$('#workcenter').attr('src','');
           				reloadTree();
        			});
                }
            });
			
		});
	}

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
	<div id="depmenu1" class="easyui-menu" style="width: 150px;">
<!-- 		<div class="addorg">增加分公司</div> -->
		<div class="adddep">增加部门</div>
		<div class="delete">删除</div>
	</div>
	<div id="depmenu2" class="easyui-menu" style="width: 150px;">
		<div class="addorg">增加分公司</div>
		<div class="adddep">增加部门</div>
	</div>
	<div id="depmenu3" class="easyui-menu" style="width: 150px;">
		<div class="delete">删除</div>
	</div>
</body>
</HTML>