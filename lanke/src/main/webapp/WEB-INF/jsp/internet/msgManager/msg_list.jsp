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
    <title>活动群发</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
</head>
<style>
    body {background-color: #f2f2f2;}
    h1 {font-size: 20px}
    #goback {color: #0a56c8;cursor: pointer;}
    .hongD{
        font-family: "微软雅黑";
        color: black;
        margin: 0;
        font-size: 26px;
        font-weight: 600;
        padding: 0;
        margin: 8px 5px 14px 20px;
        border: 0px;
    }
    .textP{
        font-family: "微软雅黑";
        margin-bottom: 10px;
        margin-left: 20px;
        box-sizing: border-box;
    }
    .huoA{
        display: inline-block;
        margin:5px 20px;
        color: #3b9ce2;
    }
</style>
<body>
<div class="layui-fluid" style="padding-bottom: 0">
    <div class="layui-card">
        <div class="layui-fluid">
            <div class="layui-inline">
                <img src="<%=basePath%>newStyle/images/memberMarke.png" width="500px">
            </div>
           <div class="layui-inline">
               <h1 class='hongD'>活动群发介绍</h1>
               <p class="textP">1)、揽客为您分析Pubwin会员消费行为，智能生成会员标签</p>
               <p class="textP">2)、网吧（故障、维护）事件一键通知，及时送达，为您的会员贴心服务</p>
               <p class="textP">3)、营销活动自由编辑，分组群发，公众号精准推送，不再发愁</p>
               <a class="huoA" href="<%=basePath%>register/user_marketing.do" target="_blank">如何玩转活动群发</a>
               <div class="layui-row">
                   <div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
                       <div class="msg_list_icon"></div>
                   </div>
               </div>
           </div>
        </div>
    </div>
</div>
<div class="layui-fluid">
    <div class="layui-card" >
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
                    <button class="layui-btn btn-info mail-btn" data-type="inform">群发通知</button>
                </div>
                <div class="layui-inline">
                    <button class="layui-btn btn-primary mail-btn" data-type="activity">群发活动</button>
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
                            <%--<button class="layui-btn btn-danger mail-btn layui-btn-sm" data-type="all_del"><i class="layui-icon layui-icon-delete"></i>批量删除</button>--%>
                        </td>
                        <td style="vertical-align:top;">
                            <div id="layPage"  style="text-align: right"></div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>

<%--群发通知--%>
<div id="inform_html" style="display: none">
    <div class="layui-row layui-col-space30" style="margin: 35px">
        <div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
            <div class="msgLayer_box " data-type="server_maintenance">
                <div class="msgLayer_icon msgLayer_icon1"></div>
                <p class="msgLayer_title">服务器维护通知</p>
                <p class="msgLayer_des">适用于网吧内游戏维护</p>
                <p class="msgLayer_tip">今日剩余次数:<font id="sm_times">0</font></p>
            </div>
        </div>
        <div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
            <div class="msgLayer_box" data-type="equipment_failure">
                <div class="msgLayer_icon msgLayer_icon2"></div>
                <p class="msgLayer_title">设备故障通知</p>
                <p class="msgLayer_des">适用于网吧上网故障</p>
                <p class="msgLayer_tip">今日剩余次数:<font id="ef_times">0</font></p>
            </div>
        </div>
        <div class="layui-col-xs4 layui-col-sm4 layui-col-md4" >
            <div class="msgLayer_box" data-type="failure_recovery">
                <div class="msgLayer_icon msgLayer_icon3"></div>
                <p class="msgLayer_title">故障恢复通知</p>
                <p class="msgLayer_des">适用于上网故障恢复</p>
                <p class="msgLayer_tip">今日剩余次数：<font id="fr_times">0</font></p>
            </div>
        </div>
    </div>
</div>

<%--群发活动--%>
<div id="activity_html" style="display: none">
    <div class="layui-row layui-col-space30" style="margin: 35px 70px;" id="outside">
        <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
            <div class="msgLayer_box"  id="next_step">
                <div class="msgLayer_icon msgLayer_icon4"></div>
                <p class="msgLayer_title">图文推送</p>
                <p class="msgLayer_des">聊天窗口推送，曝光率高</br>微信图文推送每月限制4次</p>
                <p class="msgLayer_tip">本月剩余次数:3</p>
            </div>
        </div>
        <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
            <div class="msgLayer_box" data-type="public_show">
                <div class="msgLayer_icon msgLayer_icon5"></div>
                <p class="msgLayer_title">公众号内显示</p>
                <p class="msgLayer_des">公众号内和上机时主动查看</br>掌握主动权，无次数限制</p>
            </div>
        </div>
    </div>
    <div class="layui-row layui-col-space30" style="margin: 35px 70px;display: none" id="inside">
        <p id="goback">返回上一步》</p>
        <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
            <div class="msgLayer_box" data-type="imageText_matches" style="height: 114px;">
                <div class="msgLayer_icon msgLayer_icon6"></div>
                <p class="msgLayer_title" style="border: none">群发赛事</p>
            </div>
        </div>
        <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
            <div class="msgLayer_box" style="height: 114px;" data-type="imageText_card">
                <div class="msgLayer_icon msgLayer_icon7"></div>
                <p class="msgLayer_title" style="border: none">群发卡券</p>
            </div>
        </div>
    </div>
</div>

<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script>

</script>
<script>
    var basePath = '<%=basePath%>';
    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/modules/internet/' //静态资源所在路径
    }).extend({
        mass_news : 'msgManager/mass_news',
    }).use(['mass_news']);
</script>

</body>
</html>
