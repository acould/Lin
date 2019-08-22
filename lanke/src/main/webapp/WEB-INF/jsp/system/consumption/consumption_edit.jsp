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
    <title>积分消耗编辑</title>
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
<div class="borPadding">
	<form class="layui-form layui-form-pane" name="Form" id="Form" method="post" action="">
		<input type="hidden" name="CONSUMPTION_ID" id="CONSUMPTION_ID" value="${pd.CONSUMPTION_ID}"/>
        <div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">消耗类别:</label>
            <div class="layui-input-block">
            	<select name="CONSUMPTION_TYPE" id="CONSUMPTION_TYPE" class="layui-input">
					<option value="1" <c:if test="${pd.CONSUMPTION_TYPE=='1'}">selected="selected"</c:if>>抽奖</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">消耗积分:</label>
			<div class="layui-input-block">
				<input type="number" name="NUMBER" id="NUMBER" value="${pd.NUMBER}" min="1" autocomplete="off" class="layui-input"
                   	onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" 
					onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" 
					maxlength="32" placeholder="这里输入消耗积分" title="消耗积分" >
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
			if($("#CONSUMPTION_TYPE").val()==""){
				layer.tips('请选择消耗类型', '#CONSUMPTION_TYPE', {
			 		tips: 3
	        	});
				$("#CONSUMPTION_TYPE").focus();
				return false;
			}
			if($("#NUMBER").val()==""){
				layer.tips('请输入消耗积分', '#NUMBER', {
			 		tips: 3
	        	});
				$("#NUMBER").focus();
				return false;
			}
			if($("#NUMBER").val() < 1){
				layer.tips('请输入有效数字', '#NUMBER', {
			 		tips: 3
	        	});
				$("#NUMBER").focus();
				return false;
			}
			if($("#NUMBER").val() > 10000){
				layer.tips('消耗积分最大可设置为10000', '#NUMBER', {
			 		tips: 3
	        	});
				$("#NUMBER").val(10000);
				$("#NUMBER").focus();
				return false;
			}
			
			$.ajax({
				type: "POST",
				url: '<%=basePath%>consumption/saveConsumption.do',
				data: $('#Form').serialize(),
				dataType:'json',
				cache: false,
				success: function(data){
					layer.msg(data.message);
					if(data.result == "true"){
	                  	setTimeout(function () { 
	                  		parent.location.reload();
	                  	},500)
		 			}
				},
				error:function(){
	               layer.msg("系统繁忙，请稍后再试！");
	               return false;
	            }
			});
			
		}
		
		layui.use('form', function(){
			
		});
</script>
</body>
</html>