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
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
<style>
    .layui-text ul li {
        margin-top: 12px;
    }
</style>
<body style="padding: 20px">
    <div class="lanke-audit">
        <fieldset class="layui-elem-field layui-field-title" style="">
            <legend>版本详情</legend>
        </fieldset>
        <ul class="layui-timeline">
            <li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                <div class="layui-timeline-content layui-text">
                    <div class="layui-timeline-title">
                        <h2>${pd.version}</h2>
                        <span class="layui-badge-rim">${pd.updatetime}</span>
                    </div>
                    <ul>
                     <c:forEach items="${list}" var="var">
                     <li>
                     <c:if test="${var.type == 0}"><span class="new_add">新增:</span>${var.content}</c:if>
                     <c:if test="${var.type == 1}"><span class="optimize">优化:</span>${var.content}</c:if>
                     <c:if test="${var.type == 2}"><span class="repair">修复:</span>${var.content}</c:if>
                     </li>
				     </c:forEach>
                    </ul>
                </div></li>
        </ul>
    </div>
</body>
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script type="text/javascript">

</script>
</html>