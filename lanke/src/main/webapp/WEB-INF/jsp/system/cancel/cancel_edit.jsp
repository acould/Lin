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
		<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
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
		<input type="hidden" name="CANCEL_ID" id="CANCEL_ID" value="${pd.CANCEL_ID}"/>
		<div class="layui-form-item">
			<label class="layui-form-label" style="margin:0">优惠券码:</label>
			<div class="layui-input-block">
				<input type="text" name="CARD" id="CARD" value="${pd.CARD}" maxlength="255" placeholder="这里输入优惠券码" title="优惠券码" autocomplete="off" class="layui-input"/>
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
			var CARD = $("#CARD").val();
			if(CARD == ""){
				layer.tips('请输入优惠券码', '#CARD', {
    				tips: 1
    			});
				$("#CARD").focus();
				return false;
			}else{
                $.ajax({
                    type	: "POST",
                    url		:'<%=basePath%>cancel/cardCancel.do',
                    data	:{CARD : CARD},
                    dataType:'json',
                    beforeSend: beforeSend("正在核销中"),
                    success: function(data) {
                        layer.closeAll();
                        if(data.result == "true"){
                            message(data.message);
                            setTimeout(function () {
                                parent.location.reload();
                            },800)
                        }else{
                            layer.alert(data.message);
                        }
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        // console.log(XMLHttpRequest.status);
                        // console.log(XMLHttpRequest.readyState);
                        // console.log(textStatus);

                        layer.closeAll();
                        message("系统繁忙，请稍后再试2！");
                        return;
                    }
                });
			}

			
		}
	</script>
</body>
</html>