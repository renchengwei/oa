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
	//查看邮件
	function onClickRow(rowIndex, rowData) {
		window.location.href="<%=basePath%>jsp/email/showreceiveemail.jsp?id=" + rowData.id;
	}
	
	function initdg() {
		$('#dg').datagrid({
	       	url:'EmailAction.do?method=getReceiveEmailList',
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
				{field:'receiveusernames',title:'发件人',width:'10%',
					formatter: function(value,row,index){
						if(row.isshow == '0') {
							return "<a title='" + row.senduserbean.name + "' style='font-weight:bold' class='easyui-tooltip'>" + row.senduserbean.name + "</a>";
						}else {
							return "<a title='" + row.senduserbean.name + "'class='easyui-tooltip'>" + row.senduserbean.name + "</a>";
						}
						
					}
				},
				{field:'title',title:'主题',width:'75%',
					formatter: function(value,row,index){
						if(row.isshow == '0') {
							return "<a style='font-weight:bold'>" + value + "</a>";
						}else {
							return value;
						}
						
					}
				},
				{field:'sendtime',title:'时间',width:'10%',align:'center',
					formatter: function(value,row,index){
						if(row.isshow == '0') {
							return "<a style='font-weight:bold'>" + value + "</a>";
						}else {
							return value;
						}
						
					}
				}
			]],
			toolbar: [{
				iconCls: 'icon-closeBtn',
				text:'删除',
				handler: deleteemail
			},{
				iconCls: 'icon-zfBtn',
				text:'转发',
				handler: transmitemail
			}],
			loadFilter:errorFilter
		});
	}
	//转发email
	function transmitemail() {
		var rows = $('#dg').datagrid('getChecked');
		if(rows.length < 1) {
			alertInfo('请选择需要转发的邮件!'); 
			return;
		}
		
		if(rows.length > 1) {
			alertInfo('只能转发一封邮件!'); 
			return;
		}
		
		if(rows.length > 0) {
			var id = rows[0].sendemailid;
			window.location.href="<%=basePath%>jsp/email/newemail.jsp?flag=transmit&id=" + id;
		}
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
			    	
			    	postcommit('EmailAction.do?method=deleteReceiveEmails',{ids:ids},'',function(result){
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
	  <table id="dg"  fit="true" border="false" >
      </table>
	</div>
</body>
</html>