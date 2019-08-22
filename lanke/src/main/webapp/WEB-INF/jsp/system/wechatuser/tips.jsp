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
	<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
	<link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
	<script src="<%=basePath%>newStyle/layui/layui.js"></script>
	<title>会员详情</title>
	<style>
		.card-header-msg .card-lankeIcon {
		    position: relative;
		    top: 4px;
		    width: 80px;
		}
	</style>
</head>
<body class="no-skin">	
		<div class="card-header">
			<div class="card-header-msg">
				<img src="<%=basePath%>newStyle/images/logo2.png" class="card-lankeIcon"></img><span
					class="card-header-title">网吧管理后台</span>
				<div class="card-header-right">
					<img alt="" src="<%=basePath %>static/ace/avatars/user.jpg" class="">
					<span class="card-userName layui-elip" style="font-size: 14px;"> <small>Welcome</small><br>
						${pd.INTENET_NAME}
					</span>
				</div>
			</div>
		</div>	
		<div class="lanke-noboby">
          <div class="lanke-nobody-img"></div>
          <div class="lanke-nobody-content">
          	 <p class="lanke-Loser">很遗憾，操作失败！</p>
          	 <p class="lanke-nobody-text">该会员绑定的门店: <font color="red">${pd.STORE_NAME}</font> 没有开通计费系统,无法使用该功能，如有疑问,请联系客服:4000965099</p>
         	 <p style="margin: 50px 0 0 10px;"><span class="lanke-regular-btn" id="lanke-addV" onclick="bunding()">开通计费系统</span></p>
          </div>
       </div>
	<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
		<script type="text/javascript">
		function bunding() {
            window.location.href="<%=basePath%>accountSettings/toAddv.do?STORE_ID=${pd.STORE_ID}"
        }
		
		/* layui.use([ "layer", "form", "element" ], function() {
			var layer = layui.layer, form = layui.form, $ = layui.jquery;
			$("#lanke-addV").click(function() {
				layer.msg("功能内测中", {
					time : 1500,
					icon : 0,
					offset : '100px'
				})
			})
		}) */
		</script>
</body>
</html>
