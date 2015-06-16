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
	
	function initdg() {
		$('#dg').datagrid({
	       	url:'InformationAction.do?method=getInforByPage',
			pagination:true,
			rownumbers:true,
			pageSize:10,
			pageList:[10,20,30],
			singleSelect:true,
			checkOnSelect:true,
			columns:[[
				{field:'title',title:'标题',width:350},
				{field:'issueuser',title:'发布人',width:120,
					formatter: function(value,row,index){ 
						if(value != null) {
							return row.issueuserBean.name;
						}
						return '';
			      }},
				{field:'issuetime',title:'发布时间',width:120},
				{field:'endtime',title:'截止时间',width:120},
				{field:' ',title:'操作',width:100,align:'center',
					formatter: function(value,row,index){
				      return "<a  href='javaScript:deleteUser(\""+row.id+"\")'>删除</a>&nbsp;&nbsp;&nbsp;" + 
					  "<a  href='javaScript:updateInfor(\""+row.id+"\")'>修改</a>";//html 元素	
					}	   
				}
			]],
			toolbar: [{
				iconCls: 'icon-addBtn',
				text:'发布通知',
				handler: addinfor
			}],
			loadFilter:errorFilter
		});
	}
	//新建用户
	function addinfor() {
		window.location.href="<%=basePath%>jsp/backstage/information/addinfor.jsp";
	}
	//更新用户
	function updateInfor(id) {
		window.location.href="<%=basePath%>jsp/backstage/information/addinfor.jsp?id=" + id;
	}
	//删除用户
	function deleteUser(id) {
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		    	postcommit('InformationAction.do?method=delInfor',{id:id},'',function(result){
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
					<td>
						<a id="btn" href="javascript:void(0)" class="easyui-linkbutton"  style="width:70px;" data-options="iconCls:'icon-search'">搜索</a>
					</td>
				</tr>
			</table>
		</form>
          
	</div>
    <div region="center" border="false">
	  <table id="dg"  fit="true" border="false">
      </table>
	</div>
</body>
</html>