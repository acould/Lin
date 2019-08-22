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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css"
	media="all">
<link rel="stylesheet"
	href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
<link rel="stylesheet"
	href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css"
	media="all">
<body style="background-color: #f2f2f2;">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-header">产品预告</div>
					<div class="layui-card-body">
						<c:if test="${empty pd1.title}">
						<div class="layui-tab-content">
                            <a class="btn btn-xm btn-primary"  onclick="productNews_toEditNews('add');" >
                                <i class="layui-icon" style="padding-right: 4px">&#xe654;</i>添加预告
                            </a>
                        </div>
                        </c:if>
                        <c:if test="${not empty pd1.title}">
						<div class="lk-product-box">
							<h1 class="lk-product-title" id="title">${pd1.title}</h1>
							<div id="lk-product-body" class="lk-product-body">
								<p id="content">${pd1.content}</p>
							</div>
							<div style="position: relative">
								<p class="lk-product-time">
									预计上线时间：<span id="onlinetime">${pd1.onlinetime}</span>
								</p>
								<div class="lk-product-btn">
									<div onclick="productNews_toEditNews('edit');">
										<img src="<%=basePath%>newStyle/images/bianji.png" alt=""
											width="16px"><span>编辑</span>
									</div>
									<div onclick="productNews_deleteNews();">
										<img src="<%=basePath%>newStyle/images/shanchu.png" alt=""
											width="16px"><span>删除</span>
									</div>
								</div>
							</div>
						</div>
						</c:if>
					</div>
				</div>
			</div>
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-header">产品日志列表</div>
					<div class="layui-card-body">
						<div class="layui-tab-content">
							<a class="btn btn-xm btn-primary" onclick="productNews_toEditVersion('add')">
								<i class="layui-icon" style="padding-right: 4px">&#xe654;</i>新增日志
							</a>
						</div>
						<div style="padding: 12px">
							<table id="product_list" lay-filter="product"></table>
						</div>
						<div class="page-header position-relative"
							style="padding: 8px 10px 16px 10px">
							<table style="width: 100%;">
								<tr>
									<td style="vertical-align: top;">
										<div style="float: right; padding-top: 0px; margin-top: 0px;"
											id="page"></div>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script type="text/html" id="version_body">
	<a class="btn"  lay-event="det">
		<font color="#41a7f0">详情</font>
	</a>
</script>
<script type="text/html" id="barDemo">
	<a class="btn btn-sm btn-success" lay-event="edit">
		<i class="layui-icon" style="padding-right: 2px">&#xe642;</i>编辑
	</a>
	<a class="btn btn-mini btn-danger" lay-event="del">
		<i class="layui-icon" style="padding-right: 2px">&#xe640;</i>删除
	</a>
</script>
<script type="text/javascript">
    $(top.hangge());//关闭加载状态
    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'product']);
</script>
</html>