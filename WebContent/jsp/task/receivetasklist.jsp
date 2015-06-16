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

	var depid = '${param.id}';
	
	$(document).ready(function(){
		initdg();
		initbtnClick();
	});
	
	function initbtnClick() {
		$("#btn").click(function(){		
			$('#dg').datagrid('load',{
				title:$('#title').val(),
				status:$('#status').combobox("getValue")
			});
		});
	}
	
	function onClickRow(rowIndex, rowData) {
		window.location.href="<%=basePath%>jsp/task/showreceivetask.jsp?id=" + rowData.id;
	}
	
	function initdg() {
		$('#dg').datagrid({
	       	url:'TaskAaction.do?method=getReceiveTasksByPage',
			pagination:true,
			rownumbers:true,
			pageSize:20,
			pageList:[10,20,30],
			singleSelect:true,
			checkOnSelect:false,
			striped:true,
			onClickRow:onClickRow,
			columns:[[
// 			    {field:'id',checkbox:'true',width:50},
				{field:'title',title:'标题',width:'57%'},
				{field:'name',title:'下发人',width:'10%',
					formatter: function(value,row,index){
						if(row.transactorbean) {
							return row.issueuserbean.name;
						}
						return "";
					}
				},
				{field:'levelname',title:'紧急程度',width:'10%'},
				{field:'endtime',title:'截止时间',width:'10%',align:'center'},
				{field:'status',title:'状态',width:'10%',align:'center',
					formatter: function(value,row,index){
						if(value == '0') {
							return '待办';
						}
						if(value == '1') {
							return '办理中';
						}
						if(value == '2') {
							return '办理完成';
						}
						if(value == '3') {
							return '任务办结';
						}
 					}
				}
// 				{field:' ',title:'操作',width:100,align:'center',
// 					formatter: function(value,row,index){
// 				      return "<a  href='javaScript:deleteUser(\""+row.id+"\")'>删除</a>&nbsp;&nbsp;&nbsp;" + 
// 					  "<a  href='javaScript:updateUser(\""+row.id+"\")'>修改</a>";//html 元素	
// 					}	   
// 				}
			]],
// 			toolbar: [{
// 				iconCls: 'icon-addBtn',
// 				text:'办结任务',
// 				handler: adduser
// 			},{
// 				iconCls: 'icon-addBtn',
// 				text:'重新办理',
// 				handler: adduser
// 			},],
			loadFilter:errorFilter
		});
	}
	//新建用户
	function adduser() {
		window.location.href="<%=basePath%>jsp/backstage/user/adduser.jsp?depid=" + depid;
	}
	//更新用户
	function updateUser(id) {
		window.location.href="<%=basePath%>jsp/backstage/user/adduser.jsp?id=" + id;
	}
	//删除用户
	function deleteUser(id) {
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		    	postcommit('UserAction.do?method=deleteUser',{id:id},'',function(result){
					$('#btn').trigger("click");
				});  
		    }    
		}); 
	}
</script>
</head>
<body class="easyui-layout" style="width:100%;padding: 0px;margin: 0px;border:0px">
   <div region="north" class="headSkin" style="height:45px;padding:5px;border-left: 0px;border-top: 0px;border-right: 0px;">
		<form id="searchfrom">
			<table>
				<tr>
					<td>标题:</td>
					<td>
						<input class="easyui-textbox" id="title" name="title" style="width:200px" data-options="required:false"></input>
					</td>
					<td>状态:</td>
					<td>
						<select id="status" class="easyui-combobox" name="status" style="width:200px;" editable="false" panelHeight="auto">  
						    <option value="">全部</option>  
						    <option value="0">待办</option>  
						    <option value="1">正在办理</option>  
						    <option value="2">办理完成</option>  
						    <option value="3">办理完结</option>  
						</select>
						
					</td>
<!-- 					<td>紧急程度:</td> -->
<!-- 					<td> -->
<!-- 						<input id="status" class="easyui-combobox" name="status" style="width:150px" editable="false" panelHeight="auto" required="false" -->
<!--     						data-options="valueField:'id',textField:'name',url:'DictionaryAction.do?method=getDictionaryByType&type=2', -->
<!--    								loadFilter:function(result){ -->
<!-- 									if(result) { -->
<!-- 										return $.merge( [{name:'全部',id:''}], result); -->
<!-- 									} -->
<!-- 								}"> -->
<!-- 						</input> -->
						
<!-- 					</td> -->
					<td>
						<a id="btn" href="javascript:void(0)" class="easyui-linkbutton"  style="width:70px;" data-options="iconCls:'icon-search'">搜索</a>
					</td>
				</tr>
			</table>
		</form>
          
	</div>
    <div region="center" border="false">
	  <table id="dg"  fit="true"  border="false">
      </table>
	</div>
</body>
</html>