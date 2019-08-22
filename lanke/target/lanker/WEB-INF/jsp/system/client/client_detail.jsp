<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <title></title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="icon" href="data:;base64,=">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-card">
        <input type="hidden" id="store_id" value="${pd.store_id}">
        <div class="layui-card-body">

            <%--表单--%>
            <table class="layui-hide" id="detailTable" lay-filter="detailTable"></table>
        </div>
    </div>
</div>

<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<!-- 业务js -->
<script src="<%=basePath%>newStyle/js/lk-clientList.js"></script>

</body>
</html>
