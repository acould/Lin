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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
	<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
	<link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">

    <title>Document</title>
    <style>
		.layui-badge {
			position: absolute;
			right: -8px;
			top: -4px;
			border-radius: 20px;
		}
		.layui-rate li i.layui-icon {
			font-size: 16px;}
    </style>
</head>
<body class="scroll" style="background-color: #f2f2f2;">
	<div class="layui-fluid" style="padding-bottom: 54px;">
		<div class="layui-card">
			<div class="lk-table-seach">
				<form class="layui-form" method="post" id="excel_form">
					<div class="demoTable table-seach">
						<div class="layui-inline" style="position: relative;top: -24px;line-height: 1">
							<div class="layui-inline">
								<input class="layui-input" name="keywords" id="keywords" autocomplete="off" placeholder="关键词搜索">
							</div>
							<a class="btn btn-xm btn-success" data-type="search" id="toSearch" style="margin-left: 10px;">
								<i class="layui-icon" style="padding-right: 4px;">&#xe615;</i>搜索
							</a>
						</div>
						<div class="filtrate_module" style="line-height: 1.4;padding: 10px;margin-left: 20px" id="filtrate_module">
							<ul class="layui-clear">
								<li class="active" onclick="initial('0')">全部</li>
								<li onclick="initial('1')">报名中</li>
								<li onclick="initial('2')">比赛中</li>
								<li onclick="initial('3')">未发布</li>
								<li onclick="initial('4')">报名结束</li>
								<li onclick="initial('5')">比赛结束</li>
							</ul>
						</div>
						<div style="float: right">
							<a class="btn btn-xm btn-primary" style="margin-left: 10px;" onclick="edit()">新增赛事</a>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="match_listBox">
			<div class="match_box">
				<div class="img">
					<img src="" alt="" width="100%" height="100%">
				</div>
				<div class="content">
					<p class="title">杭州市网吧电竞大赛LOL专场</p>
					<p class="store">主办门店：xxxxxxxx门店1111111111111111111</p>
					<p class="store">报名类型：3人组队、允许单人</p>
					<p class="store">报名时间：2019-01-10 至  2019-01-30</p>
					<p class="store">比赛时间：2019-01-10 至  2019-01-30</p>
					<p class="status">
						<font color="#FF6633">报名中</font>
						<span style="margin-left: 20px">已报名30人</span>
					</p>
				</div>
				<div class="card-btn-box" style="background: #fff">
					<div class="layui-row">
						<div class="layui-col-xs4 layui-col-sm4 layui-col-md4 border">
							<span class="bghover-success card-btn" title="编辑"><i class="layui-icon" >&#xe642;</i></span>
						</div>
						<div class="layui-col-xs4 layui-col-sm4 layui-col-md4 border">
							<span class="bghover-warning card-btn" title="报名表"><i class="layui-icon" >&#xe63c;</i></span>
						</div>
						<div class="layui-col-xs4 layui-col-sm4 layui-col-md4 border">
							<span class="bghover-danger card-btn" title="删除"><i class="layui-icon" >&#xe640;</i></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>




<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script type="text/javascript">
		$(top.hangge());//关闭加载状态

		$("#filtrate_module li").click(function () {
			$("#filtrate_module li").removeClass("active");
			$(this).addClass("active")
		})
		var initialUrl = "";
		initial(0)
		function initial(sysmatch_type){
			var sysmatch_html = '';
			$.post(initialUrl,{'type':sysmatch_type}, function (res){
				var data = res.data.pd;
				var mach_status = "";
				var mach_number = '';
				var mach_form = '';
				var btnClass = 'layui-col-xs4 layui-col-sm4 layui-col-md4 border';
				if(data.length > 0){
					for (var i = 0; i < data.length; i++){
						switch (data[i].mach_status){
							case '1':
								mach_status = "报名中";
								break;
							case '2':
								mach_status = "比赛中";
								break;
							case '3':
								mach_status = "未发布";
								break;
							case '4':
								mach_status = "报名结束";
								break;
							case '5':
								mach_status = "比赛结束";
								break;
						}
						if(data[i].mach_status != '3'){
							mach_number = '已报名'+data[i].mach_number+'人';
							mach_form = '<div class="layui-col-xs4 layui-col-sm4 layui-col-md4 border">'+
							'<span class="bghover-warning card-btn" title="报名表"><i class="layui-icon" >&#xe63c;</i></span>'+
							'</div>';
						}else {
							mach_number = "";
							btnClass = 'layui-col-xs6 layui-col-sm6 layui-col-md6 border';
							mach_form = "";
						}
					 sysmatch_html = '<div class="match_box">'+
							'<div class="img">'+
							 	'<img src="'+data[i].mach_path+'" alt="" width="100%" height="100%">'+
							'</div>'+
							'<div class="content">'+
							'<p class="title">'+data[i].mach_name+'</p>'+
							'<p class="store">主办门店：'+data[i].store_name+'</p>'+
							'<p class="store">报名类型：3人组队、单人</p>'+
							'<p class="store">报名时间：'+data[i].sign_beginTime+'&nbsp;至&nbsp;'+data[i].sign_endTime+'</p>'+
							'<p class="store">比赛时间：'+data[i].game_beginTime+'&nbsp;至&nbsp;'+data[i].game_endTime+'</p>'+
							'<p class="status">'+
							'<font color="#FF6633">'+mach_status+'</font>'+
							'<span style="margin-left: 20px">'+mach_number+'</span>'+
							'</p>'+
							'</div>'+
							'<div class="card-btn-box" style="background: #fff">'+
							'<div class="layui-row">'+
							'<div class="'+btnClass+'" onclick=edit('+data[i].id+')>'+
							'<span class="bghover-success card-btn" title="编辑"><i class="layui-icon" >&#xe642;</i></span>'+
							'</div>'+
							 mach_form +
							'<div class="'+btnClass+'" onclick=del('+data[i].id+')>'+
							'<span class="bghover-danger card-btn" title="删除"><i class="layui-icon" >&#xe640;</i></span>'+
							'</div>'+
							'</div>'+
							'</div>'+
							'</div>'
					}
				}
			})
		}
		//新增或编辑
		function edit(id) {
			if(id == "" || id == undefined){
				window.open('<%=basePath%>sysmatch/goAdd.do ')
			}else {
				window.open('<%=basePath%>sysmatch/goEdit.do?SYSMATCH_ID='+ID);
			}
		}

		//删除
		function del(Id){
			layer.confirm('确定要删除吗?', {
                    btn: ['确定','取消'],
                }, function(){
                	$.ajax({
						type: "POST",
						url: '<%=basePath%>sysmatch/delete.do',
						data: {SYSMATCH_ID:Id},
						dataType:'json',
						cache: false,
						success: function(data){
							layer.msg(data.message);
							if(data.result == "true"){
								setTimeout(function () {
									location.reload();
								},500)
							}
						}
					});
                }, function(){
                    return
			});
		}
		

		//发布
		function editState(Id,state){
			var message = "";
			if(state == 1){
				message = "确定发布吗？";
			}else if(state == 2){
				message = "确定取消发布吗？";
			}
			layer.confirm(message, {
                    btn: ['确定','取消'],
                }, function(){
                	$.ajax({
						type: "POST",
						url: '<%=basePath%>sysmatch/updateState.do',
	    				data: {SYSMATCH_ID:Id,STATE:state},
						dataType:'json',
						cache: false,
						success: function(data){
							layer.msg(data.message);
							if(data.result == "true"){
								setTimeout(function () { 
                 					location.reload();
                 				},500)
							}
						},
						error:function(){
			               layer.msg("系统繁忙，请稍后再试！");
			               return false;
			            }
					});
                }, function(){
                    return
                });
		}
	</script>
</body>
</html>