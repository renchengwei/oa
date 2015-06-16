

window.onload = function(){
	$('#loading-mask').fadeOut();
}

$(function(){
	//InitMenu();

})

//初始化菜单
function InitMenu() {
	$.post("LoginAction.do?method=getLoginUserMenus",function(result){
		if(result.isSuccess) {
			var data = result.data;
			$.each(data, function(i, n) {
				$('#menuspan').append('<a id="' + n.id + '" href="javascript:void(0)" onclick=menuclick(this) url="' + n.url + '">' + n.name + '</a>');
				$('#' + n.id).linkbutton({   
				    plain:true,
				    size:'large'
				}); 
			});
			//修改菜单样式
			$('.l-btn-text').css({'font-size':'18px','color':'white'});
			$('.l-btn-text').mouseover(function(){
				$(this).css('color','black');
			});
			$('.l-btn-text').mouseout(function(){
				$(this).css('color','white');
        	});
	   	}else {
	   		alertInfo(result.errorMsg);
	   	}
	},'json');
	
}

function menuclick(menu) {
	var url = $(menu).attr('url');
	$('#iframe').attr('src',url);
}
//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}
