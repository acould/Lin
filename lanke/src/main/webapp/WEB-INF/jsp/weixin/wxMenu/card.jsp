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
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <title>卡券库</title>
    <style>
    	body {background: #f3f3f3;padding:20px;}
        .cardBox {background: #fff;border-radius: 4px;overflow: hidden;position: relative;margin-bottom: 24px;box-shadow: 0px 4px 20px #e2e2e2;}
        .cardBox p { margin: 6px 0!important;color:#666!important; padding:0  15px;}
        .alier {background: rgba(0,0,0,0.7);width: 100%;height: 100%;position: absolute;top: 0;bottom: 0;left: 0;right: 0;display: none;}
        .lay-iconAuto { width: 40px;height: 43px;margin: auto;position: absolute;top: 0; left: 0; bottom: 0; right: 0;display: none;}
        .cardBox .block {display: inline-block;}
        .adrss,.xiangqing,.time {
			overflow: hidden;
			text-overflow:ellipsis;
			white-space:nowrap;
        }
        .cardBox:hover .lay-iconAuto, .cardBox:hover .alier {display: inline-block;}
        .lay-iconStyle {color:#fff;font-size: 40px;cursor: pointer;}
        .selected {
            animation:        selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
            -o-animation:     selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
            -ms-animation:    selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
            -moz-animation:   selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
            -webkit-animation: selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
        }
        .selected .lay-iconStyle{color:#00c09e;}
        @keyframes selected {
            0% { border-color: #fff; }
            50% { transform: scale(0.5); opacity: 0.8;}
            80%,100% {width: 40px;height: 43px;}
        }
        @-o-keyframes selected {
            0% { box-shadow: 0 0 0 4px #fff; }
            50% { -o-transform: scale(0.5); opacity: 0.8; }
            80%,100% {width: 40px;height: 43px;}
        }
        @-ms-keyframes selected {
            0% { box-shadow: 0 0 0 4px #fff; }
            50% {width: 40px;height: 43px; }
            80%,100% { width: 40px;height: 43px; }
        }
        @-moz-transition selected {
        0% { box-shadow: 0 0 0 4px #fff; }
        50% { -moz-transform: scale(0.5); opacity: 0.8; }
        80%,100% { width: 40px;height: 43px; }
        }
        @-webkit-keyframes selected {
            0% { box-shadow: 0 0 0 4px #fff; }
            50% { -webkit-transform: scale(0.5); opacity: 0.8;}
            80%,100% { width: 40px;height: 43px;}
        }
        .blue { background-color:#1E9FFF;}
        .red {background-color:#e63d73;}
        .Color010 {color: #63b359;}
        .Color020 {color: #2c9f67;}
        .Color030 {color: #509fc9;}
        .Color040 {color: #5885cf;}
        .Color050 {color: #9062c0;}
        .Color060 {color: #d09a45;}
        .Color070 {color: #e4b138;}
        .Color080 {color: #ee903c;}
        .Color090 {color: #dd6549;}
        .Color100 {color: #cc463d;}
        .card-det {
        	padding:10px 0;
        }
    </style>
</head>
<body class="scroll">
<input type="hidden" id="store_id" value="${pd.store_id}">
    <div class="container-fluid">
        <div class="row">
        	<c:choose>
	        	<c:when test="${not empty cardList }">
		        	<c:forEach items="${cardList }" var="var">
			            <div class="col-md-4 col-xs-6 ">
			            	<input id="${var.CARD_ID }" type="hidden" value="${var.CARD_ID }">
			                <div class="cardBox">
							<div id="hh">
			                    <h1 style="font-size: 16px;border-bottom: 1px solid #eee;font-weight: 600;padding: 14px 15px" class="title <c:if test="${var.COLOR == 'Color010' }">Color010</c:if><c:if test="${var.COLOR == 'Color020' }">Color020</c:if><c:if test="${var.COLOR == 'Color030' }">Color030</c:if><c:if test="${var.COLOR == 'Color040' }">Color040</c:if><c:if test="${var.COLOR == 'Color050' }">Color050</c:if><c:if test="${var.COLOR == 'Color060' }">Color060</c:if><c:if test="${var.COLOR == 'Color070' }">Color070</c:if><c:if test="${var.COLOR == 'Color080' }">Color080</c:if><c:if test="${var.COLOR == 'Color090' }">Color090</c:if><c:if test="${var.COLOR == 'Color100' }">Color100</c:if>" >${var.SUB_TITLE }</h1>
							</div>			                 
							 <div class="card-det" id="carddet">
			                    	<p class="time" id="time"><span style="color:#333">有效期：</span>${var.AVAILABLE_TIME }</p>
			                    	<p class="adrss" id="adrss"><span style="color:#333">使用门店：</span>${var.STORE_NAME }</p>
			                    	<p class="xiangqing" id="xiangqing"><span style="color:#333">使用说明：</span>${var.DESCRIPTION }</p>
			                    </div>
			                    <div class="alier"></div>
			                    <div class="lay-iconAuto">
			                        <i class="layui-icon lay-iconStyle">&#x1005;</i>
			                    </div>
			                </div>
			            </div>
		        	</c:forEach>
	        	</c:when>
	        	<c:otherwise>
				<c:if test="${pd.falges =='1'}">
					<div style="text-align: center;padding-top: 50px; color: blue">没有适用的卡券,请前往卡券设置功能设置需要的卡券</div>
				</c:if>
				<c:if test="${pd.falges != '1'}">
	        	<c:if test="${pd.card_id ==''}">
	        	    <div style="text-align: center;padding-top: 50px; color: blue">没有适用该门店的卡券,请前往卡券设置功能设置需要的卡券</div>
	        	</c:if>
	        	<c:if test="${pd.card_id !=''}">
	        	    <div style="text-align: center;padding-top: 50px; color: blue">该类卡券不能发送给粉丝</div>
	        	</c:if>
				</c:if>
	        	</c:otherwise>
        	</c:choose>
        </div>

    </div>
    <script>
        $('.cardBox').click(function () {
            var isthis = $(this).children(":last-child");
            isthis.toggleClass("selected block");
            isthis.prev().toggleClass("block");
            var anther = $(this).parent().siblings().children().children(":last-child");
            anther.removeClass("selected block");
            anther.prev().removeClass("block");
        })
        var callCard = function () {
            var cardbox = $(".selected").parent();
            var title = cardbox.children(".title").html();
            var titleClass = cardbox.children(".title").attr("class");
            var time = cardbox.children(".time").html();
            var adrss = cardbox.children(".adrss").html();
            var xiangqing = cardbox.children(".xiangqing").html();
            var cardId = cardbox.prev().val();
            var store_id=$("#store_id").val();
			var carddet=cardbox.children("#carddet").html();
			var hh = cardbox.children("#hh").html();
            var data = {
                title: title,
                titleClass: titleClass,
                time: time,
                adrss: adrss,
                xiangqing: xiangqing,
                cardId : cardId,
				carddet:carddet,
				hh:hh,
                store_id:store_id
            };
			
            return data;
        }
    </script>
</body>
</html>