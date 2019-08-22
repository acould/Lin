<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

	<!-- jsp文件头和头部 -->
	<%@ include file="top.jsp"%>
	<style type="text/css">
	.commitopacity{position:absolute; width:100%; height:100px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.8; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
	</style>
	<link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
	<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
	<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
	<!-- <link rel="stylesheet" href="static/ace/css/ace.onpage-help.css" /> -->
	<!-- 即时通讯
	<link rel="stylesheet" type="text/css" href="plugins/websocketInstantMsg/ext4/resources/css/ext-all.css">
	<link rel="stylesheet" type="text/css" href="plugins/websocketInstantMsg/css/websocket.css" />
	<script type="text/javascript" src="plugins/websocketInstantMsg/ext4/ext-all-debug.js"></script>
	<script type="text/javascript" src="plugins/websocketInstantMsg/websocket.js"></script>
	 -->
	 <style type="text/css">
	 	svg path,
		svg rect {fill: #ff8800;}
		.loader {display: inline-block;position: relative;top: 12px;}
		.commitopacity {z-index:10;background:#000;opacity:0.5}
		.layui-badge {margin-left:6px;}
		.sidebar-hide {
    		overflow-y: auto;
    		overflow-x:hidden;
    		height: calc(100vh - 48px);
		}
	 </style>
</head>
	<body class="no-skin scroll" style="overflow-y: hidden;">
		<!-- #section:basics/navbar.layout -->		
		<!-- 页面顶部¨ -->
		<%@ include file="head.jsp"%>
		<div id="websocket_button"></div><!-- 少了此处，聊天窗口就无法关闭 -->
		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>
			
			<!-- #section:basics/sidebar -->
			<!-- 左侧菜单 -->
			<%@ include file="left.jsp"%>

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
						<!-- #section:settings.box -->
					
						<div class="row">	
						<div id="jzts" style="display:none; width:100%; position:fixed; z-index:99999;height:100%">
						<div class="commitopacity" id="bkbgjz"></div>
						<div style="width: 108px;height: 100px;position: absolute;top: -30%;bottom: 0px;left: -7%;right: 0;margin: auto;z-index:1000">
							<div style="float: left;margin-top: 3px;">
							<!-- svg动画 -->
								<div class="loader loader--style1" title="0">
								  <svg version="1.1" id="loader-1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
								   width="40px" height="40px" viewBox="0 0 40 40" enable-background="new 0 0 40 40" xml:space="preserve">
								  <path opacity="0.3" fill="#000" d="M20.201,5.169c-8.254,0-14.946,6.692-14.946,14.946c0,8.255,6.692,14.946,14.946,14.946
								    s14.946-6.691,14.946-14.946C35.146,11.861,28.455,5.169,20.201,5.169z M20.201,31.749c-6.425,0-11.634-5.208-11.634-11.634
								    c0-6.425,5.209-11.634,11.634-11.634c6.425,0,11.633,5.209,11.633,11.634C31.834,26.541,26.626,31.749,20.201,31.749z"/>
								  <path fill="#000" d="M26.013,10.047l1.654-2.866c-2.198-1.272-4.743-2.012-7.466-2.012h0v3.312h0
								    C22.32,8.481,24.301,9.057,26.013,10.047z">
								    <animateTransform attributeType="xml"
								      attributeName="transform"
								      type="rotate"
								      from="0 20 20"
								      to="360 20 20"
								      dur="0.5s"
								      repeatCount="indefinite"/>
								    </path>
								  </svg>
								</div>
								<h5 class="lighter" style="margin-top: 20px;display: inline-block;color:#fff">&nbsp;加载中...</h5>
							</div>
							
						</div>
						</div>
						<div>
							<iframe name="mainFrame" id="mainFrame" frameborder="0" src="<%=basePath%>tab.do" style="margin:0 auto;width:100%;height:100%;"></iframe>
						</div>
						</div><!-- /.row -->	
					</div><!-- /.page-content -->
					
				</div>
			</div><!-- /.main-content -->

		</div><!-- /.main-container -->

		<!-- basic scripts -->
		<!-- 页面底部js¨ -->
		<%@ include file="foot.jsp"%>
		
		<!-- page specific plugin scripts -->
		
		<!-- ace scripts -->
		<!-- <script src="static/ace/js/ace/elements.scroller.js"></script> -->
		<!-- <script src="static/ace/js/ace/elements.colorpicker.js"></script> -->
		<!-- <script src="static/ace/js/ace/elements.fileinput.js"></script> -->
		<!-- <script src="static/ace/js/ace/elements.typeahead.js"></script> -->
		<!-- <script src="static/ace/js/ace/elements.wysiwyg.js"></script> -->
		<!-- <script src="static/ace/js/ace/elements.spinner.js"></script> -->
		<!-- <script src="static/ace/js/ace/elements.treeview.js"></script> -->
		<!-- <script src="static/ace/js/ace/elements.wizard.js"></script> -->
		<!-- <script src="static/ace/js/ace/elements.aside.js"></script> -->
		<script src="<%=basePath%>static/ace/js/ace/ace.js"></script>
		<!-- <script src="static/ace/js/ace/ace.ajax-content.js"></script> -->
		<!-- <script src="static/ace/js/ace/ace.touch-drag.js"></script> -->
		<script src="<%=basePath%>static/ace/js/ace/ace.sidebar.js"></script>
		<!-- <script src="static/ace/js/ace/ace.sidebar-scroll-1.js"></script> -->
		<!-- <script src="static/ace/js/ace/ace.submenu-hover.js"></script> -->
		<!-- <script src="static/ace/js/ace/ace.widget-box.js"></script> -->
		<!-- <script src="static/ace/js/ace/ace.settings.js"></script> -->
		<!-- <script src="static/ace/js/ace/ace.settings-rtl.js"></script> -->
		<!-- <script src="static/ace/js/ace/ace.settings-skin.js"></script> -->
		<!-- <script src="static/ace/js/ace/ace.widget-on-reload.js"></script> -->
		<!-- <script src="static/ace/js/ace/ace.searchbox-autocomplete.js"></script> -->
		<!-- inline scripts related to this page -->
		
		<!-- the following scripts are used in demo only for onpage help and you don't need them -->
		
		<!-- <script type="text/javascript"> ace.vars['base'] = '..'; </script>
		<script src="static/ace/js/ace/elements.onpage-help.js"></script>
		<script src="static/ace/js/ace/ace.onpage-help.js"></script> -->
		
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="<%=basePath%>static/js/myjs/head.js"></script>
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="<%=basePath%>static/js/myjs/index.js"></script>
		<!--引入弹窗组件start-->
		<script type="text/javascript" src="<%=basePath%>plugins/attention/zDialog/zDrag.js"></script>
		<script type="text/javascript" src="<%=basePath%>plugins/attention/zDialog/zDialog.js"></script>
		<!--引入弹窗组件end-->
		<!--提示框-->
		<!-- <script type="text/javascript" src="<%=basePath%>static/js/jquery.tips.js"></script> -->
		<script src="<%=basePath%>newStyle/layer/layer.js"></script>
	<script type="text/javascript">
		//读取站内访问量，动态加载百度的JS
		var _hmt = _hmt || [];
		(function() {
			var hm = document.createElement("script");
			hm.src = "https://hm.baidu.com/hm.js?99747536a31c41ed484b4f8c19896d9c";
			var s = document.getElementsByTagName("script")[0];
			s.parentNode.insertBefore(hm, s);
		})();
	</script>
		<script>
			getMessageNum('<%=basePath%>');
			function msg_close(){
				layer.close(layer.index);
			}
			function go_newMsg(){
				siMenu('z${pdMessage.MENU_ID}','lm${pdMessage.PARENT_ID}','${pdMessage.MENU_NAME}','${pdMessage.MENU_URL}');
				layer.close(layer.index);
			}
			$("#sidebar-collapse").click(function(){
				var c = $("#sidebar").attr("class");
				if(c == "sidebar  responsive sidebar-hide"){
					$("#sidebar").removeClass("sidebar-hide");
				}else{
					$("#sidebar").addClass("sidebar-hide");
				}
			})
		</script>
	</body>
</html>