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
    <title>报名列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
</head>
<style>
    body {background-color: #f2f2f2; min-width: 600px; overflow: auto}
</style>
<body>
<div class="card-header">
    <div class="card-header-msg">
        <img src="<%=basePath%>newStyle/images/logo2.png"class="card-lankeIcon">
        <span class="card-header-title">网吧管理后台</span>
        <div class="card-header-right">
            <img alt="" src="<%=basePath%>static/ace/avatars/user.jpg" class="">
            <span class="card-userName layui-elip"> <small>Welcome</small><br>
					${pd.NAME}
			</span>
        </div>
    </div>
</div>
<div class="card-width" style="min-width: 950px;">
    <h1>
        当前赛事<span style="font-weight: 300;">—</span><span>${pd.name}</span>
    </h1>
    <input id="matches_id" name="matches_id" type="hidden" value="${pd.matches_id}">
    <div class="layui-fluid">
        <div class="layui-card" style="box-shadow: none;">
            <%--搜索框--%>
            <div class="layui-form layui-card-header layuiadmin-card-header-auto">
                <div class="layui-form-item">
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
                    <button class="layui-btn mail-btn" data-type="toExcel">导出为Excel</button>
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
</div>



<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script>
    var basePath = '<%=basePath%>';
    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/modules/internet/' //静态资源所在路径
    }).extend({
        enroll : 'matches/enroll',
    }).use(['enroll']);
</script>

</body>
</html>
