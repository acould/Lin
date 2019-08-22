<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <meta charset="UTF-8">
    <title>下机结算</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge" chrome="1">
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
    <style>
        li{
            list-style: none;
        }
        #qiandao {
            position: fixed;
            left: 0;
            right: 0;
            top: 0;
            bottom: 0;
            background: rgba(1, 1, 1, .2);
            z-index:200;
            display: none;
        }

        #qiandao .content {
            margin: 1.7866rem auto;
            width: 7.59rem;
            height: 10.257rem;
            background-image: url('<%=basePath%>newStyle/images/qiandao_picture111.png');
            background-size: 7.59rem, 10.257rem;
            overflow: hidden;
        }

        #qiandao .content .section {
            margin-top:2.899rem;
            text-align: center;
            font-size: .3rem!important;
            line-height: .6133rem;
            color: rgb(250, 102,35);
        }

        #qiandao .content footer {
            margin: 1.36rem .613rem 0;
        }

        #qiandao .content ul {
            display: flex;
            margin: 0 auto;
        }

        #qiandao .content ul li {
            margin-right: .452rem;
            text-align: center;
        }

        #qiandao .content ul li .top span {
            display: block;
            padding-top: .384rem;
            font-size: .4rem;
        }
        /* #qiandao .content .Top {
            justify-content: space-around
        } */

        #qiandao .content ul li .footer {
            font-size: .3328rem;
            line-height: .5994rem;
            color: #333;
        }

        #qiandao .content .Bom {
            margin: 0.266rem .8265rem 0;
        }
        #qiandao .content .bright {
            width: 1.263rem;
            height: 1.506rem;
            background: url('<%=basePath%>newStyle/images/qiandao_picture1.png');
            background-size: 1.263rem, 1.506rem;
            margin: 0 auto;
        }

        #qiandao .content .top {
            width: 1.263rem;
            height: 1.506rem;
            background-size: 1.263rem, 1.506rem;
            margin: 0 auto;
        }
        .img{
            background: url('<%=basePath%>newStyle/images/qiandao_picture2.png');
        }
        .numb{
            font-family: "Microsoft YaHei", sans-serif;
            font-weight: 600;
            font-size: 2.8rem;
            color:rgb(68,68,68)
        }

    </style>
</head>
<body class="weic weic-body-bgf3f3f3">

<div id="qiandao" @click="changGe" ref="qiandao">
    <div class="content">
        <div class="sign section">本周你已连续签到0天 积分+4</div>
        <footer>
            <ul class="Top">
                <li>
                    <div class="top img">
                        <span class="numb">2</span>
                    </div>
                    <div class="footer">
                        第一天
                    </div>
                </li>
                <li>
                    <div class="top img">
                        <span class="numb">3</span>
                    </div>
                    <div class="footer">
                        第二天
                    </div>
                </li>
                <li>
                    <div class="top img">
                        <span class="numb">4</span>
                    </div>
                    <div class="footer">
                        第三天
                    </div>
                </li>
                <li>
                    <div class="top img">
                        <span class="numb">5</span>
                    </div>
                    <div class="footer">
                        第四天
                    </div>
                </li>
            </ul>
            <ul class="Bom">
                <li>
                    <div class="top img">
                        <span class="numb">6</span>
                    </div>
                    <div class="footer">
                        第五天
                    </div>
                </li>
                <li>
                    <div class="top img">
                        <span class="numb">7</span>
                    </div>
                    <div class="footer">
                        第六天
                    </div>
                </li>
                <li>
                    <div class="top img">
                        <span class="numb">8</span>
                    </div>
                    <div class="footer">
                        第七天
                    </div>
                </li>
            </ul>
        </footer>
    </div>
</div>





<div class="weic-item-padding30 user_index weic-martop-transmit weic-bgfff">
    <a class="weui-cell  weic-relative user_msg" href="javascript:;">
        <div class="weui-cell__hd weic-relative weic-my-head"
             style="background: url('${result.pdBind.imgurl}')no-repeat center center;background-size:100%">
        </div>
        <div class="weui-cell__bd">
            <p class="weic-my-name">Hi，${result.pdBind.name}</p>
            <p class="weic-user-rank">欢迎来到${result.pdBind.store_name}</p>
        </div>
        <%--<div class="weui-cell__ft">
            <span class="sign" id="signIn">已激活</span>
        </div>--%>
    </a>
    <h1 class="weic-codeIdex-num" style="font-size: 0.533rem;padding: 0">当前电脑号：${result.pdQr.serial_number}</h1>
    <p class="weic-user-rate">上网时长：小时/分钟</p>
</div>
<div class="weic-online-time">
    <div class="time-bg">
        <div class="time-bgpadding">
            <div class="weui-row">
                <div class="weui-col-50" style="width: calc((100% - 0.613rem) / 2)">
                    <div class="weui-row">
                        <div class="weui-col-50" style="width: calc((100% - 0.2rem) / 2)">
                            <div class="hour" id="hour_1">

                            </div>
                        </div>
                        <div class="weui-col-50" style="width: calc((100% - 0.2rem) / 2)">
                            <div class="hour" id="hour_2">

                            </div>
                        </div>
                    </div>
                </div>
                <div class="weui-col-50" style="width: calc((100% - 0.613rem) / 2)">
                    <div class="weui-row">
                        <div class="weui-col-50" style="width: calc((100% - 0.2rem) / 2)">
                            <div class="minute" id="minute_1">

                            </div>
                        </div>
                        <div class="weui-col-50" style="width: calc((100% - 0.2rem) / 2)">
                            <div class="minute" id="minute_2">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="weic-item-padding30  weic-bgfff">
    <p class="weic-user-rate" style="text-align: center;color: #999" id="consume_fee">当前已消费：null</p>
</div>
<div style="padding: 0.8rem 2rem" class="weic-bgfff">
    <div class="weic-dent-btn weic-btn-gradientBlue" id="shutdownId">我要下机</div>
</div>
<div class="weic-item-padding30 small weic-martop-transmit weic-bgfff weic-relative" style="margin-top: 0.266rem;padding: 0.133rem 0.4rem" id="swiper">
</div>
<p style="text-align: center;font-size: 0.34rem;color: #999;padding: 0.7rem 0 ">揽客技术支持</p>
</body>

<!--移动端适配，px转化为rem-->
<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-card.js"></script>
<script>
    var store_id = "${result.pdBind.store_id}"
    var computer_name = "${result.pdQr.computer_name.trim()}"
    var up_time = '';

    $("#qiandao").on("click",function(){
        $(this).css("display","none")
    })
    // var num = {store_id:'e7eba56d01194d32814877d65255660a'
    // }
    // var num = {store_id:store_id}
    $.post("<%=basePath%>signmanager/sign.do?store_id="+"${result.pdBind.store_id}", function (data) {
        console.log(data,'111');
        if(data.result_code==1){
        }else{
            $("#qiandao").css("display","block");
            var count=data.signCount;
            var time=data.sign_time_set;
            var time = time.split(",");
            // console.log(time);
            var num=0;
            setTimeout(function(){
                for(var i=0;i<count;i++){
                    num+=parseInt(time[i]);
                    $(".content li").eq(i).find("div").eq(0).addClass("bright");
                    $(".content li").eq(i).find("div").eq(0).removeClass("img");
                }
                for(var i=0;i<time.length;i++){
                    var span=document.querySelectorAll(".numb");
                    // console.log(span.length)
                    span[i].innerHTML=time[i];
                }
                $(".sign").text("本周你已连续签到"+count+"天 积分"+num);
                $("").text();
            },500)
        }


    })
    $(function(){
        FastClick.attach(document.body);
        judeCard();

    });
</script>

<script>
    function judeCard(){//去判断该网吧是否有
        $.ajax({
            type: "POST",
            url: '<%=basePath%>intenetmumber/judgeCard',
            data: '',
            success: function (data) {
                var html ='';
                var url="";
                if(data.result == "true"){
                    url="<%=basePath%>newStyle/weixin/images/internet_banner_small.png";
                    html+="<div class='internet' onclick=openCard('"+data.cardTerm.SUB_TITLE+"','"+data.cardTerm.NOTICE+"','"+data.cardTerm.DESCRIPTION+"','"+data.cardTerm.CONTENT+"','"+data.cardTerm.CARD_ID+"','"+data.cardTerm.BEGIN_TIMESTAMP+"','"+data.cardTerm.END_TIMESTAMP+"','"+data.cardTerm.FIXED_BEGIN_TERM+"','"+data.cardTerm.FIXED_TERM+"','"+data.cardTerm.TYPE+"','"+data.cardTerm.store+"','"+data.cardTerm.cardSum_time+"','<%=basePath%>')>"+
                        "<h1>"+data.cardTerm.card_ad+"</h1><img src='"+url+"' alt='' width='100%'>"+
                        "</div>";
                }
                $("#swiper").html(html);
            },
            error: function (){
            }
        });
    }
</script>

<script type="text/javascript">

    loadUserOnline();
    //获取用户上机信息
    function loadUserOnline() {
        var obj = new Object();
        obj.store_id = store_id;
        obj.computer_name = computer_name;
        open_loading('');

        $.post('<%=basePath%>qrCode/loadUserOnline', obj, function (res) {
            layer.closeAll();
            if (res.result == "true") {
                $("#shutdownId").attr("onclick", "shutdown()");
                var pdUserBoard = res.pdUserBoard;

                $("#consume_fee").html("当前已消费：" + pdUserBoard.consume_fee);

                up_time = pdUserBoard.up_time;
                changeTime(up_time);
            }else{
                alert(res.message);
            }
        });
    }
    //去掉定时器的方法
    // window.clearInterval(t1);
    setInterval(function () {
        changeTime(up_time)
    }, 1000);
    function changeTime(up_time) {
        if(up_time == '')
            return false;

        //一般得到的时间的格式都是：yyyy-MM-dd hh24:mi:ss，所以我就用了这个做例子，是/的格式，就不用replace了。
        var up_date = new Date(up_time.replace(/-/g,"/"));//将字符串转化为时间

        var t1 = new Date().getTime()+"";
        var t2 = up_date.getTime()+"";

        var date3 =  parseInt(t1) - parseInt(t2);  //时间差的毫秒数

        //计算出小时数
        var hours = Math.floor(date3 / (3600 * 1000))

        //计算相差分钟数
        var minutes = Math.floor((date3 - hours*3600*1000) / (60 * 1000));

        if(hours > 1){
            hours = hours - Math.floor(minutes / 60);
        }
        if(hours < 10 && hours >= 0){
            hours = '0' + hours;
        }
        if(minutes < 10){
            minutes = '0' + minutes;
        }
        hours = hours.toString();
        minutes = minutes.toString();


        $("#hour_1").html(hours.substring(0,1));
        $("#hour_2").html(hours.substring(1,2));
        $("#minute_1").html(minutes.substring(0,1));
        $("#minute_2").html(minutes.substring(1,2));
    }

    function shutdown(){
        confirm("是否确定要下机？",function(){
            $.ajax({
                type: "POST",
                url: '<%=basePath%>qrCode/userDown.do',
                data: {store_id:store_id,computer_name:computer_name},
                dataType: 'json',
                cache: false,
                beforeSend: open_loading(''),
                success: function (data) {
                    layer.closeAll();
                    if (data.result == "true") {
                        var html = '<i class="iconfont lk-success-icon">&#xe66a;</i><br>下机成功';
                        open_oneBtn(html,'我知道了',function(){
                            window.location.href = '<%=basePath %>intenetmumber/index';
                        })
                    }else{
                        message(data.message);
                    }
                },
                error: function () {
                    layer.closeAll();
                    message("系统繁忙，请稍后再试！");
                    return false;
                }
            });
        })



    }

</script>
<script>
    // new Vue({
    //     el: '#qiandao',
    //     data: {
    //         msg: '111',
    //         signCount:0,
    //         sign_time_set:'',
    //     },
    //     methods:{
    //         changGe(){
    //          this.$refs.qiandao.style.display="none";
    //         }
    //
    //



    // created:function() {






</script>

</html>