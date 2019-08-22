<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	
	<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <title>Document</title>
    <style>
        .borPadding {
            padding:20px 30px;
        }
        .layui-form-checkbox[lay-skin=primary] span {
            padding-right: 6px;
        }
    </style>
</head>
<body class="no-skin scroll">
<!-- /section:basics/navbar.layout -->
<div class="borPadding">
	<form class="layui-form layui-form-pane" name="Form" id="Form" method="post" action="<%=basePath%>welcome/${msg }.do">
		<input type="hidden" name="INTIVE_ID" id="INTIVE_ID" value="${pd.INTIVE_ID}"/>
        <div class="layui-form-item layui-form-text">
			<label class="layui-form-label">欢迎语:</label>
            <div class="layui-input-block">
            	<textarea class="layui-textarea" rows="10" cols="10" name="WELCOME" id="container" value="${pd.WELCOME}" title="欢迎语" maxlength="200" placeholder="这里输入欢迎语内容" onkeyup="checkNum()">${pd.WELCOME}</textarea>
            	<div>
					<p id="zishu" style="color: #999;text-align: right;font-size: 12px;">200/剩余字数</p>
				</div>
			</div>
		</div>
	</form>

	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		$(function(){
			var content = $("#container").val();
			var num = 200 - parseInt(content.length);
			$("#zishu").text(num+"/剩余字数");
		})
		function checkNum(){
			var content = $("#container").val();
			if(content.length > 200){
				layer.tips('超出字数限制', '#container', {
			 		tips: 3
	        	});
				$("#container").focus();
			}else{
				var num = 200 - parseInt(content.length);
				$("#zishu").text(num+"/剩余字数");
			}
		}
		//保存
		var baocun = function(){
			var container = $.trim($("#container").val());
			if(container==""){
				layer.tips('请输入欢迎语', '#container', {
    				tips: 3
    			});
    			$("#container").focus();
				return false;
			}
			$("#Form").submit();
			var data = {
            	msg : true
            };
            return data;
		}
	</script>
</body>
</html>