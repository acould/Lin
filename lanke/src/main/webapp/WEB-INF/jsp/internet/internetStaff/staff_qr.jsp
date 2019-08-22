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
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <title>Document</title>
    <style>

    </style>
</head>
<body class="scroll" style="background-color: #f2f2f2;">
    <div style="padding: 40px;text-align: center;color: red">
        <p>请服务通知接收员微信扫描下方二维码进行设置</p>
        <div id="qrcode" style="width: 210px;margin: 30px auto">

        </div>
    </div>
</body>
<!-- /.main-container -->
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script src="<%=basePath%>js/qrcode.min.js"></script>
<!-- 业务js -->
<script src="<%=basePath%>newStyle/js/lk-staff.js"></script>
<script type="text/javascript">
    var url = "${url}";
    <%--addcode('<%=basePath%>indexMember/userStaffBind.do')--%>
    addcode(url)
</script>
</body>
</html>