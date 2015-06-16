<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commontaglib.jsp" %>
<style>
	*{font-size:12px; font-family:Tahoma,Verdana,微软雅黑,新宋体}
	body{background:#D2E0F2; }
	a{ color:Black; text-decoration:none;}
	a:hover{ color:Red; text-decoration:underline;}
	.footer{text-align:center;color:#15428B; margin:0px; padding:0px;line-height:23px; font-weight:bold;}
	.head a{color:White;text-decoration:underline;}
	.navlist{list-style-type:none;margin:0px; padding:10px;}
	.navlist li{ padding:0px;}
	.navlist li a{line-height:20px;}
	.navlist li div{margin:2px 0px;padding-left:10px;padding-top:2px; border:1px dashed #ffffff;}
	.navlist li div.hover{border:1px dashed #99BBE8; background:#E0ECFF;cursor:pointer;}
	.navlist li div.hover a{color:#416AA3;}
	.navlist li div.selected{border:1px solid #99BBE8; background:#E0ECFF;cursor:default;}
	.navlist li div.selected a{color:#416AA3; font-weight:bold;}
	.icon{width:18px; line-height:18px; display:inline-block;}
</style>

    <script type="text/javascript">

	var _menus = {
	"menus": [{////begin menu 
		"menuid": "1",
		"icon": "icon-dwrymenu",
		"menuname": "单位人员",
		"menus": [{
			"menuid": "12",
			"menuname": "部门管理",
			"icon": "icon-depmenu",
			"url": "jsp/backstage/department/depindex.jsp"
		
			
		},{
			"menuid": "13",
			"menuname": "人员管理",
			"icon": "icon-usermenu",
			"url": "jsp/backstage/user/userindex.jsp"
			
		}]
	}////end menu 
	,{////begin menu 
		"menuid": "4",
		"icon": "icon-tzxxmenu",
		"menuname": "信息发布",
		"menus": [{
			"menuid": "12",
			"menuname": "发布公告",
			"icon": "icon-informenu",
			"url": "jsp/backstage/information/inforlist.jsp"
		
			
		},{
			"menuid": "13",
			"menuname": "发布新闻",
			"icon": "icon-newsmenu",
			"url": "jsp/backstage/news/newslist.jsp"
			
		},{
			"menuid": "14",
			"menuname": "短信群发",
			"icon": "icon-messagemenu",
			"url": "jsp/backstage/message/messagelist.jsp"
			
		}]
	}////end menu 
	,
	{////begin menu 
		"menuid": "4",
		"icon": "icon-ltglmenu",
		"menuname": "论坛管理",
		"menus": [{
			"menuid": "12",
			"menuname": "帖子管理",
			"icon": "icon-portmenu",
			"url": "jsp/backstage/post/postlist.jsp"
		
			
		}]
	}////end menu 
	]
	};

        $(function() {
				
        	InitLeftMenu();
        });
		
        
      	//初始化左侧
        function InitLeftMenu() {
        	$("#nav").accordion({animate:false,fit:true,border:false});
        	var selectedPanelname = '';
            $.each(_menus.menus, function(i, n) {
        		var menulist ='';
        		menulist +='<ul class="navlist">';
                $.each(n.menus, function(j, o) {
        			menulist += '<li><div ><a ref="'+o.menuid+'" href="javascript:void(0)" rel="' + o.url + '" ><span class="icon '+o.icon+'" >&nbsp;</span><span class="nav">' + o.menuname + '</span></a></div> ';

        			if(o.child && o.child.length>0)
        			{
        				//li.find('div').addClass('icon-arrow');

        				menulist += '<ul class="third_ul">';
        				$.each(o.child,function(k,p){
        					menulist += '<li><div><a ref="'+p.menuid+'" href="#" rel="' + p.url + '" ><span class="icon '+p.icon+'" >&nbsp;</span><span class="nav">' + p.menuname + '</span></a></div> </li>'
        				});
        				menulist += '</ul>';
        			}

        			menulist+='</li>';
                })
        		menulist += '</ul>';

        		$('#nav').accordion('add', {
                    title: n.menuname,
                    content: menulist,
        			border:false,
                    iconCls: 'icon ' + n.icon
                });

        		if(i==0)
        			selectedPanelname =n.menuname;

            });
        	$('#nav').accordion('select',selectedPanelname);

        	$('.navlist li a').click(function(){
        		var tabTitle = $(this).children('.nav').text();

        		var url = $(this).attr("rel");
        		var menuid = $(this).attr("ref");
        		var icon = $(this).find('.icon').attr('class');
				$('#iframe').attr('src',url);

        	}).hover(function(){
        		$(this).parent().addClass("hover");
        	},function(){
        		$(this).parent().removeClass("hover");
        	});

        	//选中第一个
        	//var panels = $('#nav').accordion('panels');
        	//var t = panels[0].panel('options').title;
            //$('#nav').accordion('select', t);
        }

    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  fit="true"   scroll="no">


    <div region="west" split="true"  title="后台管理" style="width:180px;" id="west">
		<div id="nav" >
		</div>
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
    	<iframe id="iframe" style="width:100%;height: 100%;background-color: #FAFAFA;border: 0px;">
    	
    	</iframe>
    </div>
    
</body>
</html>