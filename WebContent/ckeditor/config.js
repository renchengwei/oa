/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	 config.language = 'zh-cn';
//	 config.height = 400;
//	 config.width = 800;
	 config.toolbar = 'mybar';
	 //工具栏是否可以被收缩
	 config.toolbarCanCollapse = false;
	 //拖拽以改变尺寸
	 config.resize_enabled = false;
	// 当提交包含有此编辑器的表单时，是否自动更新元素内的数据
	 config.autoUpdateElement = true;
	//编辑器中回车产生的标签
	 config.enterMode = CKEDITOR.ENTER_P;
	 config.dialog_magnetDistance = 0;
	 
	 
//	 config.filebrowserBrowseUrl =  '/oa/ckfinder/ckfinder.html' ;  
	 config.filebrowserImageBrowseUrl =  '/oa/ckfinder/ckfinder.html?type=Images' ;  
//	 config.filebrowserFlashBrowseUrl =  '/oa/ckfinder/ckfinder.html?type=Flash' ;  
//	 config.filebrowserUploadUrl =  '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files' ;  
	 config.filebrowserImageUploadUrl =  '/oa/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images' ;  
//	 config.filebrowserFlashUploadUrl =  '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash' ;  
	 config.filebrowserWindowWidth = '1000';  
	 config.filebrowserWindowHeight = '700';  
	 config.language =  "zh-cn" ;  
	 
	 config.toolbar_Basic = [
	                         ['Font','FontSize','Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink','Image','-','TextColor','BGColor']
	                     ];
	 
	 config.toolbar_mybar = [
						//加粗     斜体，     下划线      穿过线      下标字        上标字
						['Bold','Italic','Underline','Strike','Subscript','Superscript'],
						//数字列表          实体列表            减小缩进    增大缩进
						['NumberedList','BulletedList','-','Outdent','Indent'],
						//左对齐             居中对齐          右对齐          两端对齐
						['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
						//超链接  取消超链接
						['Link','Unlink'],
						//图片    flash    表格       水平线            表情       特殊字符        分页符
						['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
//						'/',
						//样式       格式      字体    字体大小
						['Font','FontSize'],
						//文本颜色     背景颜色
						['TextColor','BGColor'],
						//全屏           显示区块
						['Maximize']
	                    ];
	  
};
