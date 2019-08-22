<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>上网记录</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge" chrome="1">
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <!--移动端适配，px转化为rem-->
    <script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">

</head>
<body class="weic weic-money weic-body-bgf3f3f3 weic-rec">
<div class="weic-navbar">
    <a class="weui-cell  weic-gradient-orange weic-borderNone " href="javascript:;" style="width: 100%;">
        <div class="weui-cell__bd">
            <p class="weic-money-month"><strong><span id="weic-ym">3个月内</span>总明细</strong></p>
            <p class="weic-money-total">总上网时长：
                <sapn id="time"></sapn>
            </p>
            <p class="weic-money-total">
                <sapn>总上网次数：
                    <sapn id='times'></sapn>
                </sapn>
            </p>
            <p class="weic-money-total">总消费：￥<span id="onlineConsume"></span></p>
        </div>
        <div class="weic-cell-ft weic-relative weic-date">
            <input type="text" value="" id="weic-time" class="weui-input">
            <i class="iconfont weic-icon-calendar">&#xe65b;</i>
        </div>
    </a>
</div>
<div style="padding-top: 3.34rem;" id="online-list">
    
    
</div>
</body>


<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-swiper.js"></script>
<script src="http://at.alicdn.com/t/font_622913_aswvqplxm0lik9.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/js/lk_wechat/lk_wechat.js"></script>
<script>
    $(document).on("click", ".show-alert", function () {
        $.alert("程序员大哥正在飞速开发中，敬请期待！");
    });



	var time2 = "";
    var card_id2 = "${card_id}";
    
    
    var date = new Date;
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var month1 = date.getMonth()
    var month2 = date.getMonth() - 1
    month = (month < 10 ?  month : month);
    month1 = (month1 < 10 ?  month1 : month1);
    month2 = (month2 < 10 ?  month2 : month2);
    $("#weic-time").val(month)
    $("#weic-time").picker({
        title: '当前只能查询最近三个月',
        cols: [
            {textAlign: 'center', values: [month2, month1, month],}
        ],
        onClose: function (p, v, dv) {
            var str = $("#weic-time").val()
            str = str.replace(/\s+/g, "");
            $("#weic-ym").text(str+"月")
            
            time2 = year+"-"+str;
            //AJAX进行搜索
            search();
        }
    });
	$(function(){
		search();
	})
    
    //换算时间
    function toString (mins) {
    	var stringTime = '';
    	if (mins < 60) {
            stringTime = mins + '分钟';
    	} else {
            var minString = mins%60 ? ( mins%60 + '分钟') : '';
            var hour = parseInt(mins/60);

            if (hour < 24) {
                stringTime = hour + '小时' +  minString;
            } else {
                var dayString = parseInt(hour/24);
                var hourString = hour%24 ? ( hour%24 + '小时') : '';
                stringTime = dayString + '天' + hourString +  minString;
            }
        }
		return stringTime;
    }
    
   
    function search() {
		//获取上网记录
		$.ajax({
			type: "POST",
			url :'<%=basePath%>myMember/userOnline.do',
			data: {card_id:card_id2,up_time:time2,},
			dataType:'json',
			async: false,
			beforeSend: beforeSend,
			success: function(data){
				layer.closeAll();
				var onlineList = data.list;
				if (data.time == null){
					$("#time").text("0 分钟");
				}else{
					$("#time").html(toString(data.time.toString()));
				}
				if (data.times == null){
					$("#times").text("0 次");
				}else{
					$("#times").text(data.times+"次");
				}
				if (data.money == null){
					$("#onlineConsume").text("0");
				}else{
					$("#onlineConsume").text(data.money);
				}

				var htmls='';
				if(onlineList.length==0){
					htmls +='<div class="weic-noboby">'+
                    '<div class="weic-nobody-img"></div>'+
                    '<p class="weic-nobody-text">暂无数据</p>'+
                    '</div>'
					$("#online-list").html(htmls);
				}else{
					 for (var i = 0; i < onlineList.length; i++) {
							var a =onlineList[i];
							htmls+='<div class="weui-cells">'+
										'<a class="weui-cell  weic-relative  weic-bgfff" href="javascript:;">'+
											'<div class="weui-cell__bd">'+
												'<p class="weic-rec-md">'+a.store_name+'</p>'+
												'<p class="weic-rec-timebox">上机时间：<span class="weic-rec-time">'+a.up_time+'</span></p>'+
												'<p class="weic-rec-timebox">下机时间：<span class="weic-rec-time">'+a.down_time+'</span></p>'+
												'<p class="weic-rec-timebox">上机时长：<span class="weic-rec-time">'+a.up_duration+'分钟</span></p>'+
											'</div>'+
											'<div class="weic-cell-ft">'+
												'<span class="weic-money-sum  weic-rec-sum">-'+a.consume_fee+'</span>'+
											'</div>'+
										'</a>'+
									'</div>'
							$("#online-list").html(htmls);
						}
				}
			},
			error:function(jqXHR, textStatus, errorThrown){
				layer.closeAll();
				console.log(jqXHR + "---" +textStatus + "---" +errorThrown,{time:1500});
				message("系统繁忙，请稍后再试！");
				return;
			}
		}); 
	}
    
    function beforeSend(){
		  open_loading("加载中")
	}
</script>
</html>