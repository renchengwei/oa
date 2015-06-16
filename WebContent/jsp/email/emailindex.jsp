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
//         	postcommit('EmailAction.do?method=getShowEmailCount','','',function(result){
//         		var count = result.data;
//         		if(count > 0) {
//         			var text = '收件箱(' + count + ')'
//         			$('#receivemenu').linkbutton({   
//         			    iconCls: 'icon-search',
//         			    plain:true,
//         			    text:text
//         			});  
//         		}
// 			});
        });
        
        function btnClick(flag) {
        	
        	
        	if(flag == 1) {//写邮件
        		$('#iframe').attr('src','jsp/email/newemail.jsp');
        	}else if(flag == 2) {//收件箱
        		$('#iframe').attr('src','jsp/email/receiveemaillist.jsp');
        	}else if(flag == 3){//发件箱
        		$('#iframe').attr('src','jsp/email/sendemaillist.jsp');
        	}else {
        		$('#iframe').attr('src','jsp/email/draftemaillist.jsp');
        	}
        }
		
    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  fit="true"   scroll="no">
    <div region="west" split="false"  title="Email" style="width:180px;" id="west" collapsible="false">
    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="btnClick(1)" style="width:100%;" data-options="iconCls:'icon-newemailmenu'" plain="true">写邮件</a>
    	<a id="receivemenu" href="javascript:void(0)" class="easyui-linkbutton" onclick="btnClick(2)" style="width:100%" data-options="iconCls:'icon-receiveemailmenu'" plain="true">收件箱</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="btnClick(3)" style="width:100%" data-options="iconCls:'icon-sendemailmenu'" plain="true">已发送</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="btnClick(4)" style="width:100%" data-options="iconCls:'icon-cgxmenu'" plain="true">草稿箱</a>
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
    	<iframe id="iframe" style="width:100%;height: 100%;background-color: #FAFAFA;border: 0px;" src="jsp/email/receiveemaillist.jsp">
    	
    	</iframe>
    </div>
    
</body>
</html>