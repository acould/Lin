<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>会员列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
</head>
<style>

</style>
<body style="background: #f3f3f3">
<div class="layui-fluid">
    <div class="layui-card">
        <%--搜索框--%>
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">
                    <div class="layui-inline">
                    <label class="layui-form-label">门店</label>
                    <div class="layui-input-block">
                        <select name="STORE_ID" id="STORE_ID">
                            <option value="">请选择</option>
                            <c:forEach items="${sList}" var="var" varStatus="vs">
                                <option value="${var.STORE_ID }">${var.STORE_NAME }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
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
                        <select name="SEX" id="SEX">
                            <option value="">请选择</option>
                            <option value="1">男</option>
                            <option value="2">女</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">活跃度</label>
                    <div class="layui-input-block">
                        <select name="live" id="live">
                            <option value="">请选择</option>
                            <option value="high">高</option>
                            <option value="mid">中</option>
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
                            <option value="mid">中</option>
                            <option value="low">低</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">活跃度</label>
                    <div class="layui-input-block">
                        <select name="consume" id="consume">
                            <option value="">请选择</option>
                            <option value="high">高</option>
                            <option value="mid">中</option>
                            <option value="low">低</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">关键词</label>
                    <div class="layui-input-block">
                        <input type="text" id="keywords" name="keywords" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <button class="layui-btn layuiadmin-btn-useradmin btn-success" id="toSearch">
                        <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>搜索
                    </button>
                </div>
                <div class="layui-inline">
                    <button class="layui-btn mail-btn btn-primary" data-type="toImport" id="toImport"><i class="layui-icon layui-icon-add-1 layuiadmin-button-btn"></i>公众号粉丝导入</button>
                </div>
            </div>
        </div>

        <div class="layui-card-body" style="padding: 20px 20px">

            <%--表单--%>
            <table class="layui-hide" id="layTable" lay-filter="layTable"></table>


            <div class="page-header position-relative" style="padding:20px 0 0 0">
                <table style="width:100%;">
                    <tr>
                        <td style="vertical-align:top;">
                            <div id="layPage"  style="text-align: right"></div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script>
    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/modules/wechatUser/' //静态资源所在路径
    }).extend({
        wechatUser : 'wechatUser',
    }).use(['wechatUser']);
</script>

</body>
</html>
