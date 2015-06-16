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
		postcommit('LoginAction.do?method=getLoginUserMainData',{page:1,rows:15},'',function(result){
			var infors = result.infors;
			var news = result.news;
			initmenus(result);
			initinfors(infors);
			initnews(news);
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
				tr += '<img alt="" src="' + menus[i].icon + '" width="100px" height="100px">';
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
		window.location.href="<%=basePath%>" + url;
	}
	//初始化公告
	function initinfors(infors) {
		var li = '';
		if(infors) {
			for(var i in infors) {
				li += '<li>';
				li += '<a href="<%=basePath%>jsp/infor/showinfor.jsp?id=' + infors[i].id + '" class="lidiv">' + infors[i].title + '</a>';
				li += '</li>';
			}
		}
		$('#inforsol').append(li);
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
		width:110px;
		text-align:center;
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
		<div id="menuidv" style="padding: 5px 5px 0px 5px">
			<table id="menutable" style="margin-left: auto;margin-right: auto;">
				<tr>
<!-- 					<td> -->
<!-- 						<div class="menudiv"> -->
<!-- 							<img alt="" src="images/news.jpg" width="100px" height="100px"> -->
<!-- 							<div style="padding-top: 2px;"> -->
<!-- 								新闻动态 -->
<!-- 							</div> -->
<!-- 					</td> -->
<!-- 					<td> -->
<!-- 						<div class="menudiv"> -->
<!-- 							<img alt="" src="images/news.jpg" width="100px" height="100px"> -->
<!-- 							<div style="padding-top: 2px;"> -->
<!-- 								通知公告 -->
<!-- 							</div> -->
							
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 					<td> -->
<!-- 						<div class="menudiv"> -->
<!-- 							<img alt="" src="images/news.jpg" width="100px" height="100px"> -->
<!-- 							<div style="padding-top: 2px;"> -->
<!-- 								内部邮箱<a style="color:red;">(120)</a> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 					<td> -->
<!-- 						<div class="menudiv"> -->
<!-- 							<img alt="" src="images/news.jpg" width="100px" height="100px"> -->
<!-- 							<div style="padding-top: 2px;"> -->
<!-- 								工作安排 -->
<!-- 							</div> -->
							
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 					<td> -->
<!-- 						<div class="menudiv"> -->
<!-- 							<img alt="" src="images/news.jpg" width="100px" height="100px"> -->
<!-- 							<div style="padding-top: 2px;"> -->
<!-- 									公司论坛 -->
<!-- 							</div> -->
					
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 					<td> -->
<!-- 						<div class="menudiv"> -->
<!-- 							<img alt="" src="images/news.jpg" width="100px" height="100px"> -->
<!-- 							<div style="padding-top: 2px;"> -->
<!-- 								系统维护 -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</td> -->
				</tr>
			</table>
		</div>
		<hr style="padding: 0px;margin: 0px;">
		<div style="padding: 0px 5px 5px 5px;">
			<table>
				<tr>
					<td style="vertical-align: top;">
						<div style="height: 25px;line-height: 25px;font-weight: bold;">
							最新动态
						</div>
						<hr style="margin: 0px;padding: 0px;">
						<div style="width: 500px;">
							<ol id="newsol" style="list-style: none;padding-left: 0px;line-height: 20px;margin-top: 5px;">
<!-- 							    <li> -->
<%-- 							    	<a href="<%=basePath %>jsp/post/postlist.jsp" class="lidiv">次要部分</a> --%>
<!-- 							    </li> -->
<!-- 							    <li>次要部分</li> -->
<!-- 							    <li>结尾部分</li> -->
							</ol>
						</div>
					</td>
					<td style="vertical-align: top;">
						<div style="height: 25px;line-height: 25px;font-weight: bold;">
							最新公告
						</div>
						<hr style="margin: 0px;padding: 0px;">
						<div style="width: 500px;padding-top: 0px;margin-top: 0px;overflow: hidden;">
							<ol id="inforsol" style="list-style: none;padding-left: 0px;line-height: 20px;margin-top: 5px;">
<!-- 							    <li style="white-space: nowrap;">开始部分</li> -->
<!-- 							    <li>次要部分</li> -->
<!-- 							    <li>结尾部分</li> -->
							</ol>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>