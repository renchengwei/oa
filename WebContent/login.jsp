<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base href="<%=basePath%>" />
<title>金蟾装饰办公系统</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>jquery-easyui-1.4/themes/icon.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/watting.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/login.css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>

<style>
button:hover,s button.hover {
	background-position: 0 -33px;
}

button {
	width: 250px;
	height: 33px;
	border: 0;
	overflow: hidden;
	vertical-align: middle;
	line-height: 31px;
	font-size: 16px;
	font-weight: bold;
	color: #FFF;
	background: #0068a3 no-repeat 0 0;
	cursor: pointer;
	zoom: 1;
	letter-spacing: 5px;
}

.user {
	float: none;
	width: 250px;
	height: 38px;
	line-height: 50px;
	padding: 4px 5px 4px 3px;
	border: 1px solid #cccccc;
	font-size: 14px;
	font-family: arial, "宋体";
}
</style>
		
<script>
	
	$($(document)).keydown(function(event){
		if(event.keyCode == '13') {
			login();
		}
	});
	
	if (self != top) {  
	    window.parent.location.href="login.jsp";
	}
		
	$(function() {
		$('#loginsubmit').click(login);
	});
		

	/** 
	 * 禁用页面 
	 */  
	function forbiddenPage(msg){  
		if(!msg) {
			msg = '正在处理，请稍候……';
		}
			$("<div class=\"wattinglayer\" style=\"background:#666666;\"></div>").css({display:"block",width:$("body")[0].offsetWidth+10,height:$(window).height()}).appendTo("body");
		$("<div class=\"wattingtip\"></div>").html(msg).appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
	}  

		/** 
		 * 释放页面 
		 * @return 
		 */  
		function releasePage(){  
		    $(".wattinglayer,.wattingtip").remove();  
		}  
		   
		function btnClick() {
			$('#tip').html('');
			$('#tip').hide();
			forbiddenPage("正在登录，请稍候……");
			login();
		}
		
	function login() {
		$.post("LoginAction.do?method=login", {
			password : $('#password').textbox('getText'),
			username : $('#username').textbox('getText')
		}, function(result) {
			releasePage();
			if(result.isSuccess) {
		   		window.location.href = "index.jsp";
		   		self.moveTo(0,0);
		   		self.resizeTo(screen.availWidth,screen.availHeight);
			}else {
				$('#tip').html(result.errorMsg);
				$('#tip').show();
			}
		}, 'json').error(function() {
			releasePage();
			$('#tip').html('服务器异常!');
			$('#tip').show();
		});
	}
	
</script>
	</head>
	<body>
		<form id="formlogin" method="post" onsubmit="return false;">
			<div class=" w1" id="entry">
				<div class="mc" id="bgDiv">
					<div style="width: 411px; height: 400px;">
						<img src="images/lougo.png" alt="" style="width: 350px;margin-left: 40px;margin-top: 5px;"/>
					</div>
					<div class="form">
						<div class="item fore1" style="margin-top: 80px;margin-bottom: 15px;">
							<span style="font-size: 16px;">用户名</span>	
							<div>
								<input id="username" name="username" class="easyui-textbox" data-options="iconCls:'icon-man'" 
									style="height:38px;width:248px;font-size: 24px;" value=""> 
							</div>
						</div>
						<div class="item fore2">
							<span style="font-size: 16px;">密码</span>
							<div>
								<input id="password" name="password" class="easyui-textbox" type="password" data-options="iconCls:'icon-lock'" 
									style="height:38px;width:248px;font-size: 20px;" value=""> 
							</div>
						</div>
						<div style="margin-top: 25px">
							<button type="button" id="loginsubmit" style="float: left; margin-right: 0px">登录</button>
						</div>
						<div style="margin-top: 70px;">
							<span id="tip" style="color: red;display: none;"></span>
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>