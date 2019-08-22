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
    <title>${internetName}</title>
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
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
    <style>

    </style>
</head>
<body class="weic weic-body-bgf3f3f3">
<div class="weic-item-padding30 user_index weic-martop-transmit weic-bgfff">
    <a class="weui-cell  weic-relative user_msg" href="javascript:;">
        <div class="weui-cell__hd weic-relative weic-my-head"
             style="background: url('${result.pdBind.imgurl}')no-repeat center center;background-size:100%">
        </div>
        <div class="weui-cell__bd">
            <p class="weic-my-name">Hi，
                <c:if test="${result.pdBind.name != null}">${result.pdBind.name}</c:if>
                <c:if test="${result.pdBind.name == null}">${result.pdBind.neck_name}</c:if></p>
            <p class="weic-user-rank">欢迎来到${result.pdBind.store_name}</p>
        </div>
        <%--<div class="weui-cell__ft">
            <span class="sign" id="signIn">已激活</span>
        </div>--%>
    </a>
    <h1 class="weic-codeIdex-num" style="font-size: 0.533rem;padding: 0">当前电脑号：${result.pdQr.serial_number}</h1>
    <%--<p class="weic-user-rate">每小时费率：10元</p>--%>
    <%--<c:if test="${result.pdQr.state == '2'}">
        <div class="user_up_btn" onclick="go_user_up()"></div>
        <p class="user_up_text">立即上机</p>
    </c:if>
    <c:if test="${result.pdQr.state == '3'}">
        <div class="user_change_btn" onclick="go_user_up()"></div>
        <p class="user_up_text">立即换机</p>
    </c:if>--%>
</div>
<form id="Form">
    <input id="computer_name" name="computer_name" type="hidden" value="${result.pdQr.computer_name.trim()}">
    <input id="store_id" name="store_id" type="hidden" value="${result.pdBind.store_id}">
    <input id="psd" name="psd" type="hidden" value="">
    <input id="state" name="state" type="hidden" value="${result.pdQr.state}">

    <input id="serial_number" name="serial_number" type="hidden" value="${result.pdQr.serial_number}">
    <input id="imgurl" name="imgurl" type="hidden" value="${result.pdBind.imgurl}">
    <input id="name" name="name" type="hidden" value="${result.pdBind.name}">
    <input id="store_name" name="store_name" type="hidden" value="${result.pdBind.store_name}">
</form>
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
    function go_user_up() {
        if(!check()){
            return false;
        }
        $.ajax({
            type: "POST",
            url: '<%=basePath%>qrCode/userUp.do',
            data: $('#Form').serialize(),
            dataType: 'json',
            cache: false,
            beforeSend: open_loading(''),
            success: function (data) {
                layer.closeAll();
                message(data.message);
                if (data.result == "true") {
                    // 正常上机
                    setTimeout(function () {
                        $("#state").val("1");//上机
                        $("#Form").attr("action","<%=basePath%>qrCode/goUserDown.do");
                        $("#Form").submit();
                        $("#Form").removeAttr("action");
                    }, 300)
                }else if (data.result == "false" && data.err_type == "psd_err"){
                    // 输入密码
                    var html = '<p class="passwrodTile" id="passwrodTile">请输入密码：</p>'+
                        '<div class="passwrodInput"><input type="password" id="password" autofocus="autofocus" placeholder="请输入密码"><p id="passwrodEorre">密码错误</p></div>';
                    open_oneBtn(html,'确定',function(){
                        window.scrollTo(0,0);
                        $("#psd").val($("#password").val());
                        // $("#passwrodEorre").html("密码错误!")
                        go_user_up();
                    })

                }else if (data.result == "false" && data.err_type == "qrr_used_err"){
                    // 电脑已被占用
                    var html = '<i class="up_tips occupy"></i><br><br>该电脑已被上机，无法操作';
                    open_oneBtn(html,'我知道了',function(){
                        layer.closeAll()
                    })
                }else if (data.result == "false" && data.err_type == "qrr_baned_err"){
                    // 电脑已被占用
                    var html = '<i class="up_tips occupy"></i><br><br>该电脑已被禁用，无法操作';
                    open_oneBtn(html,'我知道了',function(){
                        layer.closeAll()
                    })
                }else if (data.result == "false" && data.err_type == "lanke_client_stop_err"){
                    //电脑被禁用或计费系统到期
                    var html = '<i class="up_tips forbidden"></i><br><br>揽客客户端连接已断开，暂<br>时无法扫码上机';
                    open_oneBtn(html,'我知道了',function(){
                        layer.closeAll()
                    })
                }else if (data.result == "false" && data.err_type == "card_unactivated_err"){
                    //会员卡未激活
                    var html = '<i class="up_tips activate"></i><br><br>根据公安要求，请先到网吧收银台<br>激活会员卡';
                    open_oneBtn(html,'我知道了',function(){
                        layer.closeAll()
                    })
                }else{
                    if(data.message.indexOf("密码不正确") >= 0){
                            // 输入密码
                        var html = '<p class="passwrodTile" id="passwrodTile">请输入密码：</p>'+
                            '<div class="passwrodInput"><input type="password" id="password" autofocus="autofocus" placeholder="请输入密码"><p id="passwrodEorre">密码错误</p></div>';
                        open_oneBtn(html,'确定',function(){
                            window.scrollTo(0,0);
                            $("#psd").val($("#password").val());
                            // $("#passwrodEorre").html("密码错误!")
                            go_user_up();
                        })
                    }else{
                        //其他信息
                        var html = '<i class="up_tips activate"></i><br><br>'+data.message;
                        open_oneBtn(html,'我知道了',function(){
                            layer.closeAll()
                        })
                    }

                }
                //会员卡余额不足
                /*var html = '<i class="up_tips no_money"></i><br><br>根基公安要求，请先到网吧收银台<br>激活会员卡';
                open_oneBtn(html,'我知道了',function(){
                    layer.closeAll()
                })*/
            },
            error: function () {
                layer.closeAll();
                message("系统繁忙，请稍后再试！");
                return false;
            }
        });


    }

    function cleared(){
        $("#passwrodEorre").html(" ");
    }

    function check() {
        if($("#computer_name").val()=="" || $("#store_id").val()==""){
            message("数据加载异常");
            return false;
        }
        return true;
    }

    //从客户机扫描二维码进入的页面时,判断用户时上机还是换机
    var store_id = "${result.pdBind.store_id}"
    var computer_name = "${result.pdQr.computer_name.trim()}"
    loadUserUp();
    function loadUserUp() {
        var obj = new Object();
        obj.store_id = store_id;
        obj.computer_name = computer_name;
        open_loading('');
        $.post('<%=basePath%>qrCode/loadUserOnline', obj, function (res) {
            layer.closeAll();
            if (res.result == "true") {
                var ht = '<div class="user_change_btn" onclick="go_user_up()"></div><p class="user_up_text">立即换机</p>';
                $(".weic-codeIdex-num").after(ht);
                $("#state").val("3");
            }else{
                var ht = '<div class="user_up_btn" onclick="go_user_up()"></div><p class="user_up_text">立即上机</p>';
                $(".weic-codeIdex-num").after(ht);
                $("#state").val("2");
            }
        });


    }


</script>
</html>