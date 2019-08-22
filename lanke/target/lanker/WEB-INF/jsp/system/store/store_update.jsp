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
        body {overflow: hidden}
    </style>
</head>
<body class="no-skin scroll">
<!-- /section:basics/navbar.layout -->
<div class="borPadding">
	<form class="layui-form layui-form-pane" name="Form" id="Form" method="post" action="<%=basePath%>store/${msg }.do">
		<input type="hidden" name="STORE_ID" id="STORE_ID" value="${pd.STORE_ID}"/>
			<div class="layui-form-item">
				<label class="layui-form-label" style="margin:0">门店名称:</label>
                <div class="layui-input-block">
                   	<input type="text" name="STORE_NAME" id="STORE_NAME" value="${pd.STORE_NAME}" autocomplete="off" placeholder="这里输入门店名称,限10字" title="门店名称" onblur="hasS()" maxlength="10" class="layui-input">
                </div>
            </div>
	</form>
</div>
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<script type="text/javascript">
		$(top.hangge());
		//保存
		var baocun = function(){
			if($("#STORE_NAME").val()=="" || $("#STORE_NAME").val()=="此门店已存在!"){
		        layer.tips('请输入有效门店名称', '#STORE_NAME', {
	        		tips: 3
	        	});
			return false;
			}
			$("#Form").submit();
			var data = {
            	msg : true
            };
            return data;
		}
		//判断门店是否已存在
		function hasS(){
			var STORE_NAME = $.trim($("#STORE_NAME").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>store/hasS.do',
		    	data: {STORE_NAME:STORE_NAME,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" != data.result){
					 	layer.tips('此门店已存在！', '#STORE_NAME', {
	        				tips: 3
	        			});
					 }
				}
			});
		}
</script>
</body>
</html>