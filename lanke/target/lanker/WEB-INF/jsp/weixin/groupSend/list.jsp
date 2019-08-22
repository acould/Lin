<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>公众号群发</title>
    <base href="<%=basePath%>">
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp"%>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/textimg.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/awesome-bootstrap-checkbox.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/animate.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <style type="text/css">
        .modal-backdrop.in{z-index: 0!important;opacity:0.3;}
        #main{position: relative;}
        .pin{margin: 15px 0 0 20px;float:left;box-shadow: 0 1px 2px 0 rgba(0,0,0,.05);}
        .pin:first-child,.card-box:first-child {margin-left: 0;}
        .rowBoxCenter {margin-top: 0px!important;}
        #page_z119 {scrolling : no!important;}
        .card-box {background: #fff;width: 250px;margin: 15px 0 0 20px;float:left;box-shadow: 0 1px 2px 0 rgba(0,0,0,.05);}
        @media screen and (min-width: 992px){
            .lk-cardlist-content {
                max-width: initial;
            }
        }
        @media screen and (min-width: 1170px){
            .lk-cardlist-content {
                max-width: initial;
            }
        }
        .lk-cardlist-content {margin-top: 14px;}
        .article_table_icon {height: 40px;line-height: 40px;}
    </style>
</head>
<body class="no-skin scroll">

    <div class="layui-card-body">
        <h3 style="font-size: 20px;margin: 10px;">公众号群发</h3>
        <%--<div class="article_category">
            <ul>
                <li class="active casethis" id="news">群发图文</li>
                <li class="casethis" id="message">群发消息</li>
                <li class="casethis" id="record">群发记录</li>
            </ul>
        </div>--%>


        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
            <ul class="layui-tab-title">
                <li class="li-tab layui-this">群发图文</li>
                <li class="li-tab">群发消息</li>
                <li class="li-tab">群发记录</li>
            </ul>
            <div class="layui-tab-content" >
                <div class="layui-tab-item layui-show">
                    <div class="demoTable table-seach">
                        <div class="paddingSpan" onclick="addNews()"><a class="paddingA btn-warning">新建图文</a></div>
                        <%--<div class="paddingSpan" onclick=""><span class="sreachBj btn-success">草稿箱</span></div>--%>
                        <div class="layui-inline">
                            <input class="layui-input" id="keywords" autocomplete="off" placeholder="关键词搜索">
                        </div>
                        <a class="btn btn-xm btn-success" data-type="search" id="toSearch" style="margin-left: 10px;">
                            <i class="layui-icon" style="padding-right: 4px;">&#xe615;</i>搜索
                        </a>
                    </div>
                    <div id="main">
                        <%--内容--%>

                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="layui-tab-item layui-show">
                        <div class="demoTable table-seach">
                            <div class="paddingSpan" onclick="addMessage()"><a class="paddingA btn-warning">新建消息</a></div>
                            <div class="layui-inline">
                                <input class="layui-input" id="message_keywords" autocomplete="off" placeholder="关键词搜索">
                            </div>
                            <a class="btn btn-xm btn-success" data-type="search" id="message_toSearch" style="margin-left: 10px;">
                                <i class="layui-icon" style="padding-right: 4px;">&#xe615;</i>搜索
                            </a>
                        </div>
                        <div id="message_main" class="lk-cardlist-content">
                            <%--内容--%>

                        </div>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="demoTable table-seach">
                        <div class="layui-inline">
                            <input class="layui-input" id="status" autocomplete="off" placeholder="群发类型">
                        </div>
                        <a class="btn btn-xm btn-success" data-type="search" id="record_toSearch" style="margin-left: 10px;">
                            <i class="layui-icon" style="padding-right: 4px;">&#xe615;</i>搜索
                        </a>
                    </div>
                    <div id="record_main">
                        <%--内容--%>
                            <table id="record_list" lay-filter="record"></table>

                            <div id="record_page" class="article_page">
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <script src="<%=basePath%>newStyle/js/lk-message.js"></script>
    <script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/layuiadmin/modules/groupSend.js"></script>
    <script>
        $(top.hangge());//关闭加载状态
        var basePath = '<%=basePath%>';
        loadIndex($("#keywords").val());
    </script>
    <script type="text/html" id="barDemo">
        <div class="article_table_icon">
            <i class="iconfont lk_table_icon_success lk_table_icon" title="详情" lay-event="detail" style="color:#1c84c6;cursor: pointer">详情</i>
        </div>
    </script>
</body>
</html>
