<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commontaglib.jsp" %>
<script type="text/javascript" src='<%=basePath%>js/index.js'> </script>
<style>
 	*{font-size:14px; font-family:Tahoma,Verdana,微软雅黑,新宋体} 
 	.head { 
 		color:black; 
 		text-decoration:none; 
 		font-size:16px; 
 		cursor: pointer; 
 		padding-left: 10px; 
 	} 
</style>

    <script type="text/javascript">

        //设置登录窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 160,
                resizable:false
            });
        }
       
        $(function() {
        	//用户图标点击
            $('#userspan').tooltip({
    		    content: '<span>姓名:${session_user.name}<br/>角色:${session_user.role.name}<br/>职务:${session_user.dutyname}<br/>部门:${session_user.dep.name}</span>',
    		    onShow: function(){
    		         $(this).tooltip('arrow').css('left', 20);
                           $(this).tooltip('tip').css('left', $(this).offset().left);
    		    }
    		});
            $('#userspan').click(function(){
            	$('#iframe').attr('src','<%=basePath%>jsp/user/userinfo.jsp');
            });    
        });
		//注销
        function logout() {
        	$.messager.confirm('系统提示','确认退出系统吗?',function(r){
			    if (r){
			      	window.location.href="<%=basePath%>LoginAction.do?method=logout";
			    }
           });
        }
        //修改密码
        function updatepassword() {
        	$('#iframe').attr('src','<%=basePath%>jsp/user/updatepassword.jsp');
        }
        
        function tomain() {
        	$('#iframe').attr('src','<%=basePath%>main.jsp');
        }

//         function aa(obj) {
//         	var ret = $(obj).attr('url');
//         	$('#iframe').attr('src',ret);
//         }
        
    </script>
</head>
<body>
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div>
</noscript>

<div id="loading-mask" style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:#D2E0F2; z-index:20000">
<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center;  border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
    <img src="images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...</div>
</div>
<div class="easyui-layout" fit="true"  style="overflow-y: hidden" scroll="no">
   <div region="north" split="false" border="true" style="overflow: hidden; height: 100px;text-align:center;
        background: url('') #c0a062 repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
<!--         <div style="position: absolute;top: 10px;left: 15px;cursor: pointer;" onclick="tomain()"> -->
<!--         	<img src="images/log.gif" alt="" width="60px" height="60px"/> -->
<!--         </div> -->
        <div style="position:absolute;left:0px;font-size: 40px;margin-left: auto;margin-right: auto;top:10px;cursor: pointer;" onclick="tomain()">
        	<img src="images/lo.png" alt="" height="60px"/>
        </div>
		<span style="position:absolute;top:7px;right:10px;">
			<span id="userspan" class="head"><img src="icon/user.png"/>${session_user.name }</span>
			<a class="head" href="javaScript:void(0)" id="editpass" onclick="updatepassword();">修改密码</a> 
			<a class="head" href="javaScript:void(0)" id="loginOut" onclick="logout();">安全退出</a>
		</span>
		<span id="menuspan" style="position: absolute;top: 30px;left: 10px;">
		</span>
    </div>
   
    <div id="mainPanle" region="center" split="true" style="background: #eee; overflow-y:hidden">
        <iframe id="iframe" style="width: 100%;height: 100%;border: 0px;text-align: center;" src="<%=basePath%>jsp/backstage/bsindex.jsp"></iframe>
    </div>
    <div class="footer" region="south" split="false" style="background-color: #767777;color: white;overflow-y: hidden">
        <div style="height: 40px;line-height: 40px;text-align: center;font-size: 20px;">
       			 copyright2014
        </div>
    </div>
</div>
</body>
</html>