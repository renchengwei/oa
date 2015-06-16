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
		var bodyHeight = document.documentElement.clientHeight;
		$('#maindiv').css('min-height',bodyHeight-5);
		$('#menutable').css('padding-top',(bodyHeight-160)/2);
		postcommit('LoginAction.do?method=getLoginUserMainData',{page:1,rows:10},'',function(result){
			var infors = result.infors;
			var news = result.news;
			initmenus(result);
			if('${istip}' == 'true') {
				initinfors(infors);
			}
			//initnews(news);
		});
	});
	
	//初始化菜单
	function initmenus(result) {
		var menus = result.menus;
		var emailcount = result.emailcount;
		var taskcount = result.taskcount;
		
		var tr = '<tr>';
		if(menus) {
			for(var i in menus) {
				tr += '<td>';
				tr += '<div class="menudiv" onclick="menuclick(\'' + menus[i].url + '\')">';
				tr += '<img alt="" src="' + menus[i].icon + '" width="150px" height="150px">';
				tr += '<div style="padding-top: 2px;">' + menus[i].name;
				if(menus[i].code == 'gzap') {
					if(taskcount > 0) {
						tr += '<a style="color:red;">(' + taskcount + ')</a>';
					}
				}
				
				if(menus[i].code == 'nbyx') {
					if(emailcount > 0) {
						tr += '<a style="color:red;">(' + emailcount + ')</a>';
					}
				}
				
				tr += '</div>';
				tr += '</td>';
			}
		}
		tr += '</tr>';
		$('#menutable').append(tr);
	}
	
	function menuclick(url) {
		window.open("<%=basePath%>" + url,'');
		//window.location.href="<%=basePath%>" + url;
	}
	
	//初始化公告
	function initinfors(infors) {
		var htmlstr = "";
		if(infors) {
			for(var i in infors) {
				htmlstr += "<a href='<%=basePath%>jsp/infor/showinfor.jsp?id="  + infors[i].id + "' class='' style='font-size:14px;line-height:1.5;text-decoration:none'>" + infors[i].title + "</a></br>"
// 				li += '<li>';
<%-- 				li += '<a href="<%=basePath%>jsp/infor/showinfor.jsp?id=' + infors[i].id + '" class="lidiv">' + infors[i].title + '</a>'; --%>
// 				li += '</li>';
			}
		}
		if(htmlstr != '') {
			$.messager.show({
		        title:'最新公告',
		        height:300,
		        width:500,
	         	msg:htmlstr,
	            timeout:0,
	            showType:'slide'
		    });
			$.post('LoginAction.do?method=setisTip');
		}
// 		$('#inforsol').append(li);
	}
	//初始化新闻
	function initnews(news) {
		var li = '';
		if(news) {
			for(var i in news) {
				li += '<li>';
				li += '<a href="<%=basePath%>jsp/news/shownews.jsp?id=' + news[i].id + '" class="lidiv">' + news[i].title + '</a>';
				li += '</li>';
			}
		}
		$('#newsol').append(li);
	}
	
</script>
<style type="text/css">
	
	.menudiv {
		width:160px;
		text-align:center;
		font-size:20px;
		font-weight: bold;
	}
	
	.menudiv:hover{
		cursor:pointer;
		color: green;
	}
	
	.lidiv {
		color:#222;
		text-decoration: none;
	}
	
	.lidiv:hover {
		cursor:pointer;
		color: #900;
		text-decoration: underline;
	}
	
</style>
</head>
<body style="text-align: center;width:1024px;margin-left: auto;margin-right: auto;margin-top: 0px;height: 100%;margin-bottom: 0px;">
	<div id="maindiv" style="width:100%;margin-top: 0px;background-color: #F7F7F7;text-align: left;margin-bottom: 0px;padding-bottom: 0px;">
			<table id="menutable" style="margin-left: auto;margin-right: auto;">
				<tr>
				</tr>
			</table>
	</div>
</body>
</html>