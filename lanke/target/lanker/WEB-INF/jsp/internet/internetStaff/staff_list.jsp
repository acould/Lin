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
        .borPadding { padding: 20px 30px; }
        .layui-form-checkbox[lay-skin=primary] span { padding-right: 6px; }
        .layui-icon {font-size: 14px;}
        .laydate-theme-blue  .layui-this { background-color: #41a7f0!important; }
    </style>
</head>
<body class="scroll" style="background-color: #f2f2f2;">

<div class="lk-table-height">
    <div class="layui-tab-content">
        <form class="layui-form" action="" method="post" id="excel_form">
            <input type="hidden"  name="state" id="state" value="${pd.state}">
            <div class="demoTable table-seach">
                <div class="layui-inline">
                    <input class="layui-input" name="keywords" id="keywords" autocomplete="off" placeholder="接收员搜索">
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">门店：</label>
                    <div class="layui-input-inline">
                        <select name="store_id" lay-search="" lay-filter="filter" id="store_id">
                            <option value="">选择或搜索</option>
                            <option value="">全部</option>
                            <c:forEach items="${storeList}" var="var" varStatus="vs">
                                <option value="${var.STORE_ID}" >${var.STORE_NAME}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <a class="btn btn-xm btn-success" data-type="search" id="toSearch" style="margin-left: 10px;">
                    <i class="layui-icon" style="padding-right: 4px;">&#xe615;</i>搜索
                </a>
            </div>
        </form>
    </div>
    <div style="padding:12px">
        <table id="staff_list" lay-filter="staff">

        </table>
    </div>
    <div class="page-header position-relative" style="padding:8px 10px 16px 10px">
        <table style="width:100%;">
            <tr>
                <td>
                    <a class="btn btn-xm btn-primary" onclick="add();">
                        <i class="layui-icon" style="padding-right: 4px">&#xe654;</i>新增接收服务
                    </a>
                </td>
                <td style="vertical-align:top;">
                    <div style="float: right;padding-top: 0px;margin-top: 0px;" id="page"></div>
                </td>
            </tr>
        </table>
    </div>
</div>


<!-- 返回顶部 -->
<a href="#" id="btn-scroll-up"
   class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
</a>
</div>
<!-- /.main-container -->
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<!-- 业务js -->
<script src="<%=basePath%>newStyle/js/lk-staff.js"></script>
<script type="text/javascript">

</script>
</body>
</html>