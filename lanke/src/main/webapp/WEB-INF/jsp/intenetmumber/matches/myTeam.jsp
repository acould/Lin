<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <title>我的队伍</title>
    <!--移动端适配，px转化为rem-->
    <script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>
<style>
    .weui-row .weui-flex{ align-items: center;}
    .weic-team-seat { margin: 0.2rem 0.32rem;}
    .game-tips {background: none!important;}
    .weic-bor {padding: 0.4rem 0.4rem}
</style>
<body class="weic weic-body-bgf3f3f3 weic-parent-mar">
<input type="hidden" id="game_id" value="e7ec0476964e4da892049f6c166aaaee">
<form id="Form">
    <input type="hidden" id="matches_id" name="matches_id" value="${pd.matches_id}">
    <input type="hidden" id="team_id" name="team_id" value="${pd.team_id}">
    <div class="weui-cells__title">报名截止后若战队人数未满，将由主办方自由安排</div>
    <div class="weui-cells weui-cells_form">
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">战队名称</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" type="text" id="team_name" name="team_name" placeholder="请输入战队名称" readonly
                       data-group-state="false" >
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">战队简介</label></div>
            <div class="weui-cell__bd">
                <textarea class="weui-textarea" rows="2" id="team_description" name="team_description" readonly
                          placeholder="请输入战队简介" maxlength="20"></textarea>
            </div>
        </div>
    </div>
    <div class="weui-cells weui-cells_form">
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">队长信息</label></div>
        </div>
        <div class="weui-cell weui-flex">
            <div class="weic-team-seat" id="header_img">
                <%--<img src="<%=basePath%>newStyle/weixin/images/banner.jpg" alt="" width="100%" height="100%">--%>
            </div>
            <div class="weic-item-title" id="header_name"></div>
        </div>
    </div>
    <div class="weui-cells weui-cells_form">
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="">队员信息</label></div>
            <div class="weui-cell__ft"><span class="game-tips" id="lastId"></span></div>
        </div>
        <div class="weui-cell weui-row" id="memberList">
            <%--<div class="weui-col-50 weui-flex">
                <div class="weic-team-seat">
                    <img src="<%=basePath%>newStyle/weixin/images/banner.jpg" alt="" width="100%" height="100%">
                </div>
                <div class="weic-item-title">啥似的</div>
            </div>
            <div class="weui-col-50 weui-flex">
                <div class="weic-team-seat">
                    <img src="<%=basePath%>newStyle/weixin/images/banner.jpg" alt="" width="100%" height="100%">
                </div>
                <div class="weic-item-title">啥似的</div>
            </div>
            <div class="weui-col-50 weui-flex">
                <div class="weic-team-seat">
                    <img src="<%=basePath%>newStyle/weixin/images/banner.jpg" alt="" width="100%" height="100%">
                </div>
                <div class="weic-item-title">啥似的</div>
            </div>--%>
        </div>
    </div>
</form>
<div class="weic-fixed-bottom" id="fixed_bottom">
    <div class="weic-bor weic-bgfff">
        <div class="weui-row">
            <div class="weui-col-50">
                <div class="weic-dent-btn weic-btn-gradientGray" id="exit_team">退出战队</div>
            </div>
            <div class="weui-col-50">
                <div class="weic-dent-btn weic-btn-gradientBlue" id="edit_data">修改资料</div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/mobile-check.js"></script>
<script type="text/javascript">

    $("#edit_data").click(function (){
        window.location.href = '<%=basePath%>wxMatches/goCreateTeam?matches_id=' + $("#matches_id").val() ;
    })
    $("#exit_team").click(function (){
        var url = "<%=basePath%>wxMatches/quitTeam.do";
        confirm("是否确认退出战队？",function(){
            var field = new Object();
            field.matches_id = $('#matches_id').val();
            $.post(url, field, function (res) {
                message(res.errmsg);
                if(res.errcode == 0){
                    setTimeout(function () {
                        window.location.href = '<%=basePath%>wxMatches/goChooseTeam?matches_id=' + $("#matches_id").val() +"&team_id"+ $("#team_id").val();
                    }, 800)
                }
            })
        });

    })

    var url = "<%=basePath%>wxMatches/loadMyAllTeam.do";
    initialize();
    function initialize(){
        open_loading()
        var field = new Object();
        field.matches_id = $('#matches_id').val();
        $.post(url, field, function (res) {
            layer.closeAll();
            var data = res.data.pd;//组队人数,队伍信息

            $("#team_name").val(data.team_name);
            $("#team_description").val(data.team_description);

            var list = res.data.list[0].enrollList;//报名信息
            var htmls = "";
            for (var i = 0; i < list.length; i++) {
                var last = data.team_number - list.length;
                var hh = '满员';
                if(last != 0){
                    hh = '差' + last + '人';
                }
                $("#lastId").html(hh);

                if(i == 0){
                    var imgg = '<img src="' + list[i].imgurl + '" alt="" width="100%" height="100%">';
                    $("#header_img").html(imgg);
                    $("#header_name").html(list[i].name);
                }else{
                    var imgg = '<%=basePath%>newStyle/weixin/images/matches/enroller_headimg.png';
                    if(list[i].imgurl != '' && list[i].imgurl != undefined && list[i].imgurl != "undefined"){
                        imgg = list[i].imgurl;
                    }

                    htmls += '<div class="weui-col-50 weui-flex">' +
                                '<div class="weic-team-seat">' +
                                    '<img src="'+imgg+'" alt="" width="100%" height="100%">' +
                                '</div>' +
                                    '<div class="weic-item-title">'+list[i].name+'</div>' +
                                '</div>' ;
                }
            }
            $("#memberList").html(htmls);
        });
    }
</script>
</html>
