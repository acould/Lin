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
    <title>选择队伍</title>
    <!--移动端适配，px转化为rem-->
    <script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>
<style>
    .weic-bor {padding: 0.4rem 0.4rem}
    .game-tips {background: none!important;}
    .gameMsg-box .title {font-weight: bold}
    #team_content .weui-row {justify-content: left;}
    /*#team_content {padding-bottom: 2.2rem}*/
</style>
<body class="weic weic-body-bgf3f3f3 weic-parent-mar">
<input type="hidden" id="matches_id" value="${pd.matches_id}">
   <div style="padding-bottom: 2.5rem">
       <div id="team_content">
           <%--<div class="weic-bor gameMsg-box weic-bgfff weic-martop-20">
               <a class="weui-cell" href="javasrcipt:" style="padding: 0">
                   <div class="weui-cell__bd team_bottom_line">
                       <p class="title">艾格拉网咖全民对抗赛<span class="game-tips">差3人</span></p>
                       <p class="store">主办门店：文一店、九堡店、三墩店...</p>
                   </div>
               </a>
               <div class="weui-row weic-relative" style="margin-top: 0.266rem;">
                   <div class="team_header"></div>
                   <div class="weic-team-seat">
                       <img src="<%=basePath%>newStyle/weixin/images/banner.jpg" alt="" width="100%" height="100%">
                   </div>
                   <div class="weic-team-seat"></div>
                   <div class="weic-team-seat"></div>
                   <div class="weic-team-seat"></div>
                   <div class="weic-team-seat"></div>
               </div>
           </div>--%>
       </div>
       <div class="weui-loadmore" id="loading">
           <i class="weui-loading"></i>
           <span class="weui-loadmore__tips">正在加载</span>
       </div>
   </div>

<div class="weic-fixed-bottom" id="fixed_bottom">
    <%--<div class="weic-bor weic-bgfff">
        <div class="weui-row">
            <div class="weic-dent-btn weic-btn-gradientBlue" id="my_team">我的队伍</div>
            <div class="weui-col-50">
                <div class="weic-dent-btn weic-btn-gradientGray" id="join_team">加入战队</div>
            </div>
            <div class="weui-col-50">
                <div class="weic-dent-btn weic-btn-gradientBlue" id="create_team">创建战队</div>
            </div>
        </div>
    </div>--%>
</div>

</body>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/mobile-check.js"></script>
<script type="text/javascript">
    $(function() {
        FastClick.attach(document.body);
    });
</script>
<script type="text/javascript">
    var currentPage = 1;
    var url = '<%=basePath%>wxMatches/loadMatchesEnroll.do';
    var id = $("#matches_id").val();
    var team_id = '';
    var status = '';
    initialize(0)
    function initialize(currentPage){
        $("#loading").show();
        var field = new Object();
        field.matches_id = id;
        field.curr = currentPage
        $.post(url, field, function (res){
            $("#loading").hide();
            var htmls = "";
            var list = res.data.list;
            var data = res.data.pd;//组队人数，本人报名信息，totalPage总页数

            status = data.status;
            if(list.length == 0){
                //没有队伍
                var no_html = '<div class="weic-noboby choose_team" style="height: 6.2rem;">'+
                                '<div class="weic-nobody-img"></div>'+
                                '<div class="weic-nobody-text">暂无战队可加入</div>'+
                                '<div class="weic-nobody-btn">'+
                                    '<div class="weic-dent-btn weic-btn-gradientBlue" id="create_team">创建战队</div>'+
                                '</div>'+
                            '</div>'
                $("#team_content").html(no_html);
                $(document.body).destroyInfinite();

            }else{
                //有队伍，填充队伍
                for (var i = 0; i < list.length; i++) {
                    var enrollList = list[i].enrollList;

                    var team_number_html =  "";
                    var last = data.team_number - enrollList.length;
                    var hh = '满员';
                    if(last != 0){
                        hh = '差' + last + '人';
                    }
                    //循环队员
                    for (var j = 0; j < data.team_number; j++){  //队员数量
                        var team_member_photo = '';//头像
                        if(enrollList.length >= (j+1)){
                            if(enrollList[j].imgurl != "" && enrollList[j].imgurl != "undefined" && enrollList[j].imgurl != undefined){
                                team_member_photo = '<img src="'+enrollList[j].imgurl+'" alt="" width="100%" height="100%">'
                            }else if(enrollList[j].imgurl == undefined || enrollList[j].imgurl == "undefined"){
                                team_member_photo = '<img src="<%=basePath%>newStyle/weixin/images/matches/enroller_headimg.png" alt="" width="100%" height="100%">';
                            }
                        }
                        team_number_html += '<div class="weic-team-seat">'+ team_member_photo +'</div>'
                    }

                    htmls += '<div class="weic-bor gameMsg-box weic-bgfff weic-martop-20" data-id="'+list[i].id+'">'+
                                '<a class="weui-cell" href="javasrcipt:" style="padding: 0">'+
                                    '<div class="weui-cell__bd team_bottom_line">'+
                                        '<p class="title">'+list[i].team_name+'<span class="game-tips">'+hh+'</span></p>'+
                                        '<p class="store">简介：'+list[i].team_description+'</p>'+
                                    '</div>'+
                                '</a>'+
                                '<div class="weui-row weic-relative" style="margin-top: 0.266rem;">'+
                                    '<div class="team_header"></div>'+
                                    team_number_html+
                                '</div>'+
                            '</div>'
                }

                $("#team_content").append(htmls);

                currentPage += 1;
                if(currentPage >= data.totalPage){
                    $(document.body).destroyInfinite();
                    $("#team_content").append('<p class="weic-foot-tip">已经到底了</p>');
                }

                //是否已报名，修改底部按钮
                var fixed_bottom_html = "";
                if(data.name != null && data.name != '' && data.name != 'undefined'){
                    fixed_bottom_html = '<div class="weic-bor weic-bgfff">'+
                                            '<div class="weui-row">'+
                                                '<div class="weic-dent-btn weic-btn-gradientBlue" id="my_team">我的队伍</div>' +
                                            '</div>'+
                                        '</div>'
                }else{
                    fixed_bottom_html = '<div class="weic-bor weic-bgfff">' +
                                            '<div class="weui-row">' +
                                                '<div class="weui-col-50">' +
                                                '<div class="weic-dent-btn weic-btn-gradientGray" id="join_team">加入战队</div>' +
                                            '</div>' +
                                            '<div class="weui-col-50">' +
                                                '<div class="weic-dent-btn weic-btn-gradientBlue" id="create_team">创建战队</div>' +
                                                '</div>' +
                                            '</div>' +
                                        '</div>'
                }
                $("#fixed_bottom").html(fixed_bottom_html);


            }

            $(".weic-team-seat").unbind('click');
            $("#join_team").unbind('click');
            $("#my_team").unbind('click');
            $("#create_team").unbind('click');

            $(".weic-team-seat").click(function (){
                if($(this).children().length != 0){
                    return false;
                }

                $(".weic-team-seat").removeClass("weic-team-seat-active")
                $(this).addClass("weic-team-seat-active")
                $("#join_team").attr("class","weic-dent-btn  weic-btn-gradientOrange")
                team_id = $(this).parent().parent().data('id');
            })

            $("#join_team").click(function (){
                if(status != '' && status != 1){
                    message("报名已结束");
                    return;
                }
                if($(".weic-team-seat-active").length == 0){
                    message("请选择战队座位")
                }else{
                    window.location.href = '<%=basePath%>wxMatches/goCreateTeam?matches_id=' + id + '&team_id=' + team_id;
                }
            })

            $("#create_team").click(function () {
                if(status != '' && status != 1){
                    message("报名已结束");
                    return;
                }
                window.location.href = '<%=basePath%>wxMatches/goCreateTeam?matches_id='+id;
            })

            $("#my_team").click(function (){
                window.location.href = '<%=basePath%>wxMatches/goMyTeam?matches_id='+id;
            })

        })
    }

    $(document.body).infinite(50).on("infinite", function() {
        initialize(currentPage);
    });
</script>
</html>
