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

	$(document).ready(function(){
		initdg();
		initPostTypeMenu();
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
		window.location.href="<%=basePath%>jsp/backstage/post/showpost.jsp?id=" + rowData.id;
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
			singleSelect:true,
			checkOnSelect:false,
			onClickRow:onClickRow,
			queryParams: {
				type: posttype
			},
			columns:[[
				{field:'id',title:'',width:50,checkbox:true},
				{field:'title',title:'主题',width:'750',
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
// 	function newpost() {
<%-- 		window.location.href="<%=basePath%>jsp/backstage/post/newpost.jsp"; --%>
// 	}
	//置顶帖子
	function updateToptime() {
		var rows = $('#dg').datagrid('getChecked');
		if(rows && rows.length > 0) {
			var data = rows[0];
			postcommit('PostAction.do?method=updateToptime',{id:data.id},'',function(result){
				alertInfo("帖子已置顶!",function() {
					initdg();
				});
			});
		}else {
			alertInfo("请选择需要置顶的帖子!");
		}
	}
	
	function deletepost() {
		var rows = $('#dg').datagrid('getChecked');
		if(rows && rows.length > 0) {
			var data = rows[0];
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
			    if (r){    
			    	postcommit('PostAction.do?method=deletePost',{id:data.id},'',function(result){
						alertInfo("帖子已删除!",function() {
							initdg();
						});
					});
			    }    
			}); 
			
		}else {
			alertInfo("请选择需要删除的帖子!");
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
			<a href="javascript:void(0)" class="easyui-linkbutton c5" onclick="deletepost()" style="padding-left: 5px;padding-right: 5px;">删除帖子</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c1" onclick="updateToptime()" style="padding-left: 5px;padding-right: 5px;">置顶帖子</a>
		</span>
	</div>
	<hr width="1022px;" style="padding: 0px;margin: 0px;">
	<table id="dg">
    </table>
</body>
</html>