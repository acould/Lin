<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/3
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();//获取项目名路径
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>会员列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
</head>

<body  style="background: #f3f3f3">
<br>
会员列表
<br><br>
<div class="layui-fluid">
    <div class="layui-card">
            <%--搜索框--%>
            <div class="layui-form layui-card-header layuiadmin-card-header-auto">
                <div class="layui-form-item">

                <div class="layui-inline">
                <label class="layui-form-label">是否会员</label>
                    <div class="layui-input-block">
                    <select name="huiyuan" id="huiyuan">
                        <option value="">请选择</option>
                        <option value="shi">会员</option>
                        <option value="bushi">非会员</option>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">性别</label>
                <div class="layui-input-block">
                    <select name="sex" id="sex">
                        <option value="">请选择</option>
                        <option value="is">是</option>
                        <option value="notis">不是</option>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">活跃度</label>
                <div class="layui-input-block">
                    <select name="live" id="live">
                        <option value="">请选择</option>
                        <option value="high">高</option>
                        <option value="middle">中</option>
                        <option value="low">低</option>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">卡余额</label>
                <div class="layui-input-block">
                    <select name="balance" id="balance">
                        <option value="">请选择</option>
                        <option value="high">高</option>
                        <option value="middle">中</option>
                        <option value="low">低</option>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">关键字</label>
                <div class="layui-input-block">
                    <input type="text" id="keywords" name="keywords" placeholder="请输入" autocomplete="off" class="layui-input" >
                </div>
            </div>
                    <div class="layui-inline">
                        <button class="layui-btn layuiadmin-btn-useradmin btn-success" id="toSearch">
                            <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>搜索
                        </button>
                    </div>
            </div>
        </div>
    </div>
</div>

</body>
<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
</html>
