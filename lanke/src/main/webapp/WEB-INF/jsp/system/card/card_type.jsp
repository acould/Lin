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
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/card.css"  media="all">
    <title>选择场景类型</title>
    
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <style>
        
    </style>
</head>
<body style="min-width: 100%;background-color: #fff">
    <div class="card-type-box">
        <form action="" class="layui-form" method="post" id="Form" target="_blank">
        	<input type="hidden" id="FAV_TYPE" name="FAV_TYPE" value="${sceneList[0].DICT_CODE }">
        	<input type="hidden" id="SCENE_NAME" name="SCENE_NAME" value="${sceneList[0].DICT_VALUE }">
        	<c:forEach items="${sceneList}" var="var" varStatus="vs">
	            <div class="layui-form-item card-form-box <c:if test="${vs.index == 0 }">card-radio-avtive</c:if>" name="${var.DICT_CODE }">
	            	<input type="radio" name="sex" value="${var.DICT_VALUE }" title="${var.DICT_VALUE }" <c:if test="${vs.index == 0 }"> checked=""</c:if>>
	            </div>
        	</c:forEach>
        </form>
        <div class="card-msg-box">
            <div class="card-msg-borpd">
            	<div class="card-text" id="CURREN">
                    <p>通用券：</p>
                    <p>适用于自创场景，领取限制由您自行设置，核销无限制。</p>
                </div>
                <div class="card-text" id="NEW">
                    <p>新手券：</p>
                    <p>适用于为网吧微信公众号吸粉</p>
                    <p>领取方式：会员首次关注微信公众号时，公众号自动发送新手券。取关后重新关注的不会发券</p>
                    <p>核销限制：无</p>
                </div>
                <div class="card-text" id="OLD">
                    <p>老带新奖励券：</p>
                    <p>适用于激励老会员带朋友到网吧消费，为网吧带来新会员</p>
                    <p>领取方式： 新会员通过老会员的分享链接获得的新手券，在网吧办理会员卡并成功核销新手券后，微信公众号自动给老会员发送奖励券。如果链接发给了老会员，将不能获得奖励券</p>
                    <p>核销限制：无</p>
                </div>
                <div class="card-text" id="MAN">
                    <p>男神专享券:</p>
                    <p>适用于专门针对男性的营销，以身份证号为准</p>
                    <p>领取方式：券设置好后，会员在“会员福利”页面自行领取</p>
                    <p>领取限制：身份证号码为男性的会员才能领取成功</p>
                    <p>核销限制：身份证号码为男性的会员才能核销成功</p>
                </div>
                <div class="card-text" id="WEM">
                    <p>女神专享券:</p>
                    <p>适用于专门针对女性的营销，以身份证号为准</p>
                    <p>领取方式：券设置好后，会员在“会员福利”页面自行领取</p>
                    <p>领取限制：身份证号码为女性的会员才能领取成功</p>
                    <p>核销限制：身份证号码为女性的会员才能核销成功</p>
                </div>
                <div class="card-text" id="BIRTH">
                    <p>生日专享券：</p>
                    <p>适用于生日当天的会员营销，以身份证号为准</p>
                    <p>领取方式：券设置好后，会员在“会员福利”页面自行领取</p>
                    <p>核销限制：在生日当天（以身份证号码为准）才能核销成功</p>
                </div>
                <div class="card-text" id="GRAB">
                    <p>限时秒抢券：</p>
                    <p>适用于特定节日的营销,促进会员消费</p>
                    <p>领取方式：券设置好后，会员在“会员福利”页面自行抢券</p>
                    <p>核销限制：无</p>
                </div>
                <div class="card-text" id="APPLY">
                    <p>申请会员福利券：</p>
                    <p>适用于在线开通会员</p>
                    <p>领取方式：券设置好后，在在线开通会员中加入,自动推送给新会员</p>
                    <p>核销限制：无</p>
                </div>
                <div class="card-text" id="TERM">
                    <p>上网满时长福利券：</p>
                    <p>适用于上网时长等的促销,促进用户消费</p>
                    <p>领取方式：券设置好后，上网满时长自动推送,最近一周/一月的会员在“会员福利”页面自行领取</p>
                    <p>核销限制：无</p>
                </div>  
            </div>
        </div>
    </div>
</body>
<script>
    layui.use(["form","layer"],function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery;
        
        $(".card-form-box").click(function () {
            $(this).addClass("card-radio-avtive")
            $(this).siblings().removeClass("card-radio-avtive")
            var cardName = $(this).attr("name")
            var cardId = "#" + cardName
            var num = $(cardId).prevAll().length
            var trans = "translateY" + "(" + num*(-355) + "px" + ")"
            $(".card-msg-borpd").css("transform",trans)
            
	    	var code = $(".card-radio-avtive").attr("name");
	    	var value = $(".card-radio-avtive").children(":first-child").val();
	    	$("#FAV_TYPE").val(code);
	    	$("#SCENE_NAME").val(value);
        })
    })
    $(function(){
    	var name = $(".layui-form").children(":first-child").attr("name");
    	var cardId = "#" + name;
    	var num = $(cardId).prevAll().length
        var trans = "translateY" + "(" + num*(-355) + "px" + ")"
        $(".card-msg-borpd").css("transform",trans)
    })
    
    function getScene(){
    	var FAV_TYPE = $("#FAV_TYPE").val()
    	var SCENE_NAME = $("#SCENE_NAME").val();
    	var url = "<%=basePath %>card/goAddCard.do?FAV_TYPE="+FAV_TYPE+"&SCENE_NAME="+SCENE_NAME;
    	var data = {
    		url : url
    	}
    	return data;
    }
    
    function type_search(){
    	parent.location.reload();
    }
    
</script>
</html>