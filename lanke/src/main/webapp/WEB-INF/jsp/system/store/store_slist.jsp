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
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="<%=basePath%>static/ace/css/datepicker.css" />

<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<style>
.layui-icon {
	font-size: 14px;
}

.demo-class .layui-layer-btn {
	border-top: 1px solid #E9E7E7;
	background-color: #fafafa
}
</style>
</head>
<body class="no-skin scroll">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">

							<!-- 检索  -->
							<form action="account/list.do" method="post" name="Form"
								id="Form">
								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="left">门店名称</th>
											<th class="left">省份</th>
											<th class="left">城市</th>
											<th class="left">县</th>
											<th class="left">详细地址</th>
											<th class="left">门店联系方式</th>
											<th class="left">计费系统绑定到期</th>
											<th class="left">在线支付开通时间</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr>
														<td class='left' style="vertical-align: middle;">${var.STORE_NAME}</td>
														<td class='left' style="vertical-align: middle;">${var.PROVINCE}</td>
														<td class='left' style="vertical-align: middle;">${var.CITY}</td>
														<td class='left' style="vertical-align: middle;">${var.COUNTY}</td>
														<td class='left' style="vertical-align: middle;">${var.ADDRESS}</td>
														<td class='left' style="vertical-align: middle;">${var.TELEPHONE}</td>
														<td class='left' style="vertical-align: middle;">
															<c:if test="${var.STATE==1}">${var.EXPIRATION_TIME}</c:if>
														</td>
														<td class='left' style="vertical-align: middle;">
															<c:if test="${var.status == 1}">${var.updatetime}</c:if>
														</td>
													</tr>
												</c:forEach>
											</c:when>
										</c:choose>
									</tbody>
								</table>
							</form>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="<%=basePath%>static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="<%=basePath%>static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="<%=basePath%>static/ace/js/date-time/bootstrap-datepicker.js"></script>
</body>
</html>