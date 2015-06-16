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
		initbtnClick();
	});
	
	
	function initbtnClick() {
		$("#btn").click(function(){		
			$('#dg').datagrid('load',{
				title:$('#title').val()
			});
		});
	}
	
	function onClickRow(rowIndex, rowData) {
		window.location.href="<%=basePath%>jsp/infor/showinfor.jsp?id=" + rowData.id;
	}
	
	function initdg() {
		$('#dg').datagrid({
	       	url:'InformationAction.do?method=getShowInforByPage',
			pagination:true,
			rownumbers:true,
			striped:true,
			showHeader:true,
			pageSize:10,
			pageList:[10,20,30],
			singleSelect:true,
			checkOnSelect:true,
			onClickRow:onClickRow,
			columns:[[
				{field:'title',title:'标题',width:'89%'
// 					formatter: function(value,row,index){
// 						return "<a href='javaScript:showInfor(\""+row.id+"\")'>" + value + "</a>";
// 					}
				},
				{field:'issuetime',title:'发布时间',width:'10%',align:'center'}
			]],
			loadFilter:errorFilter
		});
	}
	function showInfor(id) {
		
	}
</script>
</head>
<body  style="width:1024px;padding: 0px;border:0px;margin-left: auto;margin-right: auto;">
	<div class="easyui-layout" fit="true">
   		<div region="north" class="headSkin" style="height:40px;padding:3px;">
			<form id="searchfrom">
				<table>
					<tr>
						<td>标题:</td>
						<td>
							<input class="easyui-textbox" id="title" name="title" style="width:300px" data-options="required:false"></input>
						</td>
						<td>
							<a id="btn" href="javascript:void(0)" class="easyui-linkbutton"  style="width:70px;" data-options="iconCls:'icon-search'">搜索</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	    <div region="center">
		  <table id="dg" fit="true" border="false">
	      </table>
		</div>
	</div>
</body>
</html>