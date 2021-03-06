<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commontaglib.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath %>uploadify/uploadify.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/showpost.css">
<script type="text/javascript" src="<%=basePath %>ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=basePath %>uploadify/jquery.uploadify.js"></script>
<script type="text/javascript">
	
 	var id = '${param.id}';
 	var pageSize = 10;
	var editor = null;
	var pagination;
	$(document).ready(function(){
// 		$('#postid').val(id);
// 		initckeditor();
		pagination = $('#pp').pagination({
		    pageSize:pageSize,
		    layout:['first','prev','links','next','last','sep','refresh','manual'],
		    beforePageText:'第',
		    onSelectPage:onRefresh,
		    onRefresh:onRefresh
		});
		initPage();
		//刷新表格
		onRefresh(1,pageSize);
	});
	
	//初始化页面信息，包括帖子主题和当前人员
	function initPage() {
		postcommit('PostAction.do?method=getPostAndUser',{id:id},'正在加载中...',function(result){
			var post = result.post;
			$('#title').html(post.title);
		});
	}
	
	//刷新表格
	function onRefresh(pageNumber, pageSize) {
		postcommit('PostAction.do?method=getPostAndReplyByPage',{id:id,page:pageNumber,rows:pageSize},'正在加载中...',function(result){
			var data = result.data;
			if(data) {
				settabledata(data);
			}
		});
	}
	
	//设置table值
	function settabledata(data) {
		$('#table').empty();
		$('html,body').animate({scrollTop:$('body').offset().top},100)
		$('#pp').pagination('refresh',{	// 改变选项并刷新分页栏信息
			total: data.total
		});
		var rows = data.rows;
		if(rows && rows.length > 0) {
			for(var i in rows) {
				var obj = rows[i];
				
				//空白行
				var tr = '<tr style="height: 10px;"></tr>';
				$('#table').append(tr);
				
				//数据行
				tr = '<tr>';
				//照片列
				tr += '<td style="width: 200px;vertical-align: top;">';
				tr += '<div class="ImageDivStyle">';
				tr += '<img alt="头像图片" src="<%=basePath%>userphoto/' + obj.issueuserbean.photo + '" class="ImageStyle"/>';
				tr += '</div>';
				tr += '<div style="padding-top: 10px;">' + obj.issueuserbean.nickname + '</div>';
				tr += '</td>';
				//内容列
				tr += '<td class="postcontentdivshow">';
				tr += '<div style="height: 100%">';
				tr += '<div style="width: 100%;text-align: left;font-size: 8px;padding-bottom: 10px;">';
				tr += '<span style="float: left;">发表于:'+ obj.issuetime + '</span>';
				if(obj.ordernum == 0) {
					tr += '<span style="float: right;">'+ '1楼(楼主)' + '</span>';
				}else if(obj.ordernum == 1) {
					tr += '<span style="float: right;">'+ '2楼(沙发)' + '</span>';
				}else if(obj.ordernum == 2) {
					tr += '<span style="float: right;">'+ '3楼(板凳)' + '</span>';
				}else if(obj.ordernum == 3) {
					tr += '<span style="float: right;">'+ '4楼(地板)' + '</span>';
				}else {
					var num = obj.ordernum + 1;
					tr += '<span style="float: right;">'+ num + '楼</span>';
				}
				
				tr += '</div>';
				tr += '<hr style="width: 100%;">';
				tr += '<div style="width: 100%;text-align: left;min-height: 200px;">' + obj.content + '</div>';
				
				tr += '<div style="width: 100%;text-align: right;font-size: 8px;padding-bottom: 3px;">';
				tr += '<hr style="width: 100%;">';
				if(obj.isshield == '0') {
					tr += '<a href="javascript:void(0)" onclick="shieldMsg(\'' + obj.id + '\');" style="text-decoration: none;padding-right:10px;">屏蔽</a>'
				}else {
					tr += '<a href="javascript:void(0)" onclick="shieldMsg(\'' + obj.id + '\');" style="text-decoration: none;padding-right:10px;">取消屏蔽</a>'
				}
				
				if(obj.issueuserbean.enableSendMsg == '0') {
					tr += '<a href="javascript:void(0)" onclick="enableSendMsg(\'' + obj.issueuserbean.id + '\');" style="text-decoration: none;padding-right:5px;">禁言</a>'
				}else {
					tr += '<a href="javascript:void(0)" onclick="enableSendMsg(\'' + obj.issueuserbean.id + '\');" style="text-decoration: none;padding-right:5px;">取消禁言</a>'
				}
				
				tr += '</div>';
				tr += '</div>';
				tr += '</td>';
				tr += '</tr>';
				$('#table').append(tr);
			}
		}
	}
	
	//置顶帖子
	function updateToptime() {
		postcommit('PostAction.do?method=updateToptime',{id:id},'',function(result){
			alertInfo("帖子已置顶!");
		});
	}

	//禁言
	function enableSendMsg(id) {
		postcommit('UserAction.do?method=updateEnableSendMsg',{id:id},'',function(result){
			alertInfo("禁言状态已修改!",function() {
				var obj = $('#pp').pagination('options');
				onRefresh(obj.pageNumber,obj.pageSize);
			});
		});
	}
	//屏蔽
	function shieldMsg(id) {
		postcommit('PostAction.do?method=updateIsshield',{id:id},'',function(result){
			alertInfo("屏蔽状态已修改!",function() {
				var obj = $('#pp').pagination('options');
				onRefresh(obj.pageNumber,obj.pageSize);
			});
		});
	}
	
	function back() {
		window.location.href="<%=basePath%>jsp/backstage/post/postlist.jsp";
	}
	
</script>
</head>
<body style="text-align: center;width:1024px;margin-left: auto;margin-right: auto;margin-top: 0px;height: 100%;">
	<div style="height: 30px;padding: 5px;margin: 0px;background-color: #CDC9C9;">
		<div style="text-align: left;float: left;">
			<a href="javascript:void(0)" class="easyui-linkbutton c2" style="padding-left: 5px;padding-right: 5px;margin-top: 3px;"
			 onclick="back();">返回列表</a>
		</div>
			
		<div style="text-align: left;float: right;">
			<a href="javascript:void(0)" class="easyui-linkbutton c1" style="padding-left: 5px;padding-right: 5px;margin-top: 3px;" 
 			 onclick="updateToptime()">置顶帖子</a>
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton c7" style="padding-left: 5px;padding-right: 5px;margin-top: 3px;" -->
<!-- 			 onclick="goreply()">回复帖子</a> -->
<!-- 			 <a href="javascript:void(0)" class="easyui-linkbutton c1" style="padding-left: 5px;padding-right: 5px;margin-top: 3px;" -->
<!-- 			 onclick="newpost()">发布帖子</a> -->
		</div>
	</div>
	<div style="height: 50px;padding: 0px;margin: 0px;">
		<span id="title" class="posttitle"></span>
	</div>
	
	<div class="postcontentdiv">
		<table id="table" style="border:0px;border-collapse: collapse;">
<!-- 			<tr style="height: 10px;"></tr> -->
<!-- 			<tr> -->
<!-- 				<td style="width: 200px;vertical-align: top;"> -->
<!-- 					<div class="ImageDivStyle"> -->
<%-- 						<img id="imgPre" alt="请上传头像" src="<%=basePath %>attachment/userphoto/defaultphoto.jpg" class="ImageStyle"> --%>
<!-- 						<div style="padding-top: 10px;"> -->
<!-- 							sdf -->
<!-- 						</div> -->
						
<!-- 					</div> -->
<!-- 				</td> -->
<!--  				<td style="width: 824px;background-color: white;border: 1px solid #CFCFCF;vertical-align: top;padding: 5px;">  -->
<!--  					<div style="height: 100%"> -->
<!--  						<div style="width: 100%;text-align: left;font-size: 8px;padding-bottom: 10px;"> -->
<!-- 	 						<span style="float: left;">发表于：2014-11-25 25-55-65</span> -->
<!-- 	 						<span style="float: right;">2楼(沙发)</span> -->
<!-- 	 					</div> -->
<!-- 	 					<hr style="width: 100%;"> -->
<!-- 	 					<div style="width: 100%;text-align: left;min-height: 200px;"> -->
<!-- 	 						dsf -->
<!-- 	 					</div> -->
	 					
<!-- 	 					<div style="width: 100%;text-align: left;font-size: 8px;padding-bottom: 3px;"> -->
<!-- 	 						<hr style="width: 100%;"> -->
<!-- 	 						<a href="javascript:void(0)" onclick="abc();" style="text-decoration: none;">回复</a> -->
<!-- 	 					</div> -->
<!--  					</div> -->
<!--  				</td>  -->
<!-- 			</tr> -->
<!-- 			<tr style="height: 10px;"> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td style="width: 200px;"> -->
<!-- 					<div class="ImageDivStyle"> -->
<%-- 						<img id="imgPre" alt="请上传头像" src="<%=basePath %>attachment/userphoto/defaultphoto.jpg" class="ImageStyle"> --%>
<!-- 						<div style="padding-top: 10px;"> -->
<!-- 							sdfsdfsdf -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</td> -->
<!--  				<td style="width: 824px;background-color: white;border: 1px solid #CFCFCF;">  -->
<!--  				</td>  -->
<!-- 			</tr> -->
		</table>
		<div style="width:814px;padding-top: 15px;padding-left: 200px;">
			<div id="pp" style="background:#efefef;border:1px solid #ccc;"></div>
		</div>
	</div>
<!-- 	<div id="receivediv" name="receivediv" style="width:1024px;margin-top: 10px;background-color: #F7F7F7;text-align: left;"> -->
<!-- 		<div style="height: 100%;padding-left: 180px;padding-top: 10px;padding-bottom: 10px;"> -->
<!-- 			<form id="form1"> -->
<!-- 				<textarea id="content" name="content"></textarea> -->
<!-- 				<input id="postid" name="postid" type="hidden"> -->
<!-- 			</form> -->
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton c1" style="margin-top: 10px; padding-left: 5px;padding-right: 5px;" -->
<!-- 				 onclick="savepostreply()">发表回复</a> -->
<!-- 		</div> -->
<!-- 	</div> -->
</body>
</html>