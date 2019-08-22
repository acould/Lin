<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%  String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
<title>网吧管理后台</title>
<style>
.layui-form-checkbox[lay-skin=primary] {
	margin: 6px 0 2px 2px;
}
</style>
</head>
<body>
    <input type="hidden" id="url" name="url">
	<div class="lk-massObject-box">
		<div class="lk-massObject-item" data-type="fans">
			<div class="fans">
				<div>
					<i class="iconfont">&#xe62a;</i>
					<p>全部粉丝</p>
				</div>
			</div>
		</div>
		<div class="lk-massObject-item" data-type="member">
			<div class="member">
				<div>
					<i class="iconfont">&#xe629;</i>
					<p>仅会员</p>
				</div>
			</div>
		</div>
	</div>
	
	<form class="layui-form">
		<div class="lk-chooseStore-box" style="display: none">
			<div class="back-box">
				<p><icon class="iconfont">&#xe693;</icon>
					您已选了会员群发，请选择要群发的门店
				</p>
				<span id="come-back">上一步</span>
			</div>
			<div class="choosable-box">
				<h2>可选门店</h2>
				<div class="choose-store">
					<c:choose>
						<c:when test="${not empty list}">
							<c:if test="${QX.cha == 1 }">
								<c:forEach items="${list}" var="var" varStatus="vs">
								<c:if test="${var.state == 1}">
									<input type="checkbox" title="${var.store_name}" lay-skin="primary" checked lay-filter="store" name="store_id" value="${var.store_id}">
								</c:if>	
								</c:forEach>
							</c:if>
							<c:if test="${QX.cha == 0 }">
								<tr>
									<td colspan="100" class="center">您无权查看</td>
								</tr>
							</c:if>
						</c:when>
					</c:choose>
					<input type="checkbox" title="全选" lay-skin="primary" checked lay-filter="tufure" id="allChecked">
				</div>
				<input type="hidden" id="store_list" name="store_list">
				<input type="hidden" id="store_name" name="store_name">
			</div>
			<div class="choosable-box">
				<h2>不可选门店</h2>
				<div class="choose-store">
					<c:choose>
						<c:when test="${not empty list}">
							<c:if test="${QX.cha == 1 }">
								<c:forEach items="${list}" var="var" varStatus="vs">
								<c:if test="${var.state != 1}">
									<input type="checkbox" name="" title="${var.store_name}" lay-skin="primary" disabled>
								</c:if>	
								</c:forEach>
							</c:if>
							<c:if test="${QX.cha == 0 }">
								<tr>
									<td colspan="100" class="center">您无权查看</td>
								</tr>
							</c:if>
						</c:when>
					</c:choose>
					<p class="openUp-action">
						以上门店因未开通计费系统，暂时不支持会员营销功能 <span>去开通</span>
					</p>
				</div>
			</div>
		</div>
	</form>
</body>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<!-- 业务JS -->
<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script src="<%=basePath%>newStyle/js/lk-memberMake.js"></script>
</html>