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

	var depid = '${param.id}';
	
	$(document).ready(function(){
		initdg();
		initbtnClick();
	});
	
	
	function initbtnClick() {
		$("#btn").click(function(){		
			$('#dg').datagrid('load',{
				pid:depid,
				name:$('#name').val(),
				duty:$('#duty').combobox("getValue")
			});
		});
	}
	
	function initdg() {
		$('#dg').datagrid({
	       	url:'UserAction.do?method=getUsersByPage',
			pagination:true,
			rownumbers:true,
			pageSize:10,
			pageList:[10,20,30],
			singleSelect:true,
			checkOnSelect:true,
			queryParams: {
				pid: depid,
			},
			columns:[[
				{field:'loginname',title:'用户账号',width:150},
				{field:'name',title:'用户名称',width:150},
				{field:'pid',title:'所属部门',width:150,
					 formatter: function(value,row,index){ 
				            if(row.dep){return row.dep.name;}
				      }
				},
				{field:'mobile',title:'移动电话',width:150},
				{field:'workphone',title:'办公电话',width:150},
				{field:' ',title:'操作',width:100,align:'center',
					formatter: function(value,row,index){
				      return "<a  href='javaScript:deleteUser(\""+row.id+"\")'>删除</a>&nbsp;&nbsp;&nbsp;" + 
					  "<a  href='javaScript:updateUser(\""+row.id+"\")'>修改</a>";//html 元素	
					}	   
				}
			]],
			toolbar: [{
				iconCls: 'icon-addBtn',
				text:'新增用户',
				handler: adduser
			}],
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
					<td>名称:</td>
					<td>
						<input class="easyui-textbox" id="name" name="name" style="width:150px" data-options="required:false"></input>
					</td>
					<td>职务:</td>
					<td>
						<input id="duty" class="easyui-combobox" name="duty" style="width:150px" editable="false" panelHeight="auto" required="false"
    						data-options="valueField:'id',textField:'name',url:'DictionaryAction.do?method=getDictionaryByType&type=1',
   								loadFilter:function(result){
									if(result) {
										return $.merge( [{name:'全部',id:''}], result);
									}
								}">
						</input>
						
					</td>
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