<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
<body>
	<form action="" name="Form" id="Form" method="post" enctype="multipart/form-data" class="layui-form layui-form-pane">
	    <input type="hidden" name="state" id="state" value="${pd.state}">
		<div style="padding: 20px 30px">
			<div class="layui-form-item">
				<label class="layui-form-label">预告标题：</label>
				<div class="layui-input-block">
					<input type="text" name="title" id="title" autocomplete="off" placeholder="请输入标题" class="layui-input" value="${pd.title}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">预告时间：</label>
				<div class="layui-input-block">
					<input type="text" id="onlinetime" name="onlinetime" autocomplete="off" placeholder="请选择时间" class="layui-input"  value="${pd.onlinetime}">
				</div>
			</div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">预告内容：</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" class="layui-textarea" id="content" name="content">${pd.content}</textarea>
				</div>
			</div>
		</div>
	</form>
</body>
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script type="text/javascript">
	$(top.hangge());//关闭加载状态
	
	//服务器校正保存方法
	layui.use(['layer','form','laydate'],function(){
	    var $ = layui.$
			,layer =layui.layer
			,form = layui.form
			,laydate = layui.laydate
			laydate.render({
				elem: '#onlinetime'
			});
	})

	
	function productNews_submit(){
		var state=$("#state").val();//状态
		var title=$("#title").val();//预告标题
		var onlinetime=$("#onlinetime").val();//预告时间
		var content=$("#content").val();//预告内容
		var element_id= ["#title","#onlinetime","#content"];
		var element_tip =["预告标题不能为空","预告时间不能为空","预告内容不能为空"];
		for (var i = 0; i < element_id.length; i++) {
			if($(element_id[i]).val()==""){
				layer.msg(element_tip[i]);
				return false;
			}
		}
		$.ajax({
			async:false,
			type	: "POST",
			url		:'<%=basePath%>/productNews/editNews.do',
			data: {state:state,title:title,content:content,onlinetime:onlinetime},
			dataType:'json',
			success : function(data) {
				layer.msg(data.message, {
					time : data.message.length * 200
				}, function() {
					if ("true" == data.result) {
						layer.closeAll();
						parent.location.reload();//刷新父页面	   
					}
				});
			},
			error : function() {
				layer.msg("系统繁忙，请稍后再试！");
			}
		});
	}
</script>
</html>