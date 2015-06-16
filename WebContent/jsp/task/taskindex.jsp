<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commontaglib.jsp" %>

    <script type="text/javascript">
      
       
        $(function() {
				
        //	InitLeftMenu();
        });
        
        function btnClick(flag) {
        	if(flag == 1) {//新建任务
        		$('#iframe').attr('src','jsp/task/newtask.jsp');
        	}else if(flag == 2) {//任务办理
        		$('#iframe').attr('src','jsp/task/receivetasklist.jsp');
        	}else {//已发任务
        		$('#iframe').attr('src','jsp/task/sendtasklist.jsp');
        	}
        }
		
    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  fit="true"   scroll="no">
    <div region="west" split="false"  title="Task" style="width:180px;" id="west" collapsible="false">
    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="btnClick(1)" style="width:100%;" data-options="iconCls:'icon-newemailmenu'" plain="true">下发任务</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="btnClick(2)" style="width:100%" data-options="iconCls:'icon-receiveemailmenu'" plain="true">任务办理</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="btnClick(3)" style="width:100%" data-options="iconCls:'icon-sendemailmenu'" plain="true">已发任务</a>
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
    	<iframe id="iframe" style="width:100%;height: 100%;background-color: #FAFAFA;border: 0px;" src="jsp/task/receivetasklist.jsp">
    	
    	</iframe>
    </div>
    
</body>
</html>