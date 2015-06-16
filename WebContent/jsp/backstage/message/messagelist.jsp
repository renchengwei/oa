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
			
				receiveusers:$('#receiveusers').textbox('getText'),
				content:$('#content').textbox('getText')
				
			});
		});
	}
	
	function initdg() {
		$('#dg').datagrid({
	       	url:'MessageAction.do?method=getMessageByPage',
			pagination:true,
			rownumbers:true,
			pageSize:20,
			pageList:[10,20,30],
			singleSelect:true,
			checkOnSelect:true,
			columns:[[
				{field:'receiveusers',title:'收件人',width:'40%',
					formatter: function(value,row,index){
				      	return "<a class='easyui-tooltip' title='\""+value+"\"'>" + value +"</a>";
					}	  
				},
				{field:'content',title:'内容',width:'37%',
					formatter: function(value,row,index){
				      	return "<a class='easyui-tooltip' title='\""+value+"\"'>" + value +"</a>";
					}	  
				},
				{field:'sendtime',title:'发送时间',width:120},
				{field:' ',title:'操作',width:100,align:'center',
					formatter: function(value,row,index){
						var str = "<a  href='javaScript:deletemessage(\""+row.id+"\")'>删除</a>&nbsp;&nbsp;&nbsp;";
						if(row.status == '0') {
							str += "<a  href='javaScript:updatemessage(\""+row.id+"\")'>修改</a>";
						}else {
							str += "<a  href='javaScript:showmessage(\""+row.id+"\")'>查看</a>";
						}
				      	return str;//html 元素	
					}	   
				}
			]],
			toolbar: [{
				iconCls: 'icon-addBtn',
				text:'新短信',
				handler: addmessage
			}],
			loadFilter:errorFilter
		});
	}
	//新短信
	function addmessage() {
		window.location.href="<%=basePath%>jsp/backstage/message/newmessage.jsp";
	}
	//更新短信
	function updatemessage(id) {
		window.location.href="<%=basePath%>jsp/backstage/message/newmessage.jsp?id=" + id;
	}
	function showmessage(id) {
		window.location.href="<%=basePath%>jsp/backstage/message/showmessage.jsp?id=" + id;
	}
	//删除短信
	function deletemessage(id) {
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		    	postcommit('MessageAction.do?method=deleteMessage',{id:id},'',function(result){
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
					<td>收件人:</td>
					<td>
						<input class="easyui-textbox" id="receiveusers" name="receiveusers" style="width:200px" data-options="required:false"></input>
					</td>
					<td>内容:</td>
					<td>
						<input class="easyui-textbox" id="content" name="content" style="width:200px" data-options="required:false"></input>
					</td>
					<td>
						<a id="btn" href="javascript:void(0)" class="easyui-linkbutton"  style="width:70px;" data-options="iconCls:'icon-search'">搜索</a>
					</td>
				</tr>
			</table>
		</form>
          
	</div>
    <div region="center" border="false">
	  <table id="dg"  fit="true" border="false" >
      </table>
	</div>
</body>
</html>