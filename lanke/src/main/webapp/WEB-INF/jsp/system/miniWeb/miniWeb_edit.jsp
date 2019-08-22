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
	<%@ include file="../../system/index/top.jsp"%>
	<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
	
	<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
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
	<form class="layui-form layui-form-pane" name="Form" id="Form" method="post" action="">
		<input type="hidden" name="MINIWEB_ID" id="MINIWEB_ID" value="${pd.MINIWEB_ID}"/>
			<div class="layui-form-item" style="font-size: 15px;">
				<label class="layui-form-label" style="margin:0">微官网链接:</label>
                <div class="layui-input-block">
                   	<input type="text" name="URL" id="URL" value="${pd.URL}" autocomplete="off" placeholder="链接以http://或https://开头" title="微官网链接" class="layui-input">
                </div>
            </div>
	</form>
</div>
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		//保存
		var baocun = function(){
			if($("#URL").val() == ""){
				layer.tips('请输入链接', '#URL', {
			 		tips: 3
	        	});
				return;
			}
			if($("#URL").val().indexOf("http://") != 0 && $("#URL").val().indexOf("https://") != 0){
				layer.tips('链接以http://或https://开头', '#URL', {
			 		tips: 3
	        	});
				return;
			}
			var url = encodeURIComponent($("#URL").val());
			$("#URL").val(url);
			
			$.ajax({
				type: "POST",
				url: '<%=basePath%>miniWeb/saveMiniWeb.do',
				data: $('#Form').serialize(),
				dataType:'json',
				cache: false,
				success: function(data){
					layer.msg(data.message);
					if(data.result == "true"){
	                  	setTimeout(function () { 
	                  		parent.location.reload();
	                  	},800)
		 			}
				},
				error:function(){
	               layer.msg("系统繁忙，请稍后再试！");
	               return false;
	            }
			});
			
			
		}
	</script>
</body>
</html>