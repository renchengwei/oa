//
//var pathname=window.location.pathname;
//pathname=pathname.substr(0,pathname.indexOf("/", 1));
//var basePath =window.location.protocol+"//"+window.location.host+pathname+"/";

//处理键盘事件禁止后退键（Backspace）密码或单行、多行文本框除外

function banBackSpace(e){
   var ev = e || window.event;//获取event对象
   var obj = ev.target || ev.srcElement;//获取事件源
   var t = obj.type || obj.getAttribute('type');//获取事件源类型
   //获取作为判断条件的事件类型
   var vReadOnly = obj.readOnly;
   var vDisabled = obj.disabled;
   //处理undefined值情况
   vReadOnly = (vReadOnly == undefined) ? false:vReadOnly;
   vDisabled = (vDisabled == undefined) ? true:vDisabled;
   //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
   //并且readOnly属性为true或disabled属性为true的，则退格键失效
   var flag1 = ev.keyCode == 8 && (t=="password"||t=="text"||t=="textarea")&&(vReadOnly==true||vDisabled==true);
   //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
   var flag2 = ev.keyCode == 8 && t != "password"&&t != "text"&&t != "textarea";
   //判断
   if(flag2|| flag1)
	   return false;
}

//var  easyuiThemeName=  $.cookie('easyuiThemeName');
//if(!easyuiThemeName){
//	easyuiThemeName="default";
//}
//$('head').append('<link rel="stylesheet" id="easyuiTheme" type="text/css" href="'+basePath+'jquery-easyui-1.4/themes/'+easyuiThemeName+'/easyui.css'+'" />');

$(function(){
	//禁用右键菜单
//	document.oncontextmenu=function(){
//		return false;
//	};

	//禁止退格键 作用于Firefox、Opera
	document.onkeypress=banBackSpace;
	//禁止退格键 作用于IE、Chrome
	document.onkeydown=banBackSpace;
	 
	$('.combo').keydown(function(event){
		if(event.keyCode == '40') {
			var name = $(this).find('.combo-value').attr('name');
			$('#' + name).combo('showPanel');
		}
	});
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
   
function alertInfo(content,fun){
	$.messager.alert("系统提示",content,'',fun);
}

//数字验证
$.extend($.fn.validatebox.defaults.rules, {
	numvalidate : {
		validator : function(value, param) {
			if (/^[0-9]*$/.test(value)) {
				return true;
			}
			return false;
		},
		message : '请输入数字'
	}
});

//电话号码
$.extend($.fn.validatebox.defaults.rules, {
	telephonevalidate : {
		validator : function(value, param) {
			if (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(value)) {
				return true;
			}
			return false;
		},
		message : '请输入正确的电话号码'
	}
});

//手机号码
$.extend($.fn.validatebox.defaults.rules, {
	mobilevalidate : {
		validator : function(value, param) {
			if (/^[1][0-9]{10}$/.test(value)) {
				return true;
			}
			return false;
		},
		message : '请输入正确的手机号码'
	}
});

//ip
$.extend($.fn.validatebox.defaults.rules, {
	ipvalidate : {
		validator : function(value, param) {
			if (/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(:[0-9]{1,4})?$/.test(value)) {
				return true;
			}
			return false;
		},
		message : '请输入正确的IP地址'
	}
});
//email
$.extend($.fn.validatebox.defaults.rules, {
	emailvalidate : {
		validator : function(value, param) {
			if (/^\w+([-+.]\w+)*@\w+([-.]\\w+)*\.\w+([-.]\w+)*$/.test(value)) {
				return true;
			}
			return false;
		},
		message : '请输入正确的IP地址'
	}
});

//手机前缀
$.extend($.fn.validatebox.defaults.rules, {
	phoneprefix : {
		validator : function(value, param) {
			if (/^0?$/.test(value)) {
				return true;
			}
			return false;
		},
		message : '手机前缀只能为0'
	}
});

Date.prototype.format =function(format){
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	};
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
};
//获取指定月份的最大天数
function getDaysInMonth(year,month){
    month = parseInt(month,10)+1;
    var temp = new Date(year+"/"+month+"/0");
    return temp.getDate();
}

//获取当前月份中最大天数
function getMaxDayOfCurrentMonth() {
	var nowDate = new Date();
	var year = nowDate.getFullYear();
	var month = nowDate.getMonth() + 1;
	var maxDay = getDaysInMonth(year,month);
	nowDate.setDate(maxDay);
	return nowDate.format("yyyy-MM-dd");  
}

//获取当前月份中最大天数
function getMinDayOfCurrentMonth() {
	var nowDate = new Date();
	nowDate.setDate(1);
	return nowDate.format("yyyy-MM-dd"); 
}

//获取当前日期
function getCurrentDate() {
	var nowDate = new Date();
	var nowStr = nowDate.format("yyyy-MM-dd"); 
	return nowStr;
}
//获取当前日期
function getCurrentDateTime() {
	var dtCur = new Date();  
    var yearCur = dtCur.getFullYear();  
    var monCur = dtCur.getMonth() + 1;  
    var dayCur = dtCur.getDate();  
    var hCur = dtCur.getHours();  
    var mCur = dtCur.getMinutes();  
    var sCur = dtCur.getSeconds();  
    timeCur = yearCur + "-" + (monCur < 10 ? "0" + monCur : monCur) + "-"  
            + (dayCur < 10 ? "0" + dayCur : dayCur) + " " + (hCur < 10 ? "0" + hCur : hCur)  
            + ":" + (mCur < 10 ? "0" + mCur : mCur) + ":" + (sCur < 10 ? "0" + sCur : sCur);  
    return timeCur;
}
//获取当前周的第一天
function getCurrentWeekFirstDate() {
	var nowDate = new Date();
	var day = nowDate.getDay() - 1;
	nowDate.setDate(nowDate.getDate() - day);
	return nowDate.format("yyyy-MM-dd");
}
//获取当前周的最后一天
function getCurrentWeekLastDate() {
	var nowDate = new Date();
	var day = 7 - nowDate.getDay();
	nowDate.setDate(nowDate.getDate() + day);
	return nowDate.format("yyyy-MM-dd");
}
//获取本年第一天
function getCurrentYearFirstDay() {
	var nowDate = new Date();
	nowDate.setMonth(0);
	nowDate.setDate(1);
	return nowDate.format("yyyy-MM-dd");
}
//获取本年最后一天
function getCurrentYearLastDay() {
	var nowDate = new Date();
	nowDate.setMonth(11);
	nowDate.setDate(31);
	return nowDate.format("yyyy-MM-dd");
}
//通用的时间选择下拉事件
function dateChooseEvent(record) {
	var val = record.value;
	if(val == '0') {//当天
		var nowDate = getCurrentDate();
		$('#st').datebox('setValue',nowDate);
		$('#et').datebox('setValue',nowDate);
	}else if(val == "1") {//本周
		var firstDay = getCurrentWeekFirstDate();
		var lastDay = getCurrentWeekLastDate();
		$('#st').datebox('setValue',firstDay);
		$('#et').datebox('setValue',lastDay);
	}else if(val == "2") {//本月
		var firstDay = getMinDayOfCurrentMonth();
		var lastDay = getMaxDayOfCurrentMonth();
		$('#st').datebox('setValue',firstDay);
		$('#et').datebox('setValue',lastDay);
	}else if(val == "3") {//本年
		var firstDay = getCurrentYearFirstDay();
		var lastDay = getCurrentYearLastDay();
		$('#st').datebox('setValue',firstDay);
		$('#et').datebox('setValue',lastDay);
	}
}

//获取默认搜索开始日期
function getDefaultBeginDate() {
	return getCurrentDate();
}
//获取默认搜索结束日期
function getDefaultEndDate() {
	return getCurrentDate();
}

//事件选择时触发
function stSelectEvent(date) {
	$('#datechoose').combobox('setValue', '4');
}

var HKEY_Root,HKEY_Path,HKEY_Key; 
HKEY_Root="HKEY_CURRENT_USER"; 
HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\"; 
//设置网页打印的页眉页脚为空
function pagesetup_null()
{   
	var Wsh=new ActiveXObject("WScript.Shell"); 
	HKEY_Key="header"; 
	Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,""); 
	HKEY_Key="footer"; 
	Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,""); 
}

//打印网页
function printpage(url,params) {
	params = decodeURIComponent(params,true);
	params = encodeURI(encodeURI(params));
	var iWidth=1100; 
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	var iHeight = window.screen.availHeight;
	window.open(url + params,'newwindow','top=0,left=' + iLeft +',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no,width=1100,height=' + iHeight);
}
//统一处理错误信息
function errorFilter(result) {
	if($.isArray(result)) {
		return {rows:result};
	}else {
		if(result.isSuccess) {
			return result.data;
		}else {
			alertInfo(result.errorMsg);
		}
		return null;
	}
}
var LOADINGMSG = '正在加载中，请稍候……';
//发送post请求
function postcommit(url,data,msg,onsuccess,onerror) {
	if(msg) {
		forbiddenPage(msg);
	}else {
		forbiddenPage("正在处理，请稍候……");
	}
	$.ajax({
	   type: "POST",
	   url: url,
	   data: data,
	   dataType:'json',
	   success: function(result){
		   releasePage();
		   if(result.isSuccess) {
			   if(onsuccess) {
				   onsuccess(result);
			   }
		   }else {
			   if(onerror) {
				   onerror(result);
			   }else {
				   alertInfo(result.errorMsg); 
			   }
		   }
	   },
	   error:function(){
		   //数据加载失败
		   releasePage();
		   alertInfo("服务器连接异常!");
	   }
	});
}


