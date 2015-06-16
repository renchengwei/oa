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
		//initbtnClick();
	});
	
	
	function initbtnClick() {
		$("#btn").click(function(){		
			$('#dg').datagrid('load',{
				depid:depid,
				name:$('#name').val(),
				duty:$('#duty').combobox("getValue")
			});
		});
	}
	//修改邮件
	function onClickRow(rowIndex, rowData) {
		window.location.href="<%=basePath%>jsp/email/newemail.jsp?flag=update&id=" + rowData.id;
	}
	
	function initdg() {
		$('#dg').datagrid({
	       	url:'EmailAction.do?method=getDrafteEmailList',
			pagination:true,
			rownumbers:true,
			pageSize:20,
			pageList:[10,20,30],
			striped:true,
			singleSelect:false,
			checkOnSelect:false,
			onClickRow:onClickRow,
			columns:[[
				{field:'id',title:'',width:50,checkbox:true},
				{field:'receiveusernames',title:'收件人',width:'10%',
					formatter: function(value,row,index){
						return "<a title='" + value + "'class='easyui-tooltip'>" + value + "</a>";
					}
				},
				{field:'title',title:'主题',width:'85%'}
			]],
			toolbar: [{
				iconCls: 'icon-closeBtn',
				text:'删除',
				handler: deleteemail
			}],
			loadFilter:errorFilter
		});
	}
	
	//删除邮件
	function deleteemail() {
		var rows = $('#dg').datagrid('getChecked');
		if(rows.length > 0) {
	 		$.messager.confirm('确认','邮件删除后不可恢复,您确认想要删除记录吗？',function(r){    
			    if (r){ 
			    	var ids = "";
			    	for(var i in rows) {
			    		ids += rows[i].id + "#";
			    	}
			    	
			    	postcommit('EmailAction.do?method=deleteSendEmails',{ids:ids},'',function(result){
			    		alertInfo("邮件已删除!");
			    		$('#dg').datagrid('reload');
					});
			    }    
			}); 
		}else {
			alertInfo("请选择需要删除的邮件!");
		}
	}
</script>
</head>
<body class="easyui-layout" style="width:100%;padding: 0px;margin: 0px;border:0px">
    <div region="center" border="false">
	  <table id="dg"  fit="true"  border="false">
      </table>
	</div>
</body>
</html>