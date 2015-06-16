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
	.datagrid-row { 
		height: 30px;
		cursor: pointer;
	}
	
</style>
<script type="text/javascript">

	var user;
	
	$(document).ready(function(){
		initdg();
		initPostTypeMenu();
		postcommit('UserAction.do?method=getCurrentUser','','正在加载中...',function(result){
			user = result.data;
		});
		
// 		var bodyHeight = document.documentElement.clientHeight;
// 		$('.datagrid-view').css('min-height',bodyHeight-90);
		//initbtnClick();
	});
	
	function initPostTypeMenu() {
		$.post("DictionaryAction.do?method=getDictionaryByType&type=3", function(result) {
			if(result) {
				for(var i in result) {
					var data = result[i];
					var type = '<a href="javascript:void(0)" class="easyui-linkbutton c2" id="' +
							data.id +'" style="padding-left: 5px;padding-right: 5px;margin-right:7px;" onclick="searchPost(\'' + data.id +'\')">'  
							+ data.name + '</a>';
					$('#typediv').append(type);
					$('#' + data.id).linkbutton({
					});
				}
			}
		}, 'json').error(function() {
			alertInfo("服务器异常!");
		});
	}
	
// 	function initbtnClick() {
// 		$("#btn").click(function(){		
// 			$('#dg').datagrid('load',{
// 				depid:depid,
// 				name:$('#name').val(),
// 				duty:$('#duty').combobox("getValue")
// 			});
// 		});
// 	}
	
	function onClickRow(rowIndex, rowData) {
		window.location.href="<%=basePath%>jsp/post/showpost.jsp?id=" + rowData.id;
	}
	var posttype = '';
	function initdg() {
		$('#dg').datagrid({
	       	url:'PostAction.do?method=getPostByPage',
			pagination:true,
			rownumbers:false,
			pageSize:20,
			pageList:[10,20,30],
			striped:true,
			singleSelect:false,
			checkOnSelect:false,
			onClickRow:onClickRow,
			queryParams: {
				type: posttype
			},
			columns:[[
				{field:'title',title:'主题',width:'780',
					formatter: function(value,row,index){
						return "<a title='" + value + "'class='easyui-tooltip'>" + value + "</a>";
					}
				},
				{field:'issueuser',title:'作者',width:'120',align:'center',
					formatter: function(value,row,index){
						if(row.issueuserbean) {
							var ret = row.issueuserbean.name + '<br/>' + row.issuetime.substring(0,10);
							return ret;
						}
						return '';
					}	
				},
				{field:'receivenum',title:'回复',width:'120'}
			]],
			loadFilter:errorFilter
		});
	}
	//发帖
	function newpost() {
		if(user.enableSendMsg == '1') {
			alertInfo("您的账户已被禁言，请联系系统管理员!");
		}else {
			window.location.href="<%=basePath%>jsp/post/newpost.jsp";
		}
	}
	
	function searchPost(type) {
		posttype = type;
		initdg();
	}

</script>
</head>
<body style="text-align: center;width:1024px;margin-left: auto;margin-right: auto;margin-top: 0px;height: 100%;">	
	<div style="width: 100%;background: #CDC9C9;height: 40px;">
		<span id="typediv" style="float: left;text-align: center;margin-top: 8px;margin-left: 10px;">
			<a href="javascript:void(0)" class="easyui-linkbutton c2" style="padding-left: 5px;padding-right: 5px;" onclick="searchPost('')">全部</a>
		</span>
		<span style="float: right;text-align: center;margin-top: 8px;margin-right: 10px;">
			<a href="javascript:void(0)" class="easyui-linkbutton c1" onclick="newpost()" style="padding-left: 5px;padding-right: 5px;">发布帖子</a>
		</span>
	</div>
	<hr width="1022px;" style="padding: 0px;margin: 0px;">
	<table id="dg">
    </table>
</body>
</html>